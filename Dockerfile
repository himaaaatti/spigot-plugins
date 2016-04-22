FROM debian:jessie

ARG minecraft_version=1.9.2
ARG api_jar=/spigot-api.jar
ENV plugin_name=cut

RUN apt-get update -y && apt-get -t upgrade

RUN apt-get install -y wget openjdk-7-jdk unzip git

RUN wget https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

RUN java -jar BuildTools.jar --rev ${minecraft_version:-1.9.2}
RUN cp /build/Spigot/Spigot-API/target/spigot-api-*-SNAPSHOT.jar /${api_jar}

# get kotlinc
RUN wget https://github.com/JetBrains/kotlin/releases/download/1.0.1-2/kotlin-compiler-1.0.1-2.zip

RUN unzip kotlin-compiler-1.0.1-2.zip
ENV PATH $PATH:/kotlinc/bin/

RUN git clone https://github.com/himaaaatti/spigot-plugins

WORKDIR spigot-plugins
CMD ["./build.sh", "${plugin_name}", "${api_jar}"]
