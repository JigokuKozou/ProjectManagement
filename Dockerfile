FROM openjdk:17-alpine
ARG JAR_PATH=./app/target/project-management.jar
COPY ${JAR_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]