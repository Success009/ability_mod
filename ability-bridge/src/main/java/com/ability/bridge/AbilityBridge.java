package com.ability.bridge;

import com.ability.bridge.commands.BridgeCommand;
import com.ability.bridge.commands.LinkCommand;
import com.ability.bridge.commands.LocateCommand;
import com.ability.bridge.firebase.FirebaseManager;
import com.ability.bridge.listeners.ChatListener;
import com.ability.bridge.listeners.WebListener;
import com.ability.bridge.managers.ConfigManager;
import com.ability.bridge.managers.LeaderboardManager;
import com.ability.bridge.managers.LinkManager;
import com.ability.bridge.tasks.ServerMonitorTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AbilityBridge extends JavaPlugin {

    private ConfigManager configManager;
    private FirebaseManager firebaseManager;
    private LinkManager linkManager;
    private LeaderboardManager leaderboardManager;
    private WebListener webListener;
    private ServerMonitorTask monitorTask;

    @Override
    public void onEnable() {
        // Initialize Config
        configManager = new ConfigManager(this);

        // Validate service account file
        String filename = configManager.getFirebaseServiceAccountFile();
        File serviceAccountFile = new File(getDataFolder(), filename);
        if (!serviceAccountFile.exists()) {
            saveResource(filename, false); // Try to save a template if it exists in jar
            if (!serviceAccountFile.exists()) {
                getLogger().severe("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                getLogger().severe("  SERVICE ACCOUNT FILE NOT FOUND!");
                getLogger().severe("  Expected: " + serviceAccountFile.getAbsolutePath());
                getLogger().severe("  Place your " + filename + " file");
                getLogger().severe("  in the plugin folder and restart.");
                getLogger().severe("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }

        // Initialize Firebase
        try {
            firebaseManager = new FirebaseManager(this);
            firebaseManager.initialize();
            getLogger().info("✓ Firebase connection established");
        } catch (Exception e) {
            getLogger().severe("✗ Failed to initialize Firebase!");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Initialize managers
        linkManager = new LinkManager(this, firebaseManager);
        
        if (configManager.isLeaderboardsEnabled()) {
            leaderboardManager = new LeaderboardManager(this, firebaseManager);
        }

        // Register event listeners
        if (configManager.isChatBridgeEnabled()) {
            ChatListener chatListener = new ChatListener(this, firebaseManager, leaderboardManager);
            getServer().getPluginManager().registerEvents(chatListener, this);

            // Start web chat listener
            webListener = new WebListener(this, firebaseManager);
            webListener.startListening();
        }

        // Register commands
        BridgeCommand bridgeCmd = new BridgeCommand(this);
        getCommand("bridge").setExecutor(bridgeCmd);
        getCommand("bridge").setTabCompleter(bridgeCmd);

        getCommand("bridgelocate").setExecutor(new LocateCommand(this));

        if (configManager.isLinkingEnabled()) {
            getCommand("link").setExecutor(new LinkCommand(this, linkManager));
        }

        // Start server monitor task
        if (configManager.isServerMonitorEnabled()) {
            monitorTask = new ServerMonitorTask(this, firebaseManager);
            monitorTask.start();
        }

        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        getLogger().info("  Ability SMP Plugin v" + getDescription().getVersion());
        getLogger().info("  ✓ Firebase: Connected");
        getLogger().info("  " + (configManager.isChatBridgeEnabled() ? "✓" : "✗") + " Chat Bridge: " + (configManager.isChatBridgeEnabled() ? "Active" : "Disabled"));
        getLogger().info("  " + (configManager.isLinkingEnabled() ? "✓" : "✗") + " Account Linking: " + (configManager.isLinkingEnabled() ? "Ready" : "Disabled"));
        getLogger().info("  " + (configManager.isLeaderboardsEnabled() ? "✓" : "✗") + " Leaderboard: " + (configManager.isLeaderboardsEnabled() ? "Enabled" : "Disabled"));
        getLogger().info("  " + (configManager.isServerMonitorEnabled() ? "✓" : "✗") + " Server Monitor: " + (configManager.isServerMonitorEnabled() ? "Active" : "Disabled"));
        getLogger().info("  Author: " + getDescription().getAuthors());
        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @Override
    public void onDisable() {
        // Cancel tasks
        if (monitorTask != null) {
            monitorTask.stop();
            monitorTask = null;
        }

        // Unregister all listeners for this plugin
        org.bukkit.event.HandlerList.unregisterAll(this);

        // Set server offline
        if (firebaseManager != null) {
            firebaseManager.setServerOffline();
            firebaseManager.shutdown();
            firebaseManager = null;
            getLogger().info("✓ Firebase connection closed");
        }

        getLogger().info("Ability Bridge disabled successfully.");
    }

    public void reloadPlugin() {
        getLogger().info("Restarting plugin systems...");
        
        // 1. Cleanup existing
        onDisable();
        
        // 2. Re-initialize
        onEnable();
        
        getLogger().info("Plugin systems restarted successfully.");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public FirebaseManager getFirebaseManager() {
        return firebaseManager;
    }

    public LinkManager getLinkManager() {
        return linkManager;
    }

    public LeaderboardManager getLeaderboardManager() {
        return leaderboardManager;
    }
}
