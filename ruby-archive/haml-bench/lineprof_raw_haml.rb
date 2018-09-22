require 'haml'
require 'tempfile'

code = <<~CODE
  require "lineprof"
  require "hamlit"

  Lineprof.profile(/./) do
    3000.times do
#{File.read('haml_compiled.rb')}
    end
  end
CODE

file = Tempfile.create('haml_src')
file.write(code)
file.close

system("bundle exec ruby #{file.path}")
