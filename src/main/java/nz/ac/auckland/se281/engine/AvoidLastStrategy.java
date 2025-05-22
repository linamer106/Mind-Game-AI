package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class AvoidLastStrategy implements Strategy {
  private Colour exclude;

  // how to get this exclude colour?

  public AvoidLastStrategy(Colour exclude) {
    this.exclude = exclude;
  }

  @Override
  public Colour chooseColour() {
    return Colour.getRandomColourForAi();
  }

  @Override
  public Colour guessHumanColour() {
    return Colour.getRandomColourExcluding(exclude);
  }
}
