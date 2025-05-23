package nz.ac.auckland.se281.engine;

import java.util.List;
import nz.ac.auckland.se281.model.Colour;

public class HardGame implements DifficultyLevel {
  private RandomStrategy randomStrategy;
  private AvoidLastStrategy avoidLastStrategy;
  private LeastUsedStrategy leastUsedStrategy;
  private boolean aiWonLastRound;

  private Strategy currentStrategy;
  private int roundNumber;

  public HardGame(
      Colour except, int roundNumber, List<Colour> humanChoiceHistory, boolean aiWonLastRound) {
    this.randomStrategy = new RandomStrategy();
    this.avoidLastStrategy = new AvoidLastStrategy(except);
    this.leastUsedStrategy = new LeastUsedStrategy(humanChoiceHistory);
    this.roundNumber = roundNumber;
    this.aiWonLastRound = aiWonLastRound;
    setStrategy(randomStrategy); // so need to call this every round?
  }

  private void setStrategy(Strategy s) {
    currentStrategy = s;
  }

  @Override
  public Colour aiMethodForGuessingHumanColour() {

    if (roundNumber == 3) {
      currentStrategy = leastUsedStrategy;
      setStrategy(currentStrategy);
    } else if (roundNumber >= 4) {
      if (!aiWonLastRound) { // Lost last round
        if (currentStrategy instanceof LeastUsedStrategy) {
          currentStrategy = avoidLastStrategy;
        } else {
          currentStrategy = leastUsedStrategy;
        }
      }
      setStrategy(currentStrategy);
    }
    return currentStrategy.guessHumanColour();
  }

  @Override
  public Colour aiMethodForChoosingColour() {
    return randomStrategy.chooseColour();
  }
}
