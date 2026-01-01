package io.canvasmc.moaicore.listener;

import io.canvasmc.moai.config.MoaiConfigLoader;
import io.canvasmc.moaicore.MoaiCorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Welcome Effects Listener
 * Handles welcome effects for players joining the Moai server
 */
public class WelcomeEffectsListener implements Listener {

    private final MoaiCorePlugin plugin;
    private final Map<UUID, BossBar> playerBossBars = new HashMap<>();

    public WelcomeEffectsListener(@NotNull MoaiCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!MoaiConfigLoader.isWelcomeEffectsEnabled()) {
            return;
        }

        applyWelcomeEffects(player);
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        BossBar bossBar = playerBossBars.remove(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removePlayer(player);
        }
    }

    /**
     * Apply welcome effects to a player
     *
     * @param player the player to apply effects to
     */
    public static void applyWelcomeEffects(@NotNull Player player) {
        if (!MoaiConfigLoader.isWelcomeEffectsEnabled()) {
            return;
        }

        String language = MoaiConfigLoader.getLanguage();

        // Play welcome sound
        try {
            player.playSound(
                player.getLocation(),
                Sound.ENTITY_PLAYER_LEVELUP,
                1.0f,
                1.2f
            );
        } catch (Exception e) {
            // Silently ignore if sound is not available
        }

        // Send welcome message
        if ("es".equalsIgnoreCase(language)) {
            player.sendMessage("§6§l🗿 ¡Bienvenido a Moai Server! 🗿");
            player.sendMessage("§eExperimenta el servidor Spigot más avanzado");
            player.sendMessage("§eMultihilo optimizado para máximo rendimiento");
        } else {
            player.sendMessage("§6§l🗿 Welcome to Moai Server! 🗿");
            player.sendMessage("§eExperience the most advanced Spigot server");
            player.sendMessage("§eOptimized multithreading for maximum performance");
        }

        // Schedule boss bar display with delay (server needs to be fully ready)
        Bukkit.getScheduler().scheduleSyncDelayedTask(
            MoaiCorePlugin.getInstance(),
            () -> displayWelcomeBossBar(player, language),
            5L  // 5 ticks delay
        );
    }

    /**
     * Display a welcome boss bar for the player
     *
     * @param player the player
     * @param language the language code
     */
    private static void displayWelcomeBossBar(@NotNull Player player, @NotNull String language) {
        String title = "es".equalsIgnoreCase(language)
            ? "§6🗿 Moai Server - ¡Disfrutá tu experiencia!"
            : "§6🗿 Moai Server - Enjoy your experience!";

        BossBar bossBar = Bukkit.createBossBar(
            title,
            BarColor.GOLD,
            BarStyle.SEGMENTED_20
        );

        bossBar.addPlayer(player);
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);

        // Store reference to remove on quit
        MoaiCorePlugin plugin = MoaiCorePlugin.getInstance();
        if (plugin != null) {
            WelcomeEffectsListener listener = null;
            // Find the listener instance
            for (Listener l : Bukkit.getPluginManager().getPlugins()[0].getServer().getPluginManager().getPlugins()[0].getServer().getListeners()) {
                if (l instanceof WelcomeEffectsListener) {
                    listener = (WelcomeEffectsListener) l;
                    break;
                }
            }

            if (listener != null) {
                listener.playerBossBars.put(player.getUniqueId(), bossBar);
            }
        }

        // Remove boss bar after 10 seconds
        Bukkit.getScheduler().scheduleSyncDelayedTask(
            MoaiCorePlugin.getInstance(),
            () -> {
                bossBar.removeAll();
                MoaiCorePlugin instance = MoaiCorePlugin.getInstance();
                if (instance != null) {
                    // Clean up
                }
            },
            200L  // 10 seconds = 200 ticks
        );

        // Play celebratory sound for action bar update
        Bukkit.getScheduler().scheduleSyncDelayedTask(
            MoaiCorePlugin.getInstance(),
            () -> {
                try {
                    player.playSound(
                        player.getLocation(),
                        Sound.BLOCK_BELL_RESONATE,
                        0.5f,
                        1.0f
                    );
                } catch (Exception ignored) {
                    // Silently ignore
                }
            },
            50L  // 2.5 seconds
        );
    }

    /**
     * Remove boss bar for a player
     *
     * @param player the player
     */
    public void removeBossBar(@NotNull Player player) {
        BossBar bossBar = playerBossBars.remove(player.getUniqueId());
        if (bossBar != null) {
            bossBar.removePlayer(player);
        }
    }
}
