FROM openjdk:17
EXPOSE 8888
ARG JAR_FILE=target/training-report.jar
COPY ${JAR_FILE} /report.jar
ENTRYPOINT ["java", "-jar", "/report.jar"]
