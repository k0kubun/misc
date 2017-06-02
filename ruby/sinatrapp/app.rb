require 'sinatra'
require 'haml'

set :port, ENV.fetch('PORT', 3000)
set :bind, '0.0.0.0'
set :server, :puma

get '/' do
  haml :index
end
