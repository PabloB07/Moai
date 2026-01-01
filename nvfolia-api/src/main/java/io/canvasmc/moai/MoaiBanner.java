package io.canvasmc.moai;

/**
 * Moai ASCII Banner
 * Displays the Moai server ASCII art on startup
 */
public class MoaiBanner {

    private static final String BANNER = """
             ╔═══════════════════════════════════════════════════════╗
             ║                                                       ║
             ║                  🗿 MOAI SERVER 🗿                   ║
             ║         Professional Multithreaded Minecraft         ║
             ║                                                       ║
             ║          The most advanced spigot server             ║
             ║        Built on Paper/Spigot + Threading API         ║
             ║                                                       ║
             ║                    v1.21.5 - 1.21.11                  ║
             ║                                                       ║
             ╚═══════════════════════════════════════════════════════╝
            """;

    private static final String BANNER_ES = """
             ╔═══════════════════════════════════════════════════════╗
             ║                                                       ║
             ║                  🗿 SERVIDOR MOAI 🗿                 ║
             ║       Servidor de Minecraft Multihilo Profesional    ║
             ║                                                       ║
             ║         El servidor Spigot más avanzado              ║
             ║    Construido sobre Paper/Spigot + API de Threads    ║
             ║                                                       ║
             ║                    v1.21.5 - 1.21.11                  ║
             ║                                                       ║
             ╚═══════════════════════════════════════════════════════╝
            """;

    private MoaiBanner() {
        // Utility class - no instantiation
    }

    /**
     * Display the Moai banner in console
     *
     * @param language language code (es/en)
     */
    public static void displayBanner(String language) {
        if ("es".equalsIgnoreCase(language)) {
            System.out.println(BANNER_ES);
        } else {
            System.out.println(BANNER);
        }
    }

    /**
     * Display the English banner
     */
    public static void displayBanner() {
        System.out.println(BANNER);
    }
}
