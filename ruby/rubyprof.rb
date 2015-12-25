require 'ruby-prof'

RubyProf.start
result = RubyProf.stop
RubyProf::CallStackPrinter.new(result).print(STDOUT, min_percent: 2)
