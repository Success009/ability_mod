# Class Documentation: Reaper

**Role:** Assassin / Skirmisher
**Status:** Stable (V38)
**Key Mechanic:** Soul Harvesting & Mobility
**Weapon:** "Reaper's Scythe" (Stone Hoe).

## 1. The Scythe
*   **Obtain:** `/reaper` command.
*   **Lore:** "Drenched in thirst and seeks blood".
*   **Requirement:** Abilities only function when holding this specific item.

## 2. Soul System
*   **Resource:** Souls (Max 30).
*   **Gain:**
    *   **Kill Player:** +5 Souls.
    *   **Kill Mob:** +1 Soul.
*   **Display:** 
    *   **Scoreboard:** Displays current soul count in the "SOUL BANK" section.
    *   **Chat:** Provides instant feedback on gain/loss (e.g., `+1 Soul | Total: 12/30`).

## 3. Active Abilities

### A. Soul Dash
*   **Trigger:** `Shift + Right-Click` (looking forward).
*   **Cost:** 1 Soul.
*   **Effect:** Launches the player forward in the direction they are facing.
*   **Cooldown:** 1.5 Seconds.

### B. Death Mark (Passive)
*   **Trigger:** 4th consecutive hit on the same target.
*   **Effect:** Deals **2x Damage** on the 4th hit.

### C. Lifesteal (Passive)
*   **Trigger:** Attacking an entity with the Scythe.
*   **Effect:**
    *   **Vs Player:** Heals Reaper for 1 Heart (2 HP).
    *   **Vs Mob:** Heals Reaper for 0.5 Hearts (1 HP).

### D. Projectile Immunity (Passive)
*   **Trigger:** Always active while holding the Scythe.
*   **Effect:** The Reaper is completely immune to all projectile damage (arrows, tridents, etc.).


## 4. Constraints
*   **Item Bound:** Scythe dissipates/fails if used by non-Reapers.
*   **Suppression:** Dash and Lifesteal fail if suppressed.
