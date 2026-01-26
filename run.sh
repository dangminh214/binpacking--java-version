#!/bin/bash

JAVA_FX=/usr/share/openjfx/lib
OUT=out

mkdir -p $OUT

# compile ALL java files recursively
javac \
  --module-path $JAVA_FX \
  --add-modules javafx.controls \
  -d $OUT \
  $(find src -name "*.java")

# run your JavaFX main class (adjust package if needed)
java \
  --module-path $JAVA_FX \
  --add-modules javafx.controls \
  -cp $OUT \
  HelloFX
