#!/usr/bin/bash

set -e

FORGIVA_HOME=$(dirname $(realpath ${BASH_SOURCE[0]}))
TAGV=`git describe --tags --long || echo -n 'main'`
REV=`(git log --oneline | wc -l | tr -d ' ') || echo -n '0'`
VER="${TAGV%%-*}-${REV}"


FORGIVA_SERVER_DIR="${FORGIVA_HOME}/server/forgiva.server"
FORGIVA_SERVER_URL="https://github.com/Sceptive/forgiva-server/releases/download/r1/forgiva_server-r1-3-linux-x86_64-release.tar.xz"
BUILD_DIR="${FORGIVA_HOME}/build/deploy/${VER}/"
WEBCLIENT_DIR=$BUILD_DIR/client/web/
WEBCLIENT_URL="https://github.com/Sceptive/forgiva-webclient/releases/download/r1/forgiva_webclient-r1-1-release.tar.xz"
BIN_DIR=$BUILD_DIR/bin/
DOC_DIR=$BUILD_DIR/doc/
DOC_SRC_DIR="${FORGIVA_HOME}/src/doc/"
CONF_FILE="${FORGIVA_HOME}/etc/conf/integrator.conf"
CONF_FILE_SAMPLE="${FORGIVA_HOME}/etc/conf/integrator.conf.sample"


echo "Build Directory: ${BUILD_DIR}"

if [ ! "$*" == "release" ]; then
  if [ ! -f "$CONF_FILE" ]; then
    echo "Other than creating release archive you should set $CONF_FILE for running" ; exit 1
  fi
fi


# Synchronizing Forgiva Server binaries
( echo "Synchronizin binaries" &&
  mkdir -p $BIN_DIR &&
  cd $BIN_DIR && ( curl -L "${FORGIVA_SERVER_URL}" | tar Jxvf - ) ) || { \
    echo "Could not sync Forgiva Server binaries" ; exit 1 ; }



if [ ! "$*" == "uidev" ]; then
# Synchronizing web client
(mkdir -p $WEBCLIENT_DIR &&
  cd $WEBCLIENT_DIR && ( curl -L "${WEBCLIENT_URL}" | tar Jxvf - ) ) || { \
  echo "Could not sync Forgiva Web Client" ; exit 1 ; }
fi


(
 (mkdir -p $DOC_DIR &&
 docker run --rm \
  -v ${DOC_SRC_DIR}:/data  \
  -v ${DOC_DIR}:/data_output \
   --user $(id -u):$(id -g) \
   pandoc/core \
  -s -f markdown+auto_identifiers \
  /data/configuration.md \
  /data/configuration/logging.md \
  /data/configuration/database.md \
  /data/configuration/administration.md \
  /data/configuration/security.md \
  /data/configuration/webserver.md \
  /data/configuration/ldap.md \
  /data/configuration/mailserver.md \
  --css /data/style.css --metadata title="Forgiva Integrator Configuration Guide" \
  --section-divs -o /data_output/configuration.html) || { echo "Could not build user guides please check errors. " ; exit 1  ; })


if [[ "$*" == "release" ]] || [[ "$*" == "image" ]]; then
  TARGET_FILE=forgiva_integrator-$VER-jvm8-release.tar.xz
  (sh ./gradlew build &&
    chmod +x $BUILD_DIR/bin/*.sh &&
    chmod +x $BUILD_DIR/bin/forgiva_server* &&
    cp "$CONF_FILE_SAMPLE" "$BUILD_DIR/conf/integrator.conf" &&
    cd build/deploy &&
    rm -rf "$VER/data" "$VER/log" &&
    XZ_OPT=-9e  tar cJvf ../../$TARGET_FILE "$VER" )

  if [ "$*" == "image" ]; then
    docker build -t forgiva_integrator:${VER} --build-arg VER=${VER} -f Dockerfile .
  fi


elif [ "$*" == "test" ]; then

  (sh ./gradlew test  || { echo "Failed Maven tests"} ; exit 1 ; })

elif [ "$*" == "uidev" ]; then

(sh ./gradlew build -xtest &&
  chmod +x $BIN_DIR/*.sh &&
  chmod +x $BIN_DIR/forgiva_server* &&
  FORGIVA_WC_ROOT_DIR=$WEBCLIENT_SRC_DIR/dist/ $BUILD_DIR/bin/forgiva_integrator.sh)

elif [ "$*" == "run" ]; then

  (sh ./gradlew build -xtest  &&
  chmod +x $BIN_DIR/*.sh &&
  chmod +x $BIN_DIR/forgiva_server* &&
  $BUILD_DIR/bin/forgiva_integrator.sh)
fi
