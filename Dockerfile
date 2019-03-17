FROM java:8-jdk-alpine
COPY ./target/mygames-1.0.0-SNAPSHOT.jar /home/$USER/
WORKDIR /home/$USER/
EXPOSE 8000
CMD ["java", "-jar", "mygames-1.0.0-SNAPSHOT.jar"]