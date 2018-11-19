FROM openjdk:8
ADD target/cleaning-service.jar cleaning-service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "cleaning-service.jar"]