FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /build

COPY pom.xml mvnw ./
COPY .mvn .mvn

# я не хочу разбираться с этим дерьмом
# COPY authService authService
# COPY jwtProcessing jwtProcessing
COPY ./ ./

RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    ./mvnw -B -nsu -pl authService -am dependency:go-offline
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    ./mvnw -pl authService -am clean package

FROM quay.io/wildfly/wildfly:latest-jdk17

# adding posgresql jdbc driver
USER root
RUN mkdir -p /opt/jboss/wildfly/modules/org/postgresql/main/
ADD https://jdbc.postgresql.org/download/postgresql-42.7.3.jar /opt/jboss/wildfly/modules/org/postgresql/main/postgresql.jar
COPY ./authService/postgresql-module.xml /opt/jboss/wildfly/modules/org/postgresql/main/module.xml
RUN chown -R jboss:jboss /opt/jboss/wildfly/modules/org

USER jboss
COPY --chown=jboss:jboss ./authService/standalone.xml /opt/jboss/wildfly/standalone/configuration/
COPY --from=build --chown=jboss:jboss /build/authService/target/*.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080 8080