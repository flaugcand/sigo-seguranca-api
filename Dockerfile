FROM openjdk:11

WORKDIR /app

COPY target/*.jar /app/api.jar

CMD ["java", "-jar", "api.jar"]
