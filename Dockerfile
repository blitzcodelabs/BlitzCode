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
COPY src src

# Give execute permission to Gradlew
RUN chmod +x ./gradlew
RUN ./gradlew bootJar

FROM azul/zulu-openjdk-debian:21-jre

WORKDIR /app

COPY --from=build /app/build/libs/BlitzCode.jar BlitzCode.jar

ENV DB_PASSWORD=${DB_PASSWORD}
ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV FIREBASE_API_KEY=${FIREBASE_API_KEY}

EXPOSE 8080:8080

# Run the application
ENTRYPOINT ["java", "-jar", "BlitzCode.jar"]
