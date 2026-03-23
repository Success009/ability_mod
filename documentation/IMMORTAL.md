# Class Documentation: Immortal

**Role:** Tank / Anchor
**Status:** Stable (V50)
**Key Mechanic:** Damage Reflection & Resurrection

## 1. Passive Abilities

### A. Eternal Form
*   **Fall Damage:** Completely immune. (Visuals: Red dust and heavy thud sound).
*   **Drowning:** Completely immune. (Refills air ticks constantly).

### B. Karma (One-Shot Protection)
Cancels lethal hits that would normally one-shot the user.
*   **Trigger:** Taking damage that would normally reduce HP to 0 or is greater than your max health.
*   **Condition:** Damage must be >= Max Health (Lethal Hit).
*   **Effect:**
    *   Cancels death.
    *   Sets HP to 40% (8 HP).
    *   **Reflect:** Knocks the attacker away with high velocity.
    *   **Visuals:** Totem of Undying particles and "DEATH REJECTED" title.
*   **Cooldown:** 30 Seconds.

## 2. Active Abilities

### A. Resurrection Ritual
A channeled ability to restore the user to full fighting condition.
*   **Trigger:** `Shift + Look Down (Pitch > 80) + Right-Click`.
*   **Channeling:**
    *   Duration: 1 Second (20 Ticks).
    *   Constraint: Must not move more than 0.5 blocks.
    *   Visuals: "Icosphere" particle cage.
*   **Effect (Success):**
    *   **Heal:** Restores 100% Health.
    *   **Feed:** Restores 100% Hunger.
    *   **Buffs:** Absorption II (20s) + Resistance I (5s).
    *   **Shockwave:** Explosive knockback to all entities within 6 blocks (6 damage).
*   **Cooldown:** 60 Seconds.
*   **Interrupt:** Moving cancels the ritual and applies a partial cooldown penalty.

## 3. Special Interactions
*   **Strengther Counter:** Hard-counters the Strengther's "Death Fist". If hit by it, the Immortal nullifies the damage and knocks the Strengther back with the title "IMMORTALITY PREVAILS".
