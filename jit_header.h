#define TRUE 1 /* TODO: remove this */
#define FALSE 0 /* TODO: remove this */

#include "internal.h"
#include "vm_core.h"
#include "vm_exec.h"
#include "iseq.h"
#include "eval_intern.h"

/* vm.c */
VALUE vm_invoke_bmethod(rb_thread_t *th, rb_proc_t *proc, VALUE self,
			       int argc, const VALUE *argv, VALUE block_handler);

PUREFUNC(static inline const VALUE *VM_EP_LEP(const VALUE *));
static inline const VALUE *
VM_EP_LEP(const VALUE *ep)
{
    while (!VM_ENV_LOCAL_P(ep)) {
        ep = VM_ENV_PREV_EP(ep);
    }
    return ep;
}

static struct rb_captured_block *
VM_CFP_TO_CAPTURED_BLOCK(const rb_control_frame_t *cfp)
{
    VM_ASSERT(!VM_CFP_IN_HEAP_P(GET_THREAD(), cfp));
    return (struct rb_captured_block *)&cfp->self;
}

static int
VM_BH_FROM_CFP_P(VALUE block_handler, const rb_control_frame_t *cfp)
{
    const struct rb_captured_block *captured = VM_CFP_TO_CAPTURED_BLOCK(cfp);
    return VM_TAGGED_PTR_REF(block_handler, 0x03) == captured;
}

static VALUE
vm_passed_block_handler(rb_thread_t *th)
{
    VALUE block_handler = th->passed_block_handler;
    th->passed_block_handler = VM_BLOCK_HANDLER_NONE;
    vm_block_handler_verify(block_handler);
    return block_handler;
}

extern rb_serial_t ruby_vm_global_constant_state;

#include "vm_insnhelper.h"
#define CJIT_HEADER 1
#include "vm_insnhelper.c"

/* vm_eval.c */
#include "vm_eval.c"

#include "vm_call_iseq_optimized.inc"
