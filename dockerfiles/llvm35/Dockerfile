FROM ubuntu:14.04
MAINTAINER Takashi Kokubun <takashikkbn@gmail.com>

RUN apt-get update && apt-get install -y \
  git gcc make autoconf libtool pkg-config build-essential python

ENV LLVM_SRC /tmp/llvm
ENV LLVM_BUILD /tmp/llvm-build

RUN git clone --depth=1 -b release_35 http://llvm.org/git/llvm.git $LLVM_SRC && \
  git clone --depth=1 -b release_35 http://llvm.org/git/clang.git $LLVM_SRC/tools/clang && \
  mkdir $LLVM_BUILD && cd $LLVM_BUILD && \
  $LLVM_SRC/configure \
    --disable-assertions \
    --disable-bindings \
    --disable-docs \
    --enable-optimized \
    --enable-targets=host-only \
    --enable-shared && \
  make -j4 && make install && \
  rm -rf $LLVM_SRC $LLVM_BUILD
