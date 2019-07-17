#!/bin/bash

echo "running surefire tests"

/usr/bin/mvn clean test -B -T 1C

echo "exiting...."
sleep 1000000
#exit 0