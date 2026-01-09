FROM node:lts-alpine AS build-certs
WORKDIR /
RUN apk update && \
    apk add --no-cache openssl
COPY ./gen_certs.sh .
RUN source ./gen_certs.sh

FROM node:lts-alpine AS build-front
WORKDIR /app

COPY ./monorepo .
RUN npm install && npm run build

FROM nginx:latest

COPY ./nginx-conf/nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build-certs /nginx-conf/certs /etc/nginx/certs
COPY --from=build-front /app/services/login/dist/ /var/www/html/login/
COPY --from=build-front /app/services/host/dist/ /var/www/html/host/
COPY --from=build-front /app/services/main/dist/ /var/www/html/main/
COPY --from=build-front /app/services/plot/dist/ /var/www/html/plot/
COPY --from=build-front /app/services/group/dist/ /var/www/html/group/
COPY --from=build-front /app/services/history/dist/ /var/www/html/history/
COPY --from=build-front /app/services/topbar/dist/ /var/www/html/topbar/

EXPOSE 80
EXPOSE 443