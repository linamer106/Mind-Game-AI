package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private Difficulty difficulty;
  private int numRounds;
  private String[] options;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    this.difficulty = difficulty;
    this.numRounds = numRounds;
    this.options = options;
    String namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
  }

  public void play() {
    // String line = Utils.scanner.nextLine();
  }

  public void showStats() {}
}
