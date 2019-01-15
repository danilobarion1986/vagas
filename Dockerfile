FROM openjdk:9
COPY ./target/vagas-0.0.1-SNAPSHOT.jar /usr/src/myapp/vagas.jar
CMD ["java", "-jar","-Dspring.profiles.active=docker", "/usr/src/myapp/vagas.jar"]
