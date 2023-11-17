package game.model;

/**
 * This class represents the pet in the game.
 */
public class PetImpl implements Pet {
  String name;
  int roomIndex;

  /**
   * Constructor for the PetImpl class.
   *
   * @param name      name of the pet
   * @param roomIndex index of the room
   */
  public PetImpl(String name, int roomIndex) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name can not be empty!");
    }
    this.name = name;
    this.roomIndex = roomIndex;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getRoomIndex() {
    return this.roomIndex;
  }

  @Override
  public void updateRoom(int roomIndex) {
    if (roomIndex < 0) {
      throw new IllegalArgumentException("Room index can not be negative!");
    }
    this.roomIndex = roomIndex;
  }
}
