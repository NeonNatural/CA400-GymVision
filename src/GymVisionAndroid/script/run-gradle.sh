#!/bin/sh

export GRADLE_USER_HOME="$CACHE/gradle"
cd src/GymVisionAndroid
exec sh gradlew --no-daemon "$@"
