# Mole Ability Rework Plan - "The Subterranean King"

## 1. Design Philosophy
**Identity:** A **Utility/Survival** class.
**Strengths:** Unrivaled mobility and intelligence **underground**.
**Weaknesses:** Vulnerable on the surface; lacks high burst damage.
**Goal:** The Mole shouldn't try to out-DPS a Reaper. Instead, it should be the master of **ambush, escape, and information**.

## 2. Mechanic Overhaul

### A. "Fluid Burrow" (Movement Polish)
*   **The Change:** Upgrade from 8-tick teleport (choppy) to **1-tick velocity** (smooth).
*   **Feel:** "Swimming" through the earth.
*   **Logic:**
    *   Gravity disabled while burrowing.
    *   Break blocks in a small radius (3x3) to clear the path.
    *   **Surface Limitation:** Cannot burrow through Air. If you hit air (a cave or surface), you stop or fall. This naturally limits it to the ground.

### B. "Seismic Sense" (The Key Utility)
*   **Concept:** "Moles are blind, but they feel vibrations."
*   **Trigger:** Passive while **Burrowing** OR **Sneaking** underground (Y < 64).
*   **Effect:**
    *   Every 1-2 seconds, scan for players within **30 blocks**.
    *   **Visual:** Apply the **Glowing** effect to them for 2 seconds.
    *   **Sound:** Play a deep "thump" pitch-shifted sound if a player is nearby.
    *   **Tactical Value:** You see them; they don't see you. You can tunnel under them to escape or setup a trap.

### C. "Tremor" (Self-Defense, Not Offense)
*   **Trigger:** **Swap Hand (F)**.
*   **Cooldown:** 10-15 seconds.
*   **Effect:**
    *   Slams the ground, creating a localized earthquake.
    *   **Radius:** 5 blocks.
    *   **CC:** Applies **Slowness II** (3s) and slight **Knockback** away from the Mole.
    *   **Purpose:** A "Get Off Me" tool. If a Reaper chases you into a tunnel, you use this to slow them down and burrow away.

## 3. "Terra-Form" (Passive Survival)
*   **Effect:** When below Y=64 (or "Deepslate level"), gain **Resistance I**.
*   **Logic:** The pressure of the earth strengthens your skin. This gives the Mole a slight edge in deep-cave fights without making them tanky on the surface.

## 4. Implementation Constraints
*   **Smelting:** The existing `on break` logic (fortune calculations, item drops) remains **strictly untouched**.
*   **Vision:** No Night Vision changes (standard).
*   **Performance:** The scan for Seismic Sense must be efficient (only check nearby players).