@echo off
rem Starts the app on Windows. Only Java 17+ is required (Maven is downloaded automatically).
cd /d "%~dp0"

where java >nul 2>nul
if errorlevel 1 (
  echo Java 17+ is not installed. Install it from https://adoptium.net and try again.
  pause
  exit /b 1
)

echo Starting on http://localhost:8080 (first run downloads dependencies, please wait)...
call mvnw.cmd spring-boot:run
pause
