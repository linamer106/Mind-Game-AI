package nz.ac.auckland.se281.engine;

public class HardGame implements DifficultyLevel {

  private Strategy strategy;

  @Override
  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }
}
