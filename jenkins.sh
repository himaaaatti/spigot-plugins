#!/bin/bash -x

set -eu

if [ $# -ne 1 ]; 
then
    echo you should add build number
    echo "For example"
    echo    ./jenkins.sh 100
    exit 1
fi

mkdir -p /tmp/${1}

ls -F > /tmp/ls.out
grep / /tmp/ls.out > /tmp/grep.out
plugins=`sed -e 's/\///g' /tmp/grep.out`

echo ${plugins}

for name in ${plugins[@]}
do
    docker run -e plugin_name=${name} -e BUILD_NUMBER=${1} -v /tmp/${1}:/result spigot:latest
done
