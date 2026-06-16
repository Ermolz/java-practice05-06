FROM eclipse-temurin:21-jre

WORKDIR /app

COPY build/libs/practice5-6-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
