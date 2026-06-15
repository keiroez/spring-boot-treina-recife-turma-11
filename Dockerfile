# syntax=docker/dockerfile:1

# ---------- Build stage ----------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

# Baixa as dependências primeiro (camada cacheável enquanto o pom não muda)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Compila e empacota (testes ignorados: exigem MySQL + variáveis de ambiente)
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Runtime stage ----------
FROM eclipse-temurin:25-jre AS runtime
WORKDIR /app

# Usuário não-root
RUN useradd --system --uid 1001 spring
USER spring

COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Variáveis obrigatórias em runtime: DB_URL, DB_USERNAME, DB_PASSWORD, JWT_SECRET
ENTRYPOINT ["java", "-jar", "app.jar"]
