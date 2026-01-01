package io.canvasmc.moai;

import io.canvasmc.moai.config.MoaiConfigLoader;
import org.jetbrains.annotations.NotNull;

/**
 * Moai Server Initializer
 * Main initialization class for Moai server startup
 */
public class MoaiInitializer {

    private static boolean initialized = false;

    private MoaiInitializer() {
        // Utility class - no instantiation
    }

    /**
     * Initialize the Moai server
     * This should be called during server startup, right after basic initialization
     */
    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        try {
            // Load configuration from moai.yml
            MoaiConfigLoader.initialize();

            // Display banner if enabled
            if (MoaiConfigLoader.isShowAsciBanner()) {
                MoaiBanner.displayBanner(MoaiConfigLoader.getLanguage());
            }

            // Print initialization messages
            String language = MoaiConfigLoader.getLanguage();
            if ("es".equalsIgnoreCase(language)) {
                System.out.println("[Moai] 🗿 Servidor Moai inicializado correctamente");
                System.out.println("[Moai] Idioma: Español");
                if (MoaiConfigLoader.isMinigameOptimizationsEnabled()) {
                    System.out.println("[Moai] Optimizaciones de minijuegos: HABILITADAS");
                }
                if (MoaiConfigLoader.isAsyncMobSpawningEnabled()) {
                    System.out.println("[Moai] Spawn asincrónico de mobs: HABILITADO");
                }
            } else {
                System.out.println("[Moai] 🗿 Moai server initialized successfully");
                System.out.println("[Moai] Language: English");
                if (MoaiConfigLoader.isMinigameOptimizationsEnabled()) {
                    System.out.println("[Moai] Minigame optimizations: ENABLED");
                }
                if (MoaiConfigLoader.isAsyncMobSpawningEnabled()) {
                    System.out.println("[Moai] Async mob spawning: ENABLED");
                }
            }

            initialized = true;

        } catch (Exception e) {
            System.err.println("[Moai] Error during initialization:");
            e.printStackTrace();
        }
    }

    /**
     * Main method to start the Moai server
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("[Moai] Starting Moai server...");
        initialize();
    }

    /**
     * Get the server brand/name
     *
     * @return brand name
     */
    public static @NotNull String getServerBrand() {
        return MoaiConfigLoader.getServerBrand();
    }

    /**
     * Check if server is initialized
     *
     * @return true if initialized
     */
    public static boolean isInitialized() {
        return initialized;
    }

    /**
     * Get version information
     *
     * @return version string
     */
    public static @NotNull String getVersion() {
        return "Moai 1.21.5 - 1.21.11";
    }
}
