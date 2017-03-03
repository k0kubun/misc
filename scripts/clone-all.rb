#!/usr/bin/env ruby

begin
  gem 'octokit'
rescue Gem::LoadError
  Gem.install('octokit')
end
require 'octokit'

class RepositoryFinder
  def initialize
    client.auto_paginate = true
  end

  # @return [Array<String>] full_names
  def source_repos(username)
    source_repos = client.repos(username).reject { |r| r[:fork] }
    source_repos.map { |r| r[:full_name] }
  end

  private

  def client
    @client ||= Octokit::Client.new(access_token: ENV.fetch('GITHUB_TOKEN'))
  end
end

full_names = RepositoryFinder.new.source_repos('treasure-data')
full_names.each do |full_name|
  script = "ghq get github.com:#{full_name}"
  puts ">>> #{script}"
  system(script) || $stderr.puts("Failed to clone #{full_name}!")
end
