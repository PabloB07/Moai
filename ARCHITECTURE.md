# 🗿 Moai Project Structure & Architecture

## Project Overview

```
Moai Server (Servidor Moai)
│
├─ Core API (nvfolia-api/)
│  └─ io/canvasmc/moai/
│     ├─ MoaiInitializer.java           🎯 Server startup orchestrator
│     ├─ MoaiBanner.java                🎨 ASCII art (bilingual)
│     └─ config/
│        ├─ MoaiConfig.java             ⚙️ Configuration model (@Configuration)
│        └─ MoaiConfigLoader.java       📋 Configuration manager
│
├─ Plugin System (NVFolia-test-plugins/)
│  └─ main/main/moaicore/
│     ├─ MoaiCorePlugin.java            🔌 Main plugin class
│     ├─ arena/
│     │  ├─ ArenaManager.java           🏟️ Multi-arena manager with thread pool
│     │  └─ MinigameArena.java          🎮 Individual arena with async executor
│     ├─ listener/
│     │  └─ WelcomeEffectsListener.java 🎊 Player join effects (sound, BossBar, messages)
│     └─ command/
│        └─ MoaiCoreCommand.java        💻 Command handler (bilingual)
│
├─ Bootstrap (Root directory)
│  ├─ start-moai.bat                    🖥️ Windows auto-installer
│  ├─ start-moai.sh                     🐧 Unix/Linux auto-installer
│  └─ moai.yml                          ⚙️ Server configuration file
│
└─ Documentation
   ├─ QUICK_START.md                    🚀 30-second quick start
   ├─ BOOTSTRAP_README.md               📖 Bootstrap documentation
   ├─ MOAI_PROJECT_SUMMARY.md           📊 Complete project summary
   ├─ MOAI_CORE_USAGE.md                🔧 Plugin development guide
   ├─ moai.yml.example                  💾 Configuration examples
   └─ README.md                         📝 Project information
```

---

## Component Interaction Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                      Moai Server                            │
│                    (nvfolia-server)                         │
└───────────────────────┬─────────────────────────────────────┘
                        │ Initializes
                        ▼
        ┌───────────────────────────────┐
        │    MoaiInitializer.java       │
        │  (Orchestrates startup)       │
        └───────────────┬───────────────┘
                        │
         ┌──────────────┼──────────────┐
         │              │              │
         ▼              ▼              ▼
   Load Config   Show Banner      Init Plugin
   via YAML      (moai.yml)        (Moai-Core)
         │              │              │
         ▼              ▼              ▼
  MoaiConfig     MoaiBanner    MoaiCorePlugin
  (Settings)     (ASCII art)   (onEnable())
         │              │              │
         └──────────────┴──────────────┘
                        │
                        ▼
        ┌──────────────────────────────┐
        │    Moai Server Ready ✅      │
        │  - Events listening          │
        │  - Commands registered       │
        │  - Arenas initialized        │
        └──────────────────────────────┘
```

---

## Data Flow: Player Join

```
Player Joins Server
        │
        ▼
PlayerJoinEvent (Bukkit)
        │
        ▼
WelcomeEffectsListener.onPlayerJoin()
        │
        ├─► Play Sound (ENTITY_PLAYER_LEVELUP)
        │
        ├─► Send Bilingual Message
        │   ├─ Spanish: "¡Bienvenido a Moai Server!"
        │   └─ English: "Welcome to Moai Server!"
        │
        ├─► Display BossBar
        │   ├─ Color: GOLD
        │   ├─ Duration: 10 seconds
        │   └─ Title: "🗿 Moai Server"
        │
        └─► Play Celebratory Sound (BLOCK_BELL_RESONATE)
```

---

## Architecture: Arena Management

```
MoaiCorePlugin
    │
    ├─► ArenaManager (ThreadPool)
    │   ├─► ExecutorService (4 threads)
    │   │
    │   └─► Arenas (ConcurrentHashMap)
    │       │
    │       ├─► Arena 1: "pvp_arena"
    │       │   ├─ Executor (dedicated thread)
    │       │   ├─ Players: [UUID1, UUID2, UUID3]
    │       │   └─ Async Tasks
    │       │
    │       ├─► Arena 2: "parkour_challenge"
    │       │   ├─ Executor (dedicated thread)
    │       │   ├─ Players: [UUID4, UUID5]
    │       │   └─ Async Tasks
    │       │
    │       └─► Arena N: ...
    │
    └─► Commands
        └─► MoaiCoreCommand
            ├─ /moaicore reload
            ├─ /moaicore status
            ├─ /moaicore arena list
            └─ /moaicore arena info
```

---

## Configuration Flow

```
Server Start
    │
    ▼
MoaiInitializer.initialize()
    │
    ├─► MoaiConfigLoader.initialize()
    │   │
    │   ├─► Check moai.yml exists?
    │   │   ├─ NO: Create with defaults
    │   │   └─ YES: Load existing
    │   │
    │   ├─► Parse YAML using
    │   │   └─ AnnotationBasedYamlSerializer
    │   │
    │   └─► Populate MoaiConfig fields
    │       ├─ language: "es"
    │       ├─ showAsciBanner: true
    │       ├─ enableMinigameOptimizations: true
    │       ├─ maxEntitiesPerChunk: 32
    │       └─ ... (more settings)
    │
    ├─► MoaiBanner.displayBanner(language)
    │   └─ Show ASCII art in console
    │
    └─► Ready to serve! ✅
```

---

## Plugin Class Hierarchy

```
JavaPlugin (Bukkit)
    │
    └─► MoaiCorePlugin
        ├─ Implements: Listener
        │
        ├─ Fields:
        │  ├─ arenaManager: ArenaManager
        │  └─ instance: MoaiCorePlugin (static)
        │
        ├─ onEnable():
        │  ├─ Initialize Moai core
        │  ├─ Create ArenaManager
        │  ├─ Register listeners
        │  └─ Register commands
        │
        └─ Methods:
           ├─ getInstance()
           ├─ getArenaManager()
           ├─ getServerBrand()
           └─ getServerVersion()
```

---

## Command Architecture

```
/moaicore [subcommand] [args...]
    │
    ├─► MoaiCoreCommand.onCommand()
    │   │
    │   └─► Switch (subcommand)
    │
    ├─ reload     ─────► handleReload()         (require: moai.admin)
    │              └─► MoaiConfigLoader.initialize()
    │
    ├─ status     ─────► handleStatus()         (require: moai.command.status)
    │              └─► Show server info
    │
    ├─ arena      ─────► handleArena()          (require: moai.admin)
    │   │              ├─► list - Show all arenas
    │   │              └─► info <name> - Details
    │   └─ [list|info]
    │
    ├─ version    ─────► handleVersion()        (public)
    │              └─► Show Moai version
    │
    └─ effects    ─────► handleEffects()        (public)
                   └─► Show effects status
```

---

## Bootstrap Script Execution Flow

```
User runs: start-moai.bat / start-moai.sh
    │
    ├─► Check if moai-server.jar exists
    │   ├─ NO: Download from GitHub
    │   └─ YES: Continue
    │
    ├─► Create directories
    │   ├─ plugins/
    │   ├─ logs/
    │   └─ world/
    │
    ├─► Check if moai.yml exists
    │   ├─ NO: Create with defaults
    │   └─ YES: Continue
    │
    ├─► Check if eula.txt exists
    │   ├─ NO: Create and pause (user must accept)
    │   └─ YES: Continue
    │
    ├─► Validate EULA acceptance
    │   ├─ eula=false: STOP (user must edit)
    │   └─ eula=true: Continue
    │
    ├─► Verify Java installation
    │   ├─ NOT found: Error & exit
    │   └─ Found: Continue
    │
    ├─► Display server info
    │   └─ Memory, plugins dir, starting message
    │
    └─► Execute Java with optimized flags
        └─► java -Xms4G -Xmx8G -XX:+UseG1GC ... -jar moai-server.jar nogui
```

---

## Configuration Hierarchy

```
moai.yml (YAML file)
    │
    ├─► Parsed by AnnotationBasedYamlSerializer
    │
    └─► MoaiConfig.java (Java fields)
        │
        ├─► public static String language
        ├─► public static boolean showAsciBanner
        ├─► public static boolean enableWelcomeEffects
        ├─► public static boolean enableMinigameOptimizations
        ├─► public static int maxEntitiesPerChunk
        ├─► public static boolean enableAsyncMobSpawning
        ├─► public static String serverBrand
        └─► public static boolean enablePerformanceMonitoring
                │
                └─► Accessed via MoaiConfigLoader
                    ├─ getLanguage()
                    ├─ isShowAsciBanner()
                    ├─ isWelcomeEffectsEnabled()
                    ├─ getMaxEntitiesPerChunk()
                    └─ ... (more getters)
```

---

## Event Processing Pipeline

```
PlayerJoinEvent (Bukkit Event Bus)
    │
    ▼
WelcomeEffectsListener (Registered listener)
    │
    ├─► Check: enableWelcomeEffects?
    │   ├─ false: skip all effects
    │   └─ true: continue
    │
    ├─► Play sound immediately
    │   └─ player.playSound(Sound.ENTITY_PLAYER_LEVELUP)
    │
    ├─► Send message immediately
    │   └─ player.sendMessage(localizedMessage)
    │
    └─► Schedule BossBar display (5 ticks delay)
        │
        ├─► Create BossBar (GOLD, SEGMENTED_20)
        ├─► Add player to bar
        ├─► Play bell sound (50 ticks later)
        │
        └─► Remove BossBar (200 ticks later)
            └─► Clean up on PlayerQuitEvent
```

---

## Memory Architecture

```
Moai Server Process
├─ JVM Heap (-Xms4G -Xmx8G)
│  │
│  ├─ G1GC Garbage Collector
│  │  ├─ Young Generation
│  │  └─ Old Generation
│  │
│  ├─ Bukkit/Paper
│  │  ├─ World data
│  │  └─ Chunk cache
│  │
│  ├─ Plugins
│  │  ├─ Moai-Core
│  │  │  ├─ ArenaManager
│  │  │  ├─ Listeners
│  │  │  └─ Commands
│  │  │
│  │  └─ User plugins
│  │
│  └─ Moai Configuration
│     └─ moai.yml (cached in memory)
│
└─ Native Memory
   └─ LWJGL (OpenGL for rendering)
```

---

## Thread Architecture

```
Main Server Thread (Tick loop - 20 TPS)
├─ Event processing
├─ Command execution
├─ Player movement
└─ World updates

Arena Thread 1 (from ExecutorService)
├─ Async entity processing
├─ Custom minigame logic
└─ Arena-specific tasks

Arena Thread 2 (from ExecutorService)
├─ Async mob spawning
├─ Particle effects
└─ Arena-specific tasks

Arena Thread N (from ExecutorService)
└─ ...

JVM Threads
├─ Garbage Collector
├─ File I/O
└─ Network handlers (Netty)
```

---

## Bilingual Support Architecture

```
MoaiConfigLoader.getLanguage()  ─────┐
                                      │
                    ┌─────────────────┴─────────────────┐
                    │                                   │
                    ▼                                   ▼
            "es" (Spanish)                      "en" (English)
                    │                                   │
        ┌───────────┴──────────┐        ┌──────────────┴────────┐
        │                      │        │                       │
        ▼                      ▼        ▼                       ▼
    MoaiBanner         Messages      MoaiBanner            Messages
    (Español)          (Español)     (English)             (English)
        │                  │            │                     │
        └──────────────────┴────────────┴─────────────────────┘
                           │
                           ▼
                    Player sees localized
                    content based on
                    moai.yml setting
```

---

## Deployment Workflow

```
Developer
    │
    ├─► Edit source code
    │   └─ nvfolia-api/src/main/java/io/canvasmc/moai/
    │   └─ NVFolia-test-plugins/main/main/moaicore/
    │
    ├─► ./gradlew clean build
    │   └─ Compiles API and plugins
    │
    ├─► JAR artifacts created
    │   ├─ moai-server.jar (server)
    │   ├─ moai-api.jar (API)
    │   └─ Moai-Core-1.0.0.jar (plugin)
    │
    ├─► Deploy to server
    │   └─ Copy JAR files
    │
    └─► Server admin
        │
        ├─► Run bootstrap script
        │   └─ start-moai.bat / start-moai.sh
        │
        ├─► Server downloads JAR automatically
        ├─► Creates moai.yml
        ├─► Accepts EULA
        │
        └─► Server starts with all optimizations
            ├─ Players join
            ├─ Welcome effects trigger
            └─ Minigames run optimized
```

---

## Performance Optimization Strategy

```
Moai Performance Optimization
    │
    ├─► Thread Level
    │   ├─ Per-arena threads (ArenaManager)
    │   └─ Async mob spawning (PufferFish API)
    │
    ├─► Memory Level
    │   ├─ G1GC with low pause times (200ms)
    │   ├─ Entity limiting per chunk
    │   └─ Smart entity unloading
    │
    ├─► Code Level
    │   ├─ Async operations where possible
    │   ├─ Batched updates
    │   └─ Efficient data structures
    │
    └─► Configuration Level
        ├─ maxEntitiesPerChunk tuning
        ├─ enableAsyncMobSpawning toggle
        └─ enablePerformanceMonitoring for analysis
```

---

## Extensibility Points

```
Moai-Core Plugin (Extensible)
    │
    ├─► Can create custom listeners
    │   └─ Implement Listener interface
    │
    ├─► Can register custom commands
    │   └─ Implement CommandExecutor
    │
    ├─► Can create custom arenas
    │   └─ Use ArenaManager API
    │
    ├─► Can access configuration
    │   └─ Use MoaiConfigLoader
    │
    └─► Can create dependent plugins
        └─ Depend on "Moai-Core" in plugin.yml
```

---

## File Size Reference

```
moai-server.jar         ~50 MB (complete server)
Moai-Core-1.0.0.jar     ~500 KB (plugin)
moai.yml                ~2 KB (configuration)
logs/latest.log         ~1-10 MB (log file)
world/                  ~varies (world data)
plugins/                ~varies (all plugins)
```

---

**Architecture Document**
**Version:** 1.0
**Date:** January 1, 2026
**Status:** Complete ✅

This diagram represents the complete Moai server architecture and data flows.
