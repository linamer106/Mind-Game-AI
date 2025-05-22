package nz.ac.auckland.se281.engine;

import java.util.List;
import nz.ac.auckland.se281.model.Colour;

public class LeastUsedStrategy implements Strategy {
  private List<Colour> humanChoiceHistory;

  public LeastUsedStrategy(List<Colour> humanChoiceHistory) {
    this.humanChoiceHistory = humanChoiceHistory;
  }

  @Override
  public Colour chooseColour() {
    return Colour.getRandomColourForAi();
  }

  @Override
  public Colour guessHumanColour() {

    if (humanChoiceHistory.isEmpty()) {
      return Colour.getRandomColourForAi();
    }

    int[] counts = new int[Colour.values().length];

    // Count how many times each colour appears
    for (Colour guess : humanChoiceHistory) {
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
