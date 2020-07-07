#!/bin/sh
find ./ -type f -iname "*.iml" -exec rm -fv {} \;
rm -rf build .gradle .idea ./app/build

