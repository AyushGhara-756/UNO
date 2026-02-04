# UNO

A command-line prototype of UNO with some custom twists. This repository contains a minimal playable version (v1) focused on core card mechanics and a clear roadmap toward GUI and LAN multiplayer.

### Overview
A lightweight UNO clone implemented in Java using Maven. Version 1 is a CLI prototype that supports basic card play and a simple computer opponent. Future plans include additional game modes, a graphical UI using LibGDX, and LAN-based local multiplayer.

### Features
- **Local multiplayer**: Room creation over LAN*.
- **Game modes**: No Mercy and UNO Flip*; basic mode implemented in CLI.
- **Core gameplay**: Number cards and basic play flow implemented; AI opponent available in prototype.

### Tech Stack
- **Language**: Java (Maven)
- **UI**: Command line for v1; LibGDX for GUI*.
- **Build**: Maven for dependency management and packaging.

### Roadmap
- **Short term**
    - Implement full UNO rules for single player vs computer.
    - Add special cards and modes: Skip, Reverse, Draw Two, Wild, Wild Draw Four, No Mercy, UNO Flip.
    - Improve AI behavior and input validation.
- **Mid term**
    - Create a graphical user interface using LibGDX.
    - Add animations, card drag/drop, and local hotseat play.
    - Integrate settings and mode selection screens.
- **Long term**
    - Implement LAN room creation and synchronization for local multiplayer.
    - Add matchmaking, basic latency handling, and reconnection support.
    - Polish UX, add player profiles, and persistent settings.


> ***Note:**  
> The project is at its initial phase of development.
> The features mentioned will be added soon in the app.
> Please stay tuned.