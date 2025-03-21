# Usa una imagen base de Java (por ejemplo, OpenJDK 17)
FROM openjdk:17-jdk-slim
# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

ARG JAR_FILE=target/angelesyvalientes-0.0.1.jar

# Copia el archivo JAR de tu aplicación al contenedor
COPY ${JAR_FILE} app.jar

# Expone el puerto en el que se ejecuta tu aplicación
EXPOSE 8081

# Comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]


# Usa una imagen base con Java 17
FROM openjdk:17-jdk-slim



