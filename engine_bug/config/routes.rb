Rails.application.routes.draw do
  namespace :a do
    mount Sample::Engine => '/sample'
  end

  namespace :b do
    mount Sample::Engine => '/sample'
  end
end
