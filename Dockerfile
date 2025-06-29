
FROM openjdk:17-jdk-alpine
EXPOSE 8082
COPY target/Foyer-3.1.5.jar Foyer-3.1.5.jar
ENTRYPOINT ["java", "-jar", "/Foyer-3.1.5.jar"]

