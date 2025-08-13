FROM maven:3.8-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the application, skipping tests for a faster build process
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy ONLY the built JAR from the 'build' stage into the final image
COPY --from=build /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# The command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]