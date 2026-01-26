#!/bin/bash

set -e  # stop on first error

JAVA_FX=/usr/share/openjfx/lib
OUT=out
SRC=src

echo "▶ Compiling..."
mkdir -p "$OUT"

# Compile all Java files
javac \
  --module-path "$JAVA_FX" \
  --add-modules javafx.controls,javafx.fxml \
  -d "$OUT" \
  $(find "$SRC" -name "*.java")

echo "▶ Copying FXML resources..."
cp "$SRC/main.fxml" "$OUT/"

echo "▶ Running JavaFX app..."
java \
  --module-path "$JAVA_FX" \
  --add-modules javafx.controls,javafx.fxml \
  -cp "$OUT" \
  ui.FXApp
