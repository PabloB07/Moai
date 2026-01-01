# Moai Server Bootstrap

Automatic installation and startup scripts for the Moai Server.

## Features

✨ **Automatic Server Download** - Automatically downloads the latest Moai server JAR if not present

📦 **Pre-configured Plugin** - Includes Moai-Core plugin with minigame support

⚙️ **Configuration Management** - Auto-creates `moai.yml` with sensible defaults

✅ **EULA Handling** - Ensures EULA acceptance before server start

🔧 **Optimized JVM Flags** - Pre-configured G1GC with optimal performance settings

🌐 **Bilingual Support** - Spanish (es) and English (en) support

## Requirements

### Windows
- Java 21 or higher
- Windows 10/11

### Linux/macOS
- Java 21 or higher
- Bash shell
- `curl` or `wget` for downloads

## Quick Start

### Windows
```batch
start-moai.bat
```

### Linux/macOS
```bash
./start-moai.sh
```

## First Run

On first run, the script will:

1. **Create directories** - Automatically creates `plugins/`, `logs/`, and `world/` folders
2. **Download server** - Downloads the Moai server JAR if not present
3. **Create configuration** - Generates `moai.yml` with default settings
4. **Request EULA** - Creates `eula.txt` and asks you to accept the Minecraft EULA
5. **Start server** - Launches the server with optimized JVM settings

## Configuration

### moai.yml

Located in the server directory. Available options:

```yaml
# Language: es (Spanish) or en (English)
language: es

# Show ASCII banner on startup
showAsciBanner: true

# Enable minigame optimizations
enableMinigameOptimizations: true

# Maximum entities per chunk (prevents lag in minigames)
maxEntitiesPerChunk: 32

# Enable welcome effects for players
enableWelcomeEffects: true

# Enable async mob spawning
enableAsyncMobSpawning: true

# Server brand name (shown in /version)
serverBrand: Moai

# Enable performance monitoring
enablePerformanceMonitoring: false
```

### Memory Settings

Edit the script to adjust memory:
- **Windows**: Change `MEMORY_MIN` and `MEMORY_MAX` in `start-moai.bat`
- **Linux/macOS**: Change `MEMORY_MIN` and `MEMORY_MAX` in `start-moai.sh`

Default: 4GB minimum, 8GB maximum

## Troubleshooting

### "Java not found"
Install Java 21 or higher:
- **Windows**: Download from [Oracle](https://www.oracle.com/java/technologies/downloads/)
- **Linux**: `sudo apt-get install openjdk-21-jre-headless` (Ubuntu/Debian)
- **macOS**: `brew install openjdk@21`

### "EULA not accepted"
Edit `eula.txt` and change `eula=false` to `eula=true`

### "Failed to download server"
Manually download from GitHub and place the JAR in the server directory

### Server crashes or runs slowly
- Increase memory settings in the script
- Disable unused plugins
- Check `logs/latest.log` for error messages

## Commands

Once the server is running:

```
/moaicore status       - View server status
/moaicore reload       - Reload configuration
/moaicore arena list   - List available arenas
/moaicore version      - View server version
/moaicore effects      - View welcome effects status
```

## Support

For issues or feature requests, visit the [Moai GitHub repository](https://github.com/deivaxxx/Moai)

## License

Moai Server is licensed under the same terms as the original Paper/Spigot projects.
