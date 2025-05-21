package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public interface Strategy {

  Colour chooseColour();

  Colour guessHumanColour();
}
