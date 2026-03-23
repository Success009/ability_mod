package com.ability.mod;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class JanitorPreLaunch implements PreLaunchEntrypoint {
    private static final Logger LOGGER = LoggerFactory.getLogger("ability-janitor");

    private static final List<String> BUNDLED_MODS = Arrays.asList(
            "voicechat-fabric-1.21.4-2.6.12.jar",
            "sodium-fabric-0.6.13+mc1.21.4.jar",
            "iris-fabric-1.8.8+mc1.21.4.jar",
            "lithium-fabric-0.15.3+mc1.21.4.jar",
            "ferritecore-7.1.3-fabric.jar",
            "indium-1.0.31+mc1.20.4.jar"
    );

    private static final List<String> WHITELISTED_ONLY = Arrays.asList(
            "fabric-api-0.119.4+1.21.4.jar",
            "tl_skin_cape_fabric_1.21.4-1.38.jar"
    );

    @Override
    public void onPreLaunch() {
        LOGGER.info("!!! JANITOR V98 SMART SYNC STARTING !!!");
        
        Path modsDir = FabricLoader.getInstance().getGameDir().resolve("mods");
        Path disabledDir = modsDir.resolve("disabled_mods");

        try {
            if (!Files.exists(disabledDir)) Files.createDirectories(disabledDir);

            // 1. CLEANUP
            try (Stream<Path> files = Files.list(modsDir)) {
                files.forEach(file -> {
                    String name = file.getFileName().toString();
                    if (Files.isDirectory(file) || name.contains("Ability-SMP-Ultimate") || name.equals("ability_gateway.log")) return;

                    // Clean up specific bad version
                    if (name.contains("1.21.11")) {
                        try { Files.delete(file); LOGGER.info("Deleted bad version: {}", name); } 
                        catch (IOException ignored) {}
                        return;
                    }

                    if (!BUNDLED_MODS.contains(name) && !WHITELISTED_ONLY.contains(name)) {
                        try {
                            Files.move(file, disabledDir.resolve(name), StandardCopyOption.REPLACE_EXISTING);
                            LOGGER.warn("Disabled unauthorized mod: {}", name);
                        } catch (IOException e) {
                            LOGGER.error("Failed to disable: {}", name);
                        }
                    }
                });
            }

            // 2. SMART RESTORE
            for (String jarName : BUNDLED_MODS) {
                Path targetPath = modsDir.resolve(jarName);
                
                try (InputStream is = JanitorPreLaunch.class.getResourceAsStream("/assets/abilitysmp/bundled/" + jarName)) {
                    if (is == null) {
                        LOGGER.error("CRITICAL: Missing bundled mod in JAR: {}", jarName);
                        continue;
                    }

                    // Check if exists and size matches
                    if (Files.exists(targetPath)) {
                        long diskSize = Files.size(targetPath);
                        long bundleSize = is.available(); // Approximation, but InputStreams from Jar entries usually know size
                        
                        // If sizes are drastically different, overwrite. 
                        // Note: is.available() is not always reliable for exact size, 
                        // so we might just assume if it exists, it's good, UNLESS it's 0 bytes.
                        if (diskSize > 0) {
                            LOGGER.info("Mod already exists: {} (Skipping write to avoid lock)", jarName);
                            continue;
                        }
                    }

                    // If missing or 0 bytes, write it
                    Files.copy(is, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    LOGGER.info("Restored missing mod: {}", jarName);
                    
                } catch (IOException e) {
                    LOGGER.error("Janitor failed to restore: {}", jarName, e);
                }
            }

        } catch (IOException e) {
            LOGGER.error("Janitor crashed!", e);
        }
    }
}
