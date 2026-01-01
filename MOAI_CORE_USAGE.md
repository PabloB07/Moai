# Moai-Core Plugin - Examples and Usage Guide

## Plugin Overview

Moai-Core es el plugin central del servidor Moai que proporciona:
- 🎮 Gestión de arenas de minijuegos
- 🎨 Efectos visuales de bienvenida
- 🔧 Comandos de administración
- ⚙️ Optimizaciones de rendimiento

## Installation

El plugin se incluye automáticamente con el servidor. No requiere instalación manual.

```
plugins/
└── Moai-Core-1.0.0.jar
```

## Commands

### `/moaicore reload`
**Descripción:** Recarga la configuración desde moai.yml

**Uso:**
```
/moaicore reload
```

**Permisos:** `moai.admin` (operador por defecto)

**Ejemplo:**
```
> /moaicore reload
[Moai] ✓ Configuración de Moai recargada
```

---

### `/moaicore status`
**Descripción:** Muestra el estado actual del servidor

**Uso:**
```
/moaicore status
```

**Permisos:** `moai.command.status` (todos)

**Ejemplo salida:**
```
🗿 Estado del Servidor Moai
- Versión: Moai 1.21.5 - 1.21.11
- Marca: Moai
- Arenas Activas: 2
- Idioma: Español
- Efectos de Bienvenida: Habilitados
```

---

### `/moaicore arena list`
**Descripción:** Lista todas las arenas disponibles

**Uso:**
```
/moaicore arena list
```

**Permisos:** `moai.admin`

**Ejemplo salida:**
```
Arenas Disponibles:
- pvp_arena
- skyblock_minigame
- parkour_challenge
```

---

### `/moaicore arena info <nombre>`
**Descripción:** Muestra información detallada de una arena

**Uso:**
```
/moaicore arena info pvp_arena
```

**Permisos:** `moai.admin`

**Ejemplo salida:**
```
Información de Arena: pvp_arena
- Mundo: world
- Centro: world 100.0 64.0 200.0
- Radio: 50 bloques
- Jugadores: 3
- Activa: Sí
```

---

### `/moaicore version`
**Descripción:** Muestra la versión del servidor

**Uso:**
```
/moaicore version
```

**Permisos:** Todos

**Ejemplo salida:**
```
🗿 Moai Server
Versión Moai: Moai 1.21.5 - 1.21.11
Plugin: 1.0.0
```

---

### `/moaicore effects`
**Descripción:** Muestra el estado de los efectos de bienvenida

**Uso:**
```
/moaicore effects
```

**Permisos:** Todos

**Ejemplo salida:**
```
✓ Efectos de bienvenida: Habilitados
```

---

## API Usage (Para Desarrolladores)

### Obtener la instancia del plugin

```java
import io.canvasmc.moaicore.MoaiCorePlugin;

MoaiCorePlugin plugin = MoaiCorePlugin.getInstance();
```

### Trabajar con Arenas

#### Crear una arena
```java
import io.canvasmc.moaicore.arena.MinigameArena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

World world = Bukkit.getWorld("world");
Location center = new Location(world, 100, 64, 200);

MinigameArena arena = plugin.getArenaManager().createArena(
    "pvp_arena",
    world,
    center,
    50  // radius in blocks
);
```

#### Obtener una arena
```java
MinigameArena arena = plugin.getArenaManager().getArena("pvp_arena");

if (arena != null) {
    System.out.println("Arena encontrada: " + arena.getName());
    System.out.println("Jugadores: " + arena.getPlayerCount());
}
```

#### Verificar si una ubicación está dentro de un arena
```java
Location playerLocation = player.getLocation();

if (arena.isInside(playerLocation)) {
    System.out.println("El jugador está dentro del arena");
}
```

#### Añadir/Remover jugadores de una arena
```java
// Añadir
arena.addPlayer(player.getUniqueId());

// Remover
arena.removePlayer(player.getUniqueId());
```

#### Ejecutar tareas asincrónicas en la arena
```java
arena.executeAsync(() -> {
    // Este código se ejecuta en el thread dedicado del arena
    // Ideal para lógica pesada de minijuegos
    System.out.println("Ejecutando en thread de arena");
});
```

#### Eliminar una arena
```java
plugin.getArenaManager().removeArena("pvp_arena");
```

### Acceder a la Configuración

```java
import io.canvasmc.moai.config.MoaiConfigLoader;

// Obtener idioma
String language = MoaiConfigLoader.getLanguage();

// Verificar si efectos de bienvenida están habilitados
if (MoaiConfigLoader.isWelcomeEffectsEnabled()) {
    // Hacer algo
}

// Obtener máximo de entidades por chunk
int maxEntities = MoaiConfigLoader.getMaxEntitiesPerChunk();
```

### Aplicar Efectos de Bienvenida Manualmente

```java
import io.canvasmc.moaicore.listener.WelcomeEffectsListener;

// Aplicar efectos a un jugador
WelcomeEffectsListener.applyWelcomeEffects(player);
```

---

## Configuration Examples

### ejemplo 1: Minigame PvP Arena

```java
public class PvPArenaSetup {
    private final MoaiCorePlugin plugin;

    public void setupPvPArena() {
        World world = Bukkit.getWorld("world");
        Location center = new Location(world, 100, 64, 200);
        
        // Crear arena
        MinigameArena arena = plugin.getArenaManager().createArena(
            "pvp_arena",
            world,
            center,
            50  // 50 bloques de radio
        );
        
        // Listener para jugadores que entran
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerMove(PlayerMoveEvent event) {
                Player player = event.getPlayer();
                
                if (arena.isInside(player.getLocation())) {
                    if (!arena.getPlayers().contains(player.getUniqueId())) {
                        arena.addPlayer(player.getUniqueId());
                        player.sendMessage("§a¡Entraste al PvP Arena!");
                    }
                } else {
                    if (arena.getPlayers().contains(player.getUniqueId())) {
                        arena.removePlayer(player.getUniqueId());
                        player.sendMessage("§cSaliste del PvP Arena");
                    }
                }
            }
        }, plugin);
    }
}
```

### Ejemplo 2: Plugin Externo Usando Moai-Core

```java
import org.bukkit.plugin.java.JavaPlugin;
import io.canvasmc.moaicore.MoaiCorePlugin;
import io.canvasmc.moaicore.arena.MinigameArena;

public class MyMinigamePlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Obtener instancia de Moai-Core
        MoaiCorePlugin moaiCore = (MoaiCorePlugin) Bukkit.getPluginManager()
            .getPlugin("Moai-Core");
        
        if (moaiCore != null) {
            // Crear arena para nuestro minigame
            MinigameArena arena = moaiCore.getArenaManager().createArena(
                "my_minigame",
                Bukkit.getWorld("world"),
                new Location(Bukkit.getWorld("world"), 0, 64, 0),
                30
            );
            
            getLogger().info("Arena creada exitosamente!");
        } else {
            getLogger().warning("Moai-Core no está instalado!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
```

---

## Events

### PlayerJoinEvent
Se dispara cuando un jugador se une al servidor.

Si `enableWelcomeEffects` está habilitado en moai.yml:
- 🔊 Sonido de levelup
- 💬 Mensaje de bienvenida
- 🎯 BossBar durante 10 segundos

```java
@EventHandler
public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    // Los efectos se aplican automáticamente
}
```

---

## Performance Tips

### 1. Usar Threads Dedicados para Arenas

```java
// ❌ NO RECOMENDADO - Bloquea el servidor
world.getEntities().stream()
    .filter(e -> arena.isInside(e.getLocation()))
    .forEach(e -> e.damage(1));

// ✅ RECOMENDADO - Asincrónico
arena.executeAsync(() -> {
    world.getEntities().stream()
        .filter(e -> arena.isInside(e.getLocation()))
        .forEach(e -> e.damage(1));
});
```

### 2. Limitar Entidades por Arena

En `moai.yml`:
```yaml
maxEntitiesPerChunk: 16  # Para arena de minijuegos
```

### 3. Usar Async Mob Spawning

En `moai.yml`:
```yaml
enableAsyncMobSpawning: true
```

---

## Troubleshooting

### "Moai-Core plugin not initialized!"
- **Causa:** Plugin no se cargó correctamente
- **Solución:** Verificar que `moai-plugin.yml` existe y es válido

### Efectos de bienvenida no aparecen
- **Causa:** `enableWelcomeEffects` está en false en moai.yml
- **Solución:** Cambiar a true y recargar con `/moaicore reload`

### ArenaManager da ClassNotFoundException
- **Causa:** nvfolia-api no se compiló
- **Solución:** Ejecutar `./gradlew clean build`

---

## Best Practices

1. ✅ Siempre verificar si `Moai-Core` está disponible antes de usarlo
2. ✅ Usar `arena.executeAsync()` para operaciones pesadas
3. ✅ Mantener arenas activas solo mientras se necesiten
4. ✅ Llamar a `arena.shutdown()` cuando no se necesite una arena
5. ✅ Usar permisos para comandos sensibles
6. ✅ Registrar eventos en listeners, no en loops

---

## Support & Documentation

- **GitHub:** https://github.com/deivaxxx/Moai
- **Wiki:** Consultar la documentación del proyecto
- **Issues:** Reportar bugs o sugerencias

---

**Última actualización:** 1 de Enero de 2026
**Versión Moai-Core:** 1.0.0
**Versión del servidor:** 1.21.5 - 1.21.11
