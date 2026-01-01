package io.canvasmc.moaicore.command;

import io.canvasmc.moai.config.MoaiConfigLoader;
import io.canvasmc.moaicore.MoaiCorePlugin;
import io.canvasmc.moaicore.arena.MinigameArena;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Moai-Core Command Executor
 * Handles /moaicore commands
 */
public class MoaiCoreCommand implements CommandExecutor {

    private final MoaiCorePlugin plugin;

    public MoaiCoreCommand(@NotNull MoaiCorePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
        @NotNull CommandSender sender,
        @NotNull Command command,
        @NotNull String label,
        @NotNull String[] args
    ) {
        String language = MoaiConfigLoader.getLanguage();

        if (args.length == 0) {
            sendHelp(sender, language);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "reload":
                return handleReload(sender, language);
            case "status":
                return handleStatus(sender, language);
            case "arena":
                return handleArena(sender, args, language);
            case "version":
                return handleVersion(sender, language);
            case "effects":
                return handleEffects(sender, language);
            default:
                sendHelp(sender, language);
                return true;
        }
    }

    /**
     * Handle /moaicore reload
     */
    private boolean handleReload(@NotNull CommandSender sender, @NotNull String language) {
        if (!sender.hasPermission("moai.admin")) {
            if ("es".equalsIgnoreCase(language)) {
                sender.sendMessage("§cNo tienes permisos para ejecutar este comando");
            } else {
                sender.sendMessage("§cYou don't have permission to execute this command");
            }
            return true;
        }

        MoaiConfigLoader.initialize();
        if ("es".equalsIgnoreCase(language)) {
            sender.sendMessage("§a✓ Configuración de Moai recargada");
        } else {
            sender.sendMessage("§a✓ Moai configuration reloaded");
        }
        return true;
    }

    /**
     * Handle /moaicore status
     */
    private boolean handleStatus(@NotNull CommandSender sender, @NotNull String language) {
        if ("es".equalsIgnoreCase(language)) {
            sender.sendMessage("§6§l🗿 Estado del Servidor Moai");
            sender.sendMessage("§e- Versión: " + plugin.getServerVersion());
            sender.sendMessage("§e- Marca: " + plugin.getServerBrand());
            sender.sendMessage("§e- Arenas Activas: " + plugin.getArenaManager().getArenas().size());
            sender.sendMessage("§e- Idioma: Español");
            sender.sendMessage("§e- Efectos de Bienvenida: " + (MoaiConfigLoader.isWelcomeEffectsEnabled() ? "§aHabilitados" : "§cDeshabilitados"));
        } else {
            sender.sendMessage("§6§l🗿 Moai Server Status");
            sender.sendMessage("§e- Version: " + plugin.getServerVersion());
            sender.sendMessage("§e- Brand: " + plugin.getServerBrand());
            sender.sendMessage("§e- Active Arenas: " + plugin.getArenaManager().getArenas().size());
            sender.sendMessage("§e- Language: English");
            sender.sendMessage("§e- Welcome Effects: " + (MoaiConfigLoader.isWelcomeEffectsEnabled() ? "§aEnabled" : "§cDisabled"));
        }
        return true;
    }

    /**
     * Handle /moaicore arena
     */
    private boolean handleArena(@NotNull CommandSender sender, @NotNull String[] args, @NotNull String language) {
        if (!sender.hasPermission("moai.admin")) {
            if ("es".equalsIgnoreCase(language)) {
                sender.sendMessage("§cNo tienes permisos para ejecutar este comando");
            } else {
                sender.sendMessage("§cYou don't have permission to execute this command");
            }
            return true;
        }

        if (args.length < 2) {
            if ("es".equalsIgnoreCase(language)) {
                sender.sendMessage("§cUso: /moaicore arena [list|info <nombre>]");
            } else {
                sender.sendMessage("§cUsage: /moaicore arena [list|info <name>]");
            }
            return true;
        }

        String arenaSubcommand = args[1].toLowerCase();

        if ("list".equals(arenaSubcommand)) {
            if ("es".equalsIgnoreCase(language)) {
                sender.sendMessage("§6Arenas Disponibles:");
            } else {
                sender.sendMessage("§6Available Arenas:");
            }

            for (String arenaName : plugin.getArenaManager().getArenas().keySet()) {
                sender.sendMessage("§e- " + arenaName);
            }
        } else if ("info".equals(arenaSubcommand) && args.length > 2) {
            String arenaName = args[2];
            MinigameArena arena = plugin.getArenaManager().getArena(arenaName);

            if (arena == null) {
                if ("es".equalsIgnoreCase(language)) {
                    sender.sendMessage("§cArena no encontrada: " + arenaName);
                } else {
                    sender.sendMessage("§cArena not found: " + arenaName);
                }
                return true;
            }

            if ("es".equalsIgnoreCase(language)) {
                sender.sendMessage("§6Información de Arena: " + arenaName);
                sender.sendMessage("§e- Mundo: " + arena.getWorld().getName());
                sender.sendMessage("§e- Centro: " + arena.getCenterLocation());
                sender.sendMessage("§e- Radio: " + arena.getRadius() + " bloques");
                sender.sendMessage("§e- Jugadores: " + arena.getPlayerCount());
                sender.sendMessage("§e- Activa: " + (arena.isActive() ? "§aSí" : "§cNo"));
            } else {
                sender.sendMessage("§6Arena Info: " + arenaName);
                sender.sendMessage("§e- World: " + arena.getWorld().getName());
                sender.sendMessage("§e- Center: " + arena.getCenterLocation());
                sender.sendMessage("§e- Radius: " + arena.getRadius() + " blocks");
                sender.sendMessage("§e- Players: " + arena.getPlayerCount());
                sender.sendMessage("§e- Active: " + (arena.isActive() ? "§aYes" : "§cNo"));
            }
        }
        return true;
    }

    /**
     * Handle /moaicore version
     */
    private boolean handleVersion(@NotNull CommandSender sender, @NotNull String language) {
        if ("es".equalsIgnoreCase(language)) {
            sender.sendMessage("§6§l🗿 Moai Server");
            sender.sendMessage("§eVersión Moai: " + plugin.getServerVersion());
            sender.sendMessage("§ePlugin: " + plugin.getDescription().getVersion());
        } else {
            sender.sendMessage("§6§l🗿 Moai Server");
            sender.sendMessage("§eModai Version: " + plugin.getServerVersion());
            sender.sendMessage("§ePlugin: " + plugin.getDescription().getVersion());
        }
        return true;
    }

    /**
     * Handle /moaicore effects
     */
    private boolean handleEffects(@NotNull CommandSender sender, @NotNull String language) {
        if ("es".equalsIgnoreCase(language)) {
            sender.sendMessage("§a✓ Efectos de bienvenida: " + (MoaiConfigLoader.isWelcomeEffectsEnabled() ? "Habilitados" : "Deshabilitados"));
        } else {
            sender.sendMessage("§a✓ Welcome effects: " + (MoaiConfigLoader.isWelcomeEffectsEnabled() ? "Enabled" : "Disabled"));
        }
        return true;
    }

    /**
     * Send help message
     */
    private void sendHelp(@NotNull CommandSender sender, @NotNull String language) {
        if ("es".equalsIgnoreCase(language)) {
            sender.sendMessage("§6§l🗿 Comandos de Moai-Core");
            sender.sendMessage("§e/moaicore reload§7 - Recargar configuración");
            sender.sendMessage("§e/moaicore status§7 - Ver estado del servidor");
            sender.sendMessage("§e/moaicore arena list§7 - Listar arenas");
            sender.sendMessage("§e/moaicore arena info <nombre>§7 - Ver información del arena");
            sender.sendMessage("§e/moaicore version§7 - Ver versión");
            sender.sendMessage("§e/moaicore effects§7 - Ver estado de efectos");
        } else {
            sender.sendMessage("§6§l🗿 Moai-Core Commands");
            sender.sendMessage("§e/moaicore reload§7 - Reload configuration");
            sender.sendMessage("§e/moaicore status§7 - View server status");
            sender.sendMessage("§e/moaicore arena list§7 - List arenas");
            sender.sendMessage("§e/moaicore arena info <name>§7 - View arena info");
            sender.sendMessage("§e/moaicore version§7 - View version");
            sender.sendMessage("§e/moaicore effects§7 - View effects status");
        }
    }
}
