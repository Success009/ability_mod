# Class Documentation: Dragon Wielder

**Role:** Boss / Overlord
**Status:** Special Event Class (V10)
**Key Mechanic:** Ability Override via Dragon Egg

## 1. Acquisition
*   **Trigger:** Picking up the **Dragon Egg**.
*   **Effect:** 
    *   **Class Override:** Your previous class (Strengther, Wizard, etc.) is suppressed.
    *   **Team:** Auto-joins team `DW`.
    *   **Visuals:** Glowing effect (Purple).
    *   **Title:** "DRAGON WIELDER - Your mortal abilities are replaced by the Void."

## 2. Abilities

### A. Domain Expansion: Dimensional Rift
*   **Trigger:** Placing the Dragon Egg.
*   **Channeling:** 1.5 Seconds ("CHANNELING THE VOID...").
*   **Effect:** Creates a massive sphere (Radius 20) around the egg.
    *   **Structure:** Replaces blocks with End Stone, Obsidian, and Crying Obsidian.
    *   **Suppression:** **ALL** players inside the sphere have their abilities disabled (LuckPerms group switch).
    *   **Freeze:** Enemies are frozen in place briefly upon activation.
    *   **Protection:** Blocks inside the domain cannot be broken by others.
    *   **Duration:** 30 Seconds.
    *   **Collapse:** After 30s, the domain fades and original blocks are restored.

### B. Passive Minion Control
*   **Effect:** Hostile mobs within 20 blocks automatically join the `DW` team, preventing them from attacking the Wielder.

## 3. The Egg Tracker
*   **Logic:** The Egg is a unique tracked entity.
*   **Loss:** If the Egg is destroyed (burned, void), it triggers a lightning strike and global broadcast.
*   **Drop:** Dropping the egg restores your original class abilities immediately.
