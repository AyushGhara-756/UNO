# UNO

### Overview


A lightweight UNO clone implemented in Java using Maven. Version 1 is a CLI that supports:
- Basic card play (number cards and some action cards)
- Playable against a computer opponent
- Card drawing, turn order, and basic UNO rules


### Features (Implemented)

- **Single player vs Computer**: Playable CLI version against a basic AI.
- **Core gameplay**: Number cards (0-9), and action cards (Skip, Reverse, Draw Two, Draw Four, Change Color, Change +4) implemented.
- **Deck mechanics**: Deck generation, card distribution, and turn order.
- **Basic AI**: Computer opponent can play valid cards and draw when needed.

### Features (Planned / In Progress)
- **Game modes**: No Mercy, UNO Flip.
- **LAN multiplayer**: Room creation and local network play.

### Tech Stack

- **Language**: Java (Maven)
- **UI**: Command line for v1; LibGDX for GUI*.
- **Build**: Maven for dependency management and packaging.


### Roadmap

- **Short term**
    - Polish UNO rules for single player vs computer.
    - Add and refine special cards: Skip, Reverse, Draw Two, Wild, Wild Draw Four, No Mercy, UNO Flip.
    - Improve AI behavior and input validation.
    - **Achieved and completed ✅**
- **Mid term**
    - Create a graphical user interface using LibGDX.
    - Add animations, card drag/drop, and local hotseat play.
    - Integrate settings and mode selection screens.
- **Long term**
    - Implement LAN room creation and synchronization for local multiplayer.
    - Add matchmaking, basic latency handling, and reconnection support.
    - Polish UX, add player profiles, and persistent settings.

**Next step is making the GUI.**


> ***Note:**  
> The project is in active development. The CLI version is playable, and more features are being added. Stay tuned for updates!
