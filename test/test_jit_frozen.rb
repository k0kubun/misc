# frozen_string_literal: true
require 'test/unit'

class TestJIT < Test::Unit::TestCase
  TEST_ITERATIONS = 5

  def test_freezestring
    test_results('b') { |k| def k._jit(b); "a#{b}"; end }
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
