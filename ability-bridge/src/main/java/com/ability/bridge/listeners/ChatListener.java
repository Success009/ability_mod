package com.ability.bridge.listeners;

import com.ability.bridge.AbilityBridge;
import com.ability.bridge.firebase.FirebaseManager;
import com.ability.bridge.managers.LeaderboardManager;
import com.google.firebase.database.ServerValue;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class ChatListener implements Listener {

    private final AbilityBridge plugin;
    private final FirebaseManager firebaseManager;
    private final LeaderboardManager leaderboardManager;
    private final PlainTextComponentSerializer plainSerializer;

    public ChatListener(AbilityBridge plugin, FirebaseManager firebaseManager, LeaderboardManager leaderboardManager) {
        this.plugin = plugin;
        this.firebaseManager = firebaseManager;
        this.leaderboardManager = leaderboardManager;
        this.plainSerializer = PlainTextComponentSerializer.plainText();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        String message = plainSerializer.serialize(event.message());
        
        // Strip color codes using regex
        String cleanMessage = message.replaceAll("§[0-9a-fk-or]", "");
        
        // Push to Firebase: Game -> Web
        pushChatMessage(player.getName(), cleanMessage, "MINECRAFT");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!plugin.getConfigManager().shouldLogJoins()) return;
        
        Player player = event.getPlayer();
        String message = player.getName() + " joined the game";
        
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            pushChatMessage("Server", message, "MINECRAFT");
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            if (plugin.getConfigManager().shouldLogQuits()) {
                String message = player.getName() + " left the game";
                pushChatMessage("Server", message, "MINECRAFT");
            }
            
            // LEADERBOARD HOOK: Update player stats on quit
            if (leaderboardManager != null) {
                leaderboardManager.updatePlayerStats(player);
            }
        });
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!plugin.getConfigManager().shouldLogDeaths()) return;
        
        if (event.deathMessage() != null) {
            String deathMsg = plainSerializer.serialize(event.deathMessage());
            String cleanMsg = deathMsg.replaceAll("§[0-9a-fk-or]", "");
            
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
                pushChatMessage("Server", cleanMsg, "MINECRAFT");
            });
        }
    }

    private void pushChatMessage(String playerName, String message, String source) {
        Map<String, Object> chatData = new HashMap<>();
        chatData.put("player", playerName);
        chatData.put("message", message);
        chatData.put("source", source);
        chatData.put("timestamp", ServerValue.TIMESTAMP);
        
        firebaseManager.getChatLogsRef().push().setValueAsync(chatData);
    }
}
