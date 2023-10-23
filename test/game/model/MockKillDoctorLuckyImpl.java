package game.model;

import game.constants.PlayerType;
import game.constants.ProgramState;
import game.utils.RandomGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockKillDoctorLuckyImpl implements KillDoctorLucky {
  List<Item> items;
  private List<Room> rooms;
  private int numRows;
  private int numColumns;
  private final int maxTurns;
  private String worldName;
  private Target target;
  private final List<Player> players;
  private final int maxPlayerLimit;
  private int currentPlayerIndex;
  private int currentTurn;
  private ProgramState programState;
  RandomGenerator randomGenerator;

  /**
   * Constructor for the KillDoctorLuckyImpl class.
   *
   * @param maxTurns maximum number of turns
   */
  public MockKillDoctorLuckyImpl(int maxTurns, RandomGenerator randomGenerator) {
    if (maxTurns < 0) {
      throw new IllegalArgumentException("Max turns can't be negative");
    }
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();
    this.maxTurns = maxTurns;
    this.players = new ArrayList<>();
    this.maxPlayerLimit = 10;
    programState = ProgramState.INIT;
    currentTurn = 1;
    currentPlayerIndex = 0;
    this.randomGenerator = randomGenerator;
    this.rooms = new ArrayList<>();
  }

  @Override
  public void initialMap() {
    for (Room room1 : rooms) {
      //        System.out.println(room1);
      List<Integer> neighbors = new ArrayList<>();
      for (Room room2 : rooms) {
        if (room1.equals(room2)) {
          continue;
        }
        if (overlaps(room1, room2)) {
          throw new IllegalArgumentException(
              "Rooms overlap: " + room1.getName() + " conflicts with " + room2.getName());
        }
        if (isNeighbor(room1, room2)) {
          neighbors.add(room2.getIndex());
        }
      }
      room1.setNeighborRooms(neighbors);
    }
  }

  @Override
  public boolean overlaps(Room current, Room other) {
    if (current == null || other == null) {
      throw new IllegalArgumentException("Room object can't be null");
    }
    return current.getUpperLeftRow() <= other.getLowerRightRow()
        && current.getLowerRightRow() >= other.getUpperLeftRow()
        && current.getUpperLeftCol() <= other.getLowerRightCol()
        && current.getLowerRightCol() >= other.getUpperLeftCol();
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

  /**
   * Getter for the max player limit.
   *
   * @return max player limit
   */
  @Override
  public int getMaxPlayerLimit() {
    return maxPlayerLimit;
  }

  @Override
  public String displayRoomDescription(int roomIdx) {
    return rooms.get(roomIdx).displayRoomDescription();
  }

  @Override
  public int getLocationOfTarget() {
    return this.target.getRoomIdx();
  }

  @Override
  public List<Integer> getNeighboursOfRoom(int idx) {
    return this.rooms.get(idx).getNeighbours();
  }

  @Override
  public String createWorldImage() {
    int scale = 25;
    int width = numColumns * (scale + 3);
    int height = numRows * (scale + 10);

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();

    g.setColor(Color.WHITE); // set background to white
    g.fillRect(0, 0, width, height);
    g.setStroke(new BasicStroke(2));
    g.setColor(Color.BLACK); // set color to black

    for (Room room : rooms) {
      int scaledY1 = room.getUpperLeftRow() * scale;
      int scaledX1 = room.getUpperLeftCol() * scale;
      int rectangleWidth = (room.getLowerRightCol() - room.getUpperLeftCol() + 1) * scale;
      int rectangleHeight = (room.getLowerRightRow() - room.getUpperLeftRow() + 1) * scale;
      g.drawRect(scaledX1, scaledY1, rectangleWidth, rectangleHeight);

      final String text = String.format("%s-%s", room.getIndex(), room.getName());
      int fontSize = 14;
      FontMetrics metrics;
      g.setFont(g.getFont().deriveFont((float) fontSize));
      metrics = g.getFontMetrics(g.getFont());

      final int x = scaledX1 + (rectangleWidth - metrics.stringWidth(text) + 3) / 2;
      final int y = scaledY1 + ((rectangleHeight - metrics.getHeight()) / 2) + metrics.getAscent();
      g.setFont(new Font("", Font.PLAIN, 15));
      g.drawString(text, x, y);
    }

    String outputPath = "./image.png";

    try {
      ImageIO.write(image, "png", new File(outputPath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "Successfully generate to " + outputPath;
  }

  @Override
  public String moveTarget() {
    this.target.setRoom((this.target.getRoomIdx() + 1) % this.rooms.size());
    return "Target moved to room " + this.target.getRoomIdx();
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
    StringBuilder sb = new StringBuilder();
    sb.append("World Name: ").append(this.worldName).append("\n");
    sb.append("Target Name: ").append(this.target.getName()).append("\n");
    sb.append("Target Health: ").append(this.target.getHealth()).append("\n");
    sb.append("Max Turns: ").append(this.maxTurns).append("\n");
    sb.append("Number of Rooms: ").append(this.rooms.size()).append("\n");
    sb.append("Number of Items: ").append(this.items.size()).append("\n");
    sb.append("Number of Players: ").append(this.players.size()).append("\n");
    return sb.toString();
  }

  @Override
  public String displayTargetInfo() {
    return target.toString();
  }

  @Override
  public void setNumRows(int numRows) {
    this.numRows = numRows;
  }

  @Override
  public void setNumCols(int numColumns) {
    this.numColumns = numColumns;
  }

  @Override
  public void setWorldName(String worldName) {
    this.worldName = worldName;
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
  public String addPlayer(String playerName, int roomIndex, int maxItemsLimit,
      PlayerType playerType) {

    if (players.size() >= maxPlayerLimit) {
      return "Max players limit reached!";
    }

    if (maxItemsLimit < 0) {
      throw new IllegalArgumentException("Max item limit can not be negative!");
    }

    validateRoomIndex(roomIndex);
    validatePlayer(playerName);

    Player player = new PlayerImpl(playerName, roomIndex, maxItemsLimit, playerType);
    this.players.add(player);
    Room room = rooms.get(roomIndex);
    room.addPlayer(player);
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
    Player player = players.get(currentPlayerIndex);
    // check if roomIndex is in neighbours
    if (!getNeighboursOfRoom(player.getRoomIndex()).contains(roomIndex)) {
      throw new IllegalArgumentException("Please give neighbor room index!");
    }
    validateRoomIndex(roomIndex);

    // remove player from current room
    Room originRoom = rooms.get(player.getRoomIndex());
    originRoom.removePlayer(player);
    // update player's room index
    player.setRoomIndex(roomIndex);
    // add player to new room
    Room room = rooms.get(roomIndex);
    room.addPlayer(players.get(currentPlayerIndex));
    updateTurn();
    return "Player moved successfully";
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
  public String pickItem(int itemId) {
    int roomIndex = players.get(currentPlayerIndex).getRoomIndex();
    Room room = rooms.get(roomIndex);
    List<Integer> itemsIndex = new ArrayList<>();
    for (Item item : room.getItems()) {
      itemsIndex.add(item.getId());
    }
    if (!itemsIndex.contains(itemId)) {
      throw new IllegalArgumentException("Please select a valid index!");
    }
    Item choosenItem = items.get(itemId);
    players.get(currentPlayerIndex).addItem(choosenItem);
    room.deleteItem(choosenItem);
    updateTurn();
    return "Item is picked successfully!";
  }

  @Override
  public String lookAround() {
    int roomIndex = this.players.get(currentPlayerIndex).getRoomIndex();
    Room currentRoom = rooms.get(roomIndex);
    List<Integer> neighboursIndex = currentRoom.getNeighbours();

    StringBuilder sb = new StringBuilder();
    sb.append(currentRoom.displayRoomDescription()).append("\n");
    if (!neighboursIndex.isEmpty()) {
      sb.append("Details on neighbor rooms of player are: \n").append("--------------------\n");
      for (int neighborIndex : neighboursIndex) {
        sb.append(rooms.get(neighborIndex).displayRoomDescription());
        sb.append("\n--------------------\n");
      }
    } else {
      sb.append("No neighbors for this space. \n");
    }
    updateTurn();
    return sb.toString();
  }

  void updateTurn() {

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
    Player player = getPlayer(playerName);
    return player.displayPlayerDescription();
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

  public void setProgramState(ProgramState programState) {
    this.programState = programState;
  }

  public void setCurrentPlayer(Player computerPlayer) {
    this.players.add(computerPlayer);
    this.currentPlayerIndex = 0;
    this.currentTurn = 1;
  }
}
