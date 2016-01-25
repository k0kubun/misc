require 'sinatra'

helpers do
  def html(name)
    File.read("#{name}.html")
  end

  def json(name)
    File.read("#{name}.json")
  end
end

get '/' do
  html :index
end

get '/api/comments' do
  json :comments
end
