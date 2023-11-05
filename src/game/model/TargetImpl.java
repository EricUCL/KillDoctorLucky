package game.model;

/**
 * This class represents the TargetImpl class. It implements the Target interface and has all the
 * methods which are required for the game to be played.
 */
public class TargetImpl implements Target {
  private final String name;
  private int health;
  private int roomIdx;

  /**
   * Constructor to initialize the target.
   *
   * @param name    Name of the target.
   * @param health  Health of the target.
   * @param roomIdx Room index where the target is present.
   */
  public TargetImpl(String name, int health, int roomIdx) {
    if (health < 0) {
      throw new IllegalArgumentException("Health can't be negative!");
    }
    this.name = name;
    this.health = health;
    this.roomIdx = roomIdx;
  }

  /**
   * Getter for name, needed for displaying information about target.
   *
   * @return Returns name in text format.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Update space after parsing through the input file.
   *
   * @param roomIdx Room index is needed as an argument.
   */
  @Override
  public void setRoom(int roomIdx) {
    if (roomIdx < 0) {
      throw new IllegalArgumentException("Room index can't be negative!!!");
    }
    this.roomIdx = roomIdx;
  }

  /**
   * Getter for space, to know where the target is present.
   *
   * @return Returns the space object where the target is.
   */
  @Override
  public int getRoomIndex() {
    return this.roomIdx;
  }

  /**
   * Returns the health of the target, which is necessary for game continuation.
   *
   * @return Returns the health in the number format.
   */
  @Override
  public int getHealth() {
    return this.health;
  }

  /**
   * Reduces the target health, upon being attacked by the player.
   *
   * @param health weapons strength which is used on the target.
   */
  @Override
  public void updateHealth(int health) {
    if (health < 0) {
      this.health = 0;
    }
    this.health = health;
  }

  @Override
  public String toString() {
    return "Name: " + name + ", Health: " + health + ", Room: " + roomIdx;
  }
}
