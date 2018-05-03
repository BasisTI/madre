FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JHIPSTER_SLEEP=0 \
    JAVA_OPTS="-server -Xmx2G -XX:MinHeapFreeRatio=20 -XX:MaxHeapFreeRatio=40 -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70"

COPY *.war /app.war

RUN \
    # configure timezone
	apk --no-cache add tzdata &&\
	cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime &&\
	apk del tzdata &&\
    # download and import letsencrypt certificate
    wget https://letsencrypt.org/certs/lets-encrypt-x3-cross-signed.der &&\
    keytool -importcert \
        -keystore /usr/lib/jvm/default-jvm/jre/lib/security/cacerts \
        -storepass changeit \
        -alias lets-encrypt \
        -file lets-encrypt-x3-cross-signed.der \
        -noprompt &&\
	rm -R lets-encrypt-x3-cross-signed.der

EXPOSE 8080 5701/udp

CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.war