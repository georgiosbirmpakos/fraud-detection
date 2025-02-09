# Use an official OpenJDK runtime
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY . .

# Build the JAR inside the container
RUN ./mvnw clean package -DskipTests

# Rename the JAR file to app.jar for consistency
RUN mv target/fraud-detection-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
