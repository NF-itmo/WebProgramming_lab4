mkdir -p nginx-conf/certs
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout nginx-conf/certs/selfsigned.key \
  -out nginx-conf/certs/selfsigned.crt \
  -subj "/C=RU/ST=Moscow/L=Moscow/O=Dev/OU=IT/CN=localhost"
