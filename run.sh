#!/bin/bash

set -e  # stop on first error

JAVA_FX=/usr/share/openjfx/lib
OUT=out

echo "▶ Compiling..."
mkdir -p "$OUT"

javac \
  --module-path "$JAVA_FX" \
  --add-modules javafx.controls,javafx.fxml \
  -d "$OUT" \
  $(find src -name "*.java")

echo "▶ Running JavaFX app..."
java \
  --module-path "$JAVA_FX" \
  --add-modules javafx.controls,javafx.fxml \
  -cp "$OUT" \
  ui.FXApp
