package com.ability.bridge.listeners;

import com.ability.bridge.AbilityBridge;
import com.ability.bridge.firebase.FirebaseManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class WebListener {

    private final AbilityBridge plugin;
    private final FirebaseManager firebaseManager;
    private final LegacyComponentSerializer legacySerializer;

    public WebListener(AbilityBridge plugin, FirebaseManager firebaseManager) {
        this.plugin = plugin;
        this.firebaseManager = firebaseManager;
        this.legacySerializer = LegacyComponentSerializer.legacyAmpersand();
    }

    public void startListening() {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            // Listen to last 1 message from chatLogs
            firebaseManager.getChatLogsRef().limitToLast(1).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                    processMessage(snapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot snapshot, String previousChildName) {}

                @Override
                public void onChildRemoved(DataSnapshot snapshot) {}

                @Override
                public void onChildMoved(DataSnapshot snapshot, String previousChildName) {}

                @Override
                public void onCancelled(DatabaseError error) {
                    plugin.getLogger().warning("Web listener error: " + error.getMessage());
                }
            });

            plugin.getLogger().info("Web chat listener started (Web -> Game bridge active)");
        });
    }

    private void processMessage(DataSnapshot snapshot) {
        try {
            String source = snapshot.child("source").getValue(String.class);
            
            // Only process WEB messages (ignore MINECRAFT to prevent echo)
            if (!"WEB".equals(source)) {
                return;
            }

            String player = snapshot.child("player").getValue(String.class);
            String message = snapshot.child("message").getValue(String.class);

            if (player == null || message == null) {
                return;
            }

            // Switch to main thread for broadcasting
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                String format;
                String prefix = plugin.getConfigManager().getPluginPrefix();

                if ("Admin".equalsIgnoreCase(player)) {
                    format = plugin.getConfigManager().getAdminPanelFormat();
                } else if ("Anonymous".equalsIgnoreCase(player)) {
                    format = plugin.getConfigManager().getWebAnonymousFormat();
                } else {
                    format = plugin.getConfigManager().getWebUserFormat();
                }

                String formattedMessage = format
                        .replace("{name}", player)
                        .replace("{message}", message)
                        .replace("{prefix}", prefix);

                Component broadcastMessage = legacySerializer.deserialize(formattedMessage);
                plugin.getServer().broadcast(broadcastMessage);
            });

        } catch (Exception e) {
            plugin.getLogger().warning("Error processing web message: " + e.getMessage());
        }
    }
}
