# Class Documentation: Spector

**Role:** Scout / Infiltration
**Status:** Stable (V2)
**Key Mechanic:** Spectator Mode (Phase Shift)

## 1. Phase Shift
Allows the player to enter vanilla Spectator mode temporarily to scout or escape.

### A. Activation
*   **Trigger:** `Shift + Swap Item (F) + Look Down (Pitch > 80)`.
*   **Cooldown:** 30 Seconds.
*   **Duration:** 15 Seconds.

### B. State Mechanics
*   **While Active:**
    *   Gamemode set to **Spectator**.
    *   Action Bar counts down duration (`14.5s`).
    *   Heartbeat sound effect plays.
    *   **Cancel:** `Left-Click` exits the mode early.

### C. Exiting (Re-Materialization)
*   **Safety:** If inside a block, teleports the player 1 block up to prevent suffocation.
*   **Visuals:** Plays "Beacon Deactivate" sound and soul particles.
*   **Gamemode:** Reverts to Survival.

## 4. Constraints
*   **Combat Suppression:** Cannot enter Phase Shift if you have been in combat within the last 15 seconds.
*   **Item Bound:** Spector abilities only function for authorized users.
