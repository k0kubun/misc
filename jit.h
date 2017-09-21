enum rb_jit_iseq_func {
    NOT_ADDED_JIT_ISEQ_FUNC = 0,
    NOT_READY_JIT_ISEQ_FUNC = 1,
    LAST_JIT_ISEQ_FUNC = 2,
};

#define JIT_MIN_PROFILE_CALLS 5
#define JIT_MAX_ISEQ_SIZE 1000

typedef VALUE (*jit_func_t)(rb_thread_t *, rb_control_frame_t *);

extern int jit_enabled;

extern void jit_compile(const rb_iseq_t *iseq);

static inline VALUE
jit_exec(rb_thread_t *th)
{
    struct rb_iseq_constant_body *body;
    long unsigned profile_calls;
    jit_func_t func;

    if (!jit_enabled)
	return Qundef;

    body = th->ec.cfp->iseq->body;
    profile_calls = ++body->profile_calls;

    func = body->jit_func;
    if (UNLIKELY((ptrdiff_t)func <= (ptrdiff_t)LAST_JIT_ISEQ_FUNC)) {
	switch ((enum rb_jit_iseq_func)func) {
	  case NOT_ADDED_JIT_ISEQ_FUNC:
	    if (profile_calls == JIT_MIN_PROFILE_CALLS &&
		(body->type == ISEQ_TYPE_METHOD || body->type == ISEQ_TYPE_BLOCK) &&
		body->iseq_size < JIT_MAX_ISEQ_SIZE) {
		body->jit_func = (void *)NOT_READY_JIT_ISEQ_FUNC;
		jit_compile(th->ec.cfp->iseq);
	    }
	    return Qundef;
	  case NOT_READY_JIT_ISEQ_FUNC:
	    return Qundef;
	  default:
	    break;
	}
    }

    return Qundef;
}
