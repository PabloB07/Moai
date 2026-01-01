package io.canvasmc.moaicore;

import io.canvasmc.moai.MoaiInitializer;
import io.canvasmc.moai.config.MoaiConfigLoader;
import io.canvasmc.moaicore.arena.ArenaManager;
import io.canvasmc.moaicore.command.MoaiCoreCommand;
import io.canvasmc.moaicore.listener.WelcomeEffectsListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Moai-Core Plugin
 * The core plugin for Moai server featuring:
 * - Welcome effects for new players
 * - Per-arena minigame management with thread separation
 * - Server customization and branding
 * - Performance optimization for minigames
 */
public class MoaiCorePlugin extends JavaPlugin {

    private static MoaiCorePlugin instance;
    private ArenaManager arenaManager;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize Moai core configuration
        MoaiInitializer.initialize();
        MoaiConfigLoader.initialize();

        // Setup managers
        this.arenaManager = new ArenaManager(this);

        // Register event listeners
        Bukkit.getPluginManager().registerEvents(new WelcomeEffectsListener(this), this);

        // Register commands
        getCommand("moaicore").setExecutor(new MoaiCoreCommand(this));

        // Welcome message
        String language = MoaiConfigLoader.getLanguage();
        if ("es".equalsIgnoreCase(language)) {
            getLogger().info("🗿 Plugin Moai-Core cargado correctamente");
            getLogger().info("Versión: " + getDescription().getVersion());
            getLogger().info("Optimizaciones: Minijuegos habilitadas");
        } else {
            getLogger().info("🗿 Moai-Core plugin loaded successfully");
            getLogger().info("Version: " + getDescription().getVersion());
            getLogger().info("Optimizations: Minigames enabled");
        }

        // Apply welcome effects to online players
        if (MoaiConfigLoader.isWelcomeEffectsEnabled()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                WelcomeEffectsListener.applyWelcomeEffects(player);
            }
        }
    }

    @Override
    public void onDisable() {
        if (arenaManager != null) {
            arenaManager.shutdown();
        }

        String language = MoaiConfigLoader.getLanguage();
        if ("es".equalsIgnoreCase(language)) {
            getLogger().info("🗿 Plugin Moai-Core descargado");
        } else {
            getLogger().info("🗿 Moai-Core plugin disabled");
        }
    }

    /**
     * Get the plugin instance
     *
     * @return the MoaiCorePlugin instance
     */
    public static @NotNull MoaiCorePlugin getInstance() {
        if (instance == null) {
            throw new RuntimeException("Moai-Core plugin not initialized!");
        }
        return instance;
    }

    /**
     * Get the arena manager
     *
     * @return the ArenaManager
     */
    public @NotNull ArenaManager getArenaManager() {
        return arenaManager;
    }

    /**
     * Get the server brand
     *
     * @return server brand name
     */
    public @NotNull String getServerBrand() {
        return MoaiInitializer.getServerBrand();
    }

    /**
     * Get the server version
     *
     * @return server version
     */
    public @NotNull String getServerVersion() {
        return MoaiInitializer.getVersion();
    }
}
