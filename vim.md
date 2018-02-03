## Build vim/vim
v7.4.2367
8.0.1161

```
sudo apt install git build-essential ncurses-dev lua5.2 lua5.2-dev luajit python-dev ruby-dev
make distclean
sudo apt build-dep vim
./configure --with-features=huge --with-x --enable-multibyte --enable-luainterp=dynamic --enable-gpm --enable-cscope --enable-fontset --enable-fail-if-missing --prefix=/usr/local --enable-pythoninterp=dynamic --enable-rubyinterp=dynamic
make -j8
```
