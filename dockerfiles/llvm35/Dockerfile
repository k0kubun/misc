FROM alpine:3.4

RUN apk --update add --virtual build-dependencies \
  git gcc make autoconf libtool

RUN cd /tmp \
  && wget http://llvm.org/releases/3.5.2/llvm-3.5.2.src.tar.xz \
  && wget http://llvm.org/releases/3.5.2/cfe-3.5.2.src.tar.xz \
  && wget http://llvm.org/releases/3.5.2/compiler-rt-3.5.2.src.tar.xz

RUN apk add xz
RUN cd /tmp \
  && xz -dc llvm-3.5.2.src.tar.xz | tar xvf - \
  && xz -dc cfe-3.5.2.src.tar.xz | tar xvf - \
  && xz -dc compiler-rt-3.5.2.src.tar.xz | tar xvf -

RUN mkdir ~/llvm \
  && mv /tmp/llvm-3.5.2.src ~/llvm/ \
  && mv /tmp/cfe-3.5.2.src ~/llvm/llvm-3.5.2.src/tools/clang \
  && mv /tmp/compiler-rt-3.5.2.src ~/llvm/llvm-3.5.2.src/projects/compiler-rt

CMD /bin/ash
