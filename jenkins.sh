#!/bin/bash -x

set -eu

if [ $# -ne 1 ]; 
then
    echo you should add build number
    echo "For example"
    echo    ./jenkins.sh 100
    exit 1
fi

result_dir=`pwd`/result/${1}/
mkdir -p ${result_dir}/

ls -F > /tmp/ls.out
grep / /tmp/ls.out > /tmp/grep0.out
grep -v result /tmp/grep0.out > /tmp/grep1.out
plugins=`sed -e 's/\///g' /tmp/grep1.out`

echo ${plugins}

for name in ${plugins[@]}
do
    docker run -e plugin_name=${name} -e BUILD_NUMBER=${1} -v ${result_dir}:/result spigot:latest
done
