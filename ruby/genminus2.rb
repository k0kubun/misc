if ARGV.length != 6
  $stderr.puts <<~EOS
    Usage:
      #{$0} name a_script b_script script loop-iter copy-iter

    Example:
      #{$0} plus_string_2m "'a'" "'b'" "a+b" 2000000 120
  EOS
  exit 1
end

name = ARGV[0]
a_script = ARGV[1]
b_script = ARGV[2]
script = ARGV[3]
loop_iter = Integer(ARGV[4])
copy_iter = Integer(ARGV[5]) # note that JIT_ISEQ_SIZE_THRESHOLD is only 1000.
per_line = 32

require 'shellwords'
puts "# #{$0.shellescape} #{ARGV.shelljoin}"

require 'erb'
puts ERB.new(<<-'EOS', trim_mode: '%').result(binding) # requires ruby 2.6
prelude: |
  def x
    a = <%= a_script %>
    b = <%= b_script %>
    i = <%= loop_iter %>
    while i > 0
% (copy_iter / per_line).times do
      <%= per_line.times.map { script }.join(';') %>
% end
% if (copy_iter % per_line) > 0
      <%= (copy_iter % per_line).times.map { script }.join(';') %>
% end
      i -= 1
    end
  end

  x # use --jit-min-calls=1
  if RubyVM::MJIT.enabled?
    RubyVM::MJIT.pause(wait: true) # use r64250+ [Feature #14954]
  end

benchmark:
  <%= name %>: x

loop_count: 1
EOS
