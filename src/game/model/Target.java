package game.model;

/**
 * This interface represents all the operations offered by a Target. Target interface takes care of
 * the location of target, name of the target, health and damage.
 */
public interface Target {

  /**
   * Getter for name, needed for displaying information about target.
   *
   * @return Returns name in text format.
   */
  String getName();

  /**
   * Update space after parsing through the input file.
   *
   * @param roomIdx Room index is needed as an argument.
   */
  void setRoom(int roomIdx);

  /**
   * Getter for space, to know where the target is present.
   *
   * @return Returns the space object where the target is.
   */
  int getRoomIndex();

  /**
   * Returns the health of the target, which is necessary for game continuation.
   *
   * @return Returns the health in the number format.
   */
  int getHealth();

  /**
   * Reduces the target health, upon being attacked by the player.
   *
   * @param health weapons strength which is used on the target.
   */
  void updateHealth(int health);
}
