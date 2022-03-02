#!/usr/bin/env bash

# ---------------------------------------------------------------------------
# Start Script for the Forgiva Integrator
#
# Environment Variable Prerequisites
#
# FORGIVA_INTEGRATOR_HOME   Resolves to root directory of Forgiva Integrator
#                           installation
#
# JAVA_HOME                 Must point at your Java Development Kit
#                           installation.
#
# JRE_HOME                  Must point at your Java Runtime installation.
#                           Defaults to JAVA_HOME if empty. If JRE_HOME
#                           and JAVA_HOME are both set, JRE_HOME is used.
#
# JAVA_OPTS                 (Optional) Java runtime options used when any
#                           command is executed.
#
# Notes:                    For test environments to ignore LDAPS endpoint
#                           it is possible to add -Dcom.sun.jndi.ldap.object.disableEndpointIdentification=true
#                           parameter to make validate invalid security checks.
# ---------------------------------------------------------------------------

if [ -z "${FORGIVA_INTEGRATOR_HOME}" ]; then
  PRG="$0"

  while [ -h "$PRG" ] ; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
      PRG="$link"
    else
      PRG=`dirname "$PRG"`/"$link"
    fi
  done

  SH_DIR=`dirname "$PRG"`
  FORGIVA_INTEGRATOR_HOME=$(dirname $(readlink -e "$SH_DIR"))
fi

echo "Home: " $FORGIVA_INTEGRATOR_HOME

if [ -r "$FORGIVA_INTEGRATOR_HOME"/bin/java_set.sh ]; then
    . "$FORGIVA_INTEGRATOR_HOME"/bin/java_set.sh
else
    echo "Cannot find $FORGIVA_INTEGRATOR_HOME/bin/java_set.sh"
    echo "This file is needed to run this program"
    exit 1
fi

have_tty=0
if [ "`tty`" != "not a tty" ]; then
    have_tty=1
fi



export FORGIVA_INTEGRATOR_HOME="$FORGIVA_INTEGRATOR_HOME"

if [ -z "${FORGIVA_WC_ROOT_DIR}" ]; then
export FORGIVA_WC_ROOT_DIR="$FORGIVA_INTEGRATOR_HOME/client/web/"
fi

if [ $have_tty -eq 1 ]; then
  echo "Using FORGIVA_INTEGRATOR_HOME:   $FORGIVA_INTEGRATOR_HOME"
  echo "Using FORGIVA_WC_ROOT_DIR:       $FORGIVA_WC_ROOT_DIR"
  echo "Using JRE_HOME:                  $JRE_HOME"
  echo "Using CLASSPATH:                 $CLASSPATH"
fi


eval "\"$_RUNJAVA\"" -jar $FORGIVA_INTEGRATOR_HOME/bin/forgiva.integrator.jar  "$@"


