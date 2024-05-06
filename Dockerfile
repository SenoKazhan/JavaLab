FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
COPY ./src src/
COPY ./pom.xml pom.xml
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine
COPY --from=build /target/country-code-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]