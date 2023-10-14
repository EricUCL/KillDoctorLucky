package game.model;

import java.util.List;

/**
 * This interface represents a room in the game world.
 */
public interface Room {

  /**
   * Function for getting neighbors of the room object. This is being used by GameWorld to show
   * neighbors info.
   *
   * @return List of rooms which are sharing the "wall" with the calling room object.
   */
  List<Integer> getNeighbours();

  /**
   * Getter which is used in the calculating neighbor function, which returns the index of the
   * room.
   *
   * @return Returns the index of the room
   */
  Integer getIndex();

  /**
   * Getter for fetching name of the room, needed while displaying Room information.
   *
   * @return Returns name of room object.
   */
  String getName();

  /**
   * Getter for fetching upper left row of the room, needed while displaying Room information.
   *
   * @return Returns upper left row of room object.
   */
  int getUpperLeftRow();

  /**
   * Getter for fetching upper left column of the room, needed while displaying Room information.
   *
   * @return Returns upper left column of room object.
   */
  int getUpperLeftCol();

  /**
   * Getter for fetching lower right row of the room, needed while displaying Room information.
   *
   * @return Returns lower right row of room object.
   */
  int getLowerRightRow();

  /**
   * Getter for fetching lower right column of the room, needed while displaying Room information.
   *
   * @return Returns lower right column of room object.
   */
  int getLowerRightCol();

  /**
   * Function for checking if two rooms are equal.
   *
   * @param o Room object which is being compared with the calling room object.
   * @return Returns true if both the rooms are equal, else false.
   */
  boolean equals(Room o);

  /**
   * Function for setting neighbor rooms of the calling room object.
   *
   * @param neighborRooms List of rooms which are sharing the "wall" with the calling room object.
   */
  void setNeighborRooms(List<Integer> neighborRooms);

  /**
   * Function for adding item to the calling room object.
   *
   * @param item Item object which is being added to the calling room object.
   */
  void addItem(Item item);

  /**
   * Function for getting items of the calling room object.
   *
   * @return Returns list of items which are present in the calling room object.
   */
  List<Item> getItems();

  void addPlayer(Player player);

  void removePlayer(Player player);
}
