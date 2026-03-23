# Class Documentation: Enchanter

**Role:** Utility / Support
**Status:** Stable (V13)
**Key Mechanic:** Custom GUI-less Enchanting System

## 1. The Enchanting Ritual
Enchanters can apply specific enchantments to items without RNG, using XP as currency.

### A. Interaction
*   **Block:** Enchanting Table.
*   **Trigger:** `Left-Click` (with item in hand).
*   **Menu System:** Chat-based UI.
    1.  **Select Enchant:** Sneak to cycle through valid enchants for the held item. Press `Swap Item (F)` to select.
    2.  **Select Level:** Sneak to cycle levels (I, II, III, etc.). Press `Swap Item (F)` to confirm.
    3.  **Channeling:** Hold position near the table while particles ("End Rod") swirl.

### B. Costs
The system calculates cost dynamically:
`Cost = (Base_Cost * Tier_Multiplier * Level) + 5`

*   **Base Costs:**
    *   Mending/Infinity: 12
    *   Fortune/Looting: 8
    *   Sharpness/Prot: 4
    *   Basic: 2
*   **Tier Multipliers:**
    *   Netherite: 2.0x
    *   Diamond: 1.5x
    *   Gold/Iron: 1.2x

### C. Constraints
*   **Cooldown:** 1 Minute (Timestamp based).
*   **Movement:** Moving > 1 block during the ritual cancels it.
*   **Safety:** Swapping items during the ritual cancels it.

## 2. Valid Enchantments
The script automatically detects valid enchants for the held item (e.g., Sharpness for Swords, Silk Touch for Picks, Trident enchants, etc.). It supports new 1.21 enchants like **Wind Burst**, **Density**, and **Breach**.
