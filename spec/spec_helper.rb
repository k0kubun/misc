require 'serverspec'

set :backend, :docker
set :docker_uri, ENV['DOCKER_HOST']
set :docker_container, ENV['TARGET_CONTAINER_ID']
