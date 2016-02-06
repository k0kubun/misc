FROM ubuntu:14.04
MAINTAINER k0kubun

RUN apt-get update && apt-get install -y git build-essential autoconf
RUN git clone https://github.com/jemalloc/jemalloc && git clone https://github.com/antirez/disque

RUN cd /jemalloc && autoconf && ./configure && make && make install_bin install_include install_lib
RUN cd /disque && make

CMD ["/disque/src/disque-server"]
