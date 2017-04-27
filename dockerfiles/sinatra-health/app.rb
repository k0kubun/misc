require 'sinatra'

set :port, ENV.fetch('PORT', 8080)
set :bind, '0.0.0.0'
set :server, :puma

get '/' do
  'ok'
end
