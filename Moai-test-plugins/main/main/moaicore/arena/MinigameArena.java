package io.canvasmc.moaicore.arena;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

/**
 * Minigame Arena
 * Represents a minigame arena with separate thread management
 */
public class MinigameArena {

    private final String name;
    private final World world;
    private final Location centerLocation;
    private final int radius;
    private final ExecutorService executor;
    private final List<UUID> playersInArena = new ArrayList<>();
    private boolean active = true;

    /**
     * Create a new minigame arena
     *
     * @param name arena name
     * @param world world where the arena is located
     * @param centerLocation center location of the arena
     * @param radius radius of the arena in blocks
     * @param executor thread executor for this arena
     */
    public MinigameArena(
        @NotNull String name,
        @NotNull World world,
        @NotNull Location centerLocation,
        int radius,
        @NotNull ExecutorService executor
    ) {
        this.name = name;
        this.world = world;
        this.centerLocation = centerLocation.clone();
        this.radius = radius;
        this.executor = executor;
    }

    /**
     * Get the arena name
     *
     * @return arena name
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Get the arena world
     *
     * @return the world
     */
    public @NotNull World getWorld() {
        return world;
    }

    /**
     * Get the center location
     *
     * @return center location
     */
    public @NotNull Location getCenterLocation() {
        return centerLocation.clone();
    }

    /**
     * Get the arena radius
     *
     * @return radius in blocks
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Check if a location is inside this arena
     *
     * @param location the location to check
     * @return true if location is inside the arena
     */
    public boolean isInside(@NotNull Location location) {
        if (!location.getWorld().equals(world)) {
            return false;
        }
        return location.distance(centerLocation) <= radius;
    }

    /**
     * Add a player to the arena
     *
     * @param playerUUID the player's UUID
     */
    public void addPlayer(@NotNull UUID playerUUID) {
        if (!playersInArena.contains(playerUUID)) {
            playersInArena.add(playerUUID);
        }
    }

    /**
     * Remove a player from the arena
     *
     * @param playerUUID the player's UUID
     */
    public void removePlayer(@NotNull UUID playerUUID) {
        playersInArena.remove(playerUUID);
    }

    /**
     * Get the number of players in the arena
     *
     * @return player count
     */
    public int getPlayerCount() {
        return playersInArena.size();
    }

    /**
     * Get all players in the arena
     *
     * @return list of player UUIDs
     */
    public @NotNull List<UUID> getPlayers() {
        return new ArrayList<>(playersInArena);
    }

    /**
     * Check if the arena is active
     *
     * @return true if active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Execute a task asynchronously in this arena's thread
     *
     * @param task the task to execute
     */
    public void executeAsync(@NotNull Runnable task) {
        if (active) {
            executor.execute(task);
        }
    }

    /**
     * Shutdown the arena
     */
    public void shutdown() {
        active = false;
        playersInArena.clear();
    }
}
