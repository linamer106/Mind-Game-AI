package nz.ac.auckland.se281.engine;

import java.util.List;
import nz.ac.auckland.se281.model.Colour;

public class LeastUsedStrategy implements Strategy {
  List<Colour> humanGuesses;

  public LeastUsedStrategy(List<Colour> humanGuesses) {
    this.humanGuesses = humanGuesses;
  }

  @Override
  public Colour chooseColour() {
    return Colour.getRandomColourForAi();
  }

  @Override
  public Colour guessHumanColour() {

    int[] counts = new int[Colour.values().length];

    // Count how many times each colour appears
    for (Colour guess : humanGuesses) {
      counts[guess.ordinal()]++;
    }

    // Find the index of the least used colour in canonical order
    int minIndex = 0;
    for (int i = 1; i < counts.length; i++) {
      if (counts[i] < counts[minIndex]) {
        minIndex = i;
      }
    }

    return Colour.values()[minIndex];
  }
}
