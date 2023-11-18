package game.model;

/**
 * Mock implementation of the {@link Item} interface for testing purposes.
 */
class MockItem implements Item {
  private final String name;

  /**
   * Constructor for the mock item.
   *
   * @param name name of the item.
   */
  public MockItem(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the item.
   *
   * @return name of the item.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Returns the damage the item can cause upon attacking by player on the target.
   *
   * @return damage amount which is of type number.
   */
  @Override
  public int getDamage() {
    return 0;
  }

  /**
   * Returns the id of the item.
   *
   * @return id of the item.
   */
  @Override
  public int getId() {
    return 0;
  }

}
