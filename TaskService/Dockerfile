# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="mriganka.shekharroy@yahoo.co.in"

# Add a volume pointing to /app
VOLUME /app

# Make port 5092 available to the world outside this container
EXPOSE 5092

# The application's jar file
ARG JAR_FILE=target/TaskService-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} /app/TaskService.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/TaskService.jar"]