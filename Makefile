
PLUGINS 	:= cut rocksmash helloworld

JARS		:= $(foreach name,$(PLUGINS), $(name)/$(name).jar)

SPIGOT_API_JAR := spigot-api.jar

KOTLINC		:= kotlinc

.SUFFIXES: .jar .kt 
.kt.jar:
	$(KOTLINC) -cp $(SPIGOT_API_JAR) $< -include-runtime -d $@
	jar uf $@ $(dir $<)plugin.yml 

all: $(JARS)

.PHONY: clean
clean:
	rm -rf $(JARS)
	
