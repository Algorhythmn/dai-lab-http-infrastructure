FROM alpine:latest
RUN apk add --no-cache openjdk20
WORKDIR /app
COPY target/*.jar /app/app.jar
CMD ["java", "-jar", "app.jar"]