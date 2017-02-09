require 'pry'
require 'haml'
require 'faml'
require 'hamlit'

haml_code = File.read("#{__dir__}/templates/view.haml")

buffer_options = { ugly: true, escape_html: true }
if Haml::Options.respond_to?(:buffer_defaults)
  Haml::Options.buffer_defaults.merge!(buffer_options)
end
haml_src   = Haml::Engine.new(haml_code, format: :html5, ugly: true, escape_html: true).compiler.precompiled_with_ambles([])
# faml_src   = Faml::Engine.new.call(haml_code)
hamlit_src = Hamlit::Engine.new.call(haml_code)

puts "=======================[haml]==============================="
puts Pry.Code(haml_src).highlighted
puts
# puts "=======================[faml]==============================="
# puts Pry.Code(faml_src).highlighted
# puts
#puts "=======================[hamlit]============================="
#puts Pry.Code(hamlit_src).highlighted
#puts
