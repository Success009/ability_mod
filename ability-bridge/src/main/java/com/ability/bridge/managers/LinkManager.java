package com.ability.bridge.managers;

import com.ability.bridge.AbilityBridge;
import com.ability.bridge.firebase.FirebaseManager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LinkManager {

    private final AbilityBridge plugin;
    private final FirebaseManager firebaseManager;
    private final Random random;

    public LinkManager(AbilityBridge plugin, FirebaseManager firebaseManager) {
        this.plugin = plugin;
        this.firebaseManager = firebaseManager;
        this.random = new Random();
        startListening();
    }

    private void startListening() {
        // Listen for website requests at Ability/security/link_requests/{webUid} -> "123456"
        firebaseManager.getSecurityRef().child("link_requests").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                String webUid = snapshot.getKey();
                String submittedCode = snapshot.getValue(String.class);

                if (webUid != null && submittedCode != null) {
                    verifyCode(webUid, submittedCode);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {}
            @Override
            public void onChildRemoved(DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {}
            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    private void verifyCode(String webUid, String code) {
        DatabaseReference codeRef = firebaseManager.getSecurityRef().child("codes").child(code);

        codeRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Code is valid! Link the account.
                    String ign = snapshot.child("ign").getValue(String.class);
                    String uuid = snapshot.child("uuid").getValue(String.class);

                    Map<String, Object> userData = new HashMap<>();
                    userData.put("ign", ign);
                    userData.put("uuid", uuid);
                    userData.put("linkedAt", ServerValue.TIMESTAMP);

                    // 1. Create User Entry: Ability/security/users/{webUid}
                    firebaseManager.getSecurityRef().child("users").child(webUid).setValueAsync(userData);

                    // 2. Delete the used code
                    codeRef.removeValueAsync();

                    // 3. Delete the request
                    firebaseManager.getSecurityRef().child("link_requests").child(webUid).removeValueAsync();

                    plugin.getLogger().info("Successfully linked web user " + webUid + " to player " + ign);
                } else {
                    // Invalid code - just delete the request so they can try again
                    firebaseManager.getSecurityRef().child("link_requests").child(webUid).removeValueAsync();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                plugin.getLogger().warning("Error verifying code: " + error.getMessage());
            }
        });
    }

    /**
     * Generates a 6-digit code and writes it to Firebase at:
     * Ability/security/codes/{code} -> { uuid: ..., ign: ... }
     * 
     * The website reads this path to verify the code when a user enters it.
     * 
     * @param player The player requesting the link code
     * @return The generated 6-digit code
     */
    public String generateLinkCode(Player player) {
        // Generate 6-digit code
        String code = String.format("%06d", random.nextInt(1000000));
        
        // Prepare data
        Map<String, Object> codeData = new HashMap<>();
        codeData.put("uuid", player.getUniqueId().toString());
        codeData.put("ign", player.getName());
        codeData.put("timestamp", ServerValue.TIMESTAMP);
        
        // Write to Ability/security/codes/{code}
        firebaseManager.getSecurityRef()
                .child("codes")
                .child(code)
                .setValueAsync(codeData);
        
        plugin.getLogger().info("Generated link code " + code + " for player " + player.getName());
        
        return code;
    }
}
