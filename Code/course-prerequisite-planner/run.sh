#!/usr/bin/env sh
set -e
mvn clean test
mvn exec:java
