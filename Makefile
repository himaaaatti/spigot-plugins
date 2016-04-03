.SUFFIXES: .jar .kt

PLUGINS 	:= cut rocksmash

JARS		:= $(foreach name,$(PLUGINS), $(name)/$(name).jar)

SPIGOT_API_JAR := spigot-api.jar

KOTLINC		:= kotlinc

all: $(JARS)

.kt.jar:
	./build.

.PHONY: clean
clean:
	rm -rf $()
	
