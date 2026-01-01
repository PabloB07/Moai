#!/bin/bash

set -e

echo "========================================="
echo "  Moai Server Build Environment Setup"
echo "========================================="
echo ""

build=false

if [[ "$1" == "--build" ]]; then
  build=true
  echo "Running with --build flag - full build will be performed."
  echo ""
fi

echo "Building development environment..."
./gradlew clean fatJar

if [ $? -eq 0 ]; then
  echo ""
  echo "========================================="
  echo "  ✓ Build Successful!"
  echo "========================================="
  echo ""
  echo "Generated JAR files:"
  echo "  - moai-api/build/libs/"
  echo "  - moai-server/build/libs/"
  echo "  - Moai-test-plugins/build/libs/"
  echo ""
else
  echo ""
  echo "========================================="
  echo "  ✗ Build Failed"
  echo "========================================="
  exit 1
fi
