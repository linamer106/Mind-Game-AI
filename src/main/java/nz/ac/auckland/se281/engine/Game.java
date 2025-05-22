package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

public class Game {
  public static final String AI_NAME = "HAL-9000";
  private Difficulty difficulty;
  private int numRounds;
  private String[] options;
  private int roundNumber = 1;
  private Colour humanChoice;
  private Colour humanGuess;
  private String namePlayer;
  private Strategy currentStrategy; // ok to be public?
  private DifficultyLevel gameLevel;

  private boolean gameStarted = false;
  private int playerPoints = 0;
  private int aiPoints = 0;
  private List<Colour> humanChoiceHistory = new ArrayList<>();
  private Colour lastHumanChoice = null;
  private Colour powerColour = null;
  private boolean strategyWonLastRound = false;

  private List<Integer> playerPointsPerRound = new ArrayList<>();
  private List<Integer> aiPointsPerRound = new ArrayList<>();

  public Game() {}

  public void newGame(
      Difficulty difficulty, int numRounds, String[] options) { // what is in string options?
    this.difficulty = difficulty;
    this.numRounds = numRounds;
    this.options = options;
    this.namePlayer = options[0];
    this.roundNumber = 1;
    this.playerPoints = 0;
    this.aiPoints = 0;
    this.humanChoiceHistory.clear();
    this.lastHumanChoice = null;
    this.powerColour = null;
    this.gameStarted = true;

    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
    gameLevel = GameFactory.chooseGameDifficulty(difficulty);
    currentStrategy = new RandomStrategy(); // ok logic? or must keep original one ai?
    gameLevel.setStrategy(currentStrategy);
  }

  public void play() {
    if (!gameStarted) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    if (roundNumber > numRounds) {
      endGame();
      return;
    }

    MessageCli.START_ROUND.printMessage(roundNumber, numRounds);
    List<Colour> inputColours = getHumanInput();

    humanChoice = inputColours.get(0);
    humanGuess = inputColours.get(1);

    updateStrategy();
    humanChoiceHistory.add(humanChoice); // or needs to be above that line above?

    Colour aiChoice = currentStrategy.chooseColour();
    Colour aiGuess = currentStrategy.guessHumanColour();

    // print ai and player choices and guesses
    MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiChoice, aiGuess);
    MessageCli.PRINT_INFO_MOVE.printMessage(namePlayer, humanChoice, humanGuess);

    if (roundNumber % 3 == 0) {
      powerColour = Colour.getRandomColourForPowerColour();
      MessageCli.PRINT_POWER_COLOUR.printMessage(powerColour); // why can't do model.Colour?
    }

    calculatePoints(humanChoice, humanGuess, aiChoice, aiGuess);

    int playerRoundPoints = playerPointsPerRound.get(playerPointsPerRound.size() - 1);
    int aiRoundPoints = aiPointsPerRound.get(aiPointsPerRound.size() - 1);

    MessageCli.PRINT_OUTCOME_ROUND.printMessage(namePlayer, playerRoundPoints);
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(AI_NAME, aiRoundPoints);

    // Update for next round
    lastHumanChoice = humanChoice;
    roundNumber++;

    // Check if game ended
    if (roundNumber > numRounds) { // why again can i combine?
      endGame();
    }
  }

  private void endGame() {
    showStats();
    MessageCli.PRINT_END_GAME.printMessage();

    if (playerPoints > aiPoints) {
      MessageCli.PRINT_WINNER_GAME.printMessage(namePlayer);
    } else if (aiPoints > playerPoints) {
      MessageCli.PRINT_WINNER_GAME.printMessage(AI_NAME);
    } else {
      MessageCli.PRINT_TIE_GAME.printMessage();
    }

    gameStarted = false;
  }

  private List<Colour> getHumanInput() {
    while (true) {
      MessageCli.ASK_HUMAN_INPUT.printMessage();
      String input = Utils.scanner.nextLine();
      String[] parts = input.trim().split(" ");

      if (parts.length != 2) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }

      Colour colour1 = Colour.fromInput(parts[0]);
      Colour colour2 = Colour.fromInput(parts[1]);

      if (colour1 == null || colour2 == null) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }

      return List.of(colour1, colour2);
      // could List.of() here produce me issues later due to nullpointerexception and mod
      // stuff?
    }
  }

  private void updateStrategy() {
    switch (difficulty) {
      case EASY:
        return; // no strategy change
      case MEDIUM:
        if (roundNumber
            >= 2) { // so the humanChoice chnages every time, corect implementation right? not just
          // ==2
          currentStrategy =
              new AvoidLastStrategy(humanChoiceHistory.get(humanChoiceHistory.size() - 1));
          gameLevel.setStrategy(currentStrategy);
        }
        break;
      case HARD:
        if (roundNumber == 3) {
          currentStrategy = new LeastUsedStrategy(humanChoiceHistory);
          gameLevel.setStrategy(currentStrategy);
        } else if (roundNumber >= 4) {
          // Check if the strategy won the last round
          if (aiPointsPerRound.get(aiPointsPerRound.size() - 1) >= 1) {
            strategyWonLastRound = true;
          }

          if (currentStrategy instanceof LeastUsedStrategy) {
            if (!strategyWonLastRound) {
              currentStrategy = new AvoidLastStrategy(lastHumanChoice);
            } else {
              break;
            }
          } else if (currentStrategy instanceof AvoidLastStrategy) {
            if (!strategyWonLastRound) {
              currentStrategy = new LeastUsedStrategy(humanChoiceHistory);
            } else {
              break;
            }
          }
          gameLevel.setStrategy(currentStrategy);
        }
        break;
    }
  }

  private int getPreviousPlayerPoints() {
    if (roundNumber <= 1) {
      return 0; // No previous rounds before round 1
    }
    // Sum all points except current round
    int sum = 0;
    for (int i = 0; i < playerPointsPerRound.size() - 1; i++) {
      sum += playerPointsPerRound.get(i);
    }
    return sum;
  }

  private int getPreviousAiPoints() {
    if (roundNumber <= 1) {
      return 0;
    }
    int sum = 0;
    for (int i = 0; i < aiPointsPerRound.size() - 1; i++) {
      sum += aiPointsPerRound.get(i);
    }
    return sum;
  }

  private void calculatePoints(
      Colour humanChoice, Colour humanGuess, Colour aiChoice, Colour aiGuess) {
    // Calculate player points
    int playerRoundPoints = 0;
    if (humanGuess == aiChoice) {
      playerRoundPoints = 1;
      if (powerColour != null && humanGuess == powerColour) {
        playerRoundPoints += 2;
      }
    }

    // Calculate AI points
    int aiRoundPoints = 0;
    if (aiGuess == humanChoice) {
      aiRoundPoints = 1;
      if (powerColour != null && aiGuess == powerColour) {
        aiRoundPoints += 2;
      }
    }

    // Store the points for this round
    playerPointsPerRound.add(playerRoundPoints);
    aiPointsPerRound.add(aiRoundPoints);

    // Update total points
    playerPoints += playerRoundPoints;
    aiPoints += aiRoundPoints;
  }

  public void showStats() {
    if (!gameStarted) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }
    MessageCli.PRINT_PLAYER_POINTS.printMessage(namePlayer, playerPoints);
    MessageCli.PRINT_PLAYER_POINTS.printMessage(AI_NAME, aiPoints);
  }
}
////      easyGame.gameStrategy(new leastsakdjasd)
