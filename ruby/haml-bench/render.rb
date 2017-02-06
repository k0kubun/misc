require 'haml'
require 'faml'
require 'hamlit'

haml_code = File.read("#{__dir__}/templates/view.haml")

haml_src   = Haml::Engine.new(haml_code, format: :html5, ugly: true, escape_html: true).compiler.precompiled_with_ambles([])
# faml_src   = Faml::Engine.new.call(haml_code)
hamlit_src = Hamlit::Engine.new.call(haml_code)

puts "=======================[haml]==============================="
puts eval(haml_src)
puts
# puts "=======================[faml]==============================="
# puts Pry.Code(faml_src).highlighted
# puts
puts "=======================[hamlit]============================="
puts eval(hamlit_src)
puts
