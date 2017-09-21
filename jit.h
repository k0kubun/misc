extern int jit_enabled;

static inline VALUE
jit_exec(rb_thread_t *th)
{
    if (!jit_enabled)
	return Qundef;
    return Qundef;
}
