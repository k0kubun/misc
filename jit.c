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
    // TODO: check stat
}

static void
compile_c_to_so(const char *c_fname, const char *so_fname)
{
    char **argv;
    static int common_argv_len = 13;
    static const char *common_argv[] = {
	"gcc", "-O2", "-fPIC", "-shared", "-Wfatal-errors", "-w",
	"-I./include", "-I./.ext/include/x86_64-linux", // TODO: fix this
	"-pipe", "-nostartfiles", "-nodefaultlibs", "-nostdlib", "-o"
    };
    const char *dynamic_argv[] = { so_fname, c_fname, NULL };

    argv = xmalloc((common_argv_len + 3) * sizeof(char *));
    memmove(argv, common_argv, common_argv_len * sizeof(char *));
    memmove(argv + common_argv_len, dynamic_argv, 3 * sizeof(char *));

    execute_command("gcc", argv);
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
    // TODO: dlclose(handle); on deoptimization

    return func_ptr;
}

static void
fprint_call2(FILE *f, const char *func, unsigned int *stack_size)
{
    fprintf(f, "  stack[%d] = %s(stack[%d], stack[%d]);\n", *stack_size - 2, func, *stack_size - 2, *stack_size - 1);
    (*stack_size)--;
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

static void compile_insns(const struct rb_iseq_constant_body *body, FILE *f, unsigned int stack_size, unsigned int pos, bool *compiled_for_pos);

/* Compiles insn to f, may modify stack_size_ptr and returns next position */
static unsigned int
compile_insn(const struct rb_iseq_constant_body *body, FILE *f, unsigned int *stack_size_ptr, const int insn, const VALUE *operands, unsigned int pos, bool *compiled_for_pos)
{
    unsigned int stack_size = *stack_size_ptr;
    unsigned int next_pos = pos + insn_len(insn);

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
      //case YARVINSN_getspecial:
      //  break;
      //case YARVINSN_setspecial:
      //  break;
      case YARVINSN_getinstancevariable:
	fprintf(f, "  stack[%d] = vm_getinstancevariable(cfp->self, 0x%"PRIxVALUE", 0x%"PRIxVALUE");\n", stack_size++, operands[0], operands[1]);
        break;
      case YARVINSN_setinstancevariable:
	fprintf(f, "  vm_setinstancevariable(cfp->self, 0x%"PRIxVALUE", stack[%d], 0x%"PRIxVALUE");\n", operands[0], --stack_size, operands[1]);
        break;
      //case YARVINSN_getclassvariable:
      //  break;
      //case YARVINSN_setclassvariable:
      //  break;
      //case YARVINSN_getconstant:
      //  break;
      //case YARVINSN_setconstant:
      //  break;
      //case YARVINSN_getglobal:
      //  break;
      //case YARVINSN_setglobal:
      //  break;
      case YARVINSN_putnil:
	fprintf(f, "  stack[%d] = Qnil;\n", stack_size++);
        break;
      case YARVINSN_putself:
	fprintf(f, "  stack[%d] = cfp->self;\n", stack_size++);
        break;
      case YARVINSN_putobject:
	fprintf(f, "  stack[%d] = (VALUE)0x%"PRIxVALUE";\n", stack_size++, operands[0]);
        break;
      //case YARVINSN_putspecialobject:
      //  break;
      //case YARVINSN_putiseq:
      //  break;
      case YARVINSN_putstring:
	fprintf(f, "  stack[%d] = rb_str_resurrect(0x%"PRIxVALUE");\n", stack_size++, operands[0]);
        break;
      //case YARVINSN_concatstrings:
      //  break;
      //case YARVINSN_tostring:
      //  break;
      //case YARVINSN_freezestring:
      //  break;
      //case YARVINSN_toregexp:
      //  break;
      //case YARVINSN_intern:
      //  break;
      case YARVINSN_newarray:
	fprintf(f, "  stack[%d] = rb_ary_new4(0x%"PRIxVALUE", stack + %d);\n",
		stack_size - (unsigned int)operands[0], operands[0], stack_size - (unsigned int)operands[0]);
	stack_size += 1 - (unsigned int)operands[0];
        break;
      case YARVINSN_duparray:
	fprintf(f, "  stack[%d] = rb_ary_resurrect(0x%"PRIxVALUE");\n", stack_size++, operands[0]);
        break;
      //case YARVINSN_expandarray:
      //  break;
      //case YARVINSN_concatarray:
      //  break;
      //case YARVINSN_splatarray:
      //  break;
      case YARVINSN_newhash:
	fprintf(f, "  RUBY_DTRACE_CREATE_HOOK(HASH, 0x%"PRIxVALUE");\n", operands[0]);
	fprintf(f, "  stack[%d] = rb_hash_new_with_size(0x%"PRIxVALUE" / 2);\n", stack_size, operands[0]);
	if (operands[0]) {
	    fprintf(f, "  rb_hash_bulk_insert(0x%"PRIxVALUE", stack + %d, stack[%d]);\n", operands[0], stack_size - (unsigned int)operands[0], stack_size);
	}
	stack_size++;
        break;
      //case YARVINSN_newrange:
      //  break;
      case YARVINSN_pop:
	stack_size--;
        break;
      case YARVINSN_dup:
	fprintf(f, "  stack[%d] = stack[%d];\n", stack_size, stack_size-1);
	stack_size++;
        break;
      //case YARVINSN_dupn:
      //  break;
      //case YARVINSN_swap:
      //  break;
      //case YARVINSN_reverse:
      //  break;
      //case YARVINSN_reput:
      //  break;
      //case YARVINSN_topn:
      //  break;
      //case YARVINSN_setn:
      //  break;
      //case YARVINSN_adjuststack:
      //  break;
      //case YARVINSN_defined:
      //  break;
      //case YARVINSN_checkmatch:
      //  break;
      //case YARVINSN_checkkeyword:
      //  break;
      case YARVINSN_trace:
	fprintf(f, "  vm_dtrace((rb_event_flag_t)0x%"PRIxVALUE", th);\n", operands[0]);
	if ((rb_event_flag_t)operands[0] & (RUBY_EVENT_RETURN | RUBY_EVENT_B_RETURN)) {
	    fprintf(f, "  EXEC_EVENT_HOOK(th, (rb_event_flag_t)0x%"PRIxVALUE", cfp->self, 0, 0, 0, stack[%d]);\n", operands[0], stack_size-1);
	} else {
	    fprintf(f, "  EXEC_EVENT_HOOK(th, (rb_event_flag_t)0x%"PRIxVALUE", cfp->self, 0, 0, 0, Qundef);\n", operands[0]);
	}
        break;
      //case YARVINSN_trace2:
      //  break;
      //case YARVINSN_defineclass:
      //  break;
      //case YARVINSN_send:
      //  break;
      //case YARVINSN_opt_str_freeze:
      //  break;
      //case YARVINSN_opt_str_uminus:
      //  break;
      //case YARVINSN_opt_newarray_max:
      //  break;
      //case YARVINSN_opt_newarray_min:
      //  break;
      //case YARVINSN_opt_send_without_block:
      //  break;
      //case YARVINSN_invokesuper:
      //  break;
      //case YARVINSN_invokeblock:
      //  break;
      case YARVINSN_leave:
	fprintf(f, "  th->ec.cfp = cfp+1;\n"); /* TODO: properly implement vm_pop_frame */
	fprintf(f, "  return stack[%d];\n", stack_size-1); /* TODO: assert stack_size == 1 */
	break;
      //case YARVINSN_throw:
      //  break;
      case YARVINSN_jump:
	/* TODO: RUBY_VM_CHECK_INTS(th) */
	next_pos = pos + insn_len(insn) + (unsigned int)operands[0];
	fprintf(f, "  goto label_%d;\n", next_pos);
        break;
      case YARVINSN_branchif:
	/* TODO: RUBY_VM_CHECK_INTS(th) */
	fprintf(f, "  if (RTEST(stack[%d])) goto label_%d;\n", --stack_size, pos + insn_len(insn) + (unsigned int)operands[0]);
	compile_insns(body, f, stack_size, pos + insn_len(insn), compiled_for_pos);
	next_pos = pos + insn_len(insn) + (unsigned int)operands[0];
        break;
      case YARVINSN_branchunless:
	/* TODO: RUBY_VM_CHECK_INTS(th) */
	fprintf(f, "  if (!RTEST(stack[%d])) goto label_%d;\n", --stack_size, pos + insn_len(insn) + (unsigned int)operands[0]);
	compile_insns(body, f, stack_size, pos + insn_len(insn), compiled_for_pos);
	next_pos = pos + insn_len(insn) + (unsigned int)operands[0];
        break;
      case YARVINSN_branchnil:
	/* TODO: RUBY_VM_CHECK_INTS(th) */
	fprintf(f, "  if (NIL_P(stack[%d])) goto label_%d;\n", --stack_size, pos + insn_len(insn) + (unsigned int)operands[0]);
	compile_insns(body, f, stack_size, pos + insn_len(insn), compiled_for_pos);
	next_pos = pos + insn_len(insn) + (unsigned int)operands[0];
        break;
      //case YARVINSN_branchiftype:
      //  break;
      //case YARVINSN_getinlinecache:
      //  break;
      //case YARVINSN_setinlinecache:
      //  break;
      //case YARVINSN_once:
      //  break;
      //case YARVINSN_opt_case_dispatch:
      //  break;
      case YARVINSN_opt_plus:
	fprint_call2(f, "vm_opt_plus", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_minus:
	fprint_call2(f, "vm_opt_minus", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_mult:
	fprint_call2(f, "vm_opt_mult", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_div:
	fprint_call2(f, "vm_opt_div", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_mod:
	fprint_call2(f, "vm_opt_mod", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_eq:
	fprintf(f, "stack[%d] = opt_eq_func(stack[%d], stack[%d], (CALL_INFO)0x%"PRIxVALUE", (CALL_CACHE)0x%"PRIxVALUE");\n",
		stack_size-2, stack_size-2, stack_size-1, operands[0], operands[1]);
	stack_size--;
        break;
      case YARVINSN_opt_neq:
	fprintf(f, "stack[%d] = vm_opt_neq((CALL_INFO)0x%"PRIxVALUE", (CALL_CACHE)0x%"PRIxVALUE", (CALL_INFO)0x%"PRIxVALUE", (CALL_CACHE)0x%"PRIxVALUE", stack[%d], stack[%d]);\n",
		stack_size-2, operands[0], operands[1], operands[2], operands[3], stack_size-2, stack_size-1);
	stack_size--;
        break;
      case YARVINSN_opt_lt:
	fprint_call2(f, "vm_opt_lt", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_le:
	fprint_call2(f, "vm_opt_le", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_gt:
	fprint_call2(f, "vm_opt_gt", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_ge:
	fprint_call2(f, "vm_opt_ge", &stack_size); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_ltlt:
	fprint_call2(f, "vm_opt_ltlt", &stack_size); /* TODO: handle Qundef */
        break;
      //case YARVINSN_opt_aref:
      //  break;
      //case YARVINSN_opt_aset:
      //  break;
      //case YARVINSN_opt_aset_with:
      //  break;
      //case YARVINSN_opt_aref_with:
      //  break;
      //case YARVINSN_opt_length:
      //  break;
      //case YARVINSN_opt_size:
      //  break;
      //case YARVINSN_opt_empty_p:
      //  break;
      case YARVINSN_opt_succ:
	fprintf(f, "stack[%d] = vm_opt_succ(stack[%d]);\n", stack_size-1, stack_size-1); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_not:
	fprintf(f, "stack[%d] = vm_opt_not((CALL_INFO)0x%"PRIxVALUE", (CALL_CACHE)0x%"PRIxVALUE", stack[%d]);\n",
		stack_size-1, operands[0], operands[1], stack_size-1); /* TODO: handle Qundef */
        break;
      case YARVINSN_opt_regexpmatch1:
	fprintf(f, "stack[%d] = vm_opt_regexpmatch1((VALUE)0x%"PRIxVALUE", stack[%d]);\n", stack_size-1, operands[0], stack_size-1);
        break;
      case YARVINSN_opt_regexpmatch2:
	fprint_call2(f, "vm_opt_regexpmatch2", &stack_size); /* TODO: handle Qundef */
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
	break;
    }

    *stack_size_ptr = stack_size;
    return next_pos;
}

static void
compile_insns(const struct rb_iseq_constant_body *body, FILE *f, unsigned int stack_size, unsigned int pos, bool *compiled_for_pos)
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
	pos = compile_insn(body, f, &stack_size, insn, body->iseq_encoded + (pos+1), pos, compiled_for_pos);
	/* TODO: check body->stack_max here */
    }
}

static void
compile_iseq_to_c(const struct rb_iseq_constant_body *body, FILE *f, const char *funcname)
{
    bool *compiled_for_pos;
    compiled_for_pos = ZALLOC_N(bool, body->iseq_size);

    fprintf(f, "#include \"jit_header.h\"\n\n");
    fprintf(f, "VALUE %s(rb_thread_t *th, rb_control_frame_t *cfp) {\n", funcname);

    if (body->stack_max > 0) {
	fprintf(f, "  VALUE stack[%d];\n", body->stack_max);
    }
    compile_insns(body, f, 0, 0, compiled_for_pos);

    fprintf(f, "}\n");
    xfree(compiled_for_pos);
}

void *
jit_compile(const rb_iseq_t *iseq)
{
    FILE *f;
    unsigned long unique_id;
    char c_fname[128], so_fname[128], funcname[128];
    void *func_ptr;

    /* temporary stub for testing */
    if (strcmp(RSTRING_PTR(iseq->body->location.label), "_jit")) {
	return (void *)NOT_ADDED_JIT_ISEQ_FUNC;
    }

    unique_id = ++jit_scheduled_iseqs;
    sprintf(funcname, "_cjit_%lu", unique_id);
    sprintf(c_fname, "/tmp/_cjit_%lu_%lu.c", (unsigned long)getpid(), unique_id);
    sprintf(so_fname, "/tmp/_cjit_%lu_%lu.so", (unsigned long)getpid(), unique_id);

    fprintf(stderr, "compile: %s -> %s\n", RSTRING_PTR(iseq->body->location.label), c_fname); /* debug */

    f = fopen(c_fname, "w");
    compile_iseq_to_c(iseq->body, f, funcname);
    fclose(f);

    compile_c_to_so(c_fname, so_fname);
    remove(c_fname);

    func_ptr = get_func_ptr(so_fname, funcname);
    remove(so_fname);

    return func_ptr;
}
