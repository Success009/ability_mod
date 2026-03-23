Act as a Senior Java Software Engineer specializing in PaperMC 1.21.4 and Google Firebase integrations.

I have a defined architecture for a plugin called "Ability SMP Firebase Bridge". I need you to generate the **complete, final source code** for the files listed below.

**Context:** This plugin connects a Minecraft server to a Firebase Realtime Database. The website handles the frontend. We are upgrading the plugin to support Account Linking, Admin Chat, and Future Leaderboards.

### 1. Project Specifications
*   **Java:** 21
*   **Server:** PaperMC 1.21.4
*   **Dependencies:** Paper API, Firebase Admin SDK (9.2.0).
*   **Database Root:** `Ability/`

### 2. Feature Requirements (Must be implemented exactly)

#### A. Core Connection (`FirebaseManager.java`)
*   Initialize using `firebase-service-account.json`.
*   Maintain `DatabaseReference` for: `root`, `chatLogs`, `status`, `onlinePlayers`, `security`, and `leaderboard`.

#### B. Status & Staff Indication (`ServerMonitorTask.java`)
*   Run async every 15 seconds.
*   Update `Ability/minecraft/status` with TPS, RAM, and timestamp.
*   **CRITICAL UPDATE:** When updating `Ability/minecraft/onlinePlayers`:
    *   Iterate through online players.
    *   Map UUID to Object: `{ "name": "PlayerName", "op": boolean }`.
    *   *Reason:* The website needs the `op` boolean to display a "Staff" badge.

#### C. Experience Mirror (`ChatListener.java`)
*   Listen for `AsyncChatEvent`, `PlayerDeathEvent`, `PlayerJoinEvent`, `PlayerQuitEvent`.
*   **Leaderboard Hook:** On `PlayerQuitEvent`, trigger a method in `LeaderboardManager` to push the player's stats (Kills/Deaths - use Bukkit statistics API) to Firebase.
*   Chat: Strip colors using Regex `§[0-9a-fk-or]`. Push to `chatLogs` with `source: "MINECRAFT"`.

#### D. Bi-Directional Chat & Admin Support (`WebListener.java`)
*   Listen to `Ability/minecraft/chatLogs` (limitToLast 1).
*   Filter: Ignore `source: "MINECRAFT"`. Process `source: "WEB"`.
*   **Admin Logic:** Check the `player` field.
    *   If `player` equals "Admin", broadcast to server using Red color: `[Admin] Message`.
    *   Else, broadcast using standard formatting: `[Web] Player: Message`.
*   Remember to switch to the **Main Thread** before broadcasting.

#### E. Account Linking System (`LinkManager.java` & `LinkCommand.java`)
*   **Command:** `/link` (Generates a 6-digit code).
*   **Logic:**
    1.  Save code to `Ability/security/link_requests/{temp_code}` with data `{ "uuid": "...", "ign": "...", "timestamp": <now> }`.
    2.  **Listener:** Listen to `Ability/security/link_requests` (the website writes the code here when a user types it in).
    3.  *Correction:* Actually, standard flow is: Player gets code -> Enters on Website -> Website sends "Verify Request" to DB.
    *   *Revised Logic for this Prompt:*
        1.  Player types `/link`. Plugin generates code `123456`.
        2.  Plugin writes `Ability/security/codes/123456` -> `{ uuid: <uuid>, ign: <name> }`.
        3.  Tell player: "Code: 123456".
        4.  *Website Logic (already exists)*: User enters code. Website reads `Ability/security/codes/123456`. If exists, it links the accounts.
        5.  *Plugin Task:* We just need to handle the code generation and writing to DB. The Website handles the verification.

#### F. Future-Proofing (`LeaderboardManager.java`)
*   Create a manager that has a method `updatePlayerStats(Player player)`.
*   Logic: specific path `Ability/leaderboard/{uuid}`.
*   Data: `{ "name": player.getName(), "kills": player.getStatistic(Statistic.PLAYER_KILLS), "deaths": player.getStatistic(Statistic.DEATHS), "lastSeen": ServerValue.TIMESTAMP }`.
*   Call this method on PlayerQuit.

### 3. File List to Generate
Please provide the full Java content for these files. Ensure imports are correct for Paper 1.21.4.

1.  `src/main/resources/plugin.yml` (Include permissions for /link and libraries section).
2.  `src/main/java/com/ability/bridge/AbilityBridge.java` (Main class, handle startup/shutdown logic).
3.  `src/main/java/com/ability/bridge/firebase/FirebaseManager.java`
4.  `src/main/java/com/ability/bridge/tasks/ServerMonitorTask.java` (With `op` boolean logic).
5.  `src/main/java/com/ability/bridge/listeners/ChatListener.java` (With Leaderboard hook).
6.  `src/main/java/com/ability/bridge/listeners/WebListener.java` (With Admin Chat logic).
7.  `src/main/java/com/ability/bridge/managers/LinkManager.java` (Handles code generation).
8.  `src/main/java/com/ability/bridge/managers/LeaderboardManager.java` (Scaffolding for stats).
9.  `src/main/java/com/ability/bridge/commands/LinkCommand.java`.

Do not summarize. Write the full code.