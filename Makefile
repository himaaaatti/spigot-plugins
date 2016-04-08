MINECRAFT_VERSION	:= 1.9.2
#  MINECRAFT_VERSION	:= latest

PLUGINS 	:= cut rocksmash dig helloworld

JARS		:= $(foreach name,$(PLUGINS), $(name)/$(name).jar)

API_JAR_ORG	:= $(wildcard build/Spigot/Spigot-API/target/spigot-api-*-SNAPSHOT.jar)
SPIGOT_API_JAR := spigot-api-$(MINECRAFT_VERSION).jar
BUILDTOOLS = BuildTools.jar

KOTLINC		:= kotlinc
WGET		:= wget
JAVA 		:= java

all: $(JAR)

$(BUILDTOOLS):
	$(WGET) https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar

$(API_JAR_ORG): $(BUILDTOOLS)
	mkdir -p build
	cd build && $(JAVA) -jar ../$< --rev $(MINECRAFT_VERSION)

$(SPIGOT_API_JAR): $(API_JAR_ORG) $(BUILDTOOLS)
ifeq ("" ,"$(API_JAR_ORG)")
	mkdir -p build
	cd build && $(JAVA) -jar ../$< --rev $(MINECRAFT_VERSION)
	exit "please one more"
endif

	cp ./build/Spigot/Spigot-API/target/spigot-api-*-SNAPSHOT.jar $@

.SUFFIXES: .jar .kt 
%.jar: %.kt $(SPIGOT_API_JAR)
	$(KOTLINC) -cp $(SPIGOT_API_JAR) $< -include-runtime -d $@
	jar uf $@ $(dir $<)plugin.yml 

all: $(JARS)

.PHONY: clean
clean:
	rm -rf $(JARS) 

