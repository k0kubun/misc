FROM ubuntu:14.04
MAINTAINER k0kubun

RUN apt-get update && apt-get install -y git
RUN git clone https://github.com/rbenv/ruby-build.git
RUN cd /ruby-build && ./install.sh

# ruby-build uses wget to fetch tarball
RUN apt-get install -y wget

# Build dependency for ubuntu:
# https://github.com/rbenv/ruby-build/wiki#suggested-build-environment
RUN apt-get install -y \
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
  libgdbm-dev
