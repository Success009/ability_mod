package com.ability.bridge.firebase;

import com.ability.bridge.AbilityBridge;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FirebaseManager {

    private final AbilityBridge plugin;
    private FirebaseDatabase database;

    // Core references based on Ability/ root structure
    private DatabaseReference rootRef;           // Ability/
    private DatabaseReference chatLogsRef;       // Ability/minecraft/chatLogs
    private DatabaseReference statusRef;         // Ability/minecraft/status
    private DatabaseReference onlinePlayersRef;  // Ability/minecraft/onlinePlayers
    private DatabaseReference securityRef;       // Ability/security
    private DatabaseReference leaderboardRef;    // Ability/leaderboard

    public FirebaseManager(AbilityBridge plugin) {
        this.plugin = plugin;
    }

    public void initialize() throws IOException {
        String filename = plugin.getConfigManager().getFirebaseServiceAccountFile();
        File serviceAccountFile = new File(plugin.getDataFolder(), filename);
        FileInputStream serviceAccount = new FileInputStream(serviceAccountFile);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(plugin.getConfigManager().getFirebaseDatabaseUrl())
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        database = FirebaseDatabase.getInstance();

        // Initialize references
        rootRef = database.getReference("Ability");
        chatLogsRef = rootRef.child("minecraft/chatLogs");
        statusRef = rootRef.child("minecraft/status");
        onlinePlayersRef = rootRef.child("minecraft/onlinePlayers");
        securityRef = rootRef.child("security");
        leaderboardRef = rootRef.child("leaderboard");

        plugin.getLogger().info("Firebase paths initialized:");
        plugin.getLogger().info("  Root: Ability/");
        plugin.getLogger().info("  Chat: Ability/minecraft/chatLogs");
        plugin.getLogger().info("  Status: Ability/minecraft/status");
        plugin.getLogger().info("  Players: Ability/minecraft/onlinePlayers");
        plugin.getLogger().info("  Security: Ability/security");
        plugin.getLogger().info("  Leaderboard: Ability/leaderboard");
    }

    public void setServerOffline() {
        try {
            statusRef.child("online").setValueAsync(false).get();
            statusRef.child("timestamp").setValueAsync(ServerValue.TIMESTAMP).get();
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to set server offline: " + e.getMessage());
        }
    }

    public void shutdown() {
        // Firebase cleanup handled automatically
    }

    // Getters for references
    public DatabaseReference getRootRef() {
        return rootRef;
    }

    public DatabaseReference getChatLogsRef() {
        return chatLogsRef;
    }

    public DatabaseReference getStatusRef() {
        return statusRef;
    }

    public DatabaseReference getOnlinePlayersRef() {
        return onlinePlayersRef;
    }

    public DatabaseReference getSecurityRef() {
        return securityRef;
    }

    public DatabaseReference getLeaderboardRef() {
        return leaderboardRef;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
}
