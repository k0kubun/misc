FROM ubuntu:16.04
MAINTAINER k0kubun

# Build dependency for ubuntu:
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
  libgdbm-dev
