package io.canvasmc.moai.config;

import io.canvasmc.canvas.config.Configuration;
import io.canvasmc.canvas.config.annotation.Comment;
import io.canvasmc.canvas.config.annotation.EnumValue;

/**
 * Moai Server Configuration
 * This configuration file controls the behavior of the Moai server,
 * including language settings, visual customization, and minigame optimizations.
 */
@Configuration("moai.yml")
public class MoaiConfig {

    @Comment({"Server Language", "Available options: es (Spanish), en (English)", "Default: es"})
    public static String language = "es";

    @Comment({"Display ASCII Banner", "If true, shows the Moai ASCII banner on server startup", "Default: true"})
    public static boolean showAsciBanner = true;

    @Comment({"Enable Minigame Optimizations", "Enables per-arena thread management for better performance", "Default: true"})
    public static boolean enableMinigameOptimizations = true;

    @Comment({"Maximum Entities Per Chunk for Minigames", "Helps prevent lag in minigame arenas", "Default: 32"})
    public static int maxEntitiesPerChunk = 32;

    @Comment({"Enable Welcome Effects", "Shows welcome messages, sound effects, and visual effects to new players", "Default: true"})
    public static boolean enableWelcomeEffects = true;

    @Comment({"Enable Async Mob Spawning", "Uses PufferFish API for asynchronous mob spawning", "Default: true"})
    public static boolean enableAsyncMobSpawning = true;

    @Comment({"Server Brand Name", "Displayed in the server info", "Default: Moai"})
    public static String serverBrand = "Moai";

    @Comment({"Enable Performance Monitoring", "Logs performance metrics periodically", "Default: false"})
    public static boolean enablePerformanceMonitoring = false;

    private MoaiConfig() {
        // Configuration class - no instantiation
    }
}
