package game.model;

public class PetImpl implements Pet {
  String name;
  int roomIndex;

  public PetImpl(String name, int roomIndex) {
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
  public void updateRoomIndex(int roomIndex) {
    this.roomIndex = roomIndex;
  }
}
