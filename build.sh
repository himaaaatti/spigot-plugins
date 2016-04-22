#!/bin/bash -x
set -eu

if [ $# -ne 2 ]; then
    echo "For example"
    echo "  ./build.sh cut ../spigot-api.jar"
    exit 1
fi

cd ${1}
#kotlinc -cp ../spigot-api-1.9.2.jar ${1}.kt -include-runtime -d ${1}.jar && \
kotlinc -cp ${2} ${1}.kt -include-runtime -d ${1}.jar

if [ -e plugin.yml ];
then
    jar uf ${1}.jar plugin.yml
else
    echo plugin.yml not found
    exit 1
fi

if [ -e config.yml ]; 
then
    jar uf ${1}.jar config.yml
fi

