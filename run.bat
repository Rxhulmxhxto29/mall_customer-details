@echo off
echo ========================================
echo   Shopping Mall Application Launcher
echo ========================================
echo.

REM Check if Maven is available
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven is not found in PATH!
    echo.
    echo Please add Maven to your system PATH:
    echo 1. Find your Maven installation directory
    echo 2. Add the 'bin' folder to your PATH environment variable
    echo 3. Restart this terminal
    echo.
    echo OR use VS Code:
    echo 1. Install Spring Boot Extension Pack from Extensions
    echo 2. Open Spring Boot Dashboard
    echo 3. Click Run on MallApplication
    echo.
    pause
    exit /b 1
)

REM Check Java version
echo Checking Java version...
java -version 2>&1 | findstr /i "version" | findstr /i "17\|18\|19\|20\|21"
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo WARNING: Java 17 or higher is required!
    echo Current Java version:
    java -version
    echo.
    echo Please install Java 17+ and set JAVA_HOME
    echo.
    pause
    exit /b 1
)

echo.
echo Java version OK!
echo.
echo Starting Shopping Mall Application...
echo This may take a minute on first run...
echo.
echo Once started:
echo - Website: http://localhost:8080
echo - Login: test@example.com / password
echo.
echo Press Ctrl+C to stop the server
echo.
echo ========================================
echo.

mvn spring-boot:run
