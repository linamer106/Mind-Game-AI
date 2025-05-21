package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class LeastUsedStrategy implements Strategy {

  @Override
  public Colour chooseColour() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'chooseColour'");
  }

  @Override
  public Colour guessHumanColour() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'guessHumanColour'");
  }

  //   /*As the name suggests, this strategy chooses the colour that the human has used least often
  // during the current game. The idea behind this strategy is that the player will eventually use
  // underused colours and avoid repeating the same ones too frequently.

  //   If there are multiple colours that have been used the least, the AI will choose the one that
  // comes first in the canonical order defined in the Colour class:
  //   RED, GREEN, BLUE, YELLOW.

  //   For example, if the player has used RED 3 times, GREEN 2 times, and BLUE and YELLOW 0 times,
  // the AI will guess BLUE (since it comes before YELLOW in the order). */
  // }
}
