#!/usr/bin/bash

set -e

FORGIVA_HOME=$(dirname $(realpath ${BASH_SOURCE[0]}))
TAGV=`git describe --tags --long || echo -n 'main'`
REV=`(git log --oneline | wc -l | tr -d ' ') || echo -n '0'`
VER="${TAGV%%-*}-${REV}"


FORGIVA_SERVER_DIR="${FORGIVA_HOME}/server/forgiva.server"
FORGIVA_SERVER_URL="https://github.com/Sceptive/forgiva-server/releases/download/r1/forgiva_server-r1-3-linux-x86_64-release.tar.xz"
FORGIVA_SERVER_SHA256="20004db5052e1e66172731a2856abb5812870853318ec6ef0f2c5c0d69c7d6d5"
BUILD_DIR="${FORGIVA_HOME}/build/deploy/${VER}/"
WEBCLIENT_DIR=$BUILD_DIR/client/web/
WEBCLIENT_URL="https://github.com/Sceptive/forgiva-webclient/releases/download/r1-5/forgiva_webclient-r1-5-release.tar.xz"
WEBCLIENT_SHA256="7edcd1ecff4d4daa8593368a032e351278dc707170665392e7ced4ca4788cfb7"
BIN_DIR=$BUILD_DIR/bin/
CONF_FILE="${FORGIVA_HOME}/etc/conf/integrator.conf"
CONF_FILE_SAMPLE="${FORGIVA_HOME}/etc/conf/integrator.conf.sample"

synchronize_binary () {
  URL="$1"
  FPATH="$2"
  SHASUM="$3"

  if ! [ -f "$FPATH" ]; then
    curl -L -o "$FPATH" "$URL"
  fi

  SUMV=$(shasum -a 256 "$FPATH")
  HASH=${SUMV%% *}

  if ! [[ "$HASH" == "$SHASUM" ]]; then
    echo "Could not sync $1 or SHA sums mismatch "
    echo "Found:    $HASH"
    echo "Expected: $SHASUM"
    exit 1
  fi

}

echo "Build Directory: ${BUILD_DIR}"

if [ ! "$*" == "release" ]; then
  if [ ! -f "$CONF_FILE" ]; then
    echo "Other than creating release archive you should set $CONF_FILE for running" ; exit 1
  fi
fi


# Synchronizing Forgiva Server binaries
( echo "Synchronizing Forgiva Server Binaries" &&
  mkdir -p $BIN_DIR &&
  cd $BIN_DIR &&
  synchronize_binary "${FORGIVA_SERVER_URL}" "/tmp/forgiva_server.tar.xz" "${FORGIVA_SERVER_SHA256}" &&
  tar Jxvf "/tmp/forgiva_server.tar.xz"  2>&1 > /dev/null  ) || \
   { echo "Could not sync Forgiva Server binaries" ; exit 1 ; }



if [ ! "$*" == "uidev" ]; then
# Synchronizing web client
( echo "Synchronizing Forgiva WebClient Binaries" &&
  mkdir -p $WEBCLIENT_DIR &&
  cd $WEBCLIENT_DIR &&
  synchronize_binary "${WEBCLIENT_URL}" "/tmp/forgiva_webclient.tar.xz" "${WEBCLIENT_SHA256}" &&
  tar Jxvf "/tmp/forgiva_webclient.tar.xz" 2>&1 > /dev/null  ) || { \
  echo "Could not sync Forgiva Web Client" ; exit 1 ; }

fi





if [[ "$*" == *"release"* ]] || [[ "$*" == *"image"* ]]; then
  TARGET_FILE=forgiva_integrator-$VER-jvm8-release.tar.xz
  (sh ./gradlew build `[[ "$*" == *"notest"* ]] && echo -n "-xtest"` &&
    chmod +x $BUILD_DIR/bin/*.sh &&
    chmod +x $BUILD_DIR/bin/forgiva_server* &&
    cp "$CONF_FILE_SAMPLE" "$BUILD_DIR/conf/integrator.conf" &&
    cd build/deploy &&
    rm -rf "$VER/data" "$VER/log" &&
    ([[ "$*" == *"notest"* ]] || (echo "Creating release file ${TARGET_FILE}" && XZ_OPT=-9e  tar cJvf ../../$TARGET_FILE "$VER" 2>&1 > /dev/null )) )

  if [[ "$*" == *"image"* ]]; then
    (docker build -t forgiva-integrator:${VER} --build-arg VER=${VER} -f Dockerfile . &&
      docker tag forgiva-integrator:${VER} forgiva-integrator:latest )
  fi


elif [[ "$*" == *"onlytest"* ]]; then

  (sh ./gradlew test  || { echo "Failed Maven tests"} ; exit 1 ; })

elif [[ "$*" == *"uidev"* ]]; then

(sh ./gradlew build -xtest &&
  chmod +x $BIN_DIR/*.sh &&
  chmod +x $BIN_DIR/forgiva_server* &&
  FORGIVA_WC_ROOT_DIR=$WEBCLIENT_SRC_DIR/dist/ $BUILD_DIR/bin/forgiva_integrator.sh)

elif [[ "$*" == *"run"* ]]; then

  (sh ./gradlew build -xtest  &&
  chmod +x $BIN_DIR/*.sh &&
  chmod +x $BIN_DIR/forgiva_server* &&
  $BUILD_DIR/bin/forgiva_integrator.sh)
fi
