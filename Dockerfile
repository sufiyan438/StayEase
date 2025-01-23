FROM openjdk:23-jdk-slim AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew gradlew.bat /app/

COPY gradle/wrapper /app/gradle/wrapper

COPY src /app/src

RUN ./gradlew build -x test --no-daemon

FROM openjdk:23-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
