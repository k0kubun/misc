FROM ubuntu:16.04
MAINTAINER Takashi Kokubun <takashikkbn@gmail.com>

# Ruby build dependency for ubuntu and git
# https://github.com/rbenv/ruby-build/wiki#suggested-build-environment
RUN apt-get update && apt-get install -y \
  autoconf bison build-essential libssl-dev libyaml-dev libreadline6-dev \
  zlib1g-dev libncurses5-dev libffi-dev libgdbm3 libgdbm-dev \
  git

# Build ruby
RUN git clone https://github.com/ruby/ruby /ruby && cd /ruby && autoreconf
RUN mkdir /ruby-build && cd /ruby-build && ../ruby/configure
WORKDIR /ruby-build
RUN make -j4 && make install
