FROM amazoncorretto:17-alpine3.16-jdk
EXPOSE 8080
WORKDIR /app
COPY /build/libs/user-service-0.0.1.jar user-service-0.0.1.jar
CMD ["java", "-jar", "user-service-0.0.1.jar"]