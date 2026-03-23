# Class Documentation: Mole

**Role:** Miner / Resource Gatherer
**Status:** Stable (V51)
**Key Mechanic:** Phase-Burrowing & Auto-Smelt
**Tool:** "Mole's Pickaxe" (Netherite Pickaxe).

## 1. The Pickaxe
*   **Obtain:** `/mole` command.
*   **Lore:** "The very earth yields to your will."
*   **Requirement:** Abilities trigger only with this pickaxe.

## 2. Active Abilities

### A. Burrow
Allows the player to swim through solid blocks.
*   **Trigger:** `Shift` (Sneak) + Walk into a block.
*   **Condition:** Must be facing a "diggable" block (Stone, Dirt, Gravel, Deepslate, etc.).
*   **Cost:** Drains XP continuously while burrowing.
*   **Mechanic:**
    *   Sets gravity to 0.
    *   Teleports player forward in small increments.
    *   **Clears Blocks:** Automatically breaks blocks in a radius of 3 around the path (dropping items if Survival).
*   **Stop:** Release Sneak.

### B. Auto-Smelt (Passive)
*   **Trigger:** Breaking Ores (Iron, Gold, Copper, Ancient Debris).
*   **Effect:** Drops the smelted ingot instead of the raw ore.
*   **Fortune:** Calculates fortune drops manually (finding nearby dropped raw items and converting them).

## 3. Constraints
*   **Suppression:** Burrowing stops immediately if suppressed. Tools may visually "crumble" (message only).
*   **Obstruction:** Cannot burrow through Bedrock or Air (gap crossing).
