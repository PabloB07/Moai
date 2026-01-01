![Brand](https://github.com/deivaxxx/Moai/blob/main/moai.png)


# Moai 🗿 1.21.5 - 1.21.11 Spigot Plugins Support
A custom server implementation that supports the Bukkit API and 80% of Spigot/Paper plugins

## Moai is a professional multithreaded Minecraft server implementation
Please! Moai is in active development, and when using it, save a backup of the world

Most plugins have a multithreaded compatibility setting, and Moai runs entirely on Paper/Spigot while adding advanced multithreading functionality
# The core is in Beta Testing!

# What is included in the core component?
> - All Folia components and chunk division
> -  Full availability of Bukkit/Paper plugins
> - Separation of TPS by dimensions
> - Villagers automatically become dumber if there are more than 45 of them in one chunk (Useful for optimization)
> - Separation of TPS by chunk loading distance
> - Voting for restart
> - Increase in server performance by 145%
> - Items that you lost outside of loading will not despawn
> - Fixed problems with duping rails and threads
> - Experience farms on pig-zombie, now give experience as in previous versions
> - You can adjust the rendering range from 8 to 24 chunks (Do not confuse with the simulation range)
> - Adjustment and disabling of all Folia functions without restarting
> - All new functions have been added to the DivineMC config. Selected for more convenient editing
> - Synchronization of TPS by the connection of the portals of Nether and Overworld (So that farms on withers work adequately)

**All the above features will appear in Moai-build-10**


### 🫒 I also use a simplified version of CanvasMC and the PufferFish API.

```Pufferfish```-Adding DAB system and optimizing asynchronous chunk appearance

```Paper``` - The integral structure of the nucleus

```PurPur``` - For more detailed server settings

```DivineMC``` - Used to spawn mobs asynchronously and speed up single-threaded minecraft connection

```Folia``` - The main core used for NVFolia branding

## The core was specially created for the server 
play.necovanilla.ru

### What does the NVFolia multithreaded core do, each dimension has its own thread and chunk too. Now if a player is located and loads 16 chunks, then his TPS will be the same as in these 16 chunks. Other players who load their chunks will have their own TPS
