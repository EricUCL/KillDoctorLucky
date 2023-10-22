package game.model;

import game.constants.PlayerType;

import java.util.List;

/**
 * Represents a player within the game world. Players can possess items, have a type, and are
 * located within rooms. This interface provides methods to manage player properties, their
 * inventory, and their movement within the game world.
 */
public interface Player {

  /**
   * Adds an item to the player's inventory.
   *
   * @param item The item object to be added.
   */
  void addItem(Item item);

  /**
   * Retrieves the name of the player.
   *
   * @return The name of the player.
   */
  String getPlayerName();

  /**
   * Sets the index of the room in which the player is currently located.
   *
   * @param roomIndex The index of the room.
   */
  void setRoomIndex(int roomIndex);

  /**
   * Moves the player to a specified room.
   *
   * @param roomIdx The index of the room to which the player should move.
   */
  void movePlayer(int roomIdx);

  /**
   * Retrieves the type of the player (e.g., NPC, human).
   *
   * @return The player type.
   */
  PlayerType getPlayerType();

  /**
   * Retrieves the limit of items that the player can carry.
   *
   * @return The item limit for the player.
   */
  int getItemLimit();

  /**
   * Removes an item from the player's inventory based on its index.
   *
   * @param itemIndex The index of the item to be removed.
   */
  void removeItemByIndex(int itemIndex);

  /**
   * Adds an item to the player's inventory based on its index.
   *
   * @param itemIndex The index of the item to be added.
   */
  void addItemByIndex(int itemIndex);

  /**
   * Retrieves a list of indexes of items currently in the player's inventory.
   *
   * @return A list of item indexes.
   */
  List<Integer> getItemsIndexList();

  /**
   * Retrieves the index of the room in which the player is currently located.
   *
   * @return The current room index of the player.
   */
  int getRoomIndex();

  /**
   * Provides a detailed description of the player, including their name, inventory, and current
   * location. Useful for providing a textual representation of the player's state.
   *
   * @return A string containing a detailed description of the player.
   */
  String displayPlayerDescription();
}
