FROM basisti/build-frontend-npm:node-10.16.0 AS builder

WORKDIR /var/lib/jenkins/frontend

ADD code.tar.gz .

ENV NG_CLI_ANALYTICS=ci

RUN set -eux ; \
      npm install && npm run build --prod

FROM nginx

COPY --from=builder /var/lib/jenkins/frontend/dist/ /usr/share/nginx/html/
