# Class Documentation: Shifter

**Role:** Versatile / Siege / Transformation
**Status:** Stable (V12)
**Key Mechanic:** Progressive Transformations

## 1. Transformation System
The Shifter cycles through forms based on look angle while swapping items (F key).
*   **Trigger:** `Shift + Swap Item (F)`
*   **Cooldown:** 10 Seconds.
*   **Direction:**
    *   **Looking UP (`< -80`):** Grow (Small -> Normal -> Large -> Colossal).
    *   **Looking DOWN (`> 80`):** Shrink (Colossal -> Large -> Normal -> Small).

### Forms & Stats

| Form | Scale | Health | Speed | Reach | Description |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Small** | 0.5x | 20 HP | Normal | Normal | Harder to hit, good for hiding. |
| **Normal** | 1.0x | 20 HP | Normal | Normal | Baseline Minecraft player. |
| **Large** | 1.5x | 22 HP | +30% | +0.5 | Slight combat advantage. |
| **Colossal**| 10.0x| 50 HP | +400% | +18.0 | **Requires 5 Levels**. Raid Boss. |

## 2. Colossal Form Mechanics
The Colossal form is unique and requires resource maintenance.

### A. Activation
*   **Requirement:** Must be in Large form, Look Up, Shift+F.
*   **Cost:** 5 XP Levels.
*   **Channeling:** 3 Seconds (Must hold sneak). "CHARGING COLOSSAL FORM".
*   **Maintenance:** Drains XP over time. Form collapses if XP drops below 5 levels.

### B. Colossal Leap
*   **Trigger:** Hold `Shift` while in Colossal form.
*   **Charge:** Progress fills on the **Scoreboard** (Sidebar).
*   **Activation:** Jump when fully charged.
*   **Effect:** Massive jump boost (Jump Strength 7).
*   **Impact:** Landing creates an earthquake (Particle crack), damages nearby entities (1 heart), and knocks them up.

### C. Siege Breaker
*   **Trigger:** Break a block.
*   **Effect:** Breaks an **8x8x8 cube** around the target block.
*   **Safety:** Does NOT break containers (Chests, Barrels, etc.) to prevent item loss.

## 3. Constraints
*   **Unarmed:** Logic triggers on `Swap Item` event, usually requires holding nothing or a specific item isn't strictly enforced but prevents actual offhand swapping.
*   **Suppression:** Reverts to Normal form immediately.
