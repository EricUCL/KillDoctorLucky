package game.model;

import java.util.List;
import game.constants.ProgramState;

public interface ReadonlyGameModel {
  /**
   * Return the description of the room.
   *
   * @param roomIdx index of the room for which we need description.
   * @return room description, which contains items and neighbors information.
   */
  String displayRoomDescription(int roomIdx);

  /**
   * Function for getting neighbours for a specified room with index.
   *
   * @param index Index of the room for which we have get neighbors
   * @return Returns list of rooms.
   */
  List<Integer> getNeighboursOfRoom(int index);

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
   * Function for checking if two rooms are neighbors or not.
   *
   * @param current current room object
   * @param other   other room object
   * @return true if they are neighbors, false otherwise.
   */
  boolean isNeighbor(Room current, Room other);

  /**
   * Function for getting the number of rows in the world.
   *
   * @return number of rows
   */
  int getNumRows();

  /**
   * Function for getting the number of columns in the world.
   *
   * @return number of columns
   */
  int getNumCols();

  /**
   * Retrieves a player from the game world based on their name.
   *
   * @param playerName The name of the player to retrieve.
   * @return The {@link Player} instance corresponding to the provided name.
   */
  Player getPlayer(String playerName);

  /**
   * Retrieves the current turn count.
   *
   * @return The current turn number.
   */
  int getTurnCount();

  /**
   * Provides the maximum allowed turns for the game.
   *
   * @return The maximum number of turns.
   */
  int getMaxTurns();

  /**
   * Retrieves the current state of the game program.
   *
   * @return The current state of the program.
   */
  ProgramState getProgramState();

  /**
   * Provides a list of all active players in the game.
   *
   * @return A list of all players.
   */
  List<Player> getPlayers();

  /**
   * Lists items available in the room the current player is located in.
   *
   * @return A string representation of items in the current room.
   */
  String getItemsInCurrentRoom();

  /**
   * Offers a description of a specific player.
   *
   * @param playerName The name of the player whose description is needed.
   * @return A string representation detailing the player's status and inventory.
   */
  String displayPlayerDescription(String playerName);

  /**
   * Lists items available in the current player's inventory.
   *
   * @return A string representation of items in the current player's inventory.
   */
  String displayItemsByCurrentPlayer();

  /**
   * Retrieves Turn Information about the game.
   *
   * @return Turn Information.
   */
  String getTurnInfo();

  /**
   * Provides final message after the game is over.
   *
   * @return A string message indicating the end of the game.
   */
  String displayFinalMessage();

  /**
   * Fetches the player who currently has the turn in the game.
   *
   * @return The {@link Player} instance representing the current player.
   */
  Player getCurrentPlayer();

  /**
   * Retrieves a list of all rooms in the game.
   *
   * @return A list of all rooms.
   */
  List<Room> getRooms();

  /**
   * Retrieves the target of the game.
   *
   * @return The {@link Target} instance representing the target.
   */
  Target getTarget();

  /**
   * Retrieves the current room of the game.
   *
   * @return The {@link Room} instance representing the current room.
   */
  Room getCurrentRoom();
}
