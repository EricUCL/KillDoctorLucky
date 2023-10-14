package game.constants;

/**
 * Enum for separating PlayerType.
 * HUMAN -> HUMAN player
 * COMP -> Computer player
 */
public enum PlayerType {
  HUMAN("HUMAN"), COMPUTER("COMPUTER");

  private final String disp;

  PlayerType(String disp) {
    this.disp = disp;
  }

  @Override
  public String toString() {
    return disp;
  }
}
