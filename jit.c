#include <unistd.h>
#include <sys/wait.h>
#include <sys/time.h>
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
}

static void
compile_c_to_so(const char *c_fname, const char *so_fname)
{
    char **argv;
    static int common_argv_len = 11;
    static const char *common_argv[] = {
	"gcc", "-O2", "-fPIC", "-shared", "-Wfatal-errors", "-w",
	"-pipe", "-nostartfiles", "-nodefaultlibs", "-nostdlib", "-o"
    };
    const char *dynamic_argv[] = { so_fname, c_fname, NULL };

    argv = xmalloc((common_argv_len + 3) * sizeof(char *));
    memmove(argv, common_argv, common_argv_len * sizeof(char *));
    memmove(argv + common_argv_len, dynamic_argv, 3 * sizeof(char *));

    execute_command("gcc", argv);
    xfree(argv);
}

static void
jit_compile_iseq_to_c(const rb_iseq_t *iseq, FILE *f)
{
    fprintf(f, "int main() {\n");
    fprintf(f, "  return 1;\n");
    fprintf(f, "}\n");
}

void
jit_compile(const rb_iseq_t *iseq)
{
    FILE *f;
    unsigned long unique_id;
    char c_fname[128], so_fname[128];

    /* temporary stub for testing */
    if (strcmp(RSTRING_PTR(iseq->body->location.label), "jit_compiled")) {
	return;
    }

    unique_id = ++jit_scheduled_iseqs;
    sprintf(c_fname, "/tmp/ruby_cjit_%lu_%lu.c", (unsigned long)getpid(), unique_id);
    sprintf(so_fname, "/tmp/ruby_cjit_%lu_%lu.so", (unsigned long)getpid(), unique_id);

    f = fopen(c_fname, "w");
    jit_compile_iseq_to_c(iseq, f);
    fclose(f);

    compile_c_to_so(c_fname, so_fname);
    remove(c_fname);

    remove(so_fname); /* temp */

    fprintf(stderr, "compile: %s -> %s\n", RSTRING_PTR(iseq->body->location.label), c_fname); /* debug */
}
