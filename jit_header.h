#define TRUE 1 /* TODO: remove this */
#define FALSE 0 /* TODO: remove this */

#include "internal.h"
#include "vm_core.h"
#include "iseq.h"
#include "eval_intern.h"

/* vm.c */
PUREFUNC(static inline const VALUE *VM_EP_LEP(const VALUE *));
static inline const VALUE *
VM_EP_LEP(const VALUE *ep)
{
    while (!VM_ENV_LOCAL_P(ep)) {
        ep = VM_ENV_PREV_EP(ep);
    }
    return ep;
}
extern rb_serial_t ruby_vm_global_constant_state;

/* vm_eval.c */
extern VALUE vm_exec(rb_thread_t *th);

#include "vm_insnhelper.h"
#define CJIT_HEADER 1
#include "vm_insnhelper.c"
