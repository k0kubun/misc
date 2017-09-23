require 'test/unit'

class TestJIT < Test::Unit::TestCase
  TEST_ITERATIONS = 5

  def test_put_object
    test_results { |k| def k._jit; 42; end }
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
