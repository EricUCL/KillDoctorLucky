package game.model;

/**
 * This interface represents the Pet. It has all the methods which are required for the pet to be
 * moved.
 */
public interface Pet {
  /**
   * Function for getting the name of the pet.
   *
   * @return Returns the name of the pet.
   */
  String getName();

  /**
   * Function for getting the room index of the pet.
   *
   * @return Returns the room index of the pet.
   */
  int getRoomIndex();

  /**
   * Function for updating the room index of the pet.
   *
   * @param roomIndex room index of the pet.
   */
  void updateRoom(int roomIndex);
}
