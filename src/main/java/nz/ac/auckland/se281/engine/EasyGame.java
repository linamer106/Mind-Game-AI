package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class EasyGame implements DifficultyLevel {
  private RandomStrategy randomStrategy;

  public EasyGame() {
    this.randomStrategy = new RandomStrategy();
  }

  @Override
  public Colour aiMethodForGuessingHumanColour() {
    return randomStrategy.guessHumanColour();
  }

  @Override
  public Colour aiMethodForChoosingColour() {
    return randomStrategy.chooseColour();
  }
}
