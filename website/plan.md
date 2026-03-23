# Website Fix Plan

## Objective
Fix the issue where the "Feature Grid" (and potentially other content) is not loading on the Ability SMP website.

## Analysis
The `features-grid` contains static HTML elements with the class `animate-on-scroll`.
- The CSS sets `opacity: 0` for `.animate-on-scroll`.
- A JavaScript `IntersectionObserver` is responsible for adding the `is-visible` class (setting `opacity: 1`).
- The JavaScript currently initializes Firebase at the top level.
- If Firebase fails to load (e.g., script error, network issue, or invalid config), the script execution stops.
- As a result, the `DOMContentLoaded` event listener that initializes the `IntersectionObserver` is never registered.
- This leaves the static "Feature Grid" permanently invisible (`opacity: 0`).

## Proposed Solution
1.  **Restructure JavaScript:**
    - Move `IntersectionObserver` and UI logic (tabs, modals, animations) *before* Firebase initialization.
    - Wrap Firebase initialization and dependent logic (e.g., `loadAndRenderData`) in a `try...catch` block.
    - Ensure `DOMContentLoaded` listener is registered regardless of Firebase status.
    - Check if `firebase` object exists before using it.

2.  **Safety Fallback:**
    - If JavaScript is disabled or fails completely, static content should ideally be visible. However, since we are relying on JS for the animation, ensuring the JS is robust is the primary fix.

3.  **Security Rules:**
    - Review `sec rule.txt` and rename it to `firebase.json` or `firestore.rules` (or `database.rules.json`) if appropriate, though the user said "rename that file to correctly fit what you want". Since it's Realtime Database, `database.rules.json` is standard.

## Steps
1.  Read `website/sec rule.txt`.
2.  Modify `website/index.html` to implement the robust JS structure.
3.  Rename `website/sec rule.txt` to `website/database.rules.json` (standard for Firebase Realtime DB).
