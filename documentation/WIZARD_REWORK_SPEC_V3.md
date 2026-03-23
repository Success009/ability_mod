# WIZARD REWORK SPECIFICATION (V3 - The Archmage)

**Status:** Final Design
**Goal:** Create a high-skill, ritual-based magic system where power and range come at a cost of mobility and XP.

## 1. Activation & Safety
*   **Trigger:** `Look Down (Pitch > 80)` + `Sneak` + `Swap Item (F)`.
    *   *Why?* Prevents accidental opening during combat.
*   **Movement Constraint:**
    *   The Wizard **MUST** remain stationary while selecting a spell.
    *   **Penalty:** If the Wizard moves more than **0.5 blocks** from the activation point, the menu closes, chat clears, and the ritual is cancelled ("Focus Lost").
*   **Tool:** `Amethyst Shard` (Renamed: &dArcane Focus).

## 2. Casting Modes
The Wizard now has two distinct ways to use magic, selected via the menu.

### Mode A: Ritual Cast (The Sniper)
*   **Action:** Single Left-Click after selection.
*   **Range:** Up to **300 Blocks** (Ray-traced skillshot).
*   **Cost:** High, flat XP cost.
*   **Effect:** Powerful, long-duration buff/debuff applied instantly.

### Mode B: Tether (The Link)
*   **Action:** Hold Right-Click after selection.
*   **Range:** **50 Blocks** (Must maintain Line of Sight).
*   **Cost:** Drains XP continuously (**1 Level per second**).
*   **Effect:** Continuous application of effects (e.g., rapid healing, constant speed) while the beam is held. Breaks if target moves out of range or LoS is lost.

## 3. The Tier System (Scaling)
Applies to **Ritual Casts** only. Tethers have fixed power but infinite duration (mana permitting).

| Tier | XP Cost | Range | Description |
| :--- | :--- | :--- | :--- |
| **Tier I** | 5 Levels | 50 Blocks | Quick cast. |
| **Tier II** | 15 Levels | 150 Blocks | Standard combat engagement. |
| **Tier III** | 30 Levels | 300 Blocks | Cross-map artillery. Massive duration. |

## 4. Spell List (Grimoire)

### A. Blessings (Buffs - Green/Gold Beam)
*Targeting:* Hit an ally.

**1. Alacrity (Movement)**
*   *Ritual:* Speed III + Jump Boost II (Duration: 15s / 30s / 60s).
*   *Tether:* Constant Speed II + Jump Boost I.

**2. Might (Power)**
*   *Ritual:* Strength II + Haste II (Duration: 10s / 20s / 40s).
*   *Tether:* Constant Strength I + Haste I.

**3. Vitality (Healing)**
*   *Ritual:* Regeneration III (Duration: 5s / 10s / 15s) + Absorption (Tier III only).
*   *Tether:* Constant Regeneration II (Rapid healing).

**4. Oracle (Vision)**
*   *Ritual:* Grants **Glowing** to all entities around the target (Radius: 20 / 40 / 60).
*   *Tether:* Target sees all nearby invisible entities (Spectral Arrow effect).

**5. Aegis (Protection)**
*   *Ritual:* Resistance II + Fire Res (Duration: 20s / 40s / 60s).
*   *Tether:* Constant Resistance I + Fire Resistance.

### B. Curses (Debuffs - Purple/Red Beam)
*Targeting:* Hit an enemy.

**1. Decay (Wither)**
*   *Ritual:* Wither III + Hunger III (Duration: 5s / 10s / 15s).
*   *Tether:* Constant Wither II.

**2. Frost (Control)**
*   *Ritual:* Slowness IV + Mining Fatigue III (Duration: 5s / 8s / 12s). Tier III applies **FREEZE**.
*   *Tether:* Constant Slowness III.

**3. Void (Blindness)**
*   *Ritual:* Blindness + Darkness (Duration: 5s / 10s / 20s).
*   *Tether:* Constant Darkness (Flickering).

**4. Silence (Suppression)**
*   *Ritual:* Clears positive potion effects from target. (Tier III only).
*   *Tether:* Prevents target from using Ender Pearls (Experimental).

## 5. Interaction Flow
1.  **Initiate:** Look Down + Sneak + F.
2.  **Menu 1 (Category):** `Blessings` / `Curses`.
3.  **Menu 2 (Spell):** Select the specific spell (e.g., "Alacrity").
4.  **Menu 3 (Mode):**
    *   `> Ritual (Burst)` -> Select Tier (I, II, III).
    *   `> Tether (Link)` -> Enters Charged State immediately.
5.  **Charged State:**
    *   Chat clears.
    *   ActionBar: `&d[ CHARGED ] &fLeft-Click to Fire / Hold Right-Click to Link`
    *   Movement allowed.
6.  **Action:** Perform the cast.

## 6. Implementation Notes
*   **Beam Visuals:** Use `play particle` with specific colors.
    *   Blessing: `villager happy` / `totem of undying`.
    *   Curse: `witch magic` / `soul fire flame`.
    *   Tether: High density `electric spark` line.
*   **Collision:** Custom raytrace loop (step 0.5) to detect entity bounding boxes.
*   **XP Safety:** If user runs out of XP during Tether, the link breaks immediately.
