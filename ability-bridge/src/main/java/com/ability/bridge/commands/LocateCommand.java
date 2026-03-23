package com.ability.bridge.commands;

import com.ability.bridge.AbilityBridge;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.StructureSearchResult;
import org.bukkit.util.BiomeSearchResult;
import org.jetbrains.annotations.NotNull;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import io.papermc.paper.registry.tag.Tag;
import io.papermc.paper.registry.tag.TagKey;

public class LocateCommand implements CommandExecutor {

    private final AbilityBridge plugin;

    public LocateCommand(AbilityBridge plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Usage: /bridge:locate <player> <world_id> <type> <key>
        if (args.length < 4) {
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found.");
            return true;
        }

        String worldId = args[1]; // e.g. "minecraft:overworld"
        String type = args[2]; // "Structure" or "Biome"
        String keyRaw = args[3].toLowerCase(); // e.g. "minecraft:ancient_city" or "#minecraft:village"

        boolean isTag = keyRaw.startsWith("#");
        String lookupKey = isTag ? keyRaw.substring(1) : keyRaw;

        if (!lookupKey.contains(":")) {
            lookupKey = "minecraft:" + lookupKey;
        }

        NamespacedKey key = NamespacedKey.fromString(lookupKey);
        if (key == null) {
            sender.sendMessage("§cInvalid key format: " + keyRaw);
            return true;
        }

        // Resolve World
        World world = null;
        NamespacedKey worldKey = NamespacedKey.fromString(worldId);
        if (worldKey != null) {
            world = Bukkit.getWorld(worldKey);
        }
        // Fallback to name match if key fails or returns null
        if (world == null) {
            world = Bukkit.getWorld(worldId.replace("minecraft:", ""));
        }
        if (world == null) {
            sender.sendMessage("§cWorld not found: " + worldId);
            return true;
        }

        Location origin = target.getLocation();
        if (!world.equals(origin.getWorld())) {
            origin = new Location(world, origin.getX(), origin.getY(), origin.getZ());
        }

        sender.sendMessage("§7[Bridge] Locating " + type + (isTag ? " Tag " : " ") + keyRaw + " in " + world.getName() + "...");

        if (type.equalsIgnoreCase("Structure")) {
            try {
                if (isTag) {
                    TagKey<Structure> tagKey = TagKey.create(RegistryKey.STRUCTURE, key);
                    Tag<Structure> tag = Registry.STRUCTURE.getTag(tagKey);
                    if (tag == null) {
                        sender.sendMessage("§cInvalid Structure Tag: " + key);
                        return true;
                    }

                    Location nearestLoc = null;
                    double minDistance = Double.MAX_VALUE;

                    for (TypedKey<Structure> sKey : tag.values()) {
                        Structure s = Registry.STRUCTURE.get(sKey);
                        if (s == null) continue;
                        
                        StructureSearchResult res = world.locateNearestStructure(origin, s, 5000, false);
                        if (res != null) {
                            double dist = res.getLocation().distanceSquared(origin);
                            if (dist < minDistance) {
                                minDistance = dist;
                                nearestLoc = res.getLocation();
                            }
                        }
                    }

                    if (nearestLoc != null) {
                        String cmd = String.format("locator:callback %s %d %d %d", 
                            target.getName(), nearestLoc.getBlockX(), nearestLoc.getBlockY(), nearestLoc.getBlockZ());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                    } else {
                        target.sendMessage("§3[Locator] §cCould not find " + keyRaw + " nearby.");
                    }
                } else {
                    Structure structure = Registry.STRUCTURE.get(key);
                    if (structure == null) {
                        sender.sendMessage("§cInvalid Structure key: " + key);
                        return true;
                    }
                    StructureSearchResult result = world.locateNearestStructure(origin, structure, 5000, false);
                    if (result != null) {
                        Location loc = result.getLocation();
                        String cmd = String.format("locator:callback %s %d %d %d", 
                            target.getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                    } else {
                        target.sendMessage("§3[Locator] §cCould not find " + keyRaw + " nearby.");
                    }
                }
            } catch (Exception e) {
                target.sendMessage("§3[Locator] §cError finding structure.");
                e.printStackTrace();
            }

        } else if (type.equalsIgnoreCase("Biome")) {
            try {
                Location foundLoc = null;
                if (isTag) {
                    TagKey<Biome> tagKey = TagKey.create(RegistryKey.BIOME, key);
                    Tag<Biome> tag = Registry.BIOME.getTag(tagKey);
                    if (tag == null) {
                        sender.sendMessage("§cInvalid Biome Tag: " + key);
                        return true;
                    }
                    
                    java.util.List<Biome> biomes = new java.util.ArrayList<>();
                    for (TypedKey<Biome> bKey : tag.values()) {
                        Biome b = Registry.BIOME.get(bKey);
                        if (b != null) biomes.add(b);
                    }
                    
                    BiomeSearchResult bResult = world.locateNearestBiome(origin, 5000, 64, 64, biomes.toArray(new Biome[0]));
                    if (bResult != null) {
                        foundLoc = bResult.getLocation();
                    }
                } else {
                    Biome biome = Registry.BIOME.get(key);
                    if (biome == null) {
                        sender.sendMessage("§cInvalid Biome key: " + key);
                        return true;
                    }
                    foundLoc = world.locateNearestBiome(origin, biome, 5000, 64);
                }

                if (foundLoc != null) {
                    String cmd = String.format("locator:callback %s %d %d %d", 
                        target.getName(), foundLoc.getBlockX(), foundLoc.getBlockY(), foundLoc.getBlockZ());
                    
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
                } else {
                    target.sendMessage("§3[Locator] §cCould not find " + keyRaw + " nearby.");
                }
            } catch (Exception e) {
                target.sendMessage("§3[Locator] §cError finding biome.");
                e.printStackTrace();
            }
        } else {
            sender.sendMessage("§cUnknown type. Use 'Structure' or 'Biome'.");
        }

        return true;
    }
}
