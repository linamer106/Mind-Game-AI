package nz.ac.auckland.se281.engine;

import java.util.List;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.model.Colour;

public class GameFactory {

  public static DifficultyLevel chooseGameDifficulty(
      Difficulty difficulty,
      Colour except,
      int roundNumber,
      List<Colour> humanChoiceHistory,
      boolean aiWonLastRound) { // could be given lower vs upper case?

    if (difficulty.equals(Difficulty.EASY)) {
      DifficultyLevel easyGame = new EasyGame(); // ignore case
      return easyGame;

    } else if (difficulty.equals(Difficulty.MEDIUM)) {
      DifficultyLevel mediumGame = new MediumGame(except, roundNumber);
      return mediumGame;

    } else if (difficulty.equals(Difficulty.HARD)) {
      DifficultyLevel hardGame =
          new HardGame(except, roundNumber, humanChoiceHistory, aiWonLastRound);
      return hardGame;
    }
    return null;
  }
}
