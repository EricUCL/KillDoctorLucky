package game.constants;

/**
 * Enum for separating PlayerType. HUMAN -> HUMAN player COMP -> Computer player
 */
public enum PlayerType {
  HUMAN("HUMAN"), COMPUTER("COMPUTER");

  private final String type;

  PlayerType(String type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type;
  }
}
