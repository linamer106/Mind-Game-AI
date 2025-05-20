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
      List<Colour> inputColours = new ArrayList<>();

      while (true) {
        MessageCli.ASK_HUMAN_INPUT.printMessage();
        String input = Utils.scanner.nextLine();
        String[] parts = input.trim().split(" ");
        if (parts.length != 2) { // how different from using while here?
          MessageCli.INVALID_HUMAN_INPUT.printMessage();
          continue;
        }
        Colour colour1 = Colour.fromInput(parts[0]);
        Colour colour2 = Colour.fromInput(parts[1]);
        if (colour1 == null || colour2 == null) {
          MessageCli.INVALID_HUMAN_INPUT.printMessage();
          continue;
        }
        // could List.of() here produce me issues later due to nullpointerexception and modification
        // stuff?
        inputColours.add(colour1);
        inputColours.add(colour2);
        break;
      }

      roundNumber++;
      if (roundNumber % 3 == 0) {
        MessageCli.PRINT_POWER_COLOUR.printMessage(
            Colour.getRandomColourForPowerColour()); // why can't do model.Colour?
      }
      MessageCli.PRINT_INFO_MOVE.printMessage(options[0], inputColours.get(0), inputColours.get(1));
    }
  }

  public void showStats() {}
}
