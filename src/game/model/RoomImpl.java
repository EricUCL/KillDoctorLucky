package game.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the RoomImpl class. It implements the Room interface and has all the
 * methods which are required for the game to be played.
 */
public class RoomImpl implements Room {
  private final String name;
  private final int id;
  private List<Item> items;
  private List<Integer> neighborRooms;
  private int upperLeftRow;
  private int upperLeftCol;
  private int lowerRightRow;
  private int lowerRightCol;
  private List<Player> players;

  /**
   * Constructor for the RoomImpl class.
   *
   * @param id            index of the room
   * @param name          name of the room
   * @param upperLeftRow  upper left row of the room
   * @param upperLeftCol  upper left column of the room
   * @param lowerRightRow lower right row of the room
   * @param lowerRightCol lower right column of the room
   */
  public RoomImpl(int id, String name, int upperLeftRow, int upperLeftCol, int lowerRightRow,
      int lowerRightCol) {
    if (id < 0) {
      throw new IllegalArgumentException("Invalid room index");
    }
    // check name not null or empty
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name can't be empty");
    }
    // check coordinates not null or empty
    ValidateCoordinates(upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol);
    this.name = name;
    this.id = id;
    this.upperLeftRow = upperLeftRow;
    this.upperLeftCol = upperLeftCol;
    this.lowerRightRow = lowerRightRow;
    this.lowerRightCol = lowerRightCol;
    this.items = new ArrayList<>();
    this.neighborRooms = new ArrayList<>();
    this.players = new ArrayList<>();
  }

  /**
   * Function for validating coordinates.
   *
   * @param upperLeftRow  upper left row of the room
   * @param upperLeftCol  upper left column of the room
   * @param lowerRightRow lower right row of the room
   * @param lowerRightCol lower right column of the room
   */
  private void ValidateCoordinates(int upperLeftRow, int upperLeftCol, int lowerRightRow,
      int lowerRightCol) {
    if (upperLeftRow < 0 || upperLeftCol < 0 || lowerRightRow < 0 || lowerRightCol < 0) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
    if (upperLeftRow > lowerRightRow || upperLeftCol > lowerRightCol) {
      throw new IllegalArgumentException("Invalid coordinates");
    }
  }

  @Override
  public List<Integer> getNeighbours() {
    return this.neighborRooms;
  }

  @Override
  public Integer getIndex() {
    return this.id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public int getUpperLeftRow() {
    return upperLeftRow;
  }

  @Override
  public int getUpperLeftCol() {
    return upperLeftCol;
  }

  @Override
  public int getLowerRightRow() {
    return lowerRightRow;
  }

  @Override
  public int getLowerRightCol() {
    return lowerRightCol;
  }

  @Override
  public boolean equals(Room o) {
    if (o == null) {
      throw new IllegalArgumentException("Room object can't be null");
    }
    return this.id == o.getIndex() && Objects.equals(this.name, o.getName())
        && this.upperLeftCol == o.getUpperLeftCol() && this.upperLeftRow == o.getUpperLeftRow()
        && this.lowerRightCol == o.getLowerRightCol() && this.lowerRightRow == o.getLowerRightRow();
  }

  @Override
  public void setNeighborRooms(List<Integer> neighborRooms) {
    this.neighborRooms = neighborRooms;
  }

  @Override
  public void addItem(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Item can't be null");
    }
    this.items.add(item);
  }

  @Override
  public List<Item> getItems() {
    return this.items;
  }

  @Override
  public String toString() {
    // get all neighbors id
    StringBuilder neighbors = new StringBuilder();
    for (int neighbor : neighborRooms) {
      neighbors.append(neighbor);
      neighbors.append(" ");
    }
    StringBuilder players = new StringBuilder();
    for (Player player : this.players) {
      players.append(player.getPlayerName());
      players.append(" ");
    }
    return "name: " + name + ", id: " + id + "\nItems: " + items + ", neighbor rooms id: "
        + neighbors + "\nPlayers: " + players;
  }

  @Override
  public void addPlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player can not be null!");
    }
    this.players.add(player);
  }

  @Override
  public void removePlayer(Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player can't be null");
    }
    for (int i = 0; i < this.players.size(); i++) {
      Player player1 = this.players.get(i);
      if (player1.equals(player)) {
        players.remove(i);
        return;
      }
    }
  }
}
