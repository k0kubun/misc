require 'test/unit'

class TestJIT < Test::Unit::TestCase
  TEST_ITERATIONS = 5

  # def test_nop
  # def test_getlocal
  # def test_setlocal
  # def test_getspecial
  # def test_setspecial
  # def test_getinstancevariable
  # def test_setinstancevariable
  # def test_getclassvariable
  # def test_setclassvariable
  # def test_getconstant
  # def test_setconstant
  # def test_getglobal
  # def test_setglobal
  # def test_putnil
  # def test_putself

  def test_putobject
    test_results { |k| def k._jit; -1; end }
    test_results { |k| def k._jit; 2; end }
    test_results { |k| def k._jit; true; end }
    test_results { |k| def k._jit; false; end }
    test_results { |k| def k._jit; :hello; end }
    test_results { |k| def k._jit; (1..2); end }
  end

  # def putspecialobject
  # def putiseq
  # def putstring
  # def concatstrings
  # def tostring
  # def freezestring
  # def toregexp
  # def intern
  # def newarray
  # def duparray
  # def expandarray
  # def concatarray
  # def splatarray
  # def newhash
  # def newrange
  # def pop
  # def dup
  # def dupn
  # def swap
  # def reverse
  # def reput
  # def topn
  # def setn
  # def adjuststack
  # def defined
  # def checkmatch
  # def checkkeyword
  # def trace
  # def trace2
  # def defineclass
  # def send
  # def opt_str_freeze
  # def opt_str_uminus
  # def opt_newarray_max
  # def opt_newarray_min
  # def opt_send_without_block
  # def invokesuper
  # def invokeblock
  # def leave
  # def throw
  # def jump
  # def branchif
  # def branchunless
  # def branchnil
  # def branchiftype
  # def getinlinecache
  # def setinlinecache
  # def once
  # def opt_case_dispatch
  # def opt_plus
  # def opt_minus
  # def opt_mult
  # def opt_div
  # def opt_mod
  # def opt_eq
  # def opt_neq
  # def opt_lt
  # def opt_le
  # def opt_gt
  # def opt_ge
  # def opt_ltlt
  # def opt_aref
  # def opt_aset
  # def opt_aset_with
  # def opt_aref_with
  # def opt_length
  # def opt_size
  # def opt_empty_p
  # def opt_succ
  # def opt_not
  # def opt_regexpmatch1
  # def opt_regexpmatch2
  # def opt_call_c_function
  # def bitblt
  # def answer
  # def getlocal_OP__WC__0
  # def getlocal_OP__WC__1
  # def setlocal_OP__WC__0
  # def setlocal_OP__WC__1

  def test_putobject_OP_INT2FIX_O_0_C_
    test_results { |k| def k._jit; 0; end }
  end

  def test_putobject_OP_INT2FIX_O_1_C_
    test_results { |k| def k._jit; 1; end }
  end

  private

  def test_results
    klass = Class.new
    yield(klass) # This doesn't use block to define :_jit to make it JIT-ed

    expected = klass._jit
    TEST_ITERATIONS.times do
      assert_equal expected, klass._jit
    end
  end
end
