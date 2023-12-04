# Use a base image with Java 21
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

RUN microdnf install findutils
ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV FIREBASE_API_KEY=${FIREBASE_API_KEY}

# Copy the Gradle configuration files and wrapper
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src src

# Give execute permission to Gradlew
RUN chmod +x ./gradlew

EXPOSE 8080:8080

# Run the application
CMD ["./gradlew", "bootRun"]
