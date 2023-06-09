FROM openjdk:17-jdk-alpine

WORKDIR app/

COPY target/discord-adapter-api-0.0.1-SNAPSHOT.jar /app/discord-adapter-api-0.0.1-SNAPSHOT.jar

CMD ["java", "-jar", "/app/discord-adapter-api-0.0.1-SNAPSHOT.jar"]
