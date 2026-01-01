# 🗿 Moai Server - Complete Documentation Index

## Welcome / Bienvenido

¡Bienvenido al servidor Moai! / Welcome to Moai Server!

Esta documentación completa te guiará a través de cada aspecto del servidor profesional multihilo más avanzado de Minecraft.

---

## 📑 Documentation Index

### 🚀 **Getting Started**

#### 1. [QUICK_START.md](QUICK_START.md) - **START HERE** ⭐
- **Time to read:** 5 minutos / 5 minutes
- **Level:** Beginner
- Quick installation in 30 seconds
- First-time setup walkthrough
- Basic commands overview
- Troubleshooting quick tips

**Start with this if you want to get running in 30 seconds!**

---

### 📖 **Detailed Guides**

#### 2. [BOOTSTRAP_README.md](BOOTSTRAP_README.md)
- **Time to read:** 10 minutes
- **Level:** Beginner to Intermediate
- Bootstrap script documentation
- Advanced configuration options
- Memory and JVM settings
- System requirements
- Platform-specific instructions

**Read this for detailed bootstrap information**

#### 3. [MOAI_PROJECT_SUMMARY.md](MOAI_PROJECT_SUMMARY.md)
- **Time to read:** 20 minutes
- **Level:** Intermediate
- Complete project overview
- All tasks completed
- Feature breakdown
- Architecture overview
- Performance improvements

**Read this for a comprehensive project summary**

#### 4. [MOAI_CORE_USAGE.md](MOAI_CORE_USAGE.md)
- **Time to read:** 25 minutes
- **Level:** Intermediate to Advanced
- Plugin API documentation
- Command reference with examples
- Developer guide with code samples
- Arena management API
- Configuration access examples
- Best practices and performance tips

**Read this if you want to develop plugins or extend Moai-Core**

#### 5. [ARCHITECTURE.md](ARCHITECTURE.md)
- **Time to read:** 20 minutes
- **Level:** Advanced
- System architecture diagrams
- Component interaction flows
- Data flow pipelines
- Thread architecture
- Performance optimization strategy
- Deployment workflow

**Read this if you want to understand how Moai works internally**

---

### ⚙️ **Configuration**

#### 6. [moai.yml.example](moai.yml.example)
- **Time to read:** 5 minutes
- **Level:** Beginner
- Configuration file documentation
- All available options explained
- Configuration examples for different scenarios
- Default values
- Comments in Spanish and English

**Use this to understand and configure moai.yml**

---

### 📝 **Project Information**

#### 7. [README.md](README.md)
- Project overview
- Feature list
- Installation instructions
- Contributing guidelines
- License information

**Standard project README**

---

## 🎯 Quick Navigation by Use Case

### "I want to start the server NOW"
1. Read: [QUICK_START.md](QUICK_START.md)
2. Run: `start-moai.bat` or `./start-moai.sh`
3. Accept EULA in `eula.txt`
4. Enjoy! 🎉

**Time: 5 minutes**

---

### "I want to understand Moai's features"
1. Read: [MOAI_PROJECT_SUMMARY.md](MOAI_PROJECT_SUMMARY.md)
2. Read: [ARCHITECTURE.md](ARCHITECTURE.md)
3. Explore: Plugin commands with `/moaicore help`

**Time: 40 minutes**

---

### "I want to configure the server for my needs"
1. Read: [QUICK_START.md](QUICK_START.md) - Configuration section
2. Reference: [moai.yml.example](moai.yml.example)
3. Edit: `moai.yml` in your server directory
4. Reload: `/moaicore reload`

**Time: 15 minutes**

---

### "I want to develop plugins for Moai"
1. Read: [MOAI_CORE_USAGE.md](MOAI_CORE_USAGE.md)
2. Reference: [ARCHITECTURE.md](ARCHITECTURE.md) for internals
3. Use: Moai-Core API examples in MOAI_CORE_USAGE.md
4. Create: Your own plugin using ArenaManager

**Time: 1-2 hours for fundamentals**

---

### "I want to troubleshoot issues"
1. Check: [QUICK_START.md](QUICK_START.md) - Troubleshooting section
2. Review: [BOOTSTRAP_README.md](BOOTSTRAP_README.md) - Troubleshooting section
3. Check: `logs/latest.log` for error details
4. Ask: Create an issue on GitHub

**Time: 15-30 minutes**

---

### "I want to optimize performance"
1. Read: [ARCHITECTURE.md](ARCHITECTURE.md) - Performance section
2. Reference: [moai.yml.example](moai.yml.example) - Performance settings
3. Tune: `maxEntitiesPerChunk`, `enableAsyncMobSpawning`, memory
4. Monitor: `/moaicore status` and check `enablePerformanceMonitoring`

**Time: 30 minutes setup + ongoing monitoring**

---

## 📊 Document Map

```
User Experience by Document:

QUICK_START.md
└─► BOOTSTRAP_README.md
    └─► MOAI_PROJECT_SUMMARY.md
        └─► MOAI_CORE_USAGE.md
            └─► ARCHITECTURE.md
                └─► Source Code (Advanced)

Configuration Path:
QUICK_START.md ─► moai.yml.example ─► moai.yml

Plugin Development Path:
MOAI_CORE_USAGE.md ─► ARCHITECTURE.md ─► Source Code
```

---

## 🎓 Learning Paths

### Path 1: Server Administrator
**Goal:** Run and maintain a Moai server

1. [QUICK_START.md](QUICK_START.md) (5 min)
2. [BOOTSTRAP_README.md](BOOTSTRAP_README.md) (10 min)
3. [moai.yml.example](moai.yml.example) (5 min)
4. Practice with server for 1 hour
5. **Total: ~1.5 hours to be proficient**

---

### Path 2: Plugin Developer
**Goal:** Create plugins that work with Moai-Core

1. [QUICK_START.md](QUICK_START.md) (5 min)
2. [MOAI_CORE_USAGE.md](MOAI_CORE_USAGE.md) (25 min)
3. [ARCHITECTURE.md](ARCHITECTURE.md) (20 min)
4. Review source code in `NVFolia-test-plugins/main/main/moaicore/`
5. Create a test plugin
6. **Total: ~3 hours to start developing**

---

### Path 3: System Architect
**Goal:** Understand complete system design

1. [MOAI_PROJECT_SUMMARY.md](MOAI_PROJECT_SUMMARY.md) (20 min)
2. [ARCHITECTURE.md](ARCHITECTURE.md) (20 min)
3. Review all source code files
4. Study thread architecture and data flows
5. **Total: ~2-3 hours for deep understanding**

---

## 🔗 Quick Links

### Official Resources
- **GitHub Repository:** https://github.com/deivaxxx/Moai
- **Issue Tracker:** https://github.com/deivaxxx/Moai/issues
- **Project Discussions:** GitHub Discussions

### Related Projects
- **Paper:** https://papermc.io/ (Spigot fork)
- **Spigot:** https://www.spigotmc.org/ (Plugin platform)
- **Bukkit API:** https://bukkit.org/ (Plugin development)

### Minecraft Information
- **Official Minecraft:** https://minecraft.net
- **Minecraft Wiki:** https://minecraft.fandom.com/
- **Mojang:** https://www.mojang.com/

---

## 📈 Progress Checklist

Use this checklist to track your Moai journey:

- [ ] Read QUICK_START.md
- [ ] Start server with bootstrap script
- [ ] Accept EULA and start playing
- [ ] Edit moai.yml and customize
- [ ] Use /moaicore commands
- [ ] Join a game and see welcome effects
- [ ] Read MOAI_PROJECT_SUMMARY.md
- [ ] (Optional) Read MOAI_CORE_USAGE.md
- [ ] (Optional) Create a plugin
- [ ] (Advanced) Study ARCHITECTURE.md
- [ ] (Advanced) Contribute to Moai

---

## 💡 Tips for Success

1. **Start Simple** - Don't read all docs at once
2. **Hands-On** - Start the server and explore
3. **Incremental** - Learn features as you need them
4. **Community** - Ask questions on GitHub Issues
5. **Backup** - Always backup your `world/` folder
6. **Monitor** - Check logs regularly for issues
7. **Update** - Keep Moai updated for security fixes

---

## ❓ FAQ

### Q: Which document should I read first?
**A:** Start with [QUICK_START.md](QUICK_START.md) - it's designed for 5-minute setup!

### Q: I'm a developer - where do I start?
**A:** Read [MOAI_CORE_USAGE.md](MOAI_CORE_USAGE.md) with code examples.

### Q: I want to understand the architecture
**A:** Read [ARCHITECTURE.md](ARCHITECTURE.md) with diagrams and flows.

### Q: How do I configure the server?
**A:** Edit `moai.yml` using [moai.yml.example](moai.yml.example) as reference.

### Q: Can I get the server running in 30 seconds?
**A:** Yes! Follow [QUICK_START.md](QUICK_START.md) - literally 30 seconds.

### Q: Where is the source code?
**A:** 
- **API:** `nvfolia-api/src/main/java/io/canvasmc/moai/`
- **Plugin:** `NVFolia-test-plugins/main/main/moaicore/`

---

## 📞 Support & Help

### Getting Help

1. **Check docs first** - Most questions are answered here
2. **Search issues** - GitHub issues might have your answer
3. **Create an issue** - If you found a bug or have a suggestion
4. **Discussions** - For general questions and chat

### Reporting Issues

When reporting a bug, include:
- Moai version
- Java version
- Error message from `logs/latest.log`
- Steps to reproduce
- Expected vs actual behavior

---

## 🎉 Ready to Go!

You now have everything you need to:
- ✅ Run a professional Minecraft server
- ✅ Configure it for your needs
- ✅ Extend it with plugins
- ✅ Optimize performance
- ✅ Understand the architecture

**Choose your starting document above and begin your Moai journey!**

🗿 **Welcome to Moai Server - Professional Minecraft at its finest** 🗿

---

## 📄 Document Statistics

| Document | Size | Read Time | Level |
|----------|------|-----------|-------|
| QUICK_START.md | ~12 KB | 5 min | Beginner |
| BOOTSTRAP_README.md | ~8 KB | 10 min | Beginner |
| MOAI_PROJECT_SUMMARY.md | ~15 KB | 20 min | Intermediate |
| MOAI_CORE_USAGE.md | ~20 KB | 25 min | Intermediate |
| ARCHITECTURE.md | ~18 KB | 20 min | Advanced |
| moai.yml.example | ~4 KB | 5 min | Beginner |
| **Total** | **~77 KB** | **~85 min** | **Mixed** |

---

**Documentation Index**
**Version:** 1.0
**Date:** January 1, 2026
**Status:** Complete ✅
**Last Updated:** 2026-01-01

**Table of Contents Last Generated:** January 1, 2026
