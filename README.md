# Mind Game (Guess Colours)

A Java-based interactive Mind Game where a human player competes against an AI named HAL-9000 in a colour-guessing challenge. The game combines chance, strategy, and psychology, testing your ability to anticipate your opponent’s choices.

### **🎮 Game Overview**

Each round:

Both the player and AI select a colour.

Each attempts to guess the opponent’s colour.

Points are awarded:

+1 for a correct guess.

+2 bonus points if the guess matches the Power Colour on rounds divisible by 3.

The game ends after a set number of rounds; the winner is the player with the highest score.

### **Colours**

Available colours: RED, GREEN, BLUE, YELLOW (also accepts initials: R, G, B, Y).

### **AI Difficulty Levels**

EASY: Random guesses throughout.

MEDIUM: Starts random, then switches to “Avoid Last” strategy.

HARD: Starts random, then dynamically switches between “Least Used” and “Avoid Last” based on performance.

### **🛠️ Object-Oriented Design**

Strategy Pattern: Implements AI strategies (Random, AvoidLast, LeastUsed) with runtime switching via setStrategy().

Factory Pattern: Creates AI instances based on difficulty with a static factory method.

Includes interfaces and abstract classes to support flexible, extensible design.

### **💻 How to Run**

Use the Maven wrapper:

 Unix/Mac
./mvnw clean compile exec:java@run

 Windows
.\mvnw.cmd clean compile exec:java@run

### **Commands**

NEW_GAME <DIFFICULTY_LEVEL> <NUMBER_OF_ROUNDS> — Start a new game.

PLAY — Play the next round.

SHOW_STATS — Display current scores.

HELP — Show all commands.

EXIT — Exit the game.

Commands are case-insensitive and accept underscores or hyphens interchangeably.

### **Example**
281-mind-game> new-game HARD 4
What is your name?: Lina
Welcome, Lina!
281-mind-game> play
Starting round 1 of 4:
Enter two colours (RED, GREEN, BLUE, YELLOW): R B
Player HAL-9000: chose BLUE and guessed RED
Player Lina: chose RED and guessed BLUE
Player Lina earned 0 point(s) this round.
Player HAL-9000 earned 1 point(s) this round.

### **✅ Features**
AI adapts based on chosen difficulty.

Dynamic scoring with Power Colour bonus.

Input validation and helpful error messages.

Object-oriented design with Strategy and Factory patterns.

Interactive command-line interface with clear prompts.
