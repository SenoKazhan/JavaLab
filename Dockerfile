FROM eclipse-temurin:17-alpine
COPY /target/country-code-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]