package nz.ac.auckland.se281.engine;

import java.util.List;
import nz.ac.auckland.se281.model.Colour;

public class HardGame implements DifficultyLevel {
  private RandomStrategy randomStrategy;
  private boolean aiWonLastRound;

  private Strategy currentStrategy;
  private int roundNumber;
  private Colour except;
  private List<Colour> humanChoiceHistory;

  public HardGame(
      Colour except, int roundNumber, List<Colour> humanChoiceHistory, boolean aiWonLastRound) {
    this.randomStrategy = new RandomStrategy();
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
      currentStrategy = new LeastUsedStrategy(humanChoiceHistory);
      ;
      setStrategy(currentStrategy);
    } else if (roundNumber >= 4) {
      if (!aiWonLastRound) { // Lost last round
        if (currentStrategy instanceof LeastUsedStrategy) {
          currentStrategy = new AvoidLastStrategy(except);
        } else {
          currentStrategy = new LeastUsedStrategy(humanChoiceHistory);
          ;
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

  public void setAiWonLastRound(boolean won) {
    this.aiWonLastRound = won;
  }

  public void setRoundNumber(int roundNumber) {
    this.roundNumber = roundNumber;
  }

  public void setHumanChoiceHistory(List<Colour> humanChoiceHistory) {
    this.humanChoiceHistory = humanChoiceHistory;
  }

  public void setExcept(Colour except) {
    this.except = except;
  }
}
