package game.model;

import java.util.List;

/**
 * This interface represents a room in the game world.
 */
public interface Room {

  /**
   * Function for getting neighbors of the room object.
   * GameWorld is using this to show neighbors
   * info.
   *
   * @return List of rooms which are sharing the "wall" with the calling room object.
   */
  List<Room> getNeighbours();

  /**
   * Getter, which is used in the calculating neighbor function, which returns the index of the
   * room.
   *
   * @return Returns the index of the room
   */
  Integer getIndex();

  /**
   * Getter for fetching name of the room, needed while displaying Room information.
   *
   * @return Returns name of a room object.
   */
  String getName();

  /**
   * Getter for fetching upper left row of the room, needed while displaying Room information.
   *
   * @return Returns the upper left row of a room object.
   */
  int getUpperLeftRow();

  /**
   * Getter for fetching upper left column of the room, needed while displaying Room information.
   *
   * @return Returns upper left column of a room object.
   */
  int getUpperLeftCol();

  /**
   * Getter for fetching the lower right row of the room, needed while displaying Room information.
   *
   * @return Returns lower right row of a room object.
   */
  int getLowerRightRow();

  /**
   * Getter for fetching lower right column of the room, needed while displaying Room information.
   *
   * @return Returns lower right column of a room object.
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
  void setNeighborRooms(List<Room> neighborRooms);

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

  /**
   * Adds a player to the room.
   *
   * @param player The player object to be added to the room.
   */
  void addPlayer(Player player);

  /**
   * Removes a player from the room.
   *
   * @param player The player object to be removed from the room.
   */
  void removePlayer(Player player);

  /**
   * Deletes an item from the room.
   *
   * @param item The item object to be deleted from the room.
   */
  void deleteItem(Item item);

  /**
   * Provides a detailed description of the room, including its contents and any players present.
   * This description is useful for players to get a textual representation of the room's state.
   *
   * @return A string containing a detailed description of the room.
   */
  String displayRoomDescription();
}
