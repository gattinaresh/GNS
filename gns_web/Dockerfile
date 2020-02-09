FROM openjdk:8-jdk-alpine
VOLUME /GnsVolume
ARG JAR_FILE=target/gns_web-0.0.1-SNAPSHOT.war
COPY ${JAR_FILE} gns_web.war
ENTRYPOINT ["java","-jar","gns_web.war"]
