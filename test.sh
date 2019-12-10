#!/bin/sh

if ./gradlew :check; 
then
    ./gradlew :bootRun &
 
    springPID=$!

    npm test
    
    ec=$?
    
    kill $springPID
    
    exit $ec

else
    exit 1
fi

