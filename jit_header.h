#define TRUE 1 /* TODO: remove this */
#define FALSE 0 /* TODO: remove this */

#include "internal.h"
#include "vm_core.h"

/* vm_eval.c */
extern VALUE vm_exec(rb_thread_t *th);

#include "vm_insnhelper.h"
#define CJIT_HEADER 1
#include "vm_insnhelper.c"
