#!/bin/bash

sed -i 's|${ENDERECO_API}|'"$ENDERECO_API"'|' /etc/nginx/conf.d/default.conf
sed -i 's|${BASE_HREF}|'"$BASE_HREF"'|' /etc/nginx/conf.d/default.conf
