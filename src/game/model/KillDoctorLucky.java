package game.model;

import game.constants.PlayerType;
import game.utils.OperationResult;

/**
 * This interface represents the KillDoctorLucky game. It has all the methods which are required for
 * the game to be played.
 */
public interface KillDoctorLucky extends ReadonlyGameModel {
  /**
   * Execute a move of the target, which doesn't require any input as target will be moving
   * according the room index orders.
   *
   * @return Returns the room index where the target is moved.
   */
  String moveTarget();

  /**
   * Function for generate the image of the world.
   *
   * @return image path.
   */
  String createWorldImage();

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
  OperationResult pickItem(int itemId);

  /**
   * Allows the current player to survey their surroundings.
   *
   * @return A string description of the immediate surroundings.
   */
  String lookAround();

  /**
   * Sets the maximum allowed turns for the game.
   *
   * @param maxTurns The maximum number of turns.
   */
  void setMaxTurns(int maxTurns);

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
  OperationResult attackTarget(String itemId);

}
