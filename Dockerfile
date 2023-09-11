FROM openjdk:11-jdk
VOLUME /tmp
ARG JAR_FILE=build/libs/hicardi-presscenter-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} presscenter.jar

ENTRYPOINT ["java","-jar","/presscenter.jar"]