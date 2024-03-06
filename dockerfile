FROM openjdk:17-oracle
WORKDIR /app
COPY ./config/target/*.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]