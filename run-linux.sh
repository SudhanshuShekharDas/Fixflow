#!/bin/sh
# Starts the app. Works on Linux/macOS. Only Java 17+ is required (Maven is downloaded automatically).
cd "$(dirname "$0")" || exit 1

if ! command -v java >/dev/null 2>&1; then
  echo "Java 17+ is not installed. Install it first (e.g. 'sudo apt install openjdk-17-jdk')."
  exit 1
fi

chmod +x ./mvnw 2>/dev/null
echo "Starting on http://localhost:8080 (first run downloads dependencies, please wait)..."
./mvnw spring-boot:run
