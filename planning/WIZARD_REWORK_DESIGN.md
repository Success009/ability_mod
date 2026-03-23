# DESIGN SPECIFICATION: WIZARD ABILITY REWORK (V2)

**Status:** Draft / Pending Implementation
**Focus:** Command-free interaction, Survival SMP balance, Ritual-style mechanics.

## 1. CORE MECHANICS
*   **The Tool:** Amethyst Shard (Named: `&dArcane Focus`).
*   **Interface:** Chat-based status updates (mimicking the `Enchanter` style).
*   **Targeting:** Distance-based Beam (Skillshot).
    *   Wizard shoots a particle beam at the target (Friend or Foe).
    *   Effective Range: 40 Blocks.

## 2. INTERACTION MODEL
*   **Left-Click (Air):** Cycle through Spells (Mend, Haste, Swiftness, Wither, Frost).
*   **Sneak + Left-Click:** Cycle Power Level (Tier 1, Tier 2, Tier 3).
*   **Right-Click (Hold):** Start Channeling at target.
    *   Must maintain line-of-sight and proximity to target.
    *   Walking speed reduced by 50% during channel.
*   **Release Right-Click:** Fires the spell if channel is complete.

## 3. POWER TIERS & COSTS
Consistently follows the "Enchanting Table" logic for costs:

| Tier | XP Cost | Item Cost | Channel Time | Effect Power |
| :--- | :--- | :--- | :--- | :--- |
| **Tier 1** | 2 Levels | None | Instant | Level I (Short) |
| **Tier 2** | 5 Levels | None | 1.0 Seconds | Level I (Long) |
| **Tier 3** | 10 Levels | 1 Lapis Lazuli | 2.5 Seconds | Level II (Long) |

## 4. SPELLBOOK (THE EFFECTS)
*   **Mend (Blessing):** Restores Health to target.
*   **Haste (Blessing):** Increases Attack and Mining Speed.
*   **Swiftness (Blessing):** Increases Movement Speed.
*   **Wither (Curse):** Damage over time to enemies.
*   **Frost (Curse):** Slowness and Weakness to enemies.

## 5. TECHNICAL IMPLEMENTATION (SKRIPT)
*   **Variables:**
    *   `{wizard.selected_spell.%player%}`: String (Spell Name).
    *   `{wizard.selected_tier.%player%}`: Integer (1-3).
    *   `{wizard.channeling.%player%}`: Boolean.
*   **Cooldowns:** Will use `ability_core.sk` signatures:
    *   `isOnCooldown(player, "wizard_cast", <timespan>)`
*   **Beam Logic:** Manual loop calculating points between Wizard and Target (manual vector math as per `GEMINI.md` constraints).

## 6. UI MOCKUP (Chat Display)
When cycling, the player sees:
`&5&l[WIZARD] &dSpell: &f&lHEAL &7| &dPower: &bTier 3`
`&7Cost: &a10 XP &7+ &91 Lapis`
`&7Effect: &fHeals 6 Hearts (Radius 3)`
