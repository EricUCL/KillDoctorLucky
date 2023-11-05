package game.model;

import game.constants.PlayerType;
import game.constants.ProgramState;
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
   * @param index Index of the room for which we have get neighbors
   * @return Returns list of rooms.
   */
  List<Integer> getNeighboursOfRoom(int index);

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
   * @param numRows number of rows
   */
  void setNumRows(int numRows);

  /**
   * Function for setting the number of columns in the world.
   *
   * @param numCols number of columns
   */
  void setNumCols(int numCols);

  /**
   * Sets the name of the game world.
   *
   * @param worldName The desired name for the game world.
   */
  void setWorldName(String worldName);

  /**
   * Assigns a target to the game world.
   *
   * @param target The {@link Target} instance representing the game's primary target.
   */
  void setTarget(Target target);

  /**
   * Adds a room to the game world.
   *
   * @param room The {@link Room} instance to be added.
   */
  void addRooms(Room room);

  int getMaxPlayerLimit();

  /**
   * Initializes the game's map with default or initial settings.
   */
  void initialMap();

  /**
   * Adds an item to the game world.
   *
   * @param id            The unique identifier for the item.
   * @param itemDamage    The damage value associated with the item.
   * @param itemName      The name of the item.
   * @param itemRoomIndex The room index where the item should be placed initially.
   */
  void addItems(int id, int itemDamage, String itemName, int itemRoomIndex);

  /**
   * Registers a new player to the game world.
   *
   * @param playerName    The name of the player.
   * @param maxItemsLimit The maximum number of items the player can carry.
   * @param playerType    The type of the player (e.g., Human, Computer).
   * @return A message or status after adding the player.
   */
  String addPlayer(String playerName, int maxItemsLimit, PlayerType playerType);

  /**
   * Moves a player to a specified room.
   *
   * @param roomIndex The index of the room where the player should be moved.
   * @return A message or status after moving the player.
   */
  String movePlayer(int roomIndex);

  /**
   * Adds a computer-controlled player to the game world.
   *
   * @return A message or status after adding the computer player.
   */
  String addComputerPlayer();

  /**
   * Retrieves a player from the game world based on their name.
   *
   * @param playerName The name of the player to retrieve.
   * @return The {@link Player} instance corresponding to the provided name.
   */
  Player getPlayer(String playerName);

  /**
   * Fetches the player who currently has the turn in the game.
   *
   * @return The {@link Player} instance representing the current player.
   */
  Player getCurrentPlayer();

  /**
   * Starts the game and initializes all game mechanics.
   *
   * @return A string message indicating the start of the game.
   */
  String startGame();

  /**
   * Allows the current player to pick up a specified item.
   *
   * @param itemId The index of the item to be picked up.
   * @return A string message indicating the result of the action.
   */
  String pickItem(int itemId);

  /**
   * Allows the current player to survey their surroundings.
   *
   * @return A string description of the immediate surroundings.
   */
  String lookAround();

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
   * Lists items available in the room the current player is located in.
   *
   * @return A string representation of items in the current room.
   */
  String getItemsInCurrentRoom();

  /**
   * Provides a list of all active players in the game.
   *
   * @return A list of all players.
   */
  List<Player> getPlayers();

  /**
   * Offers a description of a specific player.
   *
   * @param playerName The name of the player whose description is needed.
   * @return A string representation detailing the player's status and inventory.
   */
  String displayPlayerDescription(String playerName);

  /**
   * Facilitates the turn for a computer-controlled player.
   *
   * @return A string message detailing the actions taken by the computer player.
   */
  String computerPlayerTurn();

  /**
   * Retrieves any pre-game messages or setups required before gameplay starts.
   *
   * @return A string message indicating pre-game setups or information.
   */
  String displayPrepareMessage();

  /**
   * Assigns a pet to the game world.
   *
   * @param pet The {@link Pet} instance representing the target's pet.
   */
  void setPet(PetImpl pet);

  /**
   * Moves a pet to a specified room.
   *
   * @param roomIndex The index of the room where the pet should be moved.
   * @return A message or status after moving the pet.
   */
  String movePet(int roomIndex);

  /**
   * Allows the current player to attack target.
   *
   * @param itemId The index of the item to be attacked.
   * @return A string message indicating the result of the action.
   */
  String attackTarget(int itemId);
}
