FROM gradle:8.4.0-jdk17-alpine
EXPOSE 8080
RUN mkdir -p /app/
ARG JAR_FILE=build/libs/*.jar
ADD ${JAR_FILE} /app/weather-app.jar
ENTRYPOINT ["java", "-jar", "/app/weather-app.jar"]