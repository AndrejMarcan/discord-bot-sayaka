FROM amazoncorretto:21-alpine-jdk

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

COPY newrelic/newrelic.jar newrelic/newrelic.jar
COPY newrelic/newrelic.yml newrelic/newrelic.yml

EXPOSE 8080

ENTRYPOINT ["java", "-javaagent:/newrelic/newrelic.jar", "-Dnewrelic.config.file=/newrelic/newrelic.yml", "-jar", "/app.jar"]