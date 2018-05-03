#!/bin/sh

set -eu

cd frontend/
npm install
npm run build --prod

cd dist
tar zcvf dist.tar.gz *
mv dist.tar.gz ../../docker/nginx/
