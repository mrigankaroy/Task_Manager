# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="mriganka.shekharroy@yahoo.co.in"

# Add a volume pointing to /app
VOLUME /app

# Make port 8081 available to the world outside this container
# EXPOSE 8081

# The application's jar file
ARG JAR_FILE=target/TaskEngine-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} /app/TaskEngine.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/TaskEngine.jar"]