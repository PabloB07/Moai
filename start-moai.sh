#!/bin/bash

# Moai Server Bootstrap Script (Unix/Linux/macOS)
# Automatically downloads and installs the Moai server
# with pre-configured Moai-Core plugin

set -e

# Configuration variables
SERVER_JAR="moai-server.jar"
DOWNLOAD_URL="https://github.com/deivaxxx/Moai/releases/download/latest/moai-server.jar"
MEMORY_MIN="4G"
MEMORY_MAX="8G"
PLUGINS_DIR="plugins"
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Functions
print_header() {
    echo -e "${BLUE}"
    echo "====================================="
    echo "      Moai Server Bootstrap"
    echo "      Professional Minecraft Server"
    echo "====================================="
    echo -e "${NC}"
}

print_info() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_ok() {
    echo -e "${GREEN}[OK]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

# Main script
main() {
    cd "$SCRIPT_DIR"
    
    print_header
    
    # Create necessary directories
    print_info "Setting up directories..."
    mkdir -p "$PLUGINS_DIR"
    mkdir -p "logs"
    mkdir -p "world"
    
    # Check if server JAR exists
    if [ ! -f "$SERVER_JAR" ]; then
        echo
        print_info "Server JAR not found. Downloading Moai Server..."
        print_info "This may take a few minutes depending on your internet speed."
        echo
        
        # Try downloading with curl first, fall back to wget
        if command -v curl &> /dev/null; then
            if curl -L -o "$SERVER_JAR" "$DOWNLOAD_URL"; then
                print_ok "Server downloaded successfully"
            else
                print_error "Failed to download server with curl"
                print_info "You can manually download from: $DOWNLOAD_URL"
                exit 1
            fi
        elif command -v wget &> /dev/null; then
            if wget -O "$SERVER_JAR" "$DOWNLOAD_URL"; then
                print_ok "Server downloaded successfully"
            else
                print_error "Failed to download server with wget"
                print_info "You can manually download from: $DOWNLOAD_URL"
                exit 1
            fi
        else
            print_error "Neither curl nor wget found. Please install one of them."
            exit 1
        fi
    else
        print_ok "Server JAR found: $SERVER_JAR"
    fi
    
    # Check for moai.yml configuration file
    if [ ! -f "moai.yml" ]; then
        echo
        print_info "Creating default moai.yml configuration..."
        cat > moai.yml << 'EOF'
# Moai Server Configuration
# Language: es (Spanish) or en (English)
language: es

# Show ASCII banner on startup
showAsciBanner: true

# Enable minigame optimizations
enableMinigameOptimizations: true

# Maximum entities per chunk
maxEntitiesPerChunk: 32

# Enable welcome effects for players
enableWelcomeEffects: true

# Enable async mob spawning
enableAsyncMobSpawning: true

# Server brand name
serverBrand: Moai

# Enable performance monitoring
enablePerformanceMonitoring: false
EOF
        print_ok "moai.yml created with default configuration"
    fi
    
    # Check if eula.txt exists
    if [ ! -f "eula.txt" ]; then
        echo
        print_warning "You must agree to the Minecraft EULA to run the server."
        print_warning "The server will stop after this run."
        echo
        cat > eula.txt << 'EOF'
# By changing the below to true you are indicating your agreement to our EULA
# https://account.mojang.com/documents/minecraft_eula
eula=false
EOF
        print_ok "eula.txt created - PLEASE ACCEPT THE EULA IN eula.txt"
        print_info "Edit eula.txt and change 'eula=false' to 'eula=true'"
        echo
        exit 1
    fi
    
    # Check if EULA is accepted
    if ! grep -q "eula=true" eula.txt; then
        print_error "EULA not accepted. Please accept the EULA in eula.txt"
        exit 1
    fi
    
    # Check Java installation
    if ! command -v java &> /dev/null; then
        print_error "Java not found. Please install Java 21 or higher."
        print_error "On Ubuntu/Debian: sudo apt-get install openjdk-21-jre-headless"
        print_error "On Fedora: sudo dnf install java-21-openjdk-headless"
        print_error "On macOS: brew install openjdk@21"
        exit 1
    fi
    
    # Verify Java version
    java_version=$(java -version 2>&1 | grep -oP 'version "\K[^"]*' | cut -d. -f1)
    if [ "$java_version" -lt 21 ]; then
        print_warning "Java version should be 21 or higher (found: $java_version)"
    fi
    
    # Display server information
    echo
    echo "====================================="
    echo "      Starting Moai Server"
    echo "====================================="
    echo
    print_info "Memory: Min $MEMORY_MIN - Max $MEMORY_MAX"
    print_info "Plugins Directory: $PLUGINS_DIR"
    echo
    print_info "Moai Server is starting..."
    print_info "Press CTRL+C to stop the server"
    echo
    
    # Start the server with optimized JVM flags
    java \
        -Xms"$MEMORY_MIN" \
        -Xmx"$MEMORY_MAX" \
        -XX:+UseG1GC \
        -XX:MaxGCPauseMillis=200 \
        -XX:+UnlockExperimentalVMOptions \
        -XX:G1NewCollectionPercentage=30 \
        -XX:G1MaxNewGenPercent=40 \
        -XX:InitiatingHeapOccupancyPercent=35 \
        -XX:TargetSurvivorRatio=90 \
        -XX:G1HeapRegionSize=16m \
        -XX:G1HeapWastePercent=10 \
        -XX:G1MixedGCCountTarget=4 \
        -XX:MaxTenuringThreshold=1 \
        -XX:G1MixedGCLiveThresholdPercent=35 \
        -XX:G1RSetUpdatingPauseTimePercent=5 \
        -XX:SurvivorRatio=32 \
        -Dcom.mojang.eula.agree=true \
        -jar "$SERVER_JAR" nogui
    
    # If we reach here, server stopped
    echo
    print_info "Server stopped"
    print_info "Logs available in: logs/"
}

# Handle script interrupt
trap 'echo; print_info "Server shutdown signal received"; exit 0' SIGINT SIGTERM

# Run main function
main
