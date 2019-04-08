FROM openjdk:8
ADD target/Banking-0.0.1-SNAPSHOT.jar Banking-0.0.1-SNAPSHOT.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "Banking-0.0.1-SNAPSHOT.jar"]