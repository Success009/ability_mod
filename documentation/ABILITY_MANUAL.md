# Ability SMP - Class Manual

**System Version:** 1.0
**Framework:** Skript (Strict Mode)

## Overview
This server features unique classes ("Abilities") that grant special powers. 
*   **Activation:** Most abilities use **Shift + Click** or specific item interactions.
*   **Resource:** Many abilities use **XP Levels** as Mana.
*   **Safety:** Admins (Creators) must manually enable abilities using `/creator use <ability>`.
*   **Suppression:** The Dragon Boss or specific Domains can suppress your powers.

---

## 1. Strengther
**Role:** Tank / Brawler
**Passive:** Fall Damage immunity during Bankai.

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Shift + Jump (x3)** | **Bankai Prime** | Charges a combo. On 3rd jump, you enter "Ready" state. |
| **Attack (Ready)** | **Activation** | Hitting an enemy while Ready launches you both into the air. |
| **Attack (Air)** | **Death Fist** | Hitting the target again in the air deals **1000 Damage**. <br>*Note: Immortal class can counter this.* |
| **Shift + Right-Click** | **Grab** | Picks up a nearby entity/player. |
| **Left-Click** | **Throw/Slam** | Throws the grabbed entity. If looking down, slams them into ground. |

---

## 2. Immortal
**Role:** Tank / Support
**Passive (Eternal Form):** Immune to Fall Damage and Drowning.

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Passive** | **Karma** | If HP > 50% and you take lethal damage, death is prevented and attacker is knocked back. |
| **Shift + Look Down + R-Click** | **Ritual** | Channels for 1 second. <br>**Effect:** Full Heal + AOE Knockback + Absorption. |

---

## 3. Flotter
**Role:** Support / Scout
**Passive:** Immune to Fall Damage.

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Shift + Look Down + R-Click** | **Self-Flight** | Grants flight to yourself. |
| **Shift + R-Click (Player)** | **Tether** | Grants flight to target player (Max 100 blocks distance). |
| **Shift + R-Click (Mob)** | **Levitate** | Levitates the mob for 3 seconds. |

*   **Combat Disruption:** Taking 5 hits disables your flight.

---

## 4. Shifter
**Role:** Versatile / Siege
**Passive:** Attributes (Health/Speed/Reach) change with form.

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Shift + Swap Item + Look Up** | **Grow** | Small -> Normal -> Large -> Colossal. |
| **Shift + Swap Item + Look Down** | **Shrink** | Colossal -> Large -> Normal -> Small. |

**Forms:**
*   **Small:** 0.5x Size, Low HP, Hard to hit.
*   **Normal:** Standard Stats.
*   **Large:** 1.5x Size, 22 HP, Higher Reach.
*   **Colossal:** 10x Size, 50 HP, Massive Reach, High Jump. **Cost:** 5 Levels + Charge Time.
    *   **Leap:** Shift to charge jump -> Super Leap.
    *   **Break:** Breaking a block breaks an 8x8x8 area.

---

## 5. Wizard (Legacy V1)
**Role:** Support / Mage
**Status:** *Pending Rework to "Arcane Focus" system.*

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Command** | `/bless <player> <type>` | **Types:** Movement (Speed), Power (Str), Charisma, Vision, Heal. |
| **Command** | `/curse <player>` | Applies Wither, Darkness, and Slowness. Cost: 10 Levels. |

---

## 6. Enchanter
**Role:** Utility
**Interaction:** Left-Click **Enchanting Table**.

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Sneak** | **Cycle Menu** | Scrolls through available enchantments. |
| **Swap Item (F)** | **Select** | Confirms selection or applies enchantment. |
| **Channeling** | **Ritual** | Hold position to apply the enchantment. Costs XP. |

---

## 7. Reaper
**Role:** Assassin
**Weapon:** Scythe (Stone Hoe with Lore).

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Kill Entity** | **Harvest** | Gathers Souls. (Player = 5 Souls, Mob = 1 Soul). |
| **Right-Click** | **Soul Dash** | Consumes 1 Soul to dash forward. |
| **Attack** | **Lifesteal** | Heals 0.5 - 1.0 hearts on hit. |

---

## 8. Spector
**Role:** Scout / Stealth

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Shift + Swap Item + Look Down** | **Phase Shift** | Enter Spectator Mode for 15 seconds. |
| **Left-Click** | **Cancel** | Exits Phase Shift early. |

---

## 9. Mole
**Role:** Miner / Escape
**Tool:** Netherite Pickaxe (Custom Lore).

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **Shift** | **Burrow** | Moves you *through* solid blocks. Drains XP. |
| **Break Block** | **Smelt** | Auto-smelts ores (Iron, Gold, Copper, Debris). |

---

## 10. Locator
**Role:** Explorer
**Interaction:** Right-Click **Compass**.

| Trigger | Action | Effect |
| :--- | :--- | :--- |
| **GUI** | **Locate** | Find Biomes or Structures in any dimension. |
| **GUI** | **Warp** | Teleport to found location. Costs XP. |

---

## 11. Dragon Wielder (Special)
**Activation:** Pickup the **Dragon Egg**.
**Effect:** Overrides your current class.
**Powers:**
*   **Domain Expansion:** Place the Egg to create a zone where other players' abilities are disabled.

---

## General Systems
*   **Teams:** `/duo request <player>` (Prevents friendly fire).
*   **Creator Mode:** `/creator use <ability>` (For Admins).
*   **Suppression:** Global variable `{ability.suppressed.%uuid%}` stops all magic.
