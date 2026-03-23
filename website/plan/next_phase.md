# Website Expansion Plan: Phase 2

## Status: Planned
**Focus:** Data Visualization, User Convenience, and Content Discoverability.

## 1. Leaderboards Page
**Objective:** Display the PVP statistics (Kills/Deaths) collected by the Server Bridge.
*   **Backend:** Already collecting data to `Ability/leaderboard/{uuid}`.
*   **Frontend:**
    *   New Tab: "Leaderboards".
    *   Top 10 Kills, Top 10 Deaths (or K/D Ratio).
    *   Visuals: Player Heads (Crafatar), Rank #1/2/3 medals.

## 2. Ability Search & Filtering
**Objective:** Help players find abilities quickly as the roster grows.
*   **Frontend:**
    *   Search Bar above the Abilities Grid.
    *   Filter Buttons: "All", "Combat", "Utility", "Passive", "Support".
    *   Real-time filtering (no page reload).

## 3. Event Countdown Timer
**Objective:** Build hype for Boss Fights and Server Events.
*   **Admin Panel:**
    *   New field in "Announcements" or a dedicated "Next Event" widget.
    *   Inputs: Title, Date/Time.
*   **Homepage:**
    *   Prominent Countdown Clock at the top of the "Home" section.
    *   Visuals: "Next Event: Dragon Fight in 02:14:55".

## 4. Polishing
*   **Staff Badges:** explicitly add a [STAFF] tag or Shield Icon next to OPs in the player list (currently just red text).
*   **Mobile Optimizations:** Ensure the new Leaderboard table is scrollable on mobile.
