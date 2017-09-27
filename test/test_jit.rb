require 'test/unit'

class TestJIT < Test::Unit::TestCase
  TEST_ITERATIONS = 5

  def test_nop
    test_results { |k| def k._jit; nil rescue true; end }
  end

  # def test_getlocal
  # def test_setlocal
  # def test_getspecial
  # def test_setspecial

  def test_getinstancevariable
    test_results { |k| def k._jit; @a; end }
  end

  def test_setinstancevariable
    test_results { |k| def k._jit; @a = 2; @a; end }
  end

  # def test_getclassvariable
  # def test_setclassvariable
  # def test_getconstant
  # def test_setconstant
  # def test_getglobal
  # def test_setglobal

  def test_putnil
    test_results { |k| def k._jit; nil; end }
  end

  def test_putself
    test_results { |k| def k._jit; self; end }
  end

  def test_putobject
    test_results { |k| def k._jit; -1; end }
    test_results { |k| def k._jit; 2; end }
    test_results { |k| def k._jit; true; end }
    test_results { |k| def k._jit; false; end }
    test_results { |k| def k._jit; :hello; end }
    test_results { |k| def k._jit; (1..2); end }
  end

  # def test_putspecialobject
  # def test_putiseq
  # def test_putstring
  # def test_concatstrings
  # def test_tostring
  # def test_freezestring
  # def test_toregexp
  # def test_intern

  def test_newarray
    test_results { |k| def k._jit; []; end }
    test_results(1) { |k| def k._jit(a); [a]; end }
    test_results(1, 2) { |k| def k._jit(a, b); [a, b]; end }
    test_results(1, 2, 3, 4) { |k| def k._jit(a, b, c, d); [a, b] + [c, d]; end }
  end

  def test_duparray
    test_results { |k| def k._jit; [1]; end }
    test_results { |k| def k._jit; [1, 2]; end }
  end

  # def test_expandarray
  # def test_concatarray
  # def test_splatarray

  def test_newhash
    test_results { |k| def k._jit; {}; end }
    test_results(4) { |k| def k._jit(a); {a: a}; end }
    test_results(1, 2, 3, 4) do |k|
      def k._jit(a, b, c, d)
        {a: a, b: b} && {c: c, d: d}
      end
    end
  end

  def test_newrange
    test_results(2) { |k| def k._jit(a); (1..a); end }
    test_results(2, 4) { |k| def k._jit(a, b); (a..b); end }
  end

  def test_pop
    test_results do |k|
      def k._jit
        1+2
        3+4
      end
    end
  end

  def test_dup
    test_results { |k| def k._jit; 1+2 || false; end }
  end

  # def test_dupn
  # def test_swap
  # def test_reverse
  # def test_reput
  # def test_topn
  # def test_setn
  # def test_adjuststack
  # def test_defined
  # def test_checkmatch
  # def test_checkkeyword

  def test_trace
    test_results do |k|
      def k._jit
        1+2
        3+4
      end
    end
  end

  # def test_trace2
  # def test_defineclass
  # def test_send
  # def test_opt_str_freeze
  # def test_opt_str_uminus
  # def test_opt_newarray_max
  # def test_opt_newarray_min
  # def test_opt_send_without_block
  # def test_invokesuper
  # def test_invokeblock
  # def test_leave
  # def test_throw

  def test_jump
    test_results do |k|
      def k._jit
        if 0+1
          2
        else
          3
        end
      end
    end
  end

  def test_branchif
    test_results { |k| def k._jit; 1+2 || false; end }
  end

  def test_branchunless
    test_results do |k|
      def k._jit
        if 0+1
          2
        else
          3
        end
      end
    end
  end

  def test_branchnil
    test_results { |k| def k._jit; 1&.+(2); end }
  end

  # def test_branchiftype
  # def test_getinlinecache
  # def test_setinlinecache
  # def test_once
  # def test_opt_case_dispatch

  def test_opt_plus
    test_results { |k| def k._jit; 1+2; end }
  end

  def test_opt_minus
    test_results { |k| def k._jit; 5-3; end }
  end

  def test_opt_mult
    test_results { |k| def k._jit; 3 * 4 * 5; end }
  end

  def test_opt_div
    test_results { |k| def k._jit; 12 / 2; end }
  end

  def test_opt_mod
    test_results { |k| def k._jit; 5 % 2; end }
  end

  def test_opt_eq
    test_results { |k| def k._jit; 1 == 2; end }
    test_results { |k| def k._jit; 2 == 2; end }
  end

  def test_opt_neq
    test_results { |k| def k._jit; 1 != 2; end }
    test_results { |k| def k._jit; 2 != 2; end }
  end

  def test_opt_lt
    test_results { |k| def k._jit; 1 < 2; end }
    test_results { |k| def k._jit; 1 < 1; end }
    test_results { |k| def k._jit; 2 < 1; end }
  end

  def test_opt_le
    test_results { |k| def k._jit; 1 <= 2; end }
    test_results { |k| def k._jit; 1 <= 1; end }
    test_results { |k| def k._jit; 2 <= 1; end }
  end

  def test_opt_gt
    test_results { |k| def k._jit; 1 > 2; end }
    test_results { |k| def k._jit; 1 > 1; end }
    test_results { |k| def k._jit; 2 > 1; end }
  end

  def test_opt_ge
    test_results { |k| def k._jit; 1 >= 2; end }
    test_results { |k| def k._jit; 1 >= 1; end }
    test_results { |k| def k._jit; 2 >= 1; end }
  end

  def test_opt_ltlt
    test_results { |k| def k._jit; 'hello' << 'world'; end }
  end

  # def test_opt_aref
  # def test_opt_aset
  # def test_opt_aset_with
  # def test_opt_aref_with
  # def test_opt_length
  # def test_opt_size
  # def test_opt_empty_p

  def test_opt_succ
    test_results { |k| def k._jit; 1.succ; end }
  end

  def test_opt_not
    test_results { |k| def k._jit; !!1; end }
    test_results { |k| def k._jit; !!nil; end }
    test_results { |k| def k._jit; !false; end }
  end

  def test_opt_regexpmatch1
    test_results { |k| def k._jit; /true/ =~ 'true'; end }
  end

  def test_opt_regexpmatch2
    test_results { |k| def k._jit; 'true' =~ /true/; end }
  end

  # def test_opt_call_c_function

  def test_bitblt
    # no test
  end

  def test_answer
    # no test
  end

  def test_getlocal_OP__WC__0
    test_results(100) { |k| def k._jit(a); a; end }
    test_results(3, 1) { |k| def k._jit(a, b); b - a; end }
    test_results do |k|
      def k._jit
        a = 1
        b = 2
        a + b
      end
    end
  end

  # def test_getlocal_OP__WC__1

  def test_setlocal_OP__WC__0
    test_results { |k| def k._jit; a = 2; end }
  end

  # def test_setlocal_OP__WC__1

  def test_putobject_OP_INT2FIX_O_0_C_
    test_results { |k| def k._jit; 0; end }
  end

  def test_putobject_OP_INT2FIX_O_1_C_
    test_results { |k| def k._jit; 1; end }
  end

  private

  def test_results(*args)
    klass = Class.new
    yield(klass) # This doesn't use block to define :_jit to make it JIT-ed

    expected = klass._jit(*args)
    TEST_ITERATIONS.times do
      assert_equal expected, klass._jit(*args)
    end
  end
end
