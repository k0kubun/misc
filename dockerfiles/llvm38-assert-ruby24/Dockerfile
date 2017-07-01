FROM k0kubun/llvm38-assert
MAINTAINER Takashi Kokubun <takashikkbn@gmail.com>

# Build dependency for ubuntu, and ruby (because not ruby-build)
# https://github.com/rbenv/ruby-build/wiki#suggested-build-environment
RUN apt-get update && apt-get install -y \
  autoconf \
  bison \
  build-essential \
  libssl-dev \
  libyaml-dev \
  libreadline6-dev \
  zlib1g-dev \
  libncurses5-dev \
  libffi-dev \
  libgdbm3 \
  libgdbm-dev \
  ruby

COPY ruby /ruby
RUN cd /ruby && autoreconf && ./configure && make -j4 && make install

RUN gem install bundler
