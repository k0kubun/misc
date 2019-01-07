#!/usr/bin/env ruby

begin
  gem 'octokit'
rescue Gem::LoadError
  Gem.install('octokit')
end
require 'octokit'


client = Octokit::Client.new(access_token: ENV.fetch('GITHUB_TOKEN'))
client.auto_paginate = true

repos = client.org_repos('treasure-data').to_a
repos.each_with_index do |repository, index|
  result = client.delete_subscription(repository[:full_name])
  puts "[#{index + 1}/#{repos.size}] #{repository[:full_name]} => #{result}"
end
