require 'haml'
require 'tempfile'

haml_code = File.read("#{__dir__}/templates/view.haml")
haml_src  = Haml::Engine.new(haml_code, format: :html5, ugly: true, escape_html: true).compiler.precompiled_with_ambles([])

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
