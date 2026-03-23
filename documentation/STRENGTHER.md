# Class Documentation: Strengther

**Role:** Tank / Brawler / initiator
**Status:** Stable (V37)
**Key Mechanic:** Physics Manipulation & Combo Attacks

## 1. Passive Abilities
*   **Bankai Fall Immunity:** While the "Death Fist" (Bankai) sequence is active or pending a cost, fall damage is cancelled.
*   **Perfect Clutch:** If you slam into the ground from a great height (Air Slam), you can mitigate fall damage if timed correctly, rewarding you with a "Perfect Clutch" status.

## 2. Active Abilities

### A. Bankai (The Death Fist)
A high-risk, high-reward combo finisher.
*   **Trigger:** `Shift + Jump` (Repeat 3 times).
*   **Sequence:**
    1.  **Combo Charge:** Jump while sneaking 3 times. You will hear a beat (hat note) for 1/3 and 2/3.
    2.  **Ready State:** On the 3rd jump, you enter "Ready" state. Action Bar displays: `☠ STRIKE NOW! ☠`.
    3.  **Activation:** Hit an entity while in "Ready" state.
        *   **Cost:** 66% of Max Health. (Fails if HP is too low).
        *   **Effect:** Launches BOTH you and the target high into the air.
    4.  **The Finisher (Death Fist):** Hit the target again while mid-air.
        *   **Damage:** 1000 (Instant Kill).
        *   **Counters:**
            *   **Immortal Class:** If target is Immortal, the attack is shattered, damage is 0, and attacker is knocked back.
            *   **Bosses:** Deals 50 flat damage to Ender Dragon/Wither instead of instant kill.
*   **Cooldown:** 30 Seconds.

### B. Grab & Throw
Control the battlefield by repositioning enemies.
*   **Trigger:** `Shift + Right-Click` on an entity.
*   **Requirements:** Hands must be empty. Distance <= 3 blocks.
*   **Mechanic:** Mounts the target on top of your head.
*   **Duration:** Holds for up to 3 seconds or until thrown.
*   **Throw Trigger:** `Left-Click` while holding a target.
    *   **Normal Throw:** Launches target in direction of view.
    *   **Slam:** Look down (Pitch > 70) and Left-Click. Slams target into the ground, dealing damage and slowness to nearby entities.

### C. Air Slam
*   **Trigger:** `Shift + Left-Click` while in mid-air (no target held).
*   **Effect:** Halts current momentum and launches you upwards slightly before smashing down.
*   **Damage:** Deals AOE damage and knockback upon landing.

## 3. Constraints & Safety
*   **Creator Override:** If user has `*` permission, they must strictly have "strengther" enabled in their creator config.
*   **Suppression:** Disabled if `{ability.suppressed.%player%}` is true.
*   **Double-Tap Prevention:** Includes logic to prevent accidental double-hits during the Bankai launch phase.
