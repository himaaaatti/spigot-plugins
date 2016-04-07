MINECRAFT_VERSION	:= 1.9.2
#  MINECRAFT_VERSION	:= latest
PLUGINS 	:= cut rocksmash

JARS		:= $(foreach name,$(PLUGINS), $(name)/$(name).jar)

SPIGOT_API_JAR := spigot-api.jar
BUILDTOOLS = BuildTools.jar

KOTLINC		:= kotlinc
WGET		:= wget
JAVA 		:= java

all: $(SPIGOT_API_JAR)

$(BUILDTOOLS):
	$(WGET) https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

$(SPIGOT_API_JAR): $(BUILDTOOLS)
	mkdir -p build
	cd build
	$(JAVA) -jar $< --rev $(MINECRAFT_VERSION)

.SUFFIXES:


.PHONY: clean
clean:
	rm -rf $()
	
