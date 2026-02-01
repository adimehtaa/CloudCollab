# Build stage
FROM maven:3.9-eclipse-temurin-25 AS builder
WORKDIR /app

# Copy dependency files first for better caching
COPY pom.xml .
COPY .mvn/ .mvn
COPY mvnw .

# Download dependencies (cached if pom.xml unchanged)
RUN ./mvnw dependency:go-offline

# Copy source code and build
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Runtime stage - Java 25 JRE
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy only the JAR file from builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
