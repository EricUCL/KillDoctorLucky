package game.model;

/**
 * This interface represents an item which can be used by the player to attack the target.
 */
public interface Item {
  /**
   * Returns the name of the weapon, which is required during displaying of which item to choose.
   *
   * @return name of the item.
   */
  String getName();

  /**
   * Returns the damage the item can cause upon attacking by player on the target.
   *
   * @return damage amount which is of type number.
   */
  int getDamage();

  /**
   * Returns the id of the item.
   *
   * @return id of the item.
   */
  int getId();
}
