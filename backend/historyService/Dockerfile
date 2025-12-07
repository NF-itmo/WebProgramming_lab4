FROM eclipse-temurin:17-jdk-alpine AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN --mount=type=cache,target=/root/.m2,sharing=locked \
  ./mvnw -B -nsu dependency:resolve dependency:resolve-plugins

COPY src src
RUN --mount=type=cache,target=/root/.m2,ro \
  ./mvnw clean package

FROM quay.io/wildfly/wildfly:latest-jdk17

# adding posgresql jdbc driver
USER root
RUN mkdir -p /opt/jboss/wildfly/modules/org/postgresql/main/
ADD https://jdbc.postgresql.org/download/postgresql-42.7.3.jar /opt/jboss/wildfly/modules/org/postgresql/main/postgresql.jar
COPY ./postgresql-module.xml /opt/jboss/wildfly/modules/org/postgresql/main/module.xml
RUN chown -R jboss:jboss /opt/jboss/wildfly/modules/org

USER jboss
COPY --chown=jboss:jboss ./standalone.xml /opt/jboss/wildfly/standalone/configuration/
COPY --from=build --chown=jboss:jboss target/*.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080 8080