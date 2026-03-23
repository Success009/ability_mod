# WIZARD REWORK PLAN: The Arcane Arcanum

## 1. Goal
Rework the Wizard ability to remove chat commands (`/bless`, `/curse`) and replace them with a fluid, "Locator-style" chat menu for spell selection. The ability will transition from "Target & Command" to "Select & Cast", increasing the skill ceiling and combat fluidity.

## 2. Core Mechanics

### A. The Tool: **Arcane Wand**
*   **Item:** `Amethyst Shard` (or `Blaze Rod` if preferred for visibility).
*   **Name:** `&dArcane Wand`.
*   **Interaction:**
    *   **Right-Click (Air):** Opens/Closes the **Spell Grimoire** (Chat Menu).
    *   **Sneak (In Menu):** Cycles through options (Blessings / Curses -> Specific Spells).
    *   **Swap Hand (F) (In Menu):** Confirms selection.
    *   **Drop (Q) (In Menu):** Go Back / Close Menu.
    *   **Left-Click (Air/Entity) (When Spell Selected):** **CASTS** the selected spell.

### B. The Interface (Grimoire)
Uses the same reliable "Session" system as the Locator.
*   **Root Menu:**
    *   `➜ &bBlessings` (Positive effects for allies)
    *   `   &cCurses` (Negative effects for enemies)
*   **Blessings Menu:**
    *   `➜ &bAlacrity` (Movement/Speed)
    *   `   &aVitality` (Healing/Regen)
    *   `   &eAegis` (Protection)
*   **Curses Menu:**
    *   `➜ &4Decay` (Wither/Damage)
    *   `   &bFrost` (Slowness/Binding)
    *   `   &8Void` (Blindness/Darkness)

### C. Casting Mechanic (The Buff/Nerf)
*   **Old System:** Auto-target (Command). Guaranteed hit. Slow to type.
*   **New System:** **Ray-Trace Beam (Skillshot)**.
    *   **Range:** 20 Blocks (Standard), 30 Blocks (Sniper spells).
    *   **Visuals:** Colored Particle Beam (Green for Blessings, Purple for Curses).
    *   **Hitbox:** 0.5 block radius around the beam.
    *   **Feedback:** Sound cue on hit + Action bar message.

## 3. Spell List & Balance

*Global Cooldown between casts: 2 Seconds.*

### Blessings (Green/Gold Particles)
| Spell | Cost (XP) | Effect | Duration |
| :--- | :--- | :--- | :--- |
| **Alacrity** | 3 Lvl | Speed III + Haste II | 15s |
| **Vitality** | 5 Lvl | Instant Heal II + Regen II | 5s |
| **Aegis** | 4 Lvl | Resistance II + Fire Res | 20s |

### Curses (Purple/Black Particles)
| Spell | Cost (XP) | Effect | Duration |
| :--- | :--- | :--- | :--- |
| **Decay** | 5 Lvl | Wither II + Hunger III | 8s |
| **Frost** | 4 Lvl | Slowness IV + Mining Fatigue III | 6s |
| **Void** | 4 Lvl | Darkness + Weakness II | 10s |

## 4. Implementation Steps

### Phase 1: The Framework (`scripts/ability/mainWizard.sk`)
1.  **Port the Session Logic:** Copy the robust State/Index/Menu logic from `mainLocator.sk`.
2.  **Clean up:** Remove all old `/bless` and `/curse` command triggers.
3.  **Variable Structure:**
    *   `{wizard.state.%player%}` (ROOT, TYPE, SPELL_READY)
    *   `{wizard.selected_spell.%player%}` (e.g., "Decay")

### Phase 2: The Spells
1.  **Ray-Tracing Function:** Create a helper to detect entity in line-of-sight.
2.  **Particle Effects:** Use `ppo` or standard Skript particles for the "Beam".
3.  **Application:** Apply potion effects dynamically based on `{wizard.selected_spell.%player%}`.

### Phase 3: Polish
1.  **Sounds:** "Chime" for cycle, "Anvil Land" for confirm, "Amethyst Chime" for cast.
2.  **Cooldowns:** Add visible cooldown feedback in Action Bar.
3.  **Safety:** Prevent casting on self (for Curses) or enemies (for Blessings? - Optional, maybe "Accidental Betrayal" is funny).

## 5. User Decision Points
*   **Item:** Confirm `Amethyst Shard` is acceptable.
*   **Costs:** Are these XP costs fair? (Level 5 is a decent cost for a heal in combat).
