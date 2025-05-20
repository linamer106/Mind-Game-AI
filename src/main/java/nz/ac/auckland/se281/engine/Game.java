package nz.ac.auckland.se281.engine;

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
  private Colour inputColour;

  public Game() {}

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    this.difficulty = difficulty;
    this.numRounds = numRounds;
    this.options = options;
    String namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
  }

  // public void play() {
  //   if (roundNumber <= numRounds) {
  //     MessageCli.START_ROUND.printMessage(roundNumber, numRounds);
  //     roundNumber++;
  //     MessageCli.ASK_HUMAN_INPUT.printMessage();
  //     String input = Utils.scanner.nextLine();
  //     inputColour = Colour.fromInput(input);
  //     if (inputColour == null) {
  //       MessageCli.INVALID_HUMAN_INPUT.printMessage();
  //           roundNumber--;
  //       return;
  //     }

  //   } else {
  //     // show who won
  //   }
  // }

  public void play() {
    if (roundNumber <= numRounds) {
      MessageCli.START_ROUND.printMessage(roundNumber, numRounds);
      MessageCli.ASK_HUMAN_INPUT.printMessage();
      String input = Utils.scanner.nextLine();
      inputColour = Colour.fromInput(input);
      if (inputColour == null) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        return;
      }
      roundNumber++;

    } else {
      // show who won
    }
  }

  public void showStats() {}
}
