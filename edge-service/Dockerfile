FROM debian:9-slim AS jdkBuilder

RUN set -ex && \
    apt-get update && apt-get install -y wget unzip && \
    wget https://download.java.net/java/GA/jdk10/10/binaries/openjdk-10_linux-x64_bin.tar.gz -O jdk.tar.gz -nv && \
    mkdir -p /opt/jdk && \
    tar zxvf jdk.tar.gz -C /opt/jdk --strip-components=1 && \
    rm jdk.tar.gz && \
    rm /opt/jdk/lib/src.zip

RUN /opt/jdk/bin/jlink \
    --module-path /opt/jdk/jmods \
    --verbose \
    --add-modules java.base,java.logging,java.xml,jdk.unsupported,java.sql,java.naming,java.desktop,java.management,java.security.jgss,java.security.sasl,java.instrument \
    --output /opt/jdk-minimal \
    --compress 2 \
    --no-header-files


FROM openjdk:10-ea-jdk-slim
#COPY --from=jdkBuilder /opt/jdk-minimal /opt/jdk-minimal
COPY target/*.jar /opt/

#ENV JAVA_HOME=/opt/jdk-minimal
#ENV PATH="$PATH:$JAVA_HOME/bin"

CMD java $JAVA_OPTS -jar /opt/*.jar


