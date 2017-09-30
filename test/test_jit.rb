require 'test/unit'

class TestJIT < Test::Unit::TestCase
  TEST_ITERATIONS = 5

  def test_nop
    test_results { |k| def k._jit; nil rescue true; end }
  end

  # def test_getlocal
  # def test_setlocal

  def test_getspecial
    test_results { |k| def k._jit; $1; end }
  end

  # def test_setspecial
  #   test_results { |k| def k._jit; true if nil.nil?..nil.nil?; end }
  # end

  def test_getinstancevariable
    test_results { |k| def k._jit; @a; end }
  end

  def test_setinstancevariable
    test_results { |k| def k._jit; @a = 2; @a; end }
  end

  def test_getclassvariable
    test_results { |k| def k._jit; @@a = 1; @@a; end }
  end

  def test_setclassvariable
    test_results { |k| def k._jit; @@b = 2; end }
  end

  def test_getconstant
    test_results { |k| def k._jit; RubyVM::InstructionSequence; end }
  end

  def test_setconstant
    # unable to test... due to dynamic constant assignment
    # test_results { |k| def k._jit; Class.new::X = true; end }
  end

  def test_getglobal
    $jit_gvar_get = 1
    test_results { |k| def k._jit; $jit_gvar_get; end }
  end

  def test_setglobal
    test_results { |k| def k._jit; $jit_gvar_set = 2; end }
  end

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

  def test_putspecialobject
    test_results do |k|
      def k._jit;
        def __putspecialobject
        end
      end
    end
  end

  def test_putiseq
    test_results do |k|
      def k._jit;
        def __putiseq
        end
      end
    end
  end

  def test_putstring
    test_results { |k| def k._jit; 'hello'; end }
  end

  def test_concatstrings
    test_results('b') { |k| def k._jit(b); "a#{b}"; end }
  end

  def test_tostring
    test_results('b') { |k| def k._jit(b); "a#{b}"; end }
  end

  def test_toregexp
    test_results { |k| def k._jit; /#{true}/; end }
  end

  def test_intern
    test_results { |k| def k._jit; :"a#{2}"; end }
  end

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

  def test_expandarray
    test_results { |k| def k._jit; q, (w, e), r = 1, [2, 3], 4; e == 3; end }
  end

  def test_concatarray
    test_results { |k| def k._jit; ["t", "r", *x = "u", "e"].join; end }
  end

  def test_splatarray
    test_results { |k| def k._jit; [*(1..2)]; end }
    test_results { |k| def k._jit; [*'']; end }
  end

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

  def test_dupn
    test_results([true]) do |k|
      def k._jit(x);
        x[0] ||= nil
        x[0]
      end
    end
  end

  def test_swap
    test_results { |k| def k._jit; {}['true'] = true; end }
  end

  def test_reverse
    test_results { |k| def k._jit; q, (w, e), r = 1, [2, 3], 4; e == 3; end }
  end

  def test_reput
    # TODO: I'm not sure how to generate this insn...
  end

  def test_topn
    test_results { |k| def k._jit; {}['true'] = true; end }
  end

  def test_setn
    test_results { |k| def k._jit; [nil][0] = 1; end }
  end

  def test_adjuststack
    test_results([true]) do |k|
      def k._jit(x);
        x[0] ||= nil
        x[0]
      end
    end
  end

  def test_defined
    test_results { |k| def k._jit; defined?(@a); end }
    test_results { |k| def k._jit; @a = 1; defined?(@a); end }
  end

  def test_checkmatch
    test_results(1) do |k|
      def k._jit(a)
        case a
        when 1
          2
        else
          3
        end
      end
    end
  end

  def test_checkkeyword
    test_results(x: true) do |k|
      def k._jit(x: rand)
        x
      end
    end
  end

  def test_trace
    test_results do |k|
      def k._jit
        1+2
        3+4
      end
    end
  end

  if ENV['TEST_TRACE2'] == '1'
    def test_trace2
      require 'coverage'
      Coverage.start
      RubyVM::InstructionSequence.compile(<<~RUBY).eval
        module TestJIT::TestTrace2
          def self._jit
            1+2
            3+4
          end
        end
      RUBY
      test_jit(TestTrace2)
    end
  end

  # def test_defineclass

  def test_send
    test_results do |k|
      def k._jit
        1.times { 2 }
      end
    end

    test_results do |k|
      def k._jit
        [1, 2].inject([]) do |ary, i|
          ary << i
        end
      end
    end

    test_results do |k|
      def k._jit
        block = proc { 3 }
        1.times.map(&block)
      end
    end
  end

  def test_opt_str_freeze
    test_results { |k| def k._jit; 'str'.freeze; end }
  end

  def test_opt_str_uminus
    test_results { |k| def k._jit; -'str'; end }
  end

  def test_opt_newarray_max
    test_results(1, 2) { |k| def k._jit(a, b); [a, b].max; end }
  end

  def test_opt_newarray_min
    test_results(1, 2) { |k| def k._jit(a, b); [a, b].min; end }
    test_results(1, 2) { |k| def k._jit(a, b); [[a, b].min, [a, b, a].max].min; end }
  end

  def test_opt_send_without_block
    test_results { |k| def k._jit; print; end }
    test_results { |k| def k._jit; [1, 2].reverse; end }
    test_results { |k| def k._jit; 2 ** 3; end }
    test_results { |k| def k._jit; false || 2.even?; end }
    test_results { |k| def k._jit; [nil].push(3); end }
    test_results { |k| def k._jit; [] + [nil].push(3); end }
    test_results { |k| def k._jit; [0] + [].push(1,2,3).push(4,5,6); end }
  end

  def test_invokesuper
    mod = Module.new {
      def _jit
        super + 2
      end
    }
    klass = Class.new {
      prepend mod
      def _jit
        1
      end
    }
    object = klass.new
    test_jit(object)

    mod = Module.new {
      def _jit
        block = proc { 3 }
        super(&block) + 2
      end
    }
    klass = Class.new {
      prepend mod
      def _jit
        1 + yield
      end
    }
    object = klass.new
    test_jit(object)
  end

  def test_invokeblock
    klass = Class.new {
      def _jit
        1 + yield + 3
      end
    }
    object = klass.new
    test_jit(object) { 2 }
  end

  def test_leave
    test_results { |k| def k._jit; nil; end }
  end

  def test_throw
    # not sure how to test on method...
  end

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

    test_results do |k|
      def k._jit
        i = 0
        while i< 60
          i += 1
        end
        i
      end
    end
  end

  def test_branchif
    test_results { |k| def k._jit; 1+2 || false; end }
    test_results(false) { |k| def k._jit(a); 1+1 while a; end }
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

  def test_branchiftype
    test_results('b') { |k| def k._jit(b); "a#{b}"; end }
  end

  def test_getinlinecache
    test_results { |k| def k._jit; Struct; end }
  end

  def test_setinlinecache
    test_results { |k| def k._jit; Struct; end }
  end

  # def test_once
  #   test_results { |k| def k._jit; /#{true}/o =~ "true"; end }
  # end

  def test_opt_case_dispatch
    [1, 2].each do |aa|
      test_results(aa) do |k|
        def k._jit(a)
          case a
          when 1
            2
          else
            3
          end
        end
      end
    end

    test_results(1) do |k|
      def k._jit(a)
        1000 + case a
               when 1
                 100
               end
      end
    end

    test_results(3, 2) do |k|
      def k._jit(a, b)
        1000 + case a
               when 1
                 100
               when 2
                 200
               when 3
                 300 + case b
                       when 4
                         400
                       else
                         500
                       end + 600
               end + 700
      end
    end
  end

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

  def test_opt_aref
    test_results { |k| def k._jit; [1, 2, 3][1]; end }
  end

  def test_opt_aset
    test_results { |k| def k._jit; [nil][0] = 1; end }
  end

  def test_opt_aset_with
    test_results { |k| def k._jit; {}['true'] = true; end }
  end

  def test_opt_aref_with
    test_results(100) do |k|
      def k._jit(x)
        { 'true' => x }['true']
      end
    end
  end

  def test_opt_length
    test_results { |k| def k._jit; [].length; end }
    test_results { |k| def k._jit; [1, nil, false].length; end }
  end

  def test_opt_size
    test_results { |k| def k._jit; [].size; end }
    test_results { |k| def k._jit; [1, nil, false].size; end }
  end

  def test_opt_empty_p
    test_results { |k| def k._jit; [].empty?; end }
    test_results { |k| def k._jit; [1, nil, false].empty?; end }
  end

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

  def test_catch_table
    test_results do |k|
      def k._jit
        {}.fetch(:invalid)
        false
      rescue KeyError
        true
      end
    end
  end

  private

  def test_results(*args)
    klass = Class.new
    yield(klass) # This doesn't use block to define :_jit to make it JIT-ed
    test_jit(klass, *args)
  end

  def test_jit(klass, *args, &block)
    expected = klass._jit(*args, &block)
    TEST_ITERATIONS.times do
      assert_equal expected, klass._jit(*args, &block)
    end
  end
end
