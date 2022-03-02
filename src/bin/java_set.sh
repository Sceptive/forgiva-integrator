#!/usr/bin/env bash

# -----------------------------------------------------------------------------
#  Set JAVA_HOME or JRE_HOME if not already set, ensure any provided settings
#  are valid and consistent with the selected start-up options and set up the
#  endorsed directory.
# -----------------------------------------------------------------------------

# Make sure prerequisite environment variables are set
if [ -z "$JAVA_HOME" ] && [ -z "$JRE_HOME" ]; then
# If it is MacOSX
  if [[ "$OSTYPE" == "darwin" ]]; then
    if [ -x '/usr/libexec/java_home' ] ; then
      JAVA_HOME=$(/usr/libexec/java_home)
    elif [ -d "/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home" ]; then
      JAVA_HOME="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Home"
    fi
# Else if it is FreeBSD
  elif [[ "$OSTYPE" == "freebsd" ]]; then
    if [ -x /usr/local/openjdk8/bin/java ]; then
      JRE_HOME=/usr/local/openjdk8
    fi
# Else if it is Linux
  elif [[ "$OSTYPE" == "linux-gnu" ]]; then
    if [ -x /usr/lib/jvm/jre/bin/java ]; then
      JRE_HOME=/usr/lib/jvm/jre
    elif [ -x /usr/lib/jvm/jdk/bin/java ]; then
      JRE_HOME=/usr/lib/jvm/jdk/
    fi
  fi

  if [ -z "$JAVA_HOME" ] && [ -z "$JRE_HOME" ]; then

#   If java could not be found then try to locate by `which`
    JAVA_PATH=$(command -v java)
    if [ "x$JAVA_PATH" != "x" ]; then
      JAVA_PATH=$(dirname "$JAVA_PATH" 2>/dev/null)
      JRE_HOME=$(dirname "$JAVA_PATH" 2>/dev/null)
    fi

#   If still no java could be found then terminate
    if [ -z "$JAVA_HOME" ] && [ -z "$JRE_HOME" ]; then
      echo "Neither the JAVA_HOME nor the JRE_HOME environment variable is defined"
      echo "At least one of these environment variable is needed to run this program"
      exit 1
    fi
  fi
fi

if [ -z "$JRE_HOME" ]; then
  JRE_HOME="$JAVA_HOME"
fi

# Set standard commands for invoking Java, if not already set.
if [ -z "$_RUNJAVA" ]; then
  _RUNJAVA="$JRE_HOME"/bin/java
fi

export JAVA_HOME
export JRE_HOME
