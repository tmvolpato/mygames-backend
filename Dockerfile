FROM java:8-jdk-alpine
COPY ./target/mygames-1.0.0-SNAPSHOT.jar /home/volpato/development/docker/
WORKDIR /home/volpato/development/docker/
EXPOSE 8000
CMD ["java", "-jar", "mygames-1.0.0-SNAPSHOT.jar"]