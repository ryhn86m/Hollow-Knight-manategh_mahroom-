# Hollow Knight: Very Simplified Edition 🗡🐛

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![LibGDX](https://img.shields.io/badge/LibGDX-E34F26?style=for-the-badge&logo=libgdx&logoColor=white)
![Sharif University](https://img.shields.io/badge/Sharif_University_of_Technology-0050A0?style=for-the-badge)

## 📌 About The Project
This project is the graphic assignment for the Advanced Programming course at Sharif University of Technology.

It is a scaled-down, simplified 2D side-scrolling Metroidvania game inspired by the acclaimed title *Hollow Knight*. Developed entirely in Java using the LibGDX framework, this project demonstrates core game development mechanics including state machines, collision detection, physics, entity AI, and UI management.

## ⚙️ Tech Stack
*   Language: Java
*   Framework: LibGDX
*   Build Tool: Gradle
*   Data Storage:  SQLite (for save/load states)

## ✨ Core Features
Despite being a compact version, the game implements several authentic mechanics from the original title:

*   **Fluid Platforming & Movement:**
    *   Includes walking, jumping, double jumping, dashing, and wall-sliding.
    *   Features the iconic "Pogo Jump" mechanic (downward attack on spikes/enemies to bounce).
*   **Combat System:**
    *   Melee Nail attacks with directional variations (up, down, forward).
    *   Spell casting system including *Vengeful Spirit* and *Howling Wraiths*.
*   **Health & Soul Mechanics:**
    *   Discrete "Mask" based health system.
    *   "Soul" vessel that fills by striking enemies, which can be consumed to "Focus" and heal or cast spells.
*   **Dynamic Enemies & Boss Fight:**
    *   Custom AI for ground, flying, and static enemies (e.g., Crystal Guardian, Mosquito, Husk Hornhead).
    *   A fully operational, multi-phase Boss Fight against the False Knight featuring distance-based decision making, anti-spam AI logic, camera shake, and a stun phase.
*   **Inventory & Charms:** A functional inventory system allowing players to equip "Charms" (e.g., Dashmaster, Quick Slash) that act as passive modifiers to the gameplay loop.
*   **Interactive Environments:** Playable maps like *Forgotten Crossroads* and *Crystal Peaks* featuring hazardous spikes, breakable walls, and secret rooms.
*   **Menus & Progress:** Complete UI flow including Main Menu, Pause Menu, Settings, an Achievement System, and Save/Load capabilities.
