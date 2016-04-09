#!/bin/bash


cd ${1}
kotlinc -cp ../spigot-api-1.9.2.jar ${1}.kt -include-runtime -d ${1}.jar
jar uf ${1}.jar plugin.yml
