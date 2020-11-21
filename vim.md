## Build vim/vim

```bash
ghq get vim
cd vim
git checkout v8.2.2022
# Here, uncomment the first 2 dev-src of /etc/apt/sources.list here for build-dep
sudo apt-get update
sudo apt build-dep vim-gtk
sudo apt install git build-essential ncurses-dev lua5.3 luajit python3-dev ruby-dev
make distclean
./configure --with-features=huge --with-x --enable-multibyte --enable-luainterp=dynamic --enable-gpm --enable-cscope --enable-fontset --enable-fail-if-missing --prefix=/usr/local --enable-pythoninterp=dynamic --enable-rubyinterp=dynamic
make -j8
# apt remove vim-gtk3 here if /usr/bin/vim is still there
sudo make install
```
