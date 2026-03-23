package com.ability.bridge.managers;

import com.ability.bridge.AbilityBridge;
import com.ability.bridge.firebase.FirebaseManager;
import com.google.firebase.database.ServerValue;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardManager {

    private final AbilityBridge plugin;
    private final FirebaseManager firebaseManager;

    public LeaderboardManager(AbilityBridge plugin, FirebaseManager firebaseManager) {
        this.plugin = plugin;
        this.firebaseManager = firebaseManager;
    }

    /**
     * Updates player statistics in Firebase at:
     * Ability/leaderboard/{uuid} -> { name, kills, deaths, lastSeen }
     * 
     * Called when a player quits the server.
     * Uses Bukkit Statistics API to fetch kills and deaths.
     * 
     * @param player The player whose stats should be updated
     */
    public void updatePlayerStats(Player player) {
        Map<String, Object> statsData = new HashMap<>();
        
        // Player info
        statsData.put("name", player.getName());
        
        // Fetch statistics using Bukkit API
        int kills = player.getStatistic(Statistic.PLAYER_KILLS);
        int deaths = player.getStatistic(Statistic.DEATHS);
        
        statsData.put("kills", kills);
        statsData.put("deaths", deaths);
        statsData.put("lastSeen", ServerValue.TIMESTAMP);
        
        // Write to Ability/leaderboard/{uuid}
        firebaseManager.getLeaderboardRef()
                .child(player.getUniqueId().toString())
                .setValueAsync(statsData);
        
        plugin.getLogger().info("Updated leaderboard stats for " + player.getName() + 
                " (K: " + kills + ", D: " + deaths + ")");
    }
}
