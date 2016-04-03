.SUFFIXES: .jar .kt

PLUGINS 	:= cut rocksmash

JARS		:= $(foreach name,$(PLUGINS), $(name)/$(name).jar)

SPIGOT_API_JAR := spigot-api.jar

KOTLINC		:= kotlinc

all: $(JARS)

.kt.jar:
	$(KOTLINC) -cp $(SPIGOT_API_JAR) $< -include-runtime -d $@ 
	jar uf $@ $(dir $<)plugin.yml

.PHONY: clean
clean:
	rm -rf $(JARS)
	
