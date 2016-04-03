#!/bin/bash

if [ $# -ne 1 ]; then
    echo example
    echo    $ ./build.sh cut
    exit 1
fi

spigot_api_jar=../spigot-api.jar

cd $1
kotlinc -cp ${spigot_api_jar} ${1}.kt -include-runtime -d ${1}.jar && \
jar uf ${1}.jar plugin.yml 
