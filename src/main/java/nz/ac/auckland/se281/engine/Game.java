package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

public class Game {
  public static final String AI_NAME = "HAL-9000";
  private int numRounds;
  private String[] options;
  private int roundNumber = 1;
  private Colour humanChoice;
  private Colour humanGuess;
  private String namePlayer;
  private DifficultyLevel gameLevel;
  private Difficulty difficulty;

  private boolean gameStarted = false;
  private int playerPoints = 0;
  private int aiPoints = 0;
  private List<Colour> humanChoiceHistory = new ArrayList<>();
  private Colour powerColour = null;

  private List<Integer> playerPointsPerRound = new ArrayList<>();
  private List<Integer> aiPointsPerRound = new ArrayList<>();

  // private GamesStats stats = new GamesStats();
  // private Ai ai = new Ai(stats); // private?

  public void newGame(
      Difficulty difficulty, int numRounds, String[] options) { // what is in string options?
    this.numRounds = numRounds;
    this.options = options;
    this.namePlayer = options[0];
    this.roundNumber = 1;
    this.playerPoints = 0;
    this.aiPoints = 0;
    this.humanChoiceHistory.clear();
    this.powerColour = null;
    this.gameStarted = true;
    this.difficulty = difficulty;
    this.gameLevel = null;

    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
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

    if (gameLevel == null) {
      Colour except =
          humanChoiceHistory.isEmpty()
              ? null
              : humanChoiceHistory.get(humanChoiceHistory.size() - 1);
      gameLevel =
          GameFactory.chooseGameDifficulty(
              difficulty, except, roundNumber, humanChoiceHistory, getAiWonLastRound());
    }
    Colour except =
        humanChoiceHistory.isEmpty() ? null : humanChoiceHistory.get(humanChoiceHistory.size() - 1);

    if (gameLevel instanceof HardGame) {
      ((HardGame) gameLevel).setAiWonLastRound(getAiWonLastRound());
      ((HardGame) gameLevel).setRoundNumber(roundNumber);
      ((HardGame) gameLevel).setHumanChoiceHistory(humanChoiceHistory);
      // ((HardGame) gameLevel).setExcept(except);
      // how to make this section neater? check rewind
    }

    if (gameLevel instanceof MediumGame) {
      ((MediumGame) gameLevel).setExcept(except);
      ((MediumGame) gameLevel).setRoundNumber(roundNumber);
    }

    // invoke game level method which checks strategy based on roundnumber and send back reuslt
    Colour aiChoice = gameLevel.aiMethodForChoosingColour();
    Colour aiGuess = gameLevel.aiMethodForGuessingHumanColour();

    // print ai and player choices and guesses
    MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiChoice, aiGuess);
    MessageCli.PRINT_INFO_MOVE.printMessage(namePlayer, humanChoice, humanGuess);

    if (roundNumber % 3 == 0) {
      powerColour = Colour.getRandomColourForPowerColour();
      MessageCli.PRINT_POWER_COLOUR.printMessage(powerColour); // why can't do model.Colour?
    } else {
      powerColour = null;
    }

    calculatePoints(humanChoice, humanGuess, aiChoice, aiGuess);

    int playerRoundPoints = playerPointsPerRound.get(playerPointsPerRound.size() - 1);
    int aiRoundPoints = aiPointsPerRound.get(aiPointsPerRound.size() - 1);

    MessageCli.PRINT_OUTCOME_ROUND.printMessage(namePlayer, playerRoundPoints);
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(AI_NAME, aiRoundPoints);

    // Update for next round
    humanChoiceHistory.add(humanChoice);

    roundNumber++; // why did bringing this above solve so many test cases?

    // Check if game ended
    if (roundNumber > numRounds) { // why again can i combine?
      endGame();
    }
  }

  public boolean getAiWonLastRound() {
    if (aiPointsPerRound.isEmpty()) {
      return false;
    }

    int lastRoundPoints = aiPointsPerRound.get(aiPointsPerRound.size() - 1);

    if (lastRoundPoints == 0) {
      return false;
    }
    return true;
  }

  private void endGame() {
    // when the game ends, show the final stats
    showStats();
    MessageCli.PRINT_END_GAME.printMessage();
    // display winner based on who has more points or tie
    if (playerPoints > aiPoints) {
      MessageCli.PRINT_WINNER_GAME.printMessage(namePlayer);
    } else if (aiPoints > playerPoints) {
      MessageCli.PRINT_WINNER_GAME.printMessage(AI_NAME);
    } else {
      MessageCli.PRINT_TIE_GAME.printMessage();
    }
    // reset game state
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

    // why isnt this working powerColour = null; // just added thissssss
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
