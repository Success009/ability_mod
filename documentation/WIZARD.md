# Class Documentation: Wizard (The Archmage)

**Role:** Support / Control / Artillery
**Status:** **ACTIVE (Rework V2)**
**Key Mechanic:** Ritual-Based Casting via Grimoire Menu

## 1. Overview
The Wizard has abandoned clumsy chat commands for a sophisticated **Grimoire Menu**. By focusing their energy, they can cast powerful Blessings and Curses at varying tiers of power.

## 2. Controls
*   **Open Grimoire:** `Look Down` + `Sneak` + `Swap Item (F)`
*   **Navigate:** `Sneak` to cycle through options.
*   **Confirm:** `Swap Item (F)` to select.
*   **Cancel/Back:** `Drop Item (Q)` to go back or close.

## 3. The Ritual System
Casting is no longer instant. It requires a deliberate selection process:
1.  **Discipline:** Choose between **Blessings** (Buffs) or **Curses** (Debuffs).
2.  **Spell:** Select the specific arcane art you wish to perform.
3.  **Target:** Choose a player from the list (Must be online and in the same world).
4.  **Tier:** Select the power level (Tier I, II, or III). Higher tiers cost more Mana (XP) and have greater range/duration.

**Safety:**
*   You must remain stationary while casting. Moving more than **0.4 blocks** breaks your focus and cancels the ritual.
*   If the target leaves the world or disconnects, the ritual fails safely.

## 4. Spell List (Grimoire)

### Blessings (Green/Gold Beam)
| Spell | Effect | Description |
| :--- | :--- | :--- |
| **Alacrity** | Speed III + Haste II | Grants supernatural speed and mining capability. |
| **Might** | Strength II + Haste II | Empowers the target's physical attacks. |
| **Vitality** | Instant Heal + Regen III | A burst of life energy to save an ally. |
| **Oracle** | Glowing | Reveals all entities in a 30-block radius around the target. |
| **Aegis** | Resistance II + Fire Res | Protective warding against damage and flame. |

### Curses (Purple/Red Beam)
| Spell | Effect | Description |
| :--- | :--- | :--- |
| **Decay** | Wither III + Hunger III | Rots the target from the inside out. |
| **Frost** | Slowness IV + Mining Fat. | Freezes the target's movement and actions. Tier III freezes them solid. |
| **Void** | Blindness + Darkness | Engulfs the target in absolute shadow. |
| **Silence** | Dispel | Strips the target of all positive potion effects. |

## 5. Tiers & Costs

| Tier | XP Cost | Range | Duration (Base) |
| :--- | :--- | :--- | :--- |
| **Tier I** | 5 Levels | 50 Blocks | 15 Seconds |
| **Tier II** | 15 Levels | 150 Blocks | 30 Seconds |
| **Tier III** | 30 Levels | 300 Blocks | 60 Seconds |

## 6. Passives
*   **Arcane Shield:** The Wizard is naturally immune to most negative status effects (Poison, Wither, Blindness, Slowness, etc.).
*   **Focus:** Cannot cast while Suppressed or if Mana is insufficient.