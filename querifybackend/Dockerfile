FROM openjdk:17-jdk-alpine
COPY target/*.jar java-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "java-app.jar"]