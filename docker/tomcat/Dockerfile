FROM maven:3.8.3-jdk-8-slim AS build
COPY /src /home/university/src
COPY pom.xml /home/university
RUN mvn -f /home/university/pom.xml clean package

FROM openjdk:8
MAINTAINER afniko@gmail.com
COPY --from=build /home/university/target/task-university-java8-api-0.0.1-SNAPSHOT.jar /usr/src/task-university-java8-api-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
EXPOSE 8080
ENTRYPOINT ["java","-jar", "task-university-java8-api-0.0.1-SNAPSHOT.jar"]
