FROM maven:3.9.6-eclipse-temurin-17-alpine as builder

COPY ./src /app/src/
COPY ./pom.xml /app/pom.xml

WORKDIR /app

RUN mvn clean package -DskipTests

FROM openjdk:17
COPY --from=builder /app/target/*.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]