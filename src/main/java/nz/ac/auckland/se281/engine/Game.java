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
  private Colour inputColours;
  String namePlayer;

  public Game() {}

  public void newGame(
      Difficulty difficulty, int numRounds, String[] options) { // what is in string options?
    this.difficulty = difficulty;
    this.numRounds = numRounds;
    this.options = options;
    this.namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
  }

  public void play() {
    if (roundNumber <= numRounds) {
      MessageCli.START_ROUND.printMessage(roundNumber, numRounds);
      MessageCli.ASK_HUMAN_INPUT.printMessage();
      String input = Utils.scanner.nextLine();
      List<Colour> inputColours = new ArrayList<>();
      String[] parts = input.trim().split(" ");
      for (String part : parts) {
        Colour colour = Colour.fromInput(part);
        if (colour == null) {
          MessageCli.INVALID_HUMAN_INPUT.printMessage();
          return;
        }
        inputColours.add(colour);
      }
      roundNumber++;
      MessageCli.PRINT_INFO_MOVE.printMessage(options[0], inputColours.get(0), inputColours.get(1));
    } else {
      // show who won
    }
  }

  public void showStats() {}
}
