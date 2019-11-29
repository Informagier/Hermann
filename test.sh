#!/bin/sh

set -e

./gradlew :check
./gradlew :bootRun &
 
springPID=$!
 
npx cypress run

