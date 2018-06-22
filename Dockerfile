FROM openjdk:8-jre-alpine

MAINTAINER vncetin@gmail.com

ENV VERTICLE_FILE template-0.0.1-SNAPSHOT.jar
ENV VERTICLE_HOME /opt/template
ENV LOGGING_FILE_DIR /opt/template/logs

EXPOSE 8081:8081

# Copy your fat jar to the container
COPY target/$VERTICLE_FILE $VERTICLE_HOME/

RUN mkdir /opt/template/logs
VOLUME /opt/template/logs

# Launch the verticle
WORKDIR $VERTICLE_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=live $VERTICLE_FILE "]
