FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/user-services-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} user-services.jar
ENTRYPOINT ["java","-jar","user-services.jar"]
