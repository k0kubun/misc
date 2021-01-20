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
  def user_repos(username)
    source_repos = client.repos(username).reject { |r| r[:fork] || r[:archived] }
    source_repos.map { |r| r[:full_name] }
  end

  # user_repos can't find private repositories for organization
  # @return [Array<String>] full_names
  def org_repos(orgname)
    source_repos = client.org_repos(orgname).reject { |r| r[:fork] || r[:archived] }
    source_repos.map { |r| r[:full_name] }
  end

  private

  def client
    @client ||= Octokit::Client.new(access_token: ENV.fetch('GITHUB_TOKEN'))
  end
end

repos = []
#repos += RepositoryFinder.new.org_repos('treasure-data').sort
repos += RepositoryFinder.new.user_repos('k0kubun').sort

repos.each do |full_name|
  script = "ghq get github.com:#{full_name}"
  puts ">>> #{script}"
  system(script) || $stderr.puts("Failed to clone #{full_name}!")
end
