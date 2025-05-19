package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private Difficulty difficulty;
  private int numRounds;
  private String[] options;
  private int roundNumber = 1;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    this.difficulty = difficulty;
    this.numRounds = numRounds;
    this.options = options;
    String namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
  }

  public void play() {
    if (roundNumber <= numRounds) {
      MessageCli.START_ROUND.printMessage(roundNumber, numRounds);
      roundNumber++;
      MessageCli.ASK_HUMAN_INPUT.printMessage();
    } else {
      // show who won
    }
    // String line = Utils.scanner.nextLine();
  }

  public void showStats() {}
}
