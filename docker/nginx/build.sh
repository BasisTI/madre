#!/bin/sh

set -eu

tar zcvf code.tar.gz -C frontend/

mv code.tar.gz docker/nginx/
