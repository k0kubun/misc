/**********************************************************************

  jit.c - Method JIT compiler

  Copyright (C) 2017 Vladimir Makarov <vmakarov@redhat.com>.
  Copyright (C) 2017 Takashi Kokubun

**********************************************************************/

#include <unistd.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <dlfcn.h>
#include "internal.h"
#include "vm_core.h"
#include "vm_exec.h"
#include "insns.inc"
#include "insns_info.inc"
#include "jit.h"

/* If "-j" is specified, this becomes TRUE */
int jit_enabled = FALSE;

/* Total count of scheduled ISeqs to generate unique identifier */
static unsigned long jit_scheduled_iseqs;

static void
execute_command(const char *path, char *const argv[])
{
    int stat;
    pid_t pid;

    if ((pid = vfork()) == 0) {
	pid = execvp(path, argv); /* child process executes command */
	fprintf(stderr, "execvp failed (%d): %s\n", pid, path);
	_exit(1);
    }

    waitpid(pid, &stat, 0);
    /* TODO: check stat */
}

static void
compile_c_to_so(const char *c_fname, const char *so_fname)
{
    char **argv;
    static int common_argv_len = 13;
    static const char *common_argv[] = {
        "gcc", "-O2", "-fPIC", "-shared", "-Wfatal-errors", "-w",
        "-I./include", "-I./.ext/include/x86_64-linux", /* TODO: fix -I options */
        "-pipe", "-nostartfiles", "-nodefaultlibs", "-nostdlib", "-o"
    };
    //static int common_argv_len = 11;
    //static const char *common_argv[] = {
    //    "clang", "-O2", "-fPIC", "-shared", "-I/usr/local/include", "-L/usr/local/lib",
    //    "-I./include", "-I./.ext/include/x86_64-linux", /* TODO: fix -I options */
    //    "-w", "-bundle", "-o"
    //};
    const char *dynamic_argv[] = { so_fname, c_fname, NULL };

    argv = xmalloc((common_argv_len + 3) * sizeof(char *));
    memmove(argv, common_argv, common_argv_len * sizeof(char *));
    memmove(argv + common_argv_len, dynamic_argv, 3 * sizeof(char *));

    execute_command("gcc", argv);
    //execute_command("clang", argv);
    xfree(argv);
}

static void *
get_func_ptr(const char *so_fname, const char *funcname)
{
    void *handle, *func_ptr;

    handle = dlopen(so_fname, RTLD_NOW);
    if (handle == NULL) {
	fprintf(stderr, "  => Failed to open so: %s\n", dlerror()); /* debug */
	return (void *)NOT_ADDED_JIT_ISEQ_FUNC;
    }

    func_ptr = dlsym(handle, funcname);
    /* TODO: dlclose(handle); on deoptimization */

    return func_ptr;
}

static void
fprint_getlocal(FILE *f, unsigned int push_pos, lindex_t idx, rb_num_t level)
{
    /* COLLECT_USAGE_REGISTER_HELPER is necessary? */
    fprintf(f, "  stack[%d] = *(vm_get_ep(cfp->ep, 0x%"PRIxVALUE") - 0x%"PRIxVALUE");\n", push_pos, level, idx);
    fprintf(f, "  RB_DEBUG_COUNTER_INC(lvar_get);\n");
    if (level > 0) {
	fprintf(f, "  RB_DEBUG_COUNTER_INC(lvar_get_dynamic);\n");
    }
}

static void
fprint_setlocal(FILE *f, unsigned int pop_pos, lindex_t idx, rb_num_t level)
{
    /* COLLECT_USAGE_REGISTER_HELPER is necessary? */
    fprintf(f, "  vm_env_write(vm_get_ep(cfp->ep, 0x%"PRIxVALUE"), -(int)0x%"PRIxVALUE", stack[%d]);\n", level, idx, pop_pos);
    fprintf(f, "  RB_DEBUG_COUNTER_INC(lvar_set);\n");
    if (level > 0) {
	fprintf(f, "  RB_DEBUG_COUNTER_INC(lvar_set_dynamic);\n");
    }
}

static void compile_insns(const struct rb_iseq_constant_body *body, FILE *f, unsigned int stack_size, unsigned int pos, bool *compiled_for_pos, bool *succeeded_ptr);

struct case_dispatch_var {
    FILE *f;
    unsigned int base_pos;
};

static int
compile_case_dispatch_each(VALUE key, VALUE value, VALUE arg)
{
    struct case_dispatch_var *var = (struct case_dispatch_var *)arg;
    unsigned int offset = FIX2INT(value);

    fprintf(var->f, "    case %d:\n", offset);
    fprintf(var->f, "      goto label_%d;\n", var->base_pos + offset);
    fprintf(var->f, "      break;\n");
    return ST_CONTINUE;
}

/* push back stack in local variable to YARV's stack pointer */
static void
fprint_args(FILE *f, unsigned int argc, unsigned int pos)
{
    if (argc) {
	unsigned int i;
	/* TODO: use memmove or memcopy, if not optimized by compiler */
	for (i = 0; i < argc; i++) {
	    fprintf(f, "    *(cfp->sp) = stack[%d];\n", pos + i);
	    fprintf(f, "    cfp->sp++;\n");
	}
    }
}

/* Compiles CALL_METHOD macro to f. `calling` should be already defined in `f`. */
static void
fprint_call_method(FILE *f, VALUE ci, VALUE cc, unsigned int result_pos)
{
    fprintf(f, "    {\n");
    fprintf(f, "      VALUE v = (*((CALL_CACHE)0x%"PRIxVALUE")->call)(th, cfp, &calling, 0x%"PRIxVALUE", 0x%"PRIxVALUE");\n", cc, ci, cc);
    fprintf(f, "      if (v == Qundef) {\n"); /* TODO: also call jit_exec */
    fprintf(f, "        VM_ENV_FLAGS_SET(th->ec.cfp->ep, VM_FRAME_FLAG_FINISH);\n"); /* This is vm_call0_body's code after vm_call_iseq_setup */
    fprintf(f, "        stack[%d] = vm_exec(th);\n", result_pos);
    fprintf(f, "      } else {\n");
    fprintf(f, "        stack[%d] = v;\n", result_pos);
    fprintf(f, "      }\n");
    fprintf(f, "    }\n");
}

static void
fprint_opt_call_variables(FILE *f, unsigned int stack_size, unsigned int argc)
{
    fprintf(f, "    VALUE recv = stack[%d];\n", stack_size - argc);
    if (argc >= 2) {
	fprintf(f, "    VALUE obj = stack[%d];\n", stack_size - (argc - 1));
    }
    if (argc >= 3) {
	fprintf(f, "    VALUE obj2 = stack[%d];\n", stack_size - (argc - 2));
    }
}

static void
fprint_opt_call_fallback(FILE *f, VALUE ci, VALUE cc, unsigned int stack_size, unsigned int argc, VALUE key)
{
    fprintf(f, "    if (stack[%d] == Qundef) {\n", stack_size - argc);
    fprintf(f, "      struct rb_calling_info calling;\n");
    fprintf(f, "      calling.block_handler = VM_BLOCK_HANDLER_NONE;\n");
    fprintf(f, "      calling.argc = %d;\n", key ? argc : argc - 1);
    fprintf(f, "      vm_search_method(0x%"PRIxVALUE", 0x%"PRIxVALUE", calling.recv = recv);\n", ci, cc);
    fprintf(f, "      *(cfp->sp++) = recv;\n");
    if (key) {
	fprintf(f, "      *(cfp->sp++) = rb_str_resurrect(0x%"PRIxVALUE");\n", key);
	if (argc >= 2) {
	    fprintf(f, "      *(cfp->sp++) = obj;\n");
	}
    } else {
	if (argc >= 2) {
	    fprintf(f, "      *(cfp->sp++) = obj;\n");
	}
	if (argc >= 3) {
	    fprintf(f, "      *(cfp->sp++) = obj2;\n");
	}
    }
    fprint_call_method(f, ci, cc, stack_size - argc);
    fprintf(f, "    }\n");
}

/* Print optimized call with redefinition fallback and return stack size change.
   `format` should call function with `recv`, `obj` and `obj2` depending on `argc`. */
PRINTF_ARGS(static int, 6, 7)
fprint_opt_call(FILE *f, VALUE ci, VALUE cc, unsigned int stack_size, unsigned int argc, const char *format, ...)
{
    va_list va;

    fprintf(f, "  {\n");
    fprint_opt_call_variables(f, stack_size, argc);

    fprintf(f, "    stack[%d] = ", stack_size - argc);
    va_start(va, format);
    vfprintf(f, format, va);
    va_end(va);
    fprintf(f, ";\n");

    fprint_opt_call_fallback(f, ci, cc, stack_size, argc, (VALUE)0);
    fprintf(f, "  }\n");

    return 1 - argc;
}

/* Same as `fprint_opt_call`, but `key` will be `rb_str_resurrect`ed and pushed. */
PRINTF_ARGS(static int, 7, 8)
fprint_opt_call_with_key(FILE *f, VALUE ci, VALUE cc, VALUE key, unsigned int stack_size, unsigned int argc, const char *format, ...)
{
    va_list va;

    fprintf(f, "  {\n");
    fprint_opt_call_variables(f, stack_size, argc);

    fprintf(f, "    stack[%d] = ", stack_size - argc);
    va_start(va, format);
    vfprintf(f, format, va);
    va_end(va);
    fprintf(f, ";\n");

    fprint_opt_call_fallback(f, ci, cc, stack_size, argc, key);
    fprintf(f, "  }\n");

    return 1 - argc;
}

/* To let catch table work, this function moves program counter. */
static void
fprint_set_pc(FILE *f, const int insn, const VALUE *pc)
{
    switch (insn) {
      /* List of safe insns that do NOT throw exception, to skip moving pc for optimization.
	 Basically, any insn that may call method would raise exception in child control frame.
	 For future maintainability, this list is built as whitelist of optimizable insns. */
      case YARVINSN_nop:
      case YARVINSN_getlocal:
      case YARVINSN_setlocal:
      case YARVINSN_getspecial:
      case YARVINSN_setspecial:
      case YARVINSN_getinstancevariable:
      case YARVINSN_setinstancevariable:
      case YARVINSN_getclassvariable:
      case YARVINSN_setclassvariable:
      case YARVINSN_getconstant:
      case YARVINSN_setconstant:
      case YARVINSN_getglobal:
      case YARVINSN_setglobal:
      case YARVINSN_putnil:
      case YARVINSN_putself:
      case YARVINSN_putobject:
      case YARVINSN_putspecialobject:
      case YARVINSN_putiseq:
      case YARVINSN_putstring:
      case YARVINSN_concatstrings:
      case YARVINSN_freezestring:
      case YARVINSN_toregexp:
      case YARVINSN_intern:
      case YARVINSN_newarray:
      case YARVINSN_duparray:
      case YARVINSN_expandarray:
      case YARVINSN_concatarray:
      case YARVINSN_splatarray:
      case YARVINSN_newhash:
      case YARVINSN_newrange:
      case YARVINSN_pop:
      case YARVINSN_dup:
      case YARVINSN_dupn:
      case YARVINSN_swap:
      case YARVINSN_reverse:
      case YARVINSN_reput:
      case YARVINSN_topn:
      case YARVINSN_setn:
      case YARVINSN_adjuststack:
      case YARVINSN_defined:
      case YARVINSN_trace:
      case YARVINSN_trace2:
      case YARVINSN_leave:
      case YARVINSN_jump:
      case YARVINSN_branchif:
      case YARVINSN_branchunless:
      case YARVINSN_branchnil:
      case YARVINSN_branchiftype:
      case YARVINSN_getinlinecache:
      case YARVINSN_setinlinecache:
      case YARVINSN_answer:
      case YARVINSN_getlocal_OP__WC__0:
      case YARVINSN_getlocal_OP__WC__1:
      case YARVINSN_setlocal_OP__WC__0:
      case YARVINSN_setlocal_OP__WC__1:
      case YARVINSN_putobject_OP_INT2FIX_O_0_C_:
      case YARVINSN_putobject_OP_INT2FIX_O_1_C_:
        return;
      default:
	break;
    }
    fprintf(f, "  cfp->pc = (VALUE *)0x%"PRIxVALUE";\n", (VALUE)pc);
}

/* Compiles insn to f, may modify stack_size_ptr and returns next position */
static unsigned int
compile_insn(const struct rb_iseq_constant_body *body, FILE *f, unsigned int *stack_size_ptr, const int insn, const VALUE *operands, unsigned int pos, bool *compiled_for_pos, bool *succeeded_ptr)
{
    unsigned int stack_size = *stack_size_ptr;
    unsigned int next_pos = pos + insn_len(insn);
    bool jumped = false; /* set true if `goto` or `return` is set in the end of compiled code */

    fprint_set_pc(f, insn, body->iseq_encoded + pos);
    switch (insn) {
      case YARVINSN_nop:
	/* nop */
        break;
      case YARVINSN_getlocal:
	fprint_getlocal(f, stack_size++, operands[0], operands[1]);
        break;
      case YARVINSN_setlocal:
	fprint_setlocal(f, --stack_size, operands[0], operands[1]);
        break;
      case YARVINSN_getspecial:
	fprintf(f, "  stack[%d] = vm_getspecial(th, VM_EP_LEP(cfp->ep), 0x%"PRIxVALUE", 0x%"PRIxVALUE");\n", stack_size++, operands[0], operands[1]);
        break;
      //case YARVINSN_setspecial: /* somehow SEGVs on test... */
      //  fprintf(f, "  lep_svar_set(th, VM_EP_LEP(cfp->ep), 0x%"PRIxVALUE", stack[%d]);\n", operands[0], --stack_size);
      //  break;
      case YARVINSN_getinstancevariable:
	fprintf(f, "  stack[%d] = vm_getinstancevariable(cfp->self, 0x%"PRIxVALUE", 0x%"PRIxVALUE");\n", stack_size++, operands[0], operands[1]);
        break;
      case YARVINSN_setinstancevariable:
	fprintf(f, "  vm_setinstancevariable(cfp->self, 0x%"PRIxVALUE", stack[%d], 0x%"PRIxVALUE");\n", operands[0], --stack_size, operands[1]);
        break;
      case YARVINSN_getclassvariable:
	fprintf(f, "  stack[%d] = rb_cvar_get(vm_get_cvar_base(rb_vm_get_cref(cfp->ep), cfp), 0x%"PRIxVALUE");\n", stack_size++, operands[0]);
        break;
      case YARVINSN_setclassvariable:
	fprintf(f, "  vm_ensure_not_refinement_module(cfp->self);\n");
	fprintf(f, "  rb_cvar_set(vm_get_cvar_base(rb_vm_get_cref(cfp->ep), cfp), 0x%"PRIxVALUE", stack[%d]);\n", operands[0], --stack_size);
        break;
      case YARVINSN_getconstant:
	fprintf(f, "  stack[%d] = vm_get_ev_const(th, stack[%d], 0x%"PRIxVALUE", 0);\n", stack_size-1, stack_size-1, operands[0]);
        break;
      case YARVINSN_setconstant:
	fprintf(f, "  vm_check_if_namespace(stack[%d]);\n", stack_size-2);
	fprintf(f, "  vm_ensure_not_refinement_module(cfp->self);\n");
	fprintf(f, "  rb_const_set(stack[%d], 0x%"PRIxVALUE", stack[%d]);\n", stack_size-2, operands[0], stack_size-1);
        break;
      case YARVINSN_getglobal:
	fprintf(f, "  stack[%d] = GET_GLOBAL((VALUE)0x%"PRIxVALUE");\n", stack_size++, operands[0]);
        break;
      case YARVINSN_setglobal:
	fprintf(f, "  SET_GLOBAL((VALUE)0x%"PRIxVALUE", stack[%d]);\n", operands[0], --stack_size);
        break;
      case YARVINSN_putnil:
	fprintf(f, "  stack[%d] = Qnil;\n", stack_size++);
        break;
      case YARVINSN_putself:
	fprintf(f, "  stack[%d] = cfp->self;\n", stack_size++);
        break;
      case YARVINSN_putobject:
	fprintf(f, "  stack[%d] = (VALUE)0x%"PRIxVALUE";\n", stack_size++, operands[0]);
        break;
      case YARVINSN_putspecialobject:
	fprintf(f, "  stack[%d] = vm_get_special_object(cfp->ep, (enum vm_special_object_type)0x%"PRIxVALUE");\n", stack_size++, operands[0]);
        break;
      case YARVINSN_putiseq:
	fprintf(f, "  stack[%d] = (VALUE)0x%"PRIxVALUE";\n", stack_size++, operands[0]);
        break;
      case YARVINSN_putstring:
	fprintf(f, "  stack[%d] = rb_str_resurrect(0x%"PRIxVALUE");\n", stack_size++, operands[0]);
        break;
      case YARVINSN_concatstrings:
	fprintf(f, "  stack[%d] = rb_str_concat_literals(0x%"PRIxVALUE", stack + %d);\n",
		stack_size - (unsigned int)operands[0], operands[0], stack_size - (unsigned int)operands[0]);
	stack_size += 1 - (unsigned int)operands[0];
        break;
      case YARVINSN_tostring:
	fprintf(f, "  stack[%d] = rb_obj_as_string_result(stack[%d], stack[%d]);\n", stack_size-2, stack_size-1, stack_size-2);
	stack_size--;
        break;
      case YARVINSN_freezestring:
	fprintf(f, "  vm_freezestring(stack[%d], 0x%"PRIxVALUE");\n", stack_size-1, operands[0]);
        break;
      case YARVINSN_toregexp:
	fprintf(f, "  {\n");
	fprintf(f, "    VALUE rb_reg_new_ary(VALUE ary, int options);\n");
        fprintf(f, "    VALUE rb_ary_tmp_new_from_values(VALUE, long, const VALUE *);\n");
	fprintf(f, "    const VALUE ary = rb_ary_tmp_new_from_values(0, 0x%"PRIxVALUE", stack + %d);\n", operands[1], stack_size - (unsigned int)operands[1]);
	fprintf(f, "    stack[%d] = rb_reg_new_ary(ary, (int)0x%"PRIxVALUE");\n", stack_size - (unsigned int)operands[1], operands[0]);
	fprintf(f, "    rb_ary_clear(ary);\n");
	fprintf(f, "  }\n");
	stack_size += 1 - (unsigned int)operands[1];
        break;
      case YARVINSN_intern:
	fprintf(f, "  stack[%d] = rb_str_intern(stack[%d]);\n", stack_size-1, stack_size-1);
        break;
      case YARVINSN_newarray:
	fprintf(f, "  stack[%d] = rb_ary_new4(0x%"PRIxVALUE", stack + %d);\n",
		stack_size - (unsigned int)operands[0], operands[0], stack_size - (unsigned int)operands[0]);
	stack_size += 1 - (unsigned int)operands[0];
        break;
      case YARVINSN_duparray:
	fprintf(f, "  stack[%d] = rb_ary_resurrect(0x%"PRIxVALUE");\n", stack_size++, operands[0]);
        break;
      case YARVINSN_expandarray:
	{
	    unsigned int i, space_size;
	    space_size = (unsigned int)operands[0] + (unsigned int)((int)operands[1] & 0x01);

	    /* probably vm_expandarray should be optimized for JIT */
	    fprintf(f, "  vm_expandarray(cfp, stack[%d], 0x%"PRIxVALUE", (int)0x%"PRIxVALUE");\n", --stack_size, operands[0], operands[1]);
	    for (i = 0; i < space_size; i++) {
		fprintf(f, "  cfp->sp--;\n");
		fprintf(f, "  stack[%d] = *(cfp->sp);\n", stack_size + space_size - 1 - i);
	    }
	    stack_size += space_size;
	}
        break;
      case YARVINSN_concatarray:
	fprintf(f, "  stack[%d] = vm_concat_array(stack[%d], stack[%d]);\n", stack_size-2, stack_size-2, stack_size-1);
	stack_size--;
        break;
      case YARVINSN_splatarray:
	fprintf(f, "  stack[%d] = vm_splat_array(0x%"PRIxVALUE", stack[%d]);\n", stack_size-1, operands[0], stack_size-1);
        break;
      case YARVINSN_newhash:
	fprintf(f, "  {\n");
	fprintf(f, "    VALUE val;\n");
	fprintf(f, "    RUBY_DTRACE_CREATE_HOOK(HASH, 0x%"PRIxVALUE");\n", operands[0]);
	fprintf(f, "    val = rb_hash_new_with_size(0x%"PRIxVALUE" / 2);\n", operands[0]);
	if (operands[0]) {
	    fprintf(f, "    rb_hash_bulk_insert(0x%"PRIxVALUE", stack + %d, val);\n", operands[0], stack_size - (unsigned int)operands[0]);
	}
	fprintf(f, "    stack[%d] = val;\n", stack_size - (unsigned int)operands[0]);
	fprintf(f, "  }\n");
	stack_size += 1 - (unsigned int)operands[0];
        break;
      case YARVINSN_newrange:
	fprintf(f, "  stack[%d] = rb_range_new(stack[%d], stack[%d], (int)0x%"PRIxVALUE");\n", stack_size-2, stack_size-2, stack_size-1, operands[0]);
	stack_size--;
        break;
      case YARVINSN_pop:
	stack_size--;
        break;
      case YARVINSN_dup:
	fprintf(f, "  stack[%d] = stack[%d];\n", stack_size, stack_size-1);
	stack_size++;
        break;
      case YARVINSN_dupn:
	fprintf(f, "  MEMCPY(stack + %d, stack + %d, VALUE, 0x%"PRIxVALUE");\n",
		stack_size, stack_size - (unsigned int)operands[0], operands[0]);
	stack_size += (unsigned int)operands[0];
        break;
      case YARVINSN_swap:
	fprintf(f, "  {\n");
	fprintf(f, "    VALUE tmp = stack[%d];\n", stack_size-1);
	fprintf(f, "    stack[%d] = stack[%d];\n", stack_size-1, stack_size-2);
	fprintf(f, "    stack[%d] = tmp;\n", stack_size-2);
	fprintf(f, "  }\n");
        break;
      case YARVINSN_reverse:
	{
	    unsigned int n, i, base;
	    n = (unsigned int)operands[0];
	    base = stack_size - n;

	    fprintf(f, "  {\n");
	    fprintf(f, "    VALUE v0;\n");
	    fprintf(f, "    VALUE v1;\n");
	    for (i = 0; i < n/2; i++) {
		fprintf(f, "    v0 = stack[%d];\n", base + i);
		fprintf(f, "    v1 = stack[%d];\n", base + n - i - 1);
		fprintf(f, "    stack[%d] = v1;\n", base + i);
		fprintf(f, "    stack[%d] = v0;\n", base + n - i - 1);
	    }
	    fprintf(f, "  }\n");
	}
        break;
      case YARVINSN_reput:
	fprintf(f, "  stack[%d] = stack[%d];\n", stack_size-1, stack_size-1);
        break;
      case YARVINSN_topn:
	fprintf(f, "  stack[%d] = stack[%d];\n", stack_size, stack_size - (unsigned int)operands[0]);
	stack_size++;
        break;
      case YARVINSN_setn:
	fprintf(f, "  stack[%d] = stack[%d];\n", stack_size - 1 - (unsigned int)operands[0], stack_size-1);
        break;
      case YARVINSN_adjuststack:
	stack_size -= (unsigned int)operands[0];
        break;
      case YARVINSN_defined:
	fprintf(f, "  stack[%d] = vm_defined(th, cfp, 0x%"PRIxVALUE", 0x%"PRIxVALUE", 0x%"PRIxVALUE", stack[%d]);\n",
		stack_size-1, operands[0], operands[1], operands[2], stack_size-1);
        break;
      case YARVINSN_checkmatch:
	fprintf(f, "  stack[%d] = vm_check_match(stack[%d], stack[%d], 0x%"PRIxVALUE");\n", stack_size-2, stack_size-2, stack_size-1, operands[0]);
	stack_size--;
        break;
      case YARVINSN_checkkeyword:
	fprintf(f, "  stack[%d] = vm_check_keyword(0x%"PRIxVALUE", 0x%"PRIxVALUE", cfp->ep);\n",
		stack_size++, operands[0], operands[1]);
        break;
      case YARVINSN_trace:
	fprintf(f, "  vm_dtrace((rb_event_flag_t)0x%"PRIxVALUE", th);\n", operands[0]);
	if ((rb_event_flag_t)operands[0] & (RUBY_EVENT_RETURN | RUBY_EVENT_B_RETURN)) {
	    fprintf(f, "  EXEC_EVENT_HOOK(th, (rb_event_flag_t)0x%"PRIxVALUE", cfp->self, 0, 0, 0, stack[%d]);\n", operands[0], stack_size-1);
	} else {
	    fprintf(f, "  EXEC_EVENT_HOOK(th, (rb_event_flag_t)0x%"PRIxVALUE", cfp->self, 0, 0, 0, Qundef);\n", operands[0]);
	}
        break;
      case YARVINSN_trace2:
	fprintf(f, "  vm_dtrace((rb_event_flag_t)0x%"PRIxVALUE", th);\n", operands[0]);
	fprintf(f, "  EXEC_EVENT_HOOK(th, (rb_event_flag_t)0x%"PRIxVALUE", cfp->self, 0, 0, 0, 0x%"PRIxVALUE");\n", operands[0], operands[1]);
        break;
      //case YARVINSN_defineclass:
      //  /* use vm_exec? */
      //  break;
      case YARVINSN_send:
	{
	    CALL_INFO ci = (CALL_INFO)operands[0];
	    unsigned int push_count = ci->orig_argc + ((ci->flag & VM_CALL_ARGS_BLOCKARG) ? 1 : 0);

	    fprintf(f, "  {\n");
	    fprintf(f, "    struct rb_calling_info calling;\n");

	    fprint_args(f, push_count, stack_size - push_count);
	    fprintf(f, "    vm_caller_setup_arg_block(th, cfp, &calling, 0x%"PRIxVALUE", 0x%"PRIxVALUE", FALSE);\n", operands[0], operands[2]);
	    fprintf(f, "    calling.argc = %d;\n", ci->orig_argc);
	    fprintf(f, "    vm_search_method(0x%"PRIxVALUE", 0x%"PRIxVALUE", calling.recv = stack[%d]);\n", operands[0], operands[1], stack_size - 1 - push_count);
	    fprint_call_method(f, operands[0], operands[1], stack_size - push_count - 1);
	    fprintf(f, "  }\n");
	    stack_size -= push_count;
	}
        break;
      case YARVINSN_opt_str_freeze:
	fprintf(f, "  if (BASIC_OP_UNREDEFINED_P(BOP_FREEZE, STRING_REDEFINED_OP_FLAG)) {\n");
	fprintf(f, "    stack[%d] = 0x%"PRIxVALUE";\n", stack_size, operands[0]);
	fprintf(f, "  } else {\n");
	fprintf(f, "    stack[%d] = rb_funcall(rb_str_resurrect(0x%"PRIxVALUE"), idFreeze, 0);\n", stack_size, operands[0]);
	fprintf(f, "  }\n");
	stack_size++;
        break;
      case YARVINSN_opt_str_uminus:
	fprintf(f, "  if (BASIC_OP_UNREDEFINED_P(BOP_UMINUS, STRING_REDEFINED_OP_FLAG)) {\n");
	fprintf(f, "    stack[%d] = 0x%"PRIxVALUE";\n", stack_size, operands[0]);
	fprintf(f, "  } else {\n");
	fprintf(f, "    stack[%d] = rb_funcall(rb_str_resurrect(0x%"PRIxVALUE"), idUMinus, 0);\n", stack_size, operands[0]);
	fprintf(f, "  }\n");
	stack_size++;
        break;
      case YARVINSN_opt_newarray_max:
	fprintf(f, "  stack[%d] = vm_opt_newarray_max(0x%"PRIxVALUE", stack + %d);\n",
		stack_size - (unsigned int)operands[0], operands[0], stack_size - (unsigned int)operands[0]);
	stack_size += 1 - (unsigned int)operands[0];
        break;
      case YARVINSN_opt_newarray_min:
	fprintf(f, "  stack[%d] = vm_opt_newarray_min(0x%"PRIxVALUE", stack + %d);\n",
		stack_size - (unsigned int)operands[0], operands[0], stack_size - (unsigned int)operands[0]);
	stack_size += 1 - (unsigned int)operands[0];
        break;
      case YARVINSN_opt_send_without_block:
	{
	    CALL_INFO ci = (CALL_INFO)operands[0];
	    fprintf(f, "  {\n");
	    fprintf(f, "    struct rb_calling_info calling;\n");
	    fprintf(f, "    calling.block_handler = VM_BLOCK_HANDLER_NONE;\n");
	    fprintf(f, "    calling.argc = %d;\n", ci->orig_argc);
	    fprintf(f, "    vm_search_method(0x%"PRIxVALUE", 0x%"PRIxVALUE", calling.recv = stack[%d]);\n", operands[0], operands[1], stack_size - 1 - ci->orig_argc);
	    fprint_args(f, ci->orig_argc, stack_size - ci->orig_argc);
	    fprint_call_method(f, operands[0], operands[1], stack_size - ci->orig_argc - 1);
	    fprintf(f, "  }\n");
	    stack_size -= ci->orig_argc;
	}
        break;
      case YARVINSN_invokesuper:
	{
	    CALL_INFO ci = (CALL_INFO)operands[0];
	    unsigned int push_count = ci->orig_argc + ((ci->flag & VM_CALL_ARGS_BLOCKARG) ? 1 : 0);

	    fprintf(f, "  {\n");
	    fprintf(f, "    struct rb_calling_info calling;\n");
	    fprintf(f, "    calling.argc = %d;\n", ci->orig_argc);
	    fprint_args(f, push_count, stack_size - push_count);
	    fprintf(f, "    vm_caller_setup_arg_block(th, cfp, &calling, 0x%"PRIxVALUE", 0x%"PRIxVALUE", TRUE);\n", operands[0], operands[2]);
	    fprintf(f, "    calling.recv = cfp->self;\n");
	    fprintf(f, "    vm_search_super_method(th, cfp, &calling, 0x%"PRIxVALUE", 0x%"PRIxVALUE");\n", operands[0], operands[1]);
	    fprint_call_method(f, operands[0], operands[1], stack_size - push_count - 1);
	    fprintf(f, "  }\n");
	    stack_size -= push_count;
	}
        break;
      case YARVINSN_invokeblock:
	{
	    CALL_INFO ci = (CALL_INFO)operands[0];
	    fprintf(f, "  {\n");
	    fprintf(f, "    struct rb_calling_info calling;\n");
	    fprintf(f, "    calling.argc = %d;\n", ci->orig_argc);
	    fprintf(f, "    calling.block_handler = VM_BLOCK_HANDLER_NONE;\n");
	    fprintf(f, "    calling.recv = cfp->self;\n");

	    fprint_args(f, ci->orig_argc, stack_size - ci->orig_argc);
	    fprintf(f, "    stack[%d] = vm_invoke_block(th, cfp, &calling, 0x%"PRIxVALUE");\n", stack_size - ci->orig_argc, operands[0]);
	    fprintf(f, "    if (stack[%d] == Qundef) {\n", stack_size - ci->orig_argc);
	    fprintf(f, "      VM_ENV_FLAGS_SET(th->ec.cfp->ep, VM_FRAME_FLAG_FINISH);\n");
	    fprintf(f, "      stack[%d] = vm_exec(th);\n", stack_size - ci->orig_argc);
	    fprintf(f, "    }\n");
	    fprintf(f, "  }\n");
	    stack_size += 1 - ci->orig_argc;
	}
        break;
      case YARVINSN_leave:
	/* NOTE: We don't use YARV's stack on JIT. So vm_stack_consistency_error shouldn't be checked. */
	/* TODO: instead, assert stack_size == 1 */

	fprintf(f, "  RUBY_VM_CHECK_INTS(th);\n");
	/* TODO: is there a case that vm_pop_frame returns 0? */
	fprintf(f, "  vm_pop_frame(th, cfp, cfp->ep);\n");
#if OPT_CALL_THREADED_CODE
	fprintf(f, "  th->retval = stack[%d];\n", stack_size-1);
	fprintf(f, "  return 0;\n");
#else
	fprintf(f, "  return stack[%d];\n", stack_size-1);
#endif
	jumped = true;
	break;
      case YARVINSN_throw:
	fprintf(f, "  RUBY_VM_CHECK_INTS(th);\n");
	fprintf(f, "  THROW_EXCEPTION(vm_throw(th, cfp, 0x%"PRIxVALUE", stack[%d]));\n", operands[0], --stack_size);
	jumped = true;
        break;
      case YARVINSN_jump:
	next_pos = pos + insn_len(insn) + (unsigned int)operands[0];
	fprintf(f, "  RUBY_VM_CHECK_INTS(th);\n");
	fprintf(f, "  goto label_%d;\n", next_pos);
	jumped = true;
        break;
      case YARVINSN_branchif:
	fprintf(f, "  if (RTEST(stack[%d])) {\n", --stack_size);
	fprintf(f, "    RUBY_VM_CHECK_INTS(th);\n");
	fprintf(f, "    goto label_%d;\n", pos + insn_len(insn) + (unsigned int)operands[0]);
	fprintf(f, "  }\n");
	compile_insns(body, f, stack_size, pos + insn_len(insn), compiled_for_pos, succeeded_ptr);
	next_pos = pos + insn_len(insn) + (unsigned int)operands[0];
        break;
      case YARVINSN_branchunless:
	fprintf(f, "  if (!RTEST(stack[%d])) {\n", --stack_size);
	fprintf(f, "    RUBY_VM_CHECK_INTS(th);\n");
	fprintf(f, "    goto label_%d;\n", pos + insn_len(insn) + (unsigned int)operands[0]);
	fprintf(f, "  }\n");
	compile_insns(body, f, stack_size, pos + insn_len(insn), compiled_for_pos, succeeded_ptr);
	next_pos = pos + insn_len(insn) + (unsigned int)operands[0];
        break;
      case YARVINSN_branchnil:
	fprintf(f, "  if (NIL_P(stack[%d])) {\n", --stack_size);
	fprintf(f, "    RUBY_VM_CHECK_INTS(th);\n");
	fprintf(f, "    goto label_%d;\n", pos + insn_len(insn) + (unsigned int)operands[0]);
	fprintf(f, "  }\n");
	compile_insns(body, f, stack_size, pos + insn_len(insn), compiled_for_pos, succeeded_ptr);
	next_pos = pos + insn_len(insn) + (unsigned int)operands[0];
        break;
      case YARVINSN_branchiftype:
	fprintf(f, "  if (TYPE(stack[%d]) == (int)0x%"PRIxVALUE") {\n", --stack_size, operands[0]);
	fprintf(f, "    RUBY_VM_CHECK_INTS(th);\n");
	fprintf(f, "    goto label_%d;\n", pos + insn_len(insn) + (unsigned int)operands[1]);
	fprintf(f, "  }\n");
        break;
      case YARVINSN_getinlinecache:
	fprintf(f, "  stack[%d] = vm_ic_hit_p(0x%"PRIxVALUE", cfp->ep);", stack_size, operands[1]);
	fprintf(f, "  if (stack[%d] != Qnil) {\n", stack_size);
	fprintf(f, "    goto label_%d;\n", pos + insn_len(insn) + (unsigned int)operands[0]);
	fprintf(f, "  }\n");
	stack_size++;
        break;
      case YARVINSN_setinlinecache:
	fprintf(f, "  vm_ic_update(0x%"PRIxVALUE", stack[%d], cfp->ep);\n", operands[0], stack_size-1);
        break;
      //case YARVINSN_once:
      //  fprintf(f, "  stack[%d] = vm_once_dispatch(0x%"PRIxVALUE", 0x%"PRIxVALUE", th);\n", stack_size++, operands[0], operands[1]);
      //  break;
      case YARVINSN_opt_case_dispatch:
	{
	    struct case_dispatch_var arg;
	    arg.f = f;
	    arg.base_pos = pos + insn_len(insn);

	    fprintf(f, "  switch (vm_case_dispatch(0x%"PRIxVALUE", 0x%"PRIxVALUE", stack[%d])) {\n", operands[0], operands[1], --stack_size);
	    rb_hash_foreach(operands[0], compile_case_dispatch_each, (VALUE)&arg);
	    fprintf(f, "  }\n");
	}
        break;
      case YARVINSN_opt_plus:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_plus(recv, obj)");
        break;
      case YARVINSN_opt_minus:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_minus(recv, obj)");
        break;
      case YARVINSN_opt_mult:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_mult(recv, obj)");
        break;
      case YARVINSN_opt_div:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_div(recv, obj)");
        break;
      case YARVINSN_opt_mod:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_mod(recv, obj)");
        break;
      case YARVINSN_opt_eq:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2,
		"opt_eq_func(recv, obj, 0x%"PRIxVALUE", 0x%"PRIxVALUE")", operands[0], operands[1]);
        break;
      case YARVINSN_opt_neq:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2,
		"vm_opt_neq(0x%"PRIxVALUE", 0x%"PRIxVALUE", 0x%"PRIxVALUE", 0x%"PRIxVALUE", recv, obj)",
		operands[0], operands[1], operands[2], operands[3]);
        break;
      case YARVINSN_opt_lt:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_lt(recv, obj)");
        break;
      case YARVINSN_opt_le:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_le(recv, obj)");
        break;
      case YARVINSN_opt_gt:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_gt(recv, obj)");
        break;
      case YARVINSN_opt_ge:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_ge(recv, obj)");
        break;
      case YARVINSN_opt_ltlt:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_ltlt(recv, obj)");
        break;
      case YARVINSN_opt_aref:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_aref(recv, obj)");
        break;
      case YARVINSN_opt_aset:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 3, "vm_opt_aset(recv, obj, obj2)");
        break;
      case YARVINSN_opt_aset_with:
	stack_size += fprint_opt_call_with_key(f, operands[0], operands[1], operands[2], stack_size, 2,
		"vm_opt_aset_with(recv, 0x%"PRIxVALUE", obj)", operands[2]);
        break;
      case YARVINSN_opt_aref_with:
	stack_size += fprint_opt_call_with_key(f, operands[0], operands[1], operands[2], stack_size, 1,
		"vm_opt_aref_with(recv, 0x%"PRIxVALUE")", operands[2]);
        break;
      case YARVINSN_opt_length:
	fprint_opt_call(f, operands[0], operands[1], stack_size, 1, "vm_opt_length(recv, BOP_LENGTH)");
        break;
      case YARVINSN_opt_size:
	fprint_opt_call(f, operands[0], operands[1], stack_size, 1, "vm_opt_length(recv, BOP_SIZE)");
        break;
      case YARVINSN_opt_empty_p:
	fprint_opt_call(f, operands[0], operands[1], stack_size, 1, "vm_opt_empty_p(recv)");
        break;
      case YARVINSN_opt_succ:
	fprint_opt_call(f, operands[0], operands[1], stack_size, 1, "vm_opt_succ(recv)");
        break;
      case YARVINSN_opt_not:
	fprint_opt_call(f, operands[0], operands[1], stack_size, 1,
		"vm_opt_not(0x%"PRIxVALUE", 0x%"PRIxVALUE", recv)", operands[0], operands[1]);
        break;
      case YARVINSN_opt_regexpmatch1:
	fprintf(f, "  stack[%d] = vm_opt_regexpmatch1((VALUE)0x%"PRIxVALUE", stack[%d]);\n", stack_size-1, operands[0], stack_size-1);
        break;
      case YARVINSN_opt_regexpmatch2:
	stack_size += fprint_opt_call(f, operands[0], operands[1], stack_size, 2, "vm_opt_regexpmatch2(recv, obj)");
        break;
      //case YARVINSN_opt_call_c_function:
      //  break;
      case YARVINSN_bitblt:
	fprintf(f, "  stack[%d] = rb_str_new2(\"a bit of bacon, lettuce and tomato\");\n", stack_size++);
        break;
      case YARVINSN_answer:
	fprintf(f, "  stack[%d] = INT2FIX(42);\n", stack_size++);
        break;
      case YARVINSN_getlocal_OP__WC__0:
	fprint_getlocal(f, stack_size++, operands[0], 0);
        break;
      case YARVINSN_getlocal_OP__WC__1:
	fprint_getlocal(f, stack_size++, operands[0], 1);
        break;
      case YARVINSN_setlocal_OP__WC__0:
	fprint_setlocal(f, --stack_size, operands[0], 0);
        break;
      case YARVINSN_setlocal_OP__WC__1:
	fprint_setlocal(f, --stack_size, operands[0], 1);
        break;
      case YARVINSN_putobject_OP_INT2FIX_O_0_C_:
	fprintf(f, "  stack[%d] = INT2FIX(0);\n", stack_size++);
        break;
      case YARVINSN_putobject_OP_INT2FIX_O_1_C_:
	fprintf(f, "  stack[%d] = INT2FIX(1);\n", stack_size++);
        break;
      default:
	/* passing excessive arguments to suppress warning in insns_info.inc... */
	fprintf(stderr, "Failed to compile instruction: %s (%s: %d...)\n", insn_name(insn), insn_op_types(insn), insn_len(insn) > 0 ? insn_op_type(insn, 0) : 0);
	*succeeded_ptr = false;
	break;
    }

    /* if next_pos is already compiled, next instruction won't be compiled in C code and needs `goto`. */
    if (next_pos < body->iseq_size && compiled_for_pos[next_pos] && !jumped) {
	fprintf(f, "  goto label_%d;\n", next_pos);
    }

    *stack_size_ptr = stack_size;
    return next_pos;
}

static void
compile_insns(const struct rb_iseq_constant_body *body, FILE *f, unsigned int stack_size, unsigned int pos, bool *compiled_for_pos, bool *succeeded_ptr)
{
    int insn;

    while (pos < body->iseq_size && !compiled_for_pos[pos]) {
#if OPT_DIRECT_THREADED_CODE || OPT_CALL_THREADED_CODE
	insn = rb_vm_insn_addr2insn((void *)body->iseq_encoded[pos]);
#else
	insn = (int)body->iseq_encoded[pos];
#endif
	compiled_for_pos[pos] = true;

	fprintf(f, "\nlabel_%d: /* %s */\n", pos, insn_name(insn));
	pos = compile_insn(body, f, &stack_size, insn, body->iseq_encoded + (pos+1), pos, compiled_for_pos, succeeded_ptr);
	if (*succeeded_ptr && stack_size > body->stack_max) {
	    fprintf(stderr, "JIT stack exceeded its max...\n");
	    *succeeded_ptr = false;
	}
	if (!*succeeded_ptr) {
	    break;
	}
    }
}

/* Compile iseq to C code. Return true if succeeded to compile. */
static bool
compile_iseq_to_c(const struct rb_iseq_constant_body *body, FILE *f, const char *funcname)
{
    bool succeeded = true; /* set false if failed to compile */
    bool *compiled_for_pos;
    compiled_for_pos = ZALLOC_N(bool, body->iseq_size);

    fprintf(f, "#include \"jit_header.h\"\n\n");
    fprintf(f, "VALUE %s(rb_thread_t *th, rb_control_frame_t *cfp) {\n", funcname);

    if (body->stack_max > 0) {
	fprintf(f, "  VALUE stack[%d];\n", body->stack_max);
    }
    compile_insns(body, f, 0, 0, compiled_for_pos, &succeeded);

    fprintf(f, "}\n");
    xfree(compiled_for_pos);
    return succeeded;
}

void *
jit_compile(const rb_iseq_t *iseq)
{
    FILE *f;
    unsigned long unique_id;
    char c_fname[128], so_fname[128], funcname[128];
    void *func_ptr;
    bool succeeded;

    /* temporary stub for testing */
    if (strcmp(RSTRING_PTR(iseq->body->location.label), "_jit")) {
        return (void *)NOT_ADDED_JIT_ISEQ_FUNC;
    }

    unique_id = ++jit_scheduled_iseqs;
    sprintf(funcname, "_cjit_%lu", unique_id);
    sprintf(c_fname, "/tmp/_cjit_%lu_%lu.c", (unsigned long)getpid(), unique_id);
    sprintf(so_fname, "/tmp/_cjit_%lu_%lu.so", (unsigned long)getpid(), unique_id);

    fprintf(stderr, "compile: %s@%s -> %s\n", RSTRING_PTR(iseq->body->location.label), RSTRING_PTR(rb_iseq_path(iseq)), c_fname); /* debug */

    f = fopen(c_fname, "w");
    succeeded = compile_iseq_to_c(iseq->body, f, funcname);
    fclose(f);
    if (!succeeded) {
	remove(c_fname);
	return (void *)NOT_COMPILABLE_JIT_ISEQ_FUNC;
    }

    compile_c_to_so(c_fname, so_fname);
    remove(c_fname);

    func_ptr = get_func_ptr(so_fname, funcname);
    remove(so_fname);

    return func_ptr;
}
