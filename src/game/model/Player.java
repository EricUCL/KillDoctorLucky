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
   * Retrieves the list of items that the player currently possesses.
   *
   * @return The list of items that the player currently possesses.
   */
  List<Item> getItemsList();

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

  /**
   * Removes an item from the player's inventory.
   *
   * @param item The item to be removed.
   * @return A string message indicating the result of the action.
   */
  String removeItem(Item item);
}
