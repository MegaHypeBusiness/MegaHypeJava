# Use a imagem oficial do OpenJDK 17
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR gerado pelo build do projeto para o contêiner
COPY target/MegaHypeJavaVercel-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponha a porta na qual a aplicação Spring Boot vai rodar (geralmente a 8080)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
