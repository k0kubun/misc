require 'benchmark/ips'
require 'haml'
require 'faml'
require 'hamlit'

Benchmark.ips do |x|
  haml_code = File.read("#{__dir__}/templates/view.haml")
  context = Object.new

  Haml::Engine.new(haml_code, format: :html5, ugly: true, escape_html: true).def_method(context, :run_haml)
  context.instance_eval %Q[
    def run_faml; #{Faml::Engine.new.call(haml_code)}; end
    def run_hamlit; #{Hamlit::Engine.new.call(haml_code)}; end
  ]

  x.report("haml #{Haml::VERSION}")     { context.run_haml }
  x.report("faml #{Faml::VERSION}")     { context.run_faml }
  x.report("hamlit #{Hamlit::VERSION}") { context.run_hamlit }
  x.compare!
end
