## Build vim/vim v8.0.0606

```
sudo apt install git build-essential ncurses-dev lua5.2 lua5.2-dev luajit python-dev ruby-dev
make distclean
./configure --with-features=huge --enable-multibyte --enable-luainterp=dynamic --enable-gpm --enable-cscope --enable-fontset --enable-fail-if-missing --prefix=/usr/local --enable-pythoninterp=dynamic --enable-rubyinterp=dynamic
make -j8
```
