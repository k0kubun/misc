#!/bin/sh

set -ex

itamae_version="0.2.1"

rm -f /tmp/itamae-mruby-*

curl -o /tmp/itamae-mruby-linux.tar.gz -fL "https://github.com/k0kubun/itamae-mruby/releases/download/v${itamae_version}/itamae-x86_64-linux.tar.gz"
curl -o /tmp/itamae-mruby-darwin.zip -fL   "https://github.com/k0kubun/itamae-mruby/releases/download/v${itamae_version}/itamae-x86_64-darwin.zip"

shasum -a 256 /tmp/itamae-mruby-linux.tar.gz
shasum -a 256 /tmp/itamae-mruby-darwin.zip
