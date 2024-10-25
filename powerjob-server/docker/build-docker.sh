#!/bin/bash
baseDir=$(pwd)
echo "baseDir: $baseDir"
name=powerjob-server
ver=$1
build_date=$(date +"%Y%m%d")
if [ -z "${ver}" ]; then
  ver=5.1.0
fi
export DOCKER_CLI_EXPERIMENTAL=enabled
# export DOCKER_BUILDKIT=0
export DP_URL=http://10.10.10.41:1082
export HTTP_PROXY=$DP_URL
export HTTPS_PROXY=$DP_URL
docker buildx rm mybuilder2
docker buildx create --use --name mybuilder2 \
--driver-opt network=host \
--driver-opt env.http_proxy=http://10.10.10.41:1082 \
--driver-opt env.https_proxy=http://10.10.10.41:1082
docker buildx ls
echo ${DOCKER_HUB_KEY} | docker login --username ${DOCKER_HUB_USER} --password-stdin
cp -f ${baseDir}/../powerjob-server-starter/target/powerjob-server-starter-${ver}.jar ${baseDir}/powerjob-server.jar
docker buildx build \
  --platform linux/amd64,linux/arm64 \
  --build-arg VER=${ver} \
  --build-arg BUILD_DATE=${build_date} \
  --build-arg HTTP_PROXY=http://10.10.10.41:1082 \
  --build-arg HTTPS_PROXY=http://10.10.10.41:1082 \
  --push \
  --tag sorc/${name}:${ver}_${build_date} \
  --tag sorc/${name}:${ver} \
  --tag sorc/${name}:latest -f ${baseDir}/SorcDockerfile .
rm -f powerjob-server.jar
