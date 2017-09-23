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
#include "insns.inc"
#include "insns_info.inc"
#include "jit.h"

/* If "-j" is specified, this becomes TRUE */
int jit_enabled = FALSE;

/* Total count of scheduled ISeqs to generate unique identifier */
static unsigned long jit_scheduled_iseqs;

/* Emulates rb_control_frame's stack pointer */
struct jit_stack {
  unsigned int size;
  unsigned int max;
  char **body;
};

static void
jit_stack_push(struct jit_stack *stack, char *value)
{
    if (stack->size >= stack->max) {
	fprintf(stderr, "JIT internal stack overflow: max=%d, next size=%d", stack->max, stack->size+1);
    }
    stack->body[stack->size] = value;
    stack->size++;
}

static char*
jit_stack_pop(struct jit_stack *stack)
{
    if (stack->size <= 0) {
	fprintf(stderr, "JIT internal stack underflow: next size=%d", stack->size-1);
    }
    stack->size--;
    return stack->body[stack->size];
}

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
	fprintf(stderr, "failed to open so...\n"); /* debug */
	return (void *)NOT_ADDED_JIT_ISEQ_FUNC;
    }

    func_ptr = dlsym(handle, funcname);
    // TODO: dlclose(handle); on deoptimization

    return func_ptr;
}

/* Duplicates argument string in heap */
static char *
get_string(const char *str)
{
    char *result;
    if ((result = xmalloc(strlen(str) + 1)) != NULL) {
	strcpy(result, str);
    }
    return result;
}

PRINTF_ARGS(static char *, 1, 0)
get_stringf(const char *format, ...)
{
    char buf[128];
    va_list va;

    va_start(va, format);
    vsprintf(buf, format, va);
    va_end(va);

    return get_string(buf);
}

static char *
get_value_string(VALUE value)
{
    return get_stringf("(VALUE)0x%"PRIxVALUE, value);
}

static void
compile_insn(FILE *f, struct jit_stack *stack, const int insn, const VALUE *operands)
{
    char *value;

    switch (insn) {
      case YARVINSN_nop:
	/* nop */
        break;
      //case YARVINSN_getlocal:
      //  break;
      //case YARVINSN_setlocal:
      //  break;
      //case YARVINSN_getspecial:
      //  break;
      //case YARVINSN_setspecial:
      //  break;
      //case YARVINSN_getinstancevariable:
      //  break;
      //case YARVINSN_setinstancevariable:
      //  break;
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
	jit_stack_push(stack, get_value_string(Qnil));
        break;
      case YARVINSN_putself:
	jit_stack_push(stack, get_string("cfp->self"));
        break;
      case YARVINSN_putobject:
	jit_stack_push(stack, get_value_string(operands[0]));
        break;
      //case YARVINSN_putspecialobject:
      //  break;
      //case YARVINSN_putiseq:
      //  break;
      case YARVINSN_putstring:
	jit_stack_push(stack, get_stringf("rb_str_resurrect(0x%"PRIxVALUE")", operands[0]));
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
      //case YARVINSN_newarray:
      //  break;
      //case YARVINSN_duparray:
      //  break;
      //case YARVINSN_expandarray:
      //  break;
      //case YARVINSN_concatarray:
      //  break;
      //case YARVINSN_splatarray:
      //  break;
      //case YARVINSN_newhash:
      //  break;
      //case YARVINSN_newrange:
      //  break;
      //case YARVINSN_pop:
      //  break;
      //case YARVINSN_dup:
      //  break;
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
	/* TODO: implement this */
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
	value = jit_stack_pop(stack);
	fprintf(f, "  th->ec.cfp = cfp+1;\n"); /* vm_pop_frame */
	fprintf(f, "  return %s;\n", value);
	xfree(value);
	break;
      //case YARVINSN_throw:
      //  break;
      //case YARVINSN_jump:
      //  break;
      //case YARVINSN_branchif:
      //  break;
      //case YARVINSN_branchunless:
      //  break;
      //case YARVINSN_branchnil:
      //  break;
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
      //case YARVINSN_opt_plus:
      //  break;
      //case YARVINSN_opt_minus:
      //  break;
      //case YARVINSN_opt_mult:
      //  break;
      //case YARVINSN_opt_div:
      //  break;
      //case YARVINSN_opt_mod:
      //  break;
      //case YARVINSN_opt_eq:
      //  break;
      //case YARVINSN_opt_neq:
      //  break;
      //case YARVINSN_opt_lt:
      //  break;
      //case YARVINSN_opt_le:
      //  break;
      //case YARVINSN_opt_gt:
      //  break;
      //case YARVINSN_opt_ge:
      //  break;
      //case YARVINSN_opt_ltlt:
      //  break;
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
      //case YARVINSN_opt_succ:
      //  break;
      //case YARVINSN_opt_not:
      //  break;
      //case YARVINSN_opt_regexpmatch1:
      //  break;
      //case YARVINSN_opt_regexpmatch2:
      //  break;
      //case YARVINSN_opt_call_c_function:
      //  break;
      //case YARVINSN_bitblt:
      //  break;
      //case YARVINSN_answer:
      //  break;
      //case YARVINSN_getlocal_OP__WC__0:
      //  break;
      //case YARVINSN_getlocal_OP__WC__1:
      //  break;
      //case YARVINSN_setlocal_OP__WC__0:
      //  break;
      //case YARVINSN_setlocal_OP__WC__1:
      //  break;
      case YARVINSN_putobject_OP_INT2FIX_O_0_C_:
	jit_stack_push(stack, get_value_string(INT2FIX(0)));
        break;
      case YARVINSN_putobject_OP_INT2FIX_O_1_C_:
	jit_stack_push(stack, get_value_string(INT2FIX(1)));
        break;
      default:
	fprintf(stderr, "Failed to compile instruction: %s\n", insn_name(insn));
	break;
    }
}

static void
compile_iseq_to_c(const struct rb_iseq_constant_body *body, FILE *f, const char *funcname)
{
    unsigned int i;
    int insn;
    struct jit_stack stack;

    stack.size = 0;
    stack.max  = body->stack_max;
    stack.body = ALLOC_N(char *, body->stack_max);

    fprintf(f, "#include \"internal.h\"\n");
    fprintf(f, "#include \"vm_core.h\"\n");

    fprintf(f, "VALUE %s(rb_thread_t *th, rb_control_frame_t *cfp) {\n", funcname);
    for (i = 0; i < body->iseq_size;) {
#if OPT_DIRECT_THREADED_CODE || OPT_CALL_THREADED_CODE
	insn = rb_vm_insn_addr2insn((void *)body->iseq_encoded[i]);
#else
	insn = (int)body->iseq_encoded[i];
#endif
	compile_insn(f, &stack, insn, body->iseq_encoded + (i+1));
	i += insn_len(insn);
    }
    fprintf(f, "}\n");

    xfree(stack.body);
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
