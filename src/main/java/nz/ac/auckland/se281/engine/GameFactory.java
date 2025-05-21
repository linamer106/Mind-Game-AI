package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;

public class GameFactory {

  public static DifficultyLevel chooseGameDifficulty(
      Difficulty difficulty) { // ok  that it's type Game?

    if (difficulty.equals(Difficulty.EASY)) {
      DifficultyLevel easyGame = new EasyGame(); // ignore case
      return easyGame;

    } else if (difficulty.equals(Difficulty.MEDIUM)) {
      DifficultyLevel mediumGame = new MediumGame();
      return mediumGame;

    } else if (difficulty.equals(Difficulty.HARD)) {
      DifficultyLevel hardGame = new HardGame();
      return hardGame;
    }
    return null;
  }
}
