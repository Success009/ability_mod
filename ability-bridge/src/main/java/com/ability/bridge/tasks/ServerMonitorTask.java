package com.ability.bridge.tasks;

import com.ability.bridge.AbilityBridge;
import com.ability.bridge.firebase.FirebaseManager;
import com.google.firebase.database.ServerValue;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerMonitorTask {

    private final AbilityBridge plugin;
    private final FirebaseManager firebaseManager;
    private ScheduledExecutorService scheduler;

    public ServerMonitorTask(AbilityBridge plugin, FirebaseManager firebaseManager) {
        this.plugin = plugin;
        this.firebaseManager = firebaseManager;
    }

    public void start() {
        int interval = plugin.getConfigManager().getMonitorInterval();
        // Run real-time (independent of TPS)
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                updateStatus();
                updateOnlinePlayers();
            } catch (Exception e) {
                plugin.getLogger().warning("Error in server monitor task: " + e.getMessage());
                e.printStackTrace();
            }
        }, 5L, (long) interval, TimeUnit.SECONDS);
        
        plugin.getLogger().info("Server monitor task started (" + interval + "s interval, real-time)");
    }

    public void stop() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            try {
                // Wait a bit for existing tasks to terminate
                if (!scheduler.awaitTermination(1, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }
    }

    private void updateStatus() {
        Map<String, Object> statusData = new HashMap<>();
        
        // Basic status
        statusData.put("online", true);
        statusData.put("timestamp", ServerValue.TIMESTAMP);
        
        // TPS
        double tps = plugin.getServer().getTPS()[0]; // 1-minute average
        statusData.put("tps", Math.round(tps * 100.0) / 100.0);
        
        // Memory (in MB)
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory() / 1048576;
        long totalMemory = runtime.totalMemory() / 1048576;
        long freeMemory = runtime.freeMemory() / 1048576;
        long usedMemory = totalMemory - freeMemory;
        
        Map<String, Object> memoryData = new HashMap<>();
        memoryData.put("used", usedMemory);
        memoryData.put("free", freeMemory);
        memoryData.put("max", maxMemory);
        memoryData.put("allocated", totalMemory);
        statusData.put("memory", memoryData);
        
        // Player count
        statusData.put("playerCount", plugin.getServer().getOnlinePlayers().size());
        statusData.put("maxPlayers", plugin.getServer().getMaxPlayers());
        
        // Server info
        statusData.put("serverName", plugin.getServer().getName());
        statusData.put("version", plugin.getServer().getVersion());
        
        firebaseManager.getStatusRef().setValueAsync(statusData);
    }

    private void updateOnlinePlayers() {
        Map<String, Object> playersData = new HashMap<>();
        
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            Map<String, Object> playerInfo = new HashMap<>();
            playerInfo.put("name", player.getName());
            playerInfo.put("op", player.isOp()); // CRITICAL: Staff badge indicator
            
            playersData.put(player.getUniqueId().toString(), playerInfo);
        }
        
        firebaseManager.getOnlinePlayersRef().setValueAsync(playersData);
    }
}
