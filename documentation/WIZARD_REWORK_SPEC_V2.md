# WIZARD REWORK SPECIFICATION (V2)

**Status:** Proposed Design
**Goal:** Create a high-skill, ritual-based magic system where power and range come at a cost of mobility and XP.

## 1. Activation & Safety
*   **Trigger:** `Look Down (Pitch > 80)` + `Sneak` + `Swap Item (F)`.
    *   *Why?* Prevents accidental opening during combat.
*   **Movement Constraint:**
    *   The Wizard **MUST** remain stationary while selecting a spell.
    *   **Penalty:** If the Wizard moves more than **0.5 blocks** from the activation point, the menu closes, chat clears, and the ritual is cancelled ("Focus Lost").
*   **Tool:** `Amethyst Shard` (Renamed: &dArcane Focus).

## 2. The Tier System (Scaling)
Instead of flat costs, every spell has 3 Tiers of power. Higher tiers grant **Range** and **Potency** but cost significantly more XP.

| Tier | XP Cost | Range | Description |
| :--- | :--- | :--- | :--- |
| **Tier I (Apprentice)** | 5 Levels | 15 Blocks | Quick, close-range support. Weak effects. |
| **Tier II (Adept)** | 15 Levels | 30 Blocks | Standard combat spells. Moderate effects. |
| **Tier III (Archmage)** | 30 Levels | 50 Blocks | Ultimate abilities. Map-changing power. |

## 3. Spell List (Grimoire)

### A. Blessings (Buffs - Green Beam)
*Targeting:* Hit an ally to apply. If you hit an enemy, it does nothing (or small knockback).

**1. Alacrity (Speed & Haste)**
*   **Tier I:** Speed II (10s)
*   **Tier II:** Speed III (15s) + Haste II (15s)
*   **Tier III:** Speed IV (20s) + Haste III (20s) + Jump Boost II (20s)

**2. Vitality (Healing)**
*   **Tier I:** Regen II (5s)
*   **Tier II:** Instant Health I + Regen III (5s)
*   **Tier III:** Instant Health II + Regen IV (8s) + Absorption II (2m)

**3. Aegis (Protection)**
*   **Tier I:** Resistance I (10s)
*   **Tier II:** Resistance II (15s) + Fire Res (30s)
*   **Tier III:** Resistance III (10s) + Fire Res (1m) + Knockback Resist (1m)

### B. Curses (Debuffs - Purple Beam)
*Targeting:* Hit an enemy to apply.

**1. Decay (Wither)**
*   **Tier I:** Wither II (5s)
*   **Tier II:** Wither III (8s) + Hunger III (10s)
*   **Tier III:** Wither IV (10s) + Hunger V (20s) + Poison II (5s)

**2. Frost (Control)**
*   **Tier I:** Slowness II (5s)
*   **Tier II:** Slowness IV (5s) + Mining Fatigue II (10s)
*   **Tier III:** **FREEZE** (Ticks Frozen 140) + Slowness V (8s) + Mining Fatigue III (15s)

**3. Void (Blindness)**
*   **Tier I:** Darkness (5s)
*   **Tier II:** Blindness (5s) + Darkness (10s)
*   **Tier III:** Blindness (10s) + Darkness (20s) + Glowing (20s - Target Revealed)

## 4. Interaction Flow
1.  **Initiate:** Look Down + Sneak + F.
2.  **Menu 1 (Category):**
    *   `> Blessings`
    *   `   Curses`
    *   *(Sneak to cycle, F to confirm)*
3.  **Menu 2 (Spell):**
    *   `> Alacrity`
    *   `   Vitality`
    *   `   Aegis`
4.  **Menu 3 (Power Level):**
    *   `> Tier I (5 XP)`
    *   `   Tier II (15 XP)`
    *   `   Tier III (30 XP)`
5.  **Charged State:**
    *   Chat clears.
    *   ActionBar: `&d&l[ SPELL CHARGED: ALACRITY III ] &fLeft-Click to Fire`
    *   *Movement is now allowed.* (You can run and shoot, but if you swap items or wait too long (10s), the charge dissipates).
6.  **Cast:**
    *   **Left-Click:** Fires a particle beam.
    *   **Hit:** Effect applied, XP deducted.
    *   **Miss:** XP **NOT** deducted (or partial penalty? *Proposal: 50% XP penalty on miss to prevent spam*).

## 5. Visuals & Audio
*   **Selection:** Standard Locator "Click" sounds.
*   **Charged:** Player emits subtle enchantment table particles.
*   **Beam:**
    *   **Blessing:** `villager happy` (Green Stars) or `electric spark`.
    *   **Curse:** `witch magic` (Purple bubbles) or `dragon breath`.
*   **Impact:** Large particle burst at impact point.

## 6. Questions for Approval
1.  **Miss Penalty:** Should missing a shot cost XP? (Suggested: Lose 20-50% of the cost to prevent waste).
2.  **Tier Costs:** Are 5/15/30 Levels appropriate? (Level 30 is a full enchanting table level, making Tier III very "ultimate").
3.  **Charged Movement:** Can the player move *after* selecting the spell but *before* firing? (Suggested: Yes, otherwise aiming is impossible).
