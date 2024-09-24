# Stage 1: Build the project
FROM gradle:jdk21-alpine AS build

WORKDIR /app

# Only copy dependency-related files
COPY build.gradle settings.gradle /app/

# Only download dependencies
# Eat the expected build failure since no source code has been copied yet
RUN gradle clean build -x test --no-daemon > /dev/null 2>&1 || true

# Copy all files
COPY ./ /app/

# Do the actual build
RUN gradle clean build -x test --no-daemon

# Stage 2: Runtime image
FROM openjdk:21-jdk

WORKDIR /app

# Copy the compiled JAR from the build stage
COPY --from=build /app/build/libs/hangmanbot-0.0.1-SNAPSHOT.jar /app/hangmanbot-0.0.1-SNAPSHOT.jar

COPY .env .env
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/hangmanbot-0.0.1-SNAPSHOT.jar"]
