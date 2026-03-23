# Features Update Plan (COMPLETED)

## Status: All Objectives Achieved
**Date:** January 30, 2026

## 1. Website Updates
### A. Admin Page (`website/admin.html`)
*   **Authentication:**
    *   Implemented a secure "Lock Screen" modal.
    *   Added support for multiple Admin UIDs (`IHBGA0fWiLNBheAPdabJCQCEW2H3`, `QDdGoWJkJOYrbwreBDuFxPxZLbJ2`).
*   **Admin Chat:**
    *   Added a live chat panel to the Admin Dashboard.
    *   Admin messages are tagged as "Admin" and display in distinct colors.

### B. Main Website (`website/index.html`, `js/main.js`, `css/style.css`)
*   **Announcement Comments:**
    *   Added "Comments" button and collapsible section to announcement cards.
    *   Implemented `loadComments` (public read) and `postComment` (authenticated write).
    *   Validation ensures only linked users can comment.
*   **User Experience (UX):**
    *   Replaced native `alert()` dialogs with a custom "Login Required" modal.
    *   Prompts unauthenticated users to link their account when trying to Like or Comment.
    *   Added smooth fade-in animations for the comment section.

### C. Database Security (`website/database.rules.json`)
*   **Rules Updated:**
    *   Allowed `comments` write access for verified users only.
    *   Updated `chatLogs` validation to allow "Admin" messages from authorized UIDs.
    *   Updated `users` write permissions to strict Server/Admin only.

## 2. Plugin Architecture Refactor (`Ability SMP Firebase Bridge`)
The Java plugin was completely refactored to support the new website features.

### Key Components
*   **Authentication (`LinkManager.java`):**
    *   Generates 6-digit codes (`/link`).
    *   Listens to `Ability/security/link_requests` to verify codes submitted via the website.
    *   Automatically links Accounts (`Ability/security/users`) upon verification.
*   **Staff Badges (`ServerMonitorTask.java`):**
    *   Now pushes `{ "op": true }` status for online players.
    *   Enables the website to display Staff badges in the player list.
*   **Admin Chat (`WebListener.java`):**
    *   Detects messages from "Admin" source.
    *   Broadcasts them in-game using **Red Color** (`[Admin] Message`) vs standard Blue for web users.
*   **Leaderboards (`LeaderboardManager.java`):**
    *   Hooks into `PlayerQuitEvent`.
    *   Pushes Kill/Death statistics to `Ability/leaderboard/{uuid}` for future website display.

## 3. Future Considerations (Backlog)
*   **Ability Search & Filtering:** Filter abilities by type (Combat, Utility).
*   **Player Leaderboards UI:** Frontend display for the data now being collected.
*   **Staff Online Badge UI:** Visual indicator for OPs in the website player list.
*   **Interactive Crafting:** Recipes for custom items (e.g., Dragon Artifact).
*   **Event Timer:** Countdown for Boss Fights/Events.
*   **Global Kill Feed:** Sidebar on the Bridge showing recent PvP deaths.
