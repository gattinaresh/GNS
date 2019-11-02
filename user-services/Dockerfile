FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/user-services-0.0.1-SNAPSHOT.war
COPY ${JAR_FILE} user-services.war
ENTRYPOINT ["java","-jar","user-services.war"]
