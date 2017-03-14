#!/bin/bash

# This script compiles and executes our final project

javac *.java

java -cp .:mysql-connector-java-5.1.41-bin.jar AppMain
