# Website Restructuring & Update Log

## Date: January 30, 2026

## Objective
Update website with new authentication/chat logic and reorganize file structure.

## Actions Taken
1.  **Code Extraction:**
    - Analyzed `ai_studio_code (10).html`.
    - Extracted CSS to `css/style.css`.
    - Extracted JavaScript (Auth, Chat, UI logic) to `js/main.js`.
    - Extracted HTML structure to `index.html`.

2.  **File Organization:**
    - Created `website/assets/` for images.
    - Created `website/css/` for stylesheets.
    - Created `website/js/` for scripts.
    - Moved images (`Youtube_logo.png`, `Discord_Logo.png`) to `assets/`.

3.  **Branding Update:**
    - Renamed `Slayer_MC_Logo.png` to `Ability_SMP_Logo.png` in `assets/`.
    - Updated references in `index.html` to point to the new logo.

4.  **Cleanup:**
    - Deleted `ai_studio_code (10).html`.
    - Removed inline scripts and styles from `index.html`.

## Current Structure
- `website/index.html`: Main entry point.
- `website/css/style.css`: Stylesheet.
- `website/js/main.js`: Main application logic (Auth, Chat, Firebase).
- `website/assets/`: Images and icons.
- `website/admin.html`: Admin panel (unchanged).
