FROM eclipse-temurin:17-jdk-alpine AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    ./mvnw -B -nsu dependency:go-offline

COPY src src
RUN --mount=type=cache,target=/root/.m2,sharing=locked \
    ./mvnw -B -nsu clean install