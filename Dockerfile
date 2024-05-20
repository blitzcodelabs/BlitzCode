# Use a base image with Java 21
FROM azul/zulu-openjdk-debian:21 as build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle configuration files and wrapper
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src/main src/main

# Give execute permission to Gradlew
RUN chmod +x ./gradlew

# Build the application with Gradle, using no-daemon to free up memory after the build
RUN ./gradlew bootJar --no-daemon

# Use a slim base image for the runtime
FROM azul/zulu-openjdk-debian:21-jre-headless

WORKDIR /app

# Copy only the necessary files from the build stage
COPY --from=build /app/build/libs/BlitzCode.jar BlitzCode.jar

# Set environment variables
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV FIREBASE_API_KEY=${FIREBASE_API_KEY}

# Expose the application port
EXPOSE 8080

# Set JVM options for memory efficiency
ENV JAVA_OPTS="-XX:+UseG1GC -XX:+UseStringDeduplication"

# Run the application with memory-efficient JVM options
ENTRYPOINT ["java", "-jar", "BlitzCode.jar"]
