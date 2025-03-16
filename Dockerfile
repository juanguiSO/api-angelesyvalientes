FROM openjdk:17-slim

WORKDIR /app

COPY target/angelesyvalientes-0.0.1-SNAPSHOT.jar /app/api-angelesyvalientes.jar

EXPOSE 3000

CMD ["java", "-jar", "/app/api-angelesyvalientes.jar"]