FROM debian:jessie

ARG MINECRAFT_VERSION
ENV MINECRAFT_VERSION ${MINECRAFT_VERSION:-1.9.2}
ARG API_JAR
ENV API_JAR ${API_JAR:-/spigot-api-${MINECRAFT_VERSION}.jar}

ENV plugin_name cut

RUN apt-get update && apt-get -y upgrade

RUN apt-get install -y wget openjdk-7-jdk unzip git

RUN wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

RUN java -jar BuildTools.jar --rev ${MINECRAFT_VERSION}
RUN cp /Spigot/Spigot-API/target/spigot-api-*-SNAPSHOT.jar ${API_JAR}

# get kotlinc
RUN wget https://github.com/JetBrains/kotlin/releases/download/1.0.1-2/kotlin-compiler-1.0.1-2.zip

ENV BUILD_NUMBER ${BUILD_NUMBER:-0}
RUN unzip kotlin-compiler-1.0.1-2.zip
ENV PATH $PATH:/kotlinc/bin/

RUN git clone https://github.com/himaaaatti/spigot-plugins

WORKDIR /spigot-plugins
CMD "./build.sh" ${plugin_name} ${API_JAR} ${BUILD_NUMBER}
