package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class RandomStrategy implements Strategy {

  @Override
  public Colour chooseColour() {
    return Colour.getRandomColourForAi();
  }

  @Override
  public Colour guessHumanColour() {
    return Colour.getRandomColourForAi();
  }
}
