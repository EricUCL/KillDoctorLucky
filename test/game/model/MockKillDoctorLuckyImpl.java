package game.model;

import game.constants.PlayerType;
import game.constants.ProgramState;
import game.utils.OperationResult;
import game.utils.RandomGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the KillDoctorLuckyImpl class. It implements the KillDoctorLucky interface
 * and has all the methods which are required for the game to be played.
 */
public class MockKillDoctorLuckyImpl implements KillDoctorLucky {
  private List<Item> items;
  private List<Room> rooms;
  private final int maxTurns;
  private Target target;
  private final List<Player> players;
  private final int maxPlayerLimit;
  private final int currentPlayerIndex;
  private final int currentTurn;
  private ProgramState programState;

  /**
   * Constructor for the KillDoctorLuckyImpl class.
   *
   * @param maxTurns        maximum number of turns
   * @param randomGenerator random generator
   */
  public MockKillDoctorLuckyImpl(int maxTurns, RandomGenerator randomGenerator) {
    if (maxTurns < 0) {
      throw new IllegalArgumentException("Max turns can't be negative");
    }
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();
    this.maxTurns = maxTurns;
    this.players = new ArrayList<>();
    players.add(new PlayerImpl("Player1", 0, 10, PlayerType.HUMAN));
    players.add(new PlayerImpl("Computer1", 0, 10, PlayerType.COMPUTER));
    this.maxPlayerLimit = 10;
    programState = ProgramState.RUNNING;
    currentTurn = 1;
    currentPlayerIndex = 0;
    this.rooms = new ArrayList<>();
  }

  @Override
  public List<Room> getRooms() {
    return rooms;
  }

  @Override
  public void initialMap() {
    for (Room currentRoom : rooms) {
      List<Room> neighbors = new ArrayList<>();
      for (Room otherRoom : rooms) {
        if (currentRoom.equals(otherRoom)) {
          continue;
        }
        boolean isOverlaps = currentRoom.getUpperLeftRow() <= otherRoom.getLowerRightRow()
            && currentRoom.getLowerRightRow() >= otherRoom.getUpperLeftRow()
            && currentRoom.getUpperLeftCol() <= otherRoom.getLowerRightCol()
            && currentRoom.getLowerRightCol() >= otherRoom.getUpperLeftCol();
        if (isOverlaps) {
          throw new IllegalArgumentException(
              "Rooms overlap: " + currentRoom.getName() + " conflicts with " + otherRoom.getName());
        }
        if (isNeighbor(currentRoom, otherRoom)) {
          neighbors.add(otherRoom);
        }
      }
      currentRoom.setNeighborRooms(neighbors);
    }
  }

  @Override
  public boolean isNeighbor(Room current, Room other) {
    if (current == null || other == null) {
      throw new IllegalArgumentException("Room object can't be null");
    }

    int currentLowerRightRow = current.getLowerRightRow();
    int currentLowerRightCol = current.getLowerRightCol();
    int currentUpperLeftRow = current.getUpperLeftRow();
    int currentUpperLeftCol = current.getUpperLeftCol();

    int otherLowerRightRow = other.getLowerRightRow();
    int otherLowerRightCol = other.getLowerRightCol();
    int otherUpperLeftRow = other.getUpperLeftRow();
    int otherUpperLeftCol = other.getUpperLeftCol();

    boolean rowNeighbor = (currentLowerRightRow + 1 == otherUpperLeftRow
        && currentLowerRightCol + 1 >= otherUpperLeftCol
        && currentUpperLeftCol - 1 <= otherLowerRightCol) || (
        currentUpperLeftRow - 1 == otherLowerRightRow
            && currentLowerRightCol + 1 >= otherUpperLeftCol
            && currentUpperLeftCol - 1 <= otherLowerRightCol);

    boolean colNeighbor = (currentLowerRightCol + 1 == otherUpperLeftCol
        && currentLowerRightRow + 1 >= otherUpperLeftRow
        && currentUpperLeftRow - 1 <= otherLowerRightRow) || (
        currentUpperLeftCol - 1 == otherLowerRightCol
            && currentLowerRightRow + 1 >= otherUpperLeftRow
            && currentUpperLeftRow - 1 <= otherLowerRightRow);

    return rowNeighbor || colNeighbor;
  }

  @Override
  public String moveTarget() {
    return null;
  }

  @Override
  public String displayRoomDescription(int roomIdx) {
    return rooms.get(roomIdx).displayRoomDescription();
  }

  @Override
  public List<Integer> getNeighboursOfRoom(int index) {
    return this.rooms.get(index).getNeighbours().stream().map(Room::getIndex)
        .collect(Collectors.toList());
  }

  @Override
  public String createWorldImage() {
    return "Create World Image Activated!";
  }

  @Override
  public String displayItemInfo(int itemIdx) {
    if (itemIdx < 0 || itemIdx >= items.size()) {
      throw new IllegalArgumentException("Invalid item index");
    }
    return items.get(itemIdx).toString();
  }

  @Override
  public String getWorldDesc() {
    return "Display World Description Activated!";
  }

  @Override
  public String displayTargetInfo() {
    return "Display Target Info Activated!";
  }

  @Override
  public void setNumRows(int numRows) {
  }

  @Override
  public void setNumCols(int numColumns) {
  }

  @Override
  public void setWorldName(String worldName) {
  }

  @Override
  public void setTarget(Target target) {
    this.target = target;
  }

  @Override
  public void addRooms(Room room) {
    this.rooms.add(room);
  }

  @Override
  public void addItems(int id, int itemDamage, String itemName, int itemRoomIndex) {
    validateRoomIndex(itemRoomIndex);
    Item item = new ItemImpl(id, itemDamage, itemName);
    this.items.add(item);
    rooms.get(itemRoomIndex).addItem(item);
  }

  @Override
  public String addPlayer(String playerName, int maxItemsLimit, PlayerType playerType) {
    programState = ProgramState.FINALIZING;
    return "Player added successfully!";
  }

  @Override
  public String addComputerPlayer() {
    if (players.size() >= maxPlayerLimit) {
      return "Max players limit reached!";
    }
    String playerName = "ComputerPlayer" + players.size();
    Player player = new PlayerImpl(playerName, 0, 10, PlayerType.COMPUTER);
    this.players.add(player);
    Room room = rooms.get(0);
    room.addPlayer(player);
    return "Computer Player added successfully";
  }

  private void validateRoomIndex(int roomIndex) {
    if (roomIndex < 0 || roomIndex > rooms.size()) {
      throw new IllegalArgumentException("Invalid room index!");
    }
  }

  private void validatePlayer(String playerName) {
    if (playerName == null || playerName.isEmpty()) {
      throw new IllegalArgumentException("Player name can't be null!");
    }

    if (playerName.equals(target.getName())) {
      throw new IllegalArgumentException("Player name can't be same as target name!");
    }

    for (Player player : players) {
      if (player.getPlayerName().equals(playerName)) {
        throw new IllegalArgumentException("Player name already exists!");
      }
    }
  }

  @Override
  public String movePlayer(int roomIndex) {
    return "Move Player Activated!";
  }

  @Override
  public Player getPlayer(String playerName) {
    for (Player player : players) {
      if (player.getPlayerName().equals(playerName)) {
        return player;
      }
    }
    throw new IllegalArgumentException("Player not found!");
  }

  @Override
  public Player getCurrentPlayer() {
    return players.get(currentPlayerIndex);
  }

  @Override
  public String startGame() {
    if (players.isEmpty()) {
      throw new IllegalArgumentException("Please add Players!");
    }
    programState = ProgramState.RUNNING;
    return "Start game successfully!";
  }

  @Override
  public OperationResult pickItem(int itemId) {
    return new OperationResult(true, "Item picked successfully!");
  }

  @Override
  public String lookAround() {
    return "Look Around Activated!";
  }

  @Override
  public int getTurnCount() {
    return currentTurn;
  }

  @Override
  public int getMaxTurns() {
    return maxTurns;
  }

  @Override
  public ProgramState getProgramState() {
    return programState;
  }

  @Override
  public String getItemsInCurrentRoom() {
    int roomIndex = this.players.get(currentPlayerIndex).getRoomIndex();
    Room currentRoom = rooms.get(roomIndex);
    List<Item> items = currentRoom.getItems();
    StringBuilder sb = new StringBuilder();
    if (items.isEmpty()) {
      sb.append("No items in current room!");
      return sb.toString();
    }
    sb.append("Items in current room are: \n");
    for (Item item : items) {
      sb.append(item);
      sb.append("\n--------------------\n");
    }
    return sb.toString();
  }

  @Override
  public List<Player> getPlayers() {
    List<Player> playersCopy = new ArrayList<>();
    for (Player player : this.players) {
      playersCopy.add(
          new PlayerImpl(player.getPlayerName(), player.getRoomIndex(), player.getItemLimit(),
              player.getPlayerType()));
    }
    return playersCopy;
  }

  @Override
  public String displayPlayerDescription(String playerName) {

    return "Display Player Description Activated!";
  }

  @Override
  public String computerPlayerTurn() {
    this.programState = ProgramState.FINALIZING;
    return "Current player turn is computer player";
  }

  @Override
  public String displayPrepareMessage() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nTurn Counter: ").append(getTurnCount()).append("\n");
    sb.append("Max Turn: ").append(getMaxTurns()).append("\n");
    Player currentPlayer = getCurrentPlayer();
    sb.append("Current turn: ").append(currentPlayer.getPlayerName()).append("\n");
    sb.append(displayRoomDescription(currentPlayer.getRoomIndex()));
    return sb.toString();
  }

  @Override
  public void setPet(PetImpl pet) {

  }

  @Override
  public String movePet(int roomIndex) {
    return "Move Pet Activated!";
  }

  @Override
  public OperationResult attackTarget(String itemId) {
    return new OperationResult(true, "Attack Target Activated!");
  }

  @Override
  public String displayItemsByCurrentPlayer() {

    return "Display Items By Current Player Activated!";
  }

  @Override
  public String displayFinalMessage() {
    return "Game Over!";
  }

  /**
   * Set program state for testing.
   *
   * @param programState program state
   */
  public void setProgramState(ProgramState programState) {
    this.programState = programState;
  }
}
