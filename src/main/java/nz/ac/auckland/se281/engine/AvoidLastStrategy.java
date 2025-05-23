package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class AvoidLastStrategy implements Strategy {
  private Colour exclude;

  public AvoidLastStrategy(Colour exclude) {
    this.exclude = exclude;
  }

  @Override
  public Colour chooseColour() {
    return Colour.getRandomColourForAi();
  }

  @Override
  public Colour guessHumanColour() {
    if (exclude == null) { // when would this be null?
      return Colour.getRandomColourForAi();
    }
    return Colour.getRandomColourExcluding(exclude);
  }
}
