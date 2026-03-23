# Class Documentation: Flotter

**Role:** Mobility / Support / Scout
**Status:** Stable (V7)
**Key Mechanic:** Flight Granting (Tether) & Levitation

## 1. Passive Abilities
*   **Wind Walker:** Immune to Fall Damage (Cloud particles on impact).

## 2. Active Abilities

### A. Flight Tether (Grant Flight)
Give the power of flight to an ally.
*   **Trigger:** `Shift + Right-Click` on a Player.
*   **Cost:** 5 XP Levels (One-time cost).
*   **Channeling:** 5 Seconds.
    *   Must maintain line of sight and sneaking.
    *   Target is notified they are receiving flight.
*   **Effect:**
    *   Enables flight for the target (`essentials:fly`).
    *   **Tether Limit:** Target must stay within **100 blocks** of the Caster.
    *   **Warning:** Target gets warnings if moving too far. Flight cuts if distance > 100.

### B. Self-Flight
*   **Trigger:** `Shift + Look Down (Pitch > 80) + Right-Click`.
*   **Cost:** 5 XP Levels.
*   **Effect:** Grants flight to self. No distance limit (Self-anchored).

### C. Levitation (Offensive)
*   **Trigger:** `Shift + Right-Click` on a Mob (Non-player).
*   **Cost:** 2 XP Levels.
*   **Effect:** Applies Levitation V for 3 seconds. Useful for crowd control.

### D. Deactivation
*   **Trigger:** `Shift + Right-Click` (Target) or `Shift + Look Down + Right-Click` (Self) while flight is active.
*   **Channeling:** 2 Seconds (Pulse animation).
*   **Effect:** Safely disables flight.

## 3. Combat Disruption System
*   **Mechanic:** Flight is unstable in PvP.
*   **Trigger:** Taking damage from a player.
*   **Threshold:** Taking **5 hits** disables flight.
    *   Hits are tracked for 10 seconds.
    *   Action Bar displays "Flight integrity: X/5".
*   **Result:** Flight disables, glass break sound plays.

## 4. Constraints
*   **Mana:** Requires XP levels to activate.
*   **Suppression:** Flight cuts immediately if suppressed.
