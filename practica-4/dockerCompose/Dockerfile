# Etapa de construcción
FROM gradle:7.2-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon

# Etapa de ejecución
FROM eclipse-temurin:17-alpine
RUN mkdir /app
EXPOSE 8080
COPY --from=build /home/gradle/src/build/libs/*.jar /app/application.jar
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
