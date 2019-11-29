#!/bin/sh

if ./gradlew :check; 
then
    ./gradlew :bootRun &
 
    springPID=$!

    npx cypress run
    
    ec=$?
    
    kill $springPID
    
    exit $ec

else
    exit 1
fi

