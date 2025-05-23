package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class MediumGame implements DifficultyLevel {
  private RandomStrategy randomStrategy;
  private AvoidLastStrategy avoidLastStrategy;

  private Strategy currentStrategy;
  private int roundNumber;

  public MediumGame(Colour except, int roundNumber) {
    this.randomStrategy = new RandomStrategy();
    this.avoidLastStrategy = new AvoidLastStrategy(except);
    this.roundNumber = roundNumber;
    setStrategy(randomStrategy); // so need to call this every round?
  }

  private void setStrategy(Strategy s) {
    currentStrategy = s;
  }

  @Override
  public Colour aiMethodForGuessingHumanColour() {
    if (roundNumber >= 2) {
      currentStrategy = avoidLastStrategy; // updates exclude???
      setStrategy(currentStrategy);
      return currentStrategy.guessHumanColour();
    } else {
      setStrategy(randomStrategy);
      return currentStrategy.guessHumanColour();
    }
  }

  @Override
  public Colour aiMethodForChoosingColour() {
    return randomStrategy.chooseColour();
  }
}
