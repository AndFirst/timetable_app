#!/bin/bash

if [[ $1 == "-nt" ]]; then
    ./mvnw clean package -Pproduction -DskipTests
else
    ./mvnw clean package -Pproduction
fi

docker build . -t timetable:latest
