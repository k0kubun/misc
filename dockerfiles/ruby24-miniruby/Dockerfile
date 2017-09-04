FROM k0kubun/ruby24:latest
MAINTAINER k0kubun

RUN git clone --branch v2_4_1 https://github.com/ruby/ruby /ruby && cd /ruby && autoreconf && ./configure && make -j4 && make install
WORKDIR /ruby
