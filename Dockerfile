FROM openjdk:17-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} asv-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/asv-0.0.1-SNAPSHOT.jar"]