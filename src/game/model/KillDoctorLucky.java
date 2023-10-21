package game.model;

import game.constants.PlayerType;

import java.util.List;

/**
 * This interface represents the KillDoctorLucky game. It has all the methods which are required for
 * the game to be played.
 */
public interface KillDoctorLucky {
  /**
   * Execute a move of the target, which doesn't require any input as target will be moving
   * according the room index orders.
   *
   * @return Returns the room index where the target is moved.
   */
  String moveTarget();

  /**
   * Return the description of the room.
   *
   * @param roomIdx index of the room for which we need description.
   * @return room description, which contains items and neighbors information.
   */
  String displayRoomDescription(int roomIdx);

  /**
   * Returns the room index where the Target is located, which is used to display before every
   * player's turn.
   *
   * @return the {@link Target}s room index of the target's location.
   */
  int getLocationOfTarget();

  /**
   * Function for getting neighbours for a specified room with index.
   *
   * @param idx Index of the room for which we have get neighbors
   * @return Returns list of rooms.
   */
  List<Integer> getNeighboursOfRoom(int idx);

  /**
   * Function for generate the image of the world.
   *
   * @return image path.
   */
  String createWorldImage();

  /**
   * Function for getting information about item. This is being used by GameWorld to show item
   * info.
   *
   * @param itemIdx index of the item.
   * @return item name and damage.
   */
  String displayItemInfo(int itemIdx);

  /**
   * Function for getting the world description. This is being used by GameWorld to show world
   * information.
   *
   * @return Returns the world description.
   */
  String getWorldDesc();

  /**
   * Function for getting information of the target. This is being used by GameWorld to show target
   * info.
   *
   * @return target name, room index and health.
   */
  String displayTargetInfo();

  /**
   * Function for checking if two rooms are overlapping or not.
   *
   * @param current current room object
   * @param other   other room object
   * @return true if they are overlapping, false otherwise.
   */
  boolean overlaps(Room current, Room other);

  /**
   * Function for checking if two rooms are neighbors or not.
   *
   * @param current current room object
   * @param other   other room object
   * @return true if they are neighbors, false otherwise.
   */
  boolean isNeighbor(Room current, Room other);

  /**
   * Function for setting the number of rows in the world.
   *
   */
  void setNumRows(int numRows);

  /**
   * Function for setting the number of columns in the world.
   *
   */
  void setNumCols(int numCols);

  void setWorldName(String worldName);

  void setTarget(Target target);

  void addRooms(Room room);

  void initialMap();

  void addItems(int itemDamage, String itemName, int itemRoomIndex);

  String addPlayer(String playerName, int spaceIndex, int maxItemsLimit, PlayerType playerType);

  String movePlayer(int roomIndex);

  String addComputerPlayer();

  Player getPlayer(String playerName);

  Player getCurrentPlayer();

  String startGame();

  String pickItem(String itemName);

  String lookAround();
}
