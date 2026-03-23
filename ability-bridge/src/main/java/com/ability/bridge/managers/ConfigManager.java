package com.ability.bridge.managers;

import com.ability.bridge.AbilityBridge;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private final AbilityBridge plugin;
    private FileConfiguration config;

    public ConfigManager(AbilityBridge plugin) {
        this.plugin = plugin;
        reloadConfig();
    }

    public void reloadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        this.config = plugin.getConfig();
    }

    public String getFirebaseServiceAccountFile() {
        return config.getString("firebase.service-account-file", "firebase-service-account.json");
    }

    public String getFirebaseDatabaseUrl() {
        return config.getString("firebase.database-url", "https://community-canvas-255fa-default-rtdb.firebaseio.com/");
    }

    // Features
    public boolean isChatBridgeEnabled() {
        return config.getBoolean("features.chat-bridge", true);
    }

    public boolean isServerMonitorEnabled() {
        return config.getBoolean("features.server-monitor", true);
    }

    public boolean isLeaderboardsEnabled() {
        return config.getBoolean("features.leaderboards", true);
    }

    public boolean isLinkingEnabled() {
        return config.getBoolean("features.linking", true);
    }

    // Chat Settings
    public boolean shouldLogJoins() {
        return config.getBoolean("chat-settings.log-joins", true);
    }

    public boolean shouldLogQuits() {
        return config.getBoolean("chat-settings.log-quits", true);
    }

    public boolean shouldLogDeaths() {
        return config.getBoolean("chat-settings.log-deaths", true);
    }

    // Chat Formats
    public String getWebUserFormat() {
        return config.getString("chat-settings.formats.web-user", "&8[&bWEB&8] &f{name}&7: &f{message}");
    }

    public String getWebAnonymousFormat() {
        return config.getString("chat-settings.formats.web-anonymous", "&8[&7WEB-ANON&8] &7{name}&7: &f{message}");
    }

    public String getAdminPanelFormat() {
        return config.getString("chat-settings.formats.admin-panel", "&8[&cADMIN-PANEL&8] &f{name}&7: &e{message}");
    }

    public String getPluginPrefix() {
        return config.getString("chat-settings.formats.plugin-prefix", "&8[&bAbility&8] ");
    }

    public java.util.List<String> getLinkMessage() {
        return config.getStringList("chat-settings.formats.link-message");
    }

    // Performance
    public int getMonitorInterval() {
        return config.getInt("performance.monitor-interval", 15);
    }
}
