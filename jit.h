enum rb_jit_iseq_func {
    NOT_ADDED_JIT_ISEQ_FUNC = 0,
    NOT_READY_JIT_ISEQ_FUNC = 1,
    LAST_JIT_ISEQ_FUNC = 2,
};

typedef VALUE (*jit_func_t)(rb_thread_t *, rb_control_frame_t *);

extern int jit_enabled;

static inline VALUE
jit_exec(rb_thread_t *th)
{
    jit_func_t func;
    struct rb_iseq_constant_body *body;

    if (!jit_enabled)
	return Qundef;

    body = th->ec.cfp->iseq->body;
    func = body->jit_func;
    if (UNLIKELY((ptrdiff_t)func <= (ptrdiff_t)LAST_JIT_ISEQ_FUNC)) {
	switch ((enum rb_jit_iseq_func)func) {
	  case NOT_ADDED_JIT_ISEQ_FUNC:
	    body->jit_func = (void *)NOT_READY_JIT_ISEQ_FUNC;
	    return Qundef;
	  case NOT_READY_JIT_ISEQ_FUNC:
	    return Qundef;
	  default:
	    break;
	}
    }

    return Qundef;
}
