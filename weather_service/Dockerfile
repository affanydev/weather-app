# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim


# Create a directory for your application
WORKDIR /app

# Copy the packaged JAR file into the container at the specified directory
COPY build/libs/weather-service-0.0.1.jar weather-service-0.0.1.jar

# Expose the port that your application will run on
EXPOSE 8528

# Define the command to run your application
CMD ["java", "-jar", "weather-service-0.0.1.jar"]
