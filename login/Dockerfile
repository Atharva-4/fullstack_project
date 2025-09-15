# Stage 1: Build the application using Maven
FROM eclipse-temurin:17.0.12_7-jdk-jammy as build
WORKDIR /app
COPY . .
RUN ./mvnw clean install -DskipTests

# Stage 2: Create a smaller, final image to run the application
FROM eclipse-temurin:17.0.12_7-jre-jammy
WORKDIR /app

# Copy the built .jar file from the 'build' stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# The command to run the application
ENTRYPOINT ["java","-jar","app.jar"]