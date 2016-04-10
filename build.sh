#!/bin/bash

if [ $# -ne 1 ]; then
    echo "For example"
    echo "  ./build.sh cut"
    exit 1
fi

cd ${1}
kotlinc -cp ../spigot-api-1.9.2.jar ${1}.kt -include-runtime -d ${1}.jar && \
jar uf ${1}.jar plugin.yml
