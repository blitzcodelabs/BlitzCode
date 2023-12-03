# Use a base image with Java 21
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

RUN microdnf install findutils
ENV DB_PASSWORD=TDISBku2RDKXGNed0SKs
ENV DB_URL=jdbc:postgresql://containers-us-west-131.railway.app:6924/railway
ENV DB_USERNAME=postgres
ENV FIREBASE_API_KEY=AIzaSyDpvdTLV_poyLoqzJltr1IfmjSFP79ns2s

# Copy the Gradle configuration files and wrapper
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copy the source code
COPY src src

# Give execute permission to Gradlew
RUN chmod +x ./gradlew

# Run the application
CMD ["./gradlew", "bootRun"]
