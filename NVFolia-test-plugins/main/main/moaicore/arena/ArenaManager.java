package io.canvasmc.moaicore.arena;

import io.canvasmc.moai.config.MoaiConfigLoader;
import io.canvasmc.moaicore.MoaiCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Arena Manager
 * Manages minigame arenas with per-arena thread separation for optimal performance
 */
public class ArenaManager {

    private final MoaiCorePlugin plugin;
    private final Map<String, MinigameArena> arenas = new ConcurrentHashMap<>();
    private final ExecutorService arenaThreadPool;
    private static final int DEFAULT_THREAD_COUNT = Math.min(4, Runtime.getRuntime().availableProcessors());

    public ArenaManager(@NotNull MoaiCorePlugin plugin) {
        this.plugin = plugin;

        // Create thread pool for arena management
        int threadCount = DEFAULT_THREAD_COUNT;
        this.arenaThreadPool = Executors.newFixedThreadPool(
            threadCount,
            r -> {
                Thread t = new Thread(r, "Moai-Arena-Thread-" + threadCount);
                t.setDaemon(true);
                return t;
            }
        );

        String language = MoaiConfigLoader.getLanguage();
        if ("es".equalsIgnoreCase(language)) {
            plugin.getLogger().info("✓ Gestor de Arena inicializado con " + threadCount + " hilos");
        } else {
            plugin.getLogger().info("✓ Arena Manager initialized with " + threadCount + " threads");
        }
    }

    /**
     * Create a new minigame arena
     *
     * @param name arena name
     * @param world world where the arena is located
     * @param centerLocation center location of the arena
     * @param radius radius of the arena
     * @return the created arena
     */
    public @NotNull MinigameArena createArena(
        @NotNull String name,
        @NotNull World world,
        @NotNull Location centerLocation,
        int radius
    ) {
        if (arenas.containsKey(name)) {
            throw new IllegalArgumentException("Arena '" + name + "' already exists!");
        }

        MinigameArena arena = new MinigameArena(
            name,
            world,
            centerLocation,
            radius,
            arenaThreadPool
        );

        arenas.put(name, arena);

        String language = MoaiConfigLoader.getLanguage();
        if ("es".equalsIgnoreCase(language)) {
            plugin.getLogger().info("Arena '" + name + "' creada exitosamente");
        } else {
            plugin.getLogger().info("Arena '" + name + "' created successfully");
        }

        return arena;
    }

    /**
     * Get an arena by name
     *
     * @param name arena name
     * @return the arena or null if not found
     */
    public @Nullable MinigameArena getArena(@NotNull String name) {
        return arenas.get(name);
    }

    /**
     * Remove an arena
     *
     * @param name arena name
     * @return true if removed, false if not found
     */
    public boolean removeArena(@NotNull String name) {
        MinigameArena arena = arenas.remove(name);
        if (arena != null) {
            arena.shutdown();
            String language = MoaiConfigLoader.getLanguage();
            if ("es".equalsIgnoreCase(language)) {
                plugin.getLogger().info("Arena '" + name + "' eliminada");
            } else {
                plugin.getLogger().info("Arena '" + name + "' removed");
            }
            return true;
        }
        return false;
    }

    /**
     * Get all arenas
     *
     * @return map of all arenas
     */
    public @NotNull Map<String, MinigameArena> getArenas() {
        return new HashMap<>(arenas);
    }

    /**
     * Check if an arena exists
     *
     * @param name arena name
     * @return true if exists
     */
    public boolean hasArena(@NotNull String name) {
        return arenas.containsKey(name);
    }

    /**
     * Shutdown the arena manager and all arenas
     */
    public void shutdown() {
        for (MinigameArena arena : arenas.values()) {
            arena.shutdown();
        }
        arenas.clear();
        arenaThreadPool.shutdown();
        plugin.getLogger().info("✓ Arena Manager desconectado");
    }
}
