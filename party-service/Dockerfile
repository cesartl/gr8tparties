FROM openjdk:10-jdk-slim AS jdkBuilder

RUN $JAVA_HOME/bin/jlink \
    --module-path /opt/jdk/jmods \
    --verbose \
    --add-modules java.base,java.logging,java.xml,java.sql,jdk.unsupported,java.naming,java.desktop,java.management,java.security.jgss,java.security.sasl,jdk.crypto.cryptoki,jdk.crypto.ec,java.instrument \
    --output /opt/jdk-minimal \
    --compress 2 \
    --no-header-files


FROM debian:9-slim
COPY --from=jdkBuilder /opt/jdk-minimal /opt/jdk-minimal

ENV JAVA_HOME=/opt/jdk-minimal
COPY target/*.jar /opt/

CMD $JAVA_HOME/bin/java $JAVA_OPTS -jar /opt/*.jar
