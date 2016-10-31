#!/bin/sh

set -ex

mitamae_version="$1"

rm -f /tmp/mitamae-*

curl -o /tmp/mitamae-linux.tar.gz -fL "https://github.com/k0kubun/mitamae/releases/download/v${mitamae_version}/mitamae-x86_64-linux.tar.gz"
curl -o /tmp/mitamae-darwin.zip -fL   "https://github.com/k0kubun/mitamae/releases/download/v${mitamae_version}/mitamae-x86_64-darwin.zip"

shasum -a 256 /tmp/mitamae-linux.tar.gz
shasum -a 256 /tmp/mitamae-darwin.zip
