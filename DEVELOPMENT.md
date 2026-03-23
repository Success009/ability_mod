# Development & Build Guide

## System Requirements
- **Java:** JDK 25.0.2 (Verified)
- **Build Tool:** Apache Maven 3.9+

## Building the Plugin
The Java component is located in the `ability-bridge/` directory.

### Standard Build
```powershell
cd ability-bridge
mvn clean package
```

### Build Artifact
The resulting jar will be saved to:
`D:\ability\ability-bridge\target\Ability-2.0.0.jar`

## Architectural Overview
This project uses a **Hybrid Architecture**:
1. **Skript:** Handles 90% of game logic, menus, and user interactions.
2. **Java (Ability Plugin):** Handles tasks requiring high performance or direct API access:
   - **Locating:** Accurate structure/biome lookups via Spigot API.
   - **Bridge:** Synchronizing server data (Chat, TPS, Players) to Firebase.
   - **Account Linking:** Securely matching Minecraft UUIDs to Website UIDs.

## Deployment Notes
- When updating the website database, ensure the `details` field remains an **Array**.
- If `mvn` is not found, use a portable Maven distribution or provide the full path to `mvn.cmd`.

## Recent Stability Updates (Feb 2026)
- **FastBoard Scoreboards:** Migrated global combat system to the modern FastBoard API for high-performance, flicker-free sidebars.
- **Compact Menu Viewports:** Implemented a scrolling viewport for chat-based menus to ensure 100% visibility within the 10-line Minecraft chat limit.
- **Enchanter & Locator Refactor:** Cleaned up redundant logic and restored essential cooldown timers for balanced gameplay.
- **Documentation:** Synchronized all manual pages with the current Hybrid Interaction scheme (F-key for confirm, Q-key for back, Sneak for cycle).