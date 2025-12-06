FROM node:lts-alpine AS build
WORKDIR /static

COPY ./static .
RUN npm install && npm run build

WORKDIR /
RUN apk update && \
    apk add --no-cache openssl
COPY ./gen_certs.sh .
RUN source ./gen_certs.sh

FROM nginx:latest

COPY ./nginx-conf/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /nginx-conf/certs /etc/nginx/certs
COPY --from=build /static/build /var/www/html/

EXPOSE 80
EXPOSE 443