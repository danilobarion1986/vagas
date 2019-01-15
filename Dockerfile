FROM openjdk:9
COPY ./target/http_client-0.0.1-SNAPSHOT.jar /usr/src/myapp/http_client-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "--add-modules=jdk.incubator.httpclient", "/usr/src/myapp/http_client-0.0.1-SNAPSHOT.jar"]