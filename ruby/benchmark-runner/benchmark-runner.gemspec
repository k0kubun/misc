
lib = File.expand_path("../lib", __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require "benchmark/runner/version"

Gem::Specification.new do |spec|
  spec.name          = "benchmark-runner"
  spec.version       = Benchmark::Runner::VERSION
  spec.authors       = ["Takashi Kokubun"]
  spec.email         = ["takashikkbn@gmail.com"]

  spec.summary       = %q{Runner sub component of benchmark_driver}
  spec.description   = %q{Runner sub component of benchmark_driver}
  spec.homepage      = "https://github.com/k0kubun/benchmark_driver"
  spec.license       = "MIT"

  spec.files         = `git ls-files -z`.split("\x0").reject do |f|
    f.match(%r{^(test|spec|features)/})
  end
  spec.bindir        = "exe"
  spec.executables   = spec.files.grep(%r{^exe/}) { |f| File.basename(f) }
  spec.require_paths = ["lib"]

  spec.add_development_dependency "bundler", "~> 1.16"
  spec.add_development_dependency "rake", "~> 10.0"
end
