## memo
```
setxkbmap -layout us
apt install vim-gnome
```

## elasticsearch 2

```
sudo apt-get update
sudo apt install -y docker.io
sudo docker run -d --net=host elasticsearch:2
```

## bundler
```
bundle config --global disable_version_check true
```

## gnome
gsettings set org.gnome.desktop.interface cursor-blink false
gsettings set org.gnome.shell disable-extension-version-validation true
gsettings set org.gnome.desktop.interface clock-show-date true
