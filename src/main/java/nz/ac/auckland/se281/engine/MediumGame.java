package nz.ac.auckland.se281.engine;

public class MediumGame implements DifficultyLevel {
  Strategy strategy;

  @Override
  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }
}
