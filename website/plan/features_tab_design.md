# Website Expansion: "Server Features" Tab

## 1. Objective
Create a dedicated space on the website to showcase non-ability server mechanics (e.g., The Rift, Dragon Boss, Teams). This ensures players know about the unique systems available to them.

## 2. Design Concept
A new tab named **"Features"** (or "Guide") placed between "Sub-Abilities" and "Rules".

### Visual Style
*   **Layout:** Responsive Grid (Same as Abilities).
*   **Card Style:** "Interactive Card" (Glassmorphism).
*   **New Element:** A high-visibility "Command Badge" for features that are triggered by commands (e.g., `/rift`).

### Mockup Data (Examples)
1.  **The Rift**
    *   **Icon:** `<i class="fa-solid fa-cube"></i>`
    *   **Title:** Creative Rift
    *   **Command:** `/rift`
    *   **Description:** "A pocket dimension where you can enter Creative Mode to test ability combinations. Inventory is safely stored."
    *   **Visual:** Maybe a purple/obsidian color theme.

2.  **Dragon Boss**
    *   **Icon:** `<i class="fa-solid fa-dragon"></i>`
    *   **Title:** Apex Dragon
    *   **Description:** "A heavily modified Ender Dragon fight with 3 distinct phases, custom attacks (Beam, Freeze), and unique loot."
    *   **Visual:** Deep purple/black.

3.  **Duo Teams**
    *   **Icon:** `<i class="fa-solid fa-handshake"></i>`
    *   **Title:** Team System
    *   **Command:** `/team`
    *   **Description:** "Link souls with a partner. Disables friendly fire and allows for shared victory conditions."

## 3. Implementation Plan

### A. Database Structure (Firebase)
New node: `Ability/features`
```json
{
  "feature_id": {
    "title": "The Rift",
    "description": "...",
    "command": "/rift",
    "icon": "fa-cube",
    "color": "#9b59b6",
    "order": 1
  }
}
```

### B. Website (`index.html` & `main.js`)
1.  **HTML:** Add `<div class="tab" data-tab="features">Features</div>` and the corresponding content section.
2.  **JS:** Add a renderer `renderFeature(data)` that handles the optional "Command" field display.

### C. Admin Panel (`admin.html`)
1.  **Sidebar:** Add "Features" link.
2.  **CRUD:** Add Create/Edit/Delete logic for Features.
3.  **Form:** Inputs for Title, Description, Command (Optional), Icon Class (FontAwesome), and Color.

## 4. Why this is "Engaging"
*   **Discoverability:** Players often miss commands like `/rift`. Putting it front-and-center with a shiny card increases usage.
*   **Clarity:** Separates "Tools" (Features) from "Powers" (Abilities), reducing confusion.
*   **Dynamic:** You can add "Meteor Event" or "KOTH" later without touching code, just using the Admin Panel.
