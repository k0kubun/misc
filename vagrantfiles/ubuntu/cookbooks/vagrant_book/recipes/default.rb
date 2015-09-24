execute "apt-get update"
package "apache2"
execute "rm -rf /var/www"
link "/var/www" do
  to "/vagrant"
end
