package nz.ac.auckland.se281.engine;

public class EasyGame implements DifficultyLevel {

  private Strategy strategy;

  @Override
  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }

  // Strategy strat = new RandomStrategy();
  //   easyGame.setStrategy(strat);
}
