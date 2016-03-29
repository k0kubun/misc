require 'vagrant-digitalocean'

Vagrant.configure(2) do |config|
  config.vm.define :trusty do |c|
    c.vm.provider :digital_ocean do |provider, override|
      override.vm.box = 'digital_ocean'
      override.vm.box_url = 'https://github.com/smdahlen/vagrant-digitalocean/raw/master/box/digital_ocean.box'

      provider.token = ENV['DIGITALOCEAN_TOKEN']
      provider.image = 'ubuntu-14-04-x64' # ubuntu
      provider.region = 'nyc3'
      provider.size = '512mb'
    end
  end
end
