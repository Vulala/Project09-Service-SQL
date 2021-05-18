FROM openjdk:15-jdk-alpine as build 
ARG JAR_FILE=target/*.jar 
COPY Project09-Service-SQL-1.0.0.jar Service-SQL-1.0.0.jar 
ENTRYPOINT ["java","-jar","/Service-SQL-1.0.0.jar"]
