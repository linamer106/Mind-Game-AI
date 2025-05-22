package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private Difficulty difficulty;
  private int numRounds;
  private String[] options;
  private int roundNumber = 1;
  private String input;
  private Colour humanColourChoice;
  private String namePlayer;
  private Strategy currentStrategy; // ok to be public?
  private DifficultyLevel gameLevel;

  private boolean gameStarted = false;
  private int playerPoints = 0;
  private int aiPoints = 0;
  private List<Colour> humanChoiceHistory = new ArrayList<>();
  private Colour lastHumanGuess = null;
  private Colour powerColour = null;
  private boolean strategyChangedThisRound = false;

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
    this.lastHumanGuess = null;
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

    if (roundNumber <= numRounds) {
      MessageCli.START_ROUND.printMessage(roundNumber, numRounds);
      List<Colour> inputColours = getHumanInput();

      if (inputColours == null) {
        return; // purpose of this?
      }

      humanColourChoice = inputColours.get(0);
      lastHumanGuess = inputColours.get(1);
      humanChoiceHistory.add(humanColourChoice);

      // updateStrategy();

      Colour aiChoice = currentStrategy.chooseColour();
      Colour aiGuess = currentStrategy.guessHumanColour();

      // print ai and player choices and guesses
      MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiChoice, aiGuess);
      MessageCli.PRINT_INFO_MOVE.printMessage(namePlayer, humanColourChoice, lastHumanGuess);

      if (roundNumber % 3 == 0) {
        MessageCli.PRINT_POWER_COLOUR.printMessage(
            Colour.getRandomColourForPowerColour()); // why can't do model.Colour?
      }
      roundNumber++;
      MessageCli.PRINT_INFO_MOVE.printMessage(options[0], inputColours.get(0), inputColours.get(1));
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

  // private void updateStrategy() {
  //   switch (difficulty) {
  //     case MEDIUM:
  //       if (roundNumber == 2) {
  //         currentStrategy = new AvoidLastStrategy(humanColourChoice);
  //         gameLevel.setStrategy(currentStrategy);
  //       }
  //       break;
  //     case HARD:
  //       if (roundNumber == 3) {
  //         currentStrategy = new LeastUsedStrategy(humanChoiceHistory);
  //         gameLevel.setStrategy(currentStrategy);
  //         strategyChangedThisRound = true;
  //       } else if (roundNumber >= 4 && !strategyChangedThisRound) {
  //         // Switch strategy if lost last round
  //         int lastRoundAiPoints = aiPoints - getPreviousAiPoints();
  //         if (lastRoundAiPoints == 0) {
  //           if (currentStrategy instanceof LeastUsedStrategy) {
  //             currentStrategy = new AvoidLastStrategy(humanColourChoice);
  //           } else {
  //             currentStrategy = new LeastUsedStrategy(humanChoiceHistory);
  //           }
  //           gameLevel.setStrategy(currentStrategy);
  //         }
  //       }
  //       strategyChangedThisRound = false;
  //       break;
  //   }
  // }

  private int getPreviousPlayerPoints() {
    return playerPoints - (roundNumber > 1 ? (playerPoints - getPreviousPlayerPoints()) : 0);
  } // confused here?????????????

  private int getPreviousAiPoints() {
    return aiPoints - (roundNumber > 1 ? (aiPoints - getPreviousAiPoints()) : 0);
  }

  public void showStats() {}
}
////      easyGame.gameStrategy(new leastsakdjasd)
