#!/bin/bash

MACHINE=xbox

echo "testing"

while test -n "$1"; do
  case "$1" in
    "--help" | "-h")
      echo "Usage: . $0 [build directory]"
      return 0
      ;;
    *)
      BUILDDIRECTORY=$1
      ;;
  esac
  shift
done

if [ -n "$BASH_SOURCE" ]; then
    THIS_SCRIPT=$BASH_SOURCE
elif [ -n "$ZSH_NAME" ]; then
    THIS_SCRIPT=$0
else
    THIS_SCRIPT="$(pwd)/oe-init-build-env"
    if [ ! -e "$THIS_SCRIPT" ]; then
        echo "Error: $THIS_SCRIPT doesn't exist!" >&2
        echo "Please run this script in oe-init-build-env's directory." >&2
        exit 1
    fi
fi
if [ -n "$BBSERVER" ]; then
    unset BBSERVER
fi

if [ -z "$ZSH_NAME" ] && [ "$0" = "$THIS_SCRIPT" ]; then
    echo "Error: This script needs to be sourced. Please run as '. $THIS_SCRIPT'" >&2
    exit 1
fi

echo "testing2"

if [ -z "$MACHINE" ]; then
  echo "Error: MACHINE environment variable not defined"
  return 0
fi

echo "testing3"

BUILDDIRECTORY=${BUILDDIRECTORY:-build-${MACHINE}}

echo "Testing4"

if [ ! -e ${PWD}/${BUILDDIRECTORY} ]; then
  mkdir -p ${PWD}/${BUILDDIRECTORY}/conf
  cp ${PWD}/sources/meta-uglyoldbob/conf/${MACHINE}.bblayers  ${PWD}/${BUILDDIRECTORY}/conf/bblayers.conf
  cp ${PWD}/sources/meta-uglyoldbob/conf/${MACHINE}.conf.sample  ${PWD}/${BUILDDIRECTORY}/conf/local.conf
fi

echo "Testing5"

TEMPLATECONF="${PWD}/sources/meta-uglyoldbob/conf"

echo "testing 5.1 ${TEMPLATECONF}"

. sources/poky/oe-init-build-env ${BUILDDIRECTORY}

echo "testing6"

unset BUILDDIRECTORY
unset TEMPLATECONF

echo "testing7"
