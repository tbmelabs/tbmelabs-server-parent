#!/bin/bash
# Deployment script for tbmelabs-tv-constants
# Code open source hosted on: https://github.com/tbmelabs/tbmelabs-tv-constants

set -ev

if [[ $TRAVIS_BRANCH == "master" && $TRAVIS_PULL_REQUEST == "false" ]]; then
  # mvn versions:set deploy -DremoveSnapshot -DskipTests=true
elif [[ $TRAVIS_PULL_REQUEST == "false" ]]; then
  mvn deploy -DskipTests=true
fi

exit $?
