# Dockerfile aplicação Java com Java 21
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/tdinheiro-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080
