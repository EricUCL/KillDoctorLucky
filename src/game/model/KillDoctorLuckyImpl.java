package game.model;

import game.constants.PlayerType;
import game.constants.ProgramState;
import game.utils.DfsGraphImpl;
import game.utils.Graph;
import game.utils.OperationResult;
import game.utils.RandomGenerator;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

/**
 * This class represents the KillDoctorLuckyImpl class. It implements the KillDoctorLucky interface
 * and has all the methods which are required for the game to be played.
 */
public class KillDoctorLuckyImpl implements KillDoctorLucky {
  private final List<Item> items;
  private final List<Room> rooms;
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
  private final RandomGenerator randomGenerator;
  private Pet pet;
  Graph graph;
  public static final Logger logger = Logger.getLogger(KillDoctorLuckyImpl.class.getName());

  /**
   * Constructor for the KillDoctorLuckyImpl class.
   *
   * @param maxTurns        maximum number of turns
   * @param randomGenerator random generator
   */
  public KillDoctorLuckyImpl(int maxTurns, RandomGenerator randomGenerator) throws IOException {
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
    graph = new DfsGraphImpl();

    FileHandler fileHandler = new FileHandler("./logs/myapp_%u_%g.log");
    fileHandler.setFormatter(new SimpleFormatter());
    logger.addHandler(fileHandler);
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

    boolean isRowNeighbor = (currentLowerRightRow + 1 == otherUpperLeftRow
        && currentLowerRightCol + 1 >= otherUpperLeftCol
        && currentUpperLeftCol - 1 <= otherLowerRightCol) || (
        currentUpperLeftRow - 1 == otherLowerRightRow
            && currentLowerRightCol + 1 >= otherUpperLeftCol
            && currentUpperLeftCol - 1 <= otherLowerRightCol);

    boolean isColNeighbor = (currentLowerRightCol + 1 == otherUpperLeftCol
        && currentLowerRightRow + 1 >= otherUpperLeftRow
        && currentUpperLeftRow - 1 <= otherLowerRightRow) || (
        currentUpperLeftCol - 1 == otherLowerRightCol
            && currentLowerRightRow + 1 >= otherUpperLeftRow
            && currentUpperLeftRow - 1 <= otherLowerRightRow);

    return isRowNeighbor || isColNeighbor;
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
    int scale = 25;
    int width = numColumns * (scale + 3);
    int height = numRows * (scale + 10);

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = image.createGraphics();

    graphics.setColor(Color.WHITE); // set background to white
    graphics.fillRect(0, 0, width, height);
    graphics.setStroke(new BasicStroke(2));
    graphics.setColor(Color.BLACK); // set color to black

    for (Room room : rooms) {
      int scaledY1 = room.getUpperLeftRow() * scale;
      int scaledX1 = room.getUpperLeftCol() * scale;
      int rectangleWidth = (room.getLowerRightCol() - room.getUpperLeftCol() + 1) * scale;
      int rectangleHeight = (room.getLowerRightRow() - room.getUpperLeftRow() + 1) * scale;
      graphics.drawRect(scaledX1, scaledY1, rectangleWidth, rectangleHeight);

      String text = room.getIndex() + "-" + room.getName();
      int fontSize = 14;
      FontMetrics metrics;
      graphics.setFont(graphics.getFont().deriveFont((float) fontSize));
      metrics = graphics.getFontMetrics(graphics.getFont());

      int x = scaledX1 + (rectangleWidth - metrics.stringWidth(text) + 3) / 2;
      int y = scaledY1 + ((rectangleHeight - metrics.getHeight()) / 2) + metrics.getAscent();
      graphics.setFont(new Font("", Font.PLAIN, 15));
      graphics.drawString(text, x, y);
    }

    String outputPath = "./image.png";

    try {
      ImageIO.write(image, "png", new File(outputPath));
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to write image");
    }

    return "Successfully generated image: " + outputPath;
  }

  @Override
  public String moveTarget() {
    this.target.setRoom((this.target.getRoomIndex() + 1) % this.rooms.size());
    return "Target moved to room " + this.target.getRoomIndex();
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
    return "World Name: " + this.worldName + "\n" + "Target Name: " + this.target.getName() + "\n"
        + "Target Health: " + this.target.getHealth() + "\n" + "Max Turns: " + this.maxTurns + "\n"
        + "Number of Rooms: " + this.rooms.size() + "\n" + "Number of Items: " + this.items.size()
        + "\n" + "Number of Players: " + this.players.size() + "\n";
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
  public String addPlayer(String playerName, int maxItemsLimit, PlayerType playerType) {
    if (players.size() >= maxPlayerLimit) {
      return "Max players limit reached!";
    }

    if (maxItemsLimit < 0) {
      throw new IllegalArgumentException("Max item limit cannot be negative!");
    }

    int roomIndex = randomGenerator.getRandomNumberInRange(0, rooms.size() - 1);
    validateRoomIndex(roomIndex);

    if (playerName == null || playerName.isEmpty()) {
      throw new IllegalArgumentException("Player name cannot be null!");
    }

    if (playerName.equals(target.getName())) {
      throw new IllegalArgumentException("Player name cannot be the same as target name!");
    }

    for (Player player : players) {
      if (player.getPlayerName().equals(playerName)) {
        throw new IllegalArgumentException("Player name already exists!");
      }
    }

    Player player = new PlayerImpl(playerName, roomIndex, maxItemsLimit, playerType);
    players.add(player);
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

  @Override
  public String movePlayer(int roomIndex) {
    Player currentPlayer = players.get(currentPlayerIndex);
    List<Integer> neighborRooms = getNeighboursOfRoom(currentPlayer.getRoomIndex());

    if (!neighborRooms.contains(roomIndex)) {
      throw new IllegalArgumentException("Please provide a valid neighbor room index!");
    }

    validateRoomIndex(roomIndex);

    Room currentRoom = rooms.get(currentPlayer.getRoomIndex());
    currentRoom.removePlayer(currentPlayer);

    currentPlayer.setRoomIndex(roomIndex);

    Room newRoom = rooms.get(roomIndex);
    newRoom.addPlayer(currentPlayer);

    updateTurn(true);

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
    graph.setStartingRoom(this.rooms.get(0));
    return "Start game successfully!";
  }

  @Override
  public OperationResult pickItem(int itemId) {
    Player currentPlayer = players.get(currentPlayerIndex);
    int roomIndex = currentPlayer.getRoomIndex();
    Room currentRoom = rooms.get(roomIndex);

    List<Integer> itemIds = currentRoom.getItems().stream().map(Item::getId)
        .collect(Collectors.toList());

    if (!itemIds.contains(itemId)) {
      throw new IllegalArgumentException("Please select a valid item ID!");
    }

    Item chosenItem = items.get(itemId);
    currentPlayer.addItem(chosenItem);
    currentRoom.deleteItem(chosenItem);

    updateTurn(true);

    return new OperationResult(true, "Item picked successfully!");
  }

  @Override
  public String lookAround() {
    Player currentPlayer = players.get(currentPlayerIndex);
    Room currentRoom = rooms.get(currentPlayer.getRoomIndex());
    List<Room> neighbours = currentRoom.getNeighbours();

    StringBuilder sb = new StringBuilder();

    if (!neighbours.isEmpty()) {
      sb.append("Details on neighbor rooms of you are: \n").append("--------------------\n");
      for (Room neighbor : neighbours) {
        if (neighbor.getIndex() == pet.getRoomIndex()) {
          sb.append("Room Name: ").append(neighbor.getName()).append("\n");
          sb.append("Room Index: ").append(neighbor.getIndex()).append("\n");
          sb.append("Occupied by the pet!").append("\n");
        }
        sb.append(neighbor.displayRoomDescription());
        sb.append("--------------------\n");
      }
    } else {
      sb.append("No neighbors for this space. \n");
    }

    updateTurn(true);
    return sb.toString();
  }

  private void updateTurn(boolean movePet) {
    if (currentTurn > maxTurns || target.getHealth() <= 0) {
      programState = ProgramState.FINALIZING;
      return;
    }

    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    if (rooms.size() > 1) {
      moveTarget();
    }
    currentTurn++;

    if (movePet) {
      // move pet
      Room nextRoom = graph.getNextRoom();
      if (nextRoom != null) {
        pet.updateRoom(nextRoom.getIndex());
      }
      logger.info("pet: " + pet.getRoomIndex());
    }
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
      throw new IllegalArgumentException("No items in current room!");
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
    for (Player player : players) {
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
    Player player = getCurrentPlayer();
    if (target.getRoomIndex() == player.getRoomIndex()) {
      Optional<Item> highestDamageItem = player.getItemsList().stream()
          .max(Comparator.comparingInt(Item::getDamage));
      OperationResult result;
      if (highestDamageItem.isPresent()) {
        result = attackTarget(String.valueOf(highestDamageItem.get().getId()));
      } else {
        result = attackTarget("p");
      }
      if (result.isSuccess()) {
        return "Computer player attacked target. " + result.getDetails();
      }
    }

    int randomOperateIndex = randomGenerator.getRandomNumberInRange(0, 2);
    switch (randomOperateIndex) {
      case 0:
        // pick item
        Room currentRoom = rooms.get(player.getRoomIndex());
        if (currentRoom.getItems().isEmpty()) {
          return computerPlayerTurn();
        }
        int randomItemIndex = randomGenerator.getRandomNumberInRange(0,
            currentRoom.getItems().size() - 1);
        Item pickedItem = currentRoom.getItems().get(randomItemIndex);
        OperationResult result = pickItem(pickedItem.getId());
        if (result.isSuccess()) {
          return "Computer player picked item " + pickedItem.getName();
        } else {
          return computerPlayerTurn();
        }
      case 1:
        // look around
        return "Computer player looked around\n\n" + lookAround();
      case 2:
        // move
        List<Integer> neighbors = getNeighboursOfRoom(player.getRoomIndex());
        if (neighbors.isEmpty()) {
          return computerPlayerTurn();
        }
        int randomNeighborIndex = randomGenerator.getRandomNumberInRange(0, neighbors.size() - 1);
        movePlayer(neighbors.get(randomNeighborIndex));
        return "Computer player moved to room " + neighbors.get(randomNeighborIndex);
      default:
        return "Invalid operation";
    }
  }

  @Override
  public String displayPrepareMessage() {
    int remainingTurns = getMaxTurns() - getTurnCount();
    String playerName = getCurrentPlayer().getPlayerName();

    Room currentRoom = rooms.get(getCurrentPlayer().getRoomIndex());
    StringBuilder sb = new StringBuilder();
    sb.append("Current Room Name: ").append(currentRoom.getName()).append("\n");
    sb.append("Current Room Index: ").append(currentRoom.getIndex()).append("\n");

    List<Item> items = currentRoom.getItems();
    if (!items.isEmpty()) {
      sb.append(currentRoom.displayItems()).append("\n");
    } else {
      sb.append("No items in this room\n");
    }

    List<Player> players = currentRoom.getPlayers();
    if (players.size() > 1) {
      sb.append("Other Players in this Room: ");
      for (Player player : players) {
        if (player != getCurrentPlayer()) {
          sb.append(player.getPlayerName()).append(" ");
        }
      }
      sb.append("\n");
    } else {
      sb.append("No other players in this room.\n");
    }

    return String.format("\nNumber of remaining turns: %d\nCurrent turn: %s\n%s", remainingTurns,
        playerName, sb);
  }

  @Override
  public void setPet(PetImpl pet) {
    this.pet = pet;
  }

  @Override
  public String movePet(int roomIndex) {
    if (roomIndex < 0 || roomIndex >= rooms.size()) {
      throw new IllegalArgumentException("Invalid room index!");
    }

    Room room = rooms.get(roomIndex);
    pet.updateRoom(roomIndex);
    graph.setStartingRoom(room);
    updateTurn(false);

    return "Pet moved to room " + roomIndex;
  }

  @Override
  public OperationResult attackTarget(String chosenItem) {
    Room currentRoom = rooms.get(getCurrentPlayer().getRoomIndex());
    Room targetRoom = rooms.get(target.getRoomIndex());

    if (currentRoom != targetRoom) {
      throw new IllegalArgumentException("Target is not in the same room!");
    }

    if (currentRoom.getPlayers().size() > 1) {
      return new OperationResult(false, "Target is not alone in the room!");
    }

    if (this.pet.getRoomIndex() == currentRoom.getIndex()) {
      return new OperationResult(false, "Pet in the room!");
    }

    if (currentRoom.getNeighbours().stream().allMatch(room -> room.getPlayers().isEmpty())) {
      if (Objects.equals(chosenItem, "p")) {
        target.updateHealth(target.getHealth() - 1);
      } else {
        int itemId = Integer.parseInt(chosenItem);
        if (itemId < 0 || itemId >= items.size()) {
          throw new IllegalArgumentException("Invalid item index!");
        }
        Item item = items.get(itemId);
        target.updateHealth(target.getHealth() - item.getDamage());
        getCurrentPlayer().removeItem(item);
      }
      updateTurn(true);
      return new OperationResult(true, "Target health is updated to " + target.getHealth());
    }

    return new OperationResult(false, "Can be seen in the neighbor room!");
  }

  @Override
  public String displayItemsByCurrentPlayer() {
    Player player = getCurrentPlayer();
    List<Item> items = player.getItemsList();

    StringBuilder result = new StringBuilder();
    for (int i = 0; i < items.size(); i++) {
      Item item = items.get(i);
      result.append("ID: ").append(i).append(" Name: ").append(item.getName()).append(" Damage: ")
          .append(item.getDamage()).append("\n");
    }

    return result.toString();
  }

  @Override
  public String displayFinalMessage() {
    return "Game over. " + (target.getHealth() > 0
        ? "Tie game!"
        : getCurrentPlayer().getPlayerName() + " wins!");
  }
}
