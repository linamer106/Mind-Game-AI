package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;

public class Game {
  public static String AI_NAME = "HAL-9000";
  private Difficulty difficulty;
  private int numRounds;
  private String[] options;
  private String name;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    this.difficulty = difficulty;
    this.numRounds = numRounds;
    this.options = options;
  }

  public void play() {
    // get name from user
    String line = Utils.scanner.nextLine();
    name = line; // need to define line varibale on top?
    MessageCli.WELCOME_PLAYER.printMessage(name);
  }

  public void showStats() {}
}
