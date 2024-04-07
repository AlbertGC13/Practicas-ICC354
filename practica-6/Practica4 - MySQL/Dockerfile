FROM gradle:8.2.1-jdk17-jammy AS build

LABEL authors="John & Albert"

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle bootjar --no-daemon

FROM eclipse-temurin:17.0.8.1_1-jre-alpine

ENV NOMBRE_APP = 'practica-mock'

ENV DB_NAME=practica_mock

ENV spring.datasource.username='root'

ENV spring.datasource.password='admin'

VOLUME /tmp

EXPOSE 8080

COPY --from=build /home/gradle/src/build/libs/*.jar mi_app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","mi_app.jar"]