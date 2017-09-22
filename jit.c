#include <unistd.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <dlfcn.h>
#include "internal.h"
#include "vm_core.h"

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
	fprintf(stderr, "failed to open so...\n"); /* debug */
	return (void *)NOT_ADDED_JIT_ISEQ_FUNC;
    }

    func_ptr = dlsym(handle, funcname);
    // TODO: dlclose(handle); on deoptimization

    return func_ptr;
}

static void
jit_compile_iseq_to_c(const rb_iseq_t *iseq, FILE *f, const char *funcname)
{
    fprintf(f, "#include \"internal.h\"\n");
    fprintf(f, "#include \"vm_core.h\"\n");

    fprintf(f, "VALUE %s(rb_thread_t *th, rb_control_frame_t *cfp) {\n", funcname);
    fprintf(f, "  th->ec.cfp = cfp+1;\n"); /* vm_pop_frame */
    fprintf(f, "  return INT2FIX(42);\n");
    fprintf(f, "}\n");
}

void *
jit_compile(const rb_iseq_t *iseq)
{
    FILE *f;
    unsigned long unique_id;
    char c_fname[128], so_fname[128], funcname[128];
    void *func_ptr;

    /* temporary stub for testing */
    if (strcmp(RSTRING_PTR(iseq->body->location.label), "jit_compiled")) {
	return (void *)NOT_ADDED_JIT_ISEQ_FUNC;
    }

    unique_id = ++jit_scheduled_iseqs;
    sprintf(funcname, "_cjit_%lu", unique_id);
    sprintf(c_fname, "/tmp/_cjit_%lu_%lu.c", (unsigned long)getpid(), unique_id);
    sprintf(so_fname, "/tmp/_cjit_%lu_%lu.so", (unsigned long)getpid(), unique_id);

    fprintf(stderr, "compile: %s -> %s\n", RSTRING_PTR(iseq->body->location.label), c_fname); /* debug */

    f = fopen(c_fname, "w");
    jit_compile_iseq_to_c(iseq, f, funcname);
    fclose(f);

    compile_c_to_so(c_fname, so_fname);
    remove(c_fname);

    func_ptr = get_func_ptr(so_fname, funcname);
    remove(so_fname);

    return func_ptr;
}
