#!/bin/sh
echo "Current directoyr"
pwd
./gradlew --build-file=build.gradle build --exclude-task test
echo "Starting the app..."
./gradlew --build-file build.gradle bootRun