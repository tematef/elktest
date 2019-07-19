#!/bin/bash

echo "running surefire tests"

/usr/bin/mvn clean test -B -T 1C

echo "generating allure report"

/usr/bin/mvn allure:report

echo "copy allure report to mounted folder"

cp -rf /framework/target/allure-report /framework/mnt

echo "exiting...."
exit 0