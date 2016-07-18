FROM alpine:3.4

RUN apk --update add --virtual build-dependencies \
  git gcc make autoconf libtool

RUN apk add llvm clang
RUN apk add flex bison
