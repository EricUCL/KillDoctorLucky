package game.model;

/**
 * This class represents the TargetImpl class. It implements the Target interface and has all the
 * methods which are required for the game to be played.
 */
public class ItemImpl implements Item {
  private int damage;
  private String name;
  //  private int roomIdx;
  private int id;

  /**
   * Constructor for the ItemImpl class.
   *
   * @param damage damage of the item
   * @param name   name of the item
   * @param id     index of the item
   */
  public ItemImpl(int id, int damage, String name) {
    if (damage < 0) {
      throw new IllegalArgumentException("Damage can't be negative!");
    }
    this.damage = damage;
    this.name = name;
    //    this.roomIdx = itemRoomIndex;
    this.id = id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getDamage() {
    return this.damage;
  }

  @Override
  public String toString() {
    return "Id: " + id + ", Name: " + name + ", Damage: " + damage;
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Item)) {
      return false;
    }
    Item that = (Item) o;
    return this.getName().equals(that.getName());
  }
}
