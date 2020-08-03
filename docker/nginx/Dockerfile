FROM basisti/build-frontend-npm:node-10.16.0 AS builder

WORKDIR /usr/src/app

COPY . .

ENV NG_CLI_ANALYTICS=ci

RUN set -eux ; \
      npm install && npm run build --prod

FROM nginx

COPY --from=builder /usr/src/app/dist/ /usr/share/nginx/html/
