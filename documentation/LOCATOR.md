# Class Documentation: Locator

**Role:** Explorer / Pathfinder
**Status:** Stable (V18 - Pure Navigation)
**Key Mechanic:** Structure & Biome Compass

## 1. The Grand Compass
*   **Tool:** Vanilla Compass.
*   **Trigger:** `Right-Click` to open the "Ritual" menu.

## 2. The Ritual (Menu System)
Uses a chat-based UI (Sneak to cycle, Swap Item (F) to select).

### A. Selection Flow
1.  **Dimension:** Overworld, Nether, End.
2.  **Type:** Structure or Biome.

### B. Costs (XP Levels)
*   **Biome Scan:** 10 Levels.
*   **Common Structure:** 15 Levels.
*   **Rare Structure:** 20-30 Levels.
*   **Epic Structure (Stronghold/Mansion):** 40 Levels.

### C. Searching
*   **Action:** "Scanning [Dimension] for [Target]..."
*   **Technique:** High-accuracy search via **Java Bridge API**.

## 3. Warp Ability (Teleportation)
Once a target is found:
*   **Trigger:** Click the `[ CLICK HERE TO WARP ]` message in chat.
*   **Warp Cost:** `5 + (Distance / 250)` Levels.
*   **Safety:** Teleports to Y=320 with Resistance & Slow Falling.

## 4. Constraints
*   **Combat Suppression:** Cannot perform the ritual if you have been in combat within the last 15 seconds.
*   **Movement:** Moving during menu navigation or warping cancels the session.
*   **Mana:** Requires sufficient XP levels to initiate scans or warps.

## 4. Waypoints
The Grand Compass can store your current location for later travel.
*   **Capacity:** Up to **4 total waypoints** across all dimensions.
*   **Cross-Dimension Travel:** **Disabled.** You can only see and warp to waypoints in your current dimension.
*   **Saving:** Look Down + F while in the Waypoint menu to save your current coordinates.
*   **Recall:** Select a stored waypoint to generate a warp point.
