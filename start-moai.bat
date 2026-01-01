@echo off
REM Moai Server Bootstrap Script (Windows)
REM Automatically downloads and installs the Moai server
REM with pre-configured Moai-Core plugin

setlocal enabledelayedexpansion

title Moai Server Bootstrap
color 0A

echo.
echo =====================================
echo      Moai Server Bootstrap
echo      Professional Minecraft Server
echo =====================================
echo.

REM Configuration variables
set SERVER_JAR=moai-server.jar
set DOWNLOAD_URL=https://github.com/deivaxxx/Moai/releases/download/latest/moai-server.jar
set MEMORY_MIN=4G
set MEMORY_MAX=8G
set PLUGINS_DIR=plugins

REM Create necessary directories
if not exist "%PLUGINS_DIR%" (
    echo Creating plugins directory...
    mkdir "%PLUGINS_DIR%"
)

if not exist "logs" (
    mkdir "logs"
)

if not exist "world" (
    mkdir "world"
)

REM Check if server JAR exists, if not download it
if not exist "%SERVER_JAR%" (
    echo.
    echo [INFO] Server JAR not found. Downloading Moai Server...
    echo [INFO] This may take a few minutes depending on your internet speed.
    echo.
    
    powershell -Command "& {
        try {
            $ProgressPreference = 'SilentlyContinue'
            Invoke-WebRequest -Uri '%DOWNLOAD_URL%' -OutFile '%SERVER_JAR%' -ErrorAction Stop
            Write-Host '[OK] Server downloaded successfully' -ForegroundColor Green
        } catch {
            Write-Host '[ERROR] Failed to download server' -ForegroundColor Red
            Write-Host $_.Exception.Message -ForegroundColor Red
            exit 1
        }
    }"
    
    if %errorlevel% neq 0 (
        echo [ERROR] Download failed. Please check your internet connection.
        echo [ERROR] You can manually download from: %DOWNLOAD_URL%
        pause
        exit /b 1
    )
) else (
    echo [OK] Server JAR found: %SERVER_JAR%
)

REM Check for moai.yml configuration file
if not exist "moai.yml" (
    echo.
    echo [INFO] Creating default moai.yml configuration...
    (
        echo # Moai Server Configuration
        echo # Language: es ^(Spanish^) or en ^(English^)
        echo language: es
        echo.
        echo # Show ASCII banner on startup
        echo showAsciBanner: true
        echo.
        echo # Enable minigame optimizations
        echo enableMinigameOptimizations: true
        echo.
        echo # Maximum entities per chunk
        echo maxEntitiesPerChunk: 32
        echo.
        echo # Enable welcome effects for players
        echo enableWelcomeEffects: true
        echo.
        echo # Enable async mob spawning
        echo enableAsyncMobSpawning: true
        echo.
        echo # Server brand name
        echo serverBrand: Moai
        echo.
        echo # Enable performance monitoring
        echo enablePerformanceMonitoring: false
    ) > moai.yml
    echo [OK] moai.yml created with default configuration
)

REM Check if eula.txt exists, if not create it
if not exist "eula.txt" (
    echo.
    echo [WARNING] You must agree to the Minecraft EULA to run the server.
    echo [WARNING] The server will stop after this run.
    echo.
    (
        echo # By changing the below to true you are indicating your agreement to our EULA
        echo # https://account.mojang.com/documents/minecraft_eula
        echo eula=false
    ) > eula.txt
    echo [OK] eula.txt created - PLEASE ACCEPT THE EULA IN eula.txt
    echo [INFO] Edit eula.txt and change 'eula=false' to 'eula=true'
    echo.
    pause
    exit /b 1
)

REM Check if EULA is accepted
for /f "tokens=2 delims==" %%a in (eula.txt) do (
    if "%%a"=="true" (
        set EULA_ACCEPTED=true
    )
)

if not "!EULA_ACCEPTED!"=="true" (
    echo [ERROR] EULA not accepted. Please accept the EULA in eula.txt
    pause
    exit /b 1
)

REM Check Java installation
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java not found. Please install Java 21 or higher.
    echo [ERROR] Download from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

REM Display server information
echo.
echo =====================================
echo      Starting Moai Server
echo =====================================
echo.
echo Memory: Min %MEMORY_MIN% - Max %MEMORY_MAX%
echo Plugins Directory: %PLUGINS_DIR%
echo.
echo Moai Server is starting...
echo Press CTRL+C to stop the server
echo.

REM Start the server
java -Xms%MEMORY_MIN% -Xmx%MEMORY_MAX% -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:G1NewCollectionPercentage=30 -XX:G1MaxNewGenPercent=40 -XX:InitiatingHeapOccupancyPercent=35 -XX:TargetSurvivorRatio=90 -XX:G1HeapRegionSize=16m -XX:G1HeapWastePercent=10 -XX:G1MixedGCCountTarget=4 -XX:MaxTenuringThreshold=1 -XX:G1MixedGCLiveThresholdPercent=35 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:SurvivorRatio=32 -Dcom.mojang.eula.agree=true -jar "%SERVER_JAR%" nogui

REM If we reach here, server stopped
echo.
echo [INFO] Server stopped
echo [INFO] Logs available in: logs\
pause
