package io.canvasmc.moai.config;

import io.canvasmc.canvas.config.AnnotationBasedYamlSerializer;
import io.canvasmc.canvas.config.internal.ConfigurationManager;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jetbrains.annotations.NotNull;

/**
 * Moai Configuration Loader
 * Initializes and manages the loading of moai.yml configuration file.
 */
public class MoaiConfigLoader {

    private static final Path CONFIG_PATH = Paths.get("moai.yml");
    private static boolean initialized = false;

    private MoaiConfigLoader() {
        // Utility class - no instantiation
    }

    /**
     * Initialize the Moai configuration from moai.yml file.
     * If the file doesn't exist, it will be created with default values.
     *
     * @return the ConfigHolder for MoaiConfig
     */
    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        try {
            ConfigurationManager.register(
                MoaiConfig.class,
                (definition, configClass) -> new AnnotationBasedYamlSerializer(
                    definition,
                    configClass,
                    System.out::println
                )
            );
            initialized = true;
            System.out.println("[Moai] Configuration loaded successfully from moai.yml");
        } catch (Exception e) {
            System.err.println("[Moai] Failed to load moai.yml configuration:");
            e.printStackTrace();
            // Continue with default values
            initialized = true;
        }
    }

    /**
     * Get the current language setting
     *
     * @return language code (es/en)
     */
    public static @NotNull String getLanguage() {
        return MoaiConfig.language;
    }

    /**
     * Check if ASCII banner should be displayed
     *
     * @return true if banner should be shown
     */
    public static boolean isShowAsciBanner() {
        return MoaiConfig.showAsciBanner;
    }

    /**
     * Check if minigame optimizations are enabled
     *
     * @return true if optimizations are enabled
     */
    public static boolean isMinigameOptimizationsEnabled() {
        return MoaiConfig.enableMinigameOptimizations;
    }

    /**
     * Get the maximum entities per chunk for minigames
     *
     * @return max entities
     */
    public static int getMaxEntitiesPerChunk() {
        return MoaiConfig.maxEntitiesPerChunk;
    }

    /**
     * Check if welcome effects are enabled
     *
     * @return true if welcome effects are enabled
     */
    public static boolean isWelcomeEffectsEnabled() {
        return MoaiConfig.enableWelcomeEffects;
    }

    /**
     * Check if async mob spawning is enabled
     *
     * @return true if enabled
     */
    public static boolean isAsyncMobSpawningEnabled() {
        return MoaiConfig.enableAsyncMobSpawning;
    }

    /**
     * Get the server brand name
     *
     * @return server brand
     */
    public static @NotNull String getServerBrand() {
        return MoaiConfig.serverBrand;
    }

    /**
     * Check if performance monitoring is enabled
     *
     * @return true if monitoring is enabled
     */
    public static boolean isPerformanceMonitoringEnabled() {
        return MoaiConfig.enablePerformanceMonitoring;
    }
}
