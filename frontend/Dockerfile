FROM node:lts-alpine AS build-certs
WORKDIR /
RUN apk update && \
    apk add --no-cache openssl
COPY ./gen_certs.sh .
RUN source ./gen_certs.sh

FROM node:lts-alpine AS build-main-page
WORKDIR /app

COPY ./static .
RUN npm install && npm run build

FROM nginx:latest

COPY ./nginx-conf/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-certs /nginx-conf/certs /etc/nginx/certs
COPY --from=build-main-page /app/build /var/www/html/

EXPOSE 80
EXPOSE 443