#!/bin/sh

SCRDIR=$(dirname "$0")
cd $SCRDIR
# Install api2html if not installed
npm i api2html -g
# Convert API to HTML
api2html -o ../../src/doc/api/api.html -c ../../src/doc/resources/doclogo.png ../../etc/api/reference/integratorapi.v1.yaml


# Build docker image if not built
[ ! -z $(docker images -q dita-ot) ] || docker build --build-arg VERSION=3.4 --tag=dita-ot .
# Build documentation
cd ../../
mkdir -p target/doc
chmod a+rw target/doc
docker run -it \
-v `pwd`/src/doc:/src -v `pwd`/target/doc:/out localhost/dita-ot \
-i /src/forgiva_integrator.ditamap \
-o /out \
--propertyfile=/src/build.properties \
-f html5 -v

