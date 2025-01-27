# Go Boom

Go Boom is a multiplayer card game implemented in Java. This game follows the classic *Go Boom* rules with some modifications and supports both console and GUI modes.

## 🎥 Video Demo
- [Part 1](https://youtu.be/GyUQuptXnSE)
- [Part 2](https://youtu.be/ce_maPx2P1I)

## 🎮 Features
- Fully randomized **52-card deck** for each new game.
- **Turn-based gameplay** for four players.
- **Automatic card dealing** (7 cards per player).
- **Lead card system** to determine the first player based on predefined rules:
  - `A, 5, 9, K` → Player 1
  - `2, 6, 10` → Player 2
  - `3, 7, J` → Player 3
  - `4, 8, Q` → Player 4
- Players must **follow suit or match rank** of the lead card.
- **Winning conditions**: The highest-rank card in the same suit wins the trick.
- **Turn management**: The winner of a trick plays the next lead card.

## 🛠️ Installation
### **Requirements**
- Java Development Kit (**JDK 8 or later**)
- JavaFX (for GUI mode)

## 🎲 How to Play
1. **Start a New Game**: Launch the game and follow on-screen instructions.
2. **Follow Suit/Rank**: Players must play a card that follows the suit or matches the rank of the lead card.
3. **Draw Cards**: If no valid moves exist, draw from the deck until a valid card is obtained.
4. **Win the Trick**: The highest card in the lead suit wins the trick and plays the next lead card.
5. **Scoring**: The game tracks player scores across multiple rounds.
6. **Save & Load**: Players can save their progress and resume later.
7. **Exit/Reset**: Players can quit the game or reset it for a fresh start.

## 📜 Commands (Console Mode)
- `s` → Start a new game.
- `x` → Exit the game.
- `d` → Draw cards until a playable card is obtained (if deck exists).
- `card_name` → Play a valid card (e.g., `h7` for 7 of Hearts).
- `save` → Save game state.
- `load` → Load saved game state.

## 📌 Progress Log
- [Part 1](PART1.md)
- [Part 2](PART2.md)

---
🚀 *Enjoy the game! Feel free to contribute or modify it.*

