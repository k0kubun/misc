require 'haml'
require 'tempfile'

haml_code = File.read("#{__dir__}/templates/view.haml")
haml_src  = Haml::Engine.new(haml_code, format: :html5, ugly: true, escape_html: true).compiler.precompiled_with_ambles([])

code = [
  'require "lineprof"',
  'require "hamlit"',

  'Lineprof.profile(/./) do',
    '3000.times do',
      haml_src,
    'end',
  'end',
].join("\n")

file = Tempfile.create('haml_src')
file.write(code)
file.close

system("bundle exec ruby #{file.path}")
