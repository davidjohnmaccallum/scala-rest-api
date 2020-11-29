FROM openjdk:8

WORKDIR /app
COPY target/universal/scala-rest-api-1.0.zip .
RUN unzip scala-rest-api-1.0.zip

CMD /app/scala-rest-api-1.0/bin/scala-rest-api