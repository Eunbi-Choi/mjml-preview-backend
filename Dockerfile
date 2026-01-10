# Build stage
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build -x test

# Run stage (JRE만)
FROM amazoncorretto:17-alpine-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ARG PROFILES
ARG ENV
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]
