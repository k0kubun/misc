require 'hamlit'
require 'tempfile'

haml_code = File.read("#{__dir__}/templates/view.haml")
haml_src  = Hamlit::Engine.new.call(haml_code)

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
