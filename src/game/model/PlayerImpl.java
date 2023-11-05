package game.model;

import game.constants.PlayerType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the player in the game.
 */
public class PlayerImpl implements Player {
  private final String playerName;
  private int roomIndex;
  private final int maxItems;
  private final List<Item> itemsList;
  private final PlayerType playerType;

  /**
   * Constructor for the PlayerImpl class.
   *
   * @param playerName name of the player
   * @param roomIndex  index of the room
   * @param maxItems   maximum number of items that the player can carry
   * @param playerType type of the player
   */
  public PlayerImpl(String playerName, int roomIndex, int maxItems, PlayerType playerType) {
    this.playerName = playerName;
    this.roomIndex = roomIndex;
    this.maxItems = maxItems;
    this.playerType = playerType;
    this.itemsList = new ArrayList<>();
  }

  @Override
  public List<Item> getItemsList() {
    return itemsList;
  }

  @Override
  public void addItem(Item item) {
    if (itemsList.size() >= maxItems) {
      throw new IllegalArgumentException("Items reached to upper limit！");
    }
    itemsList.add(item);
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public int getRoomIndex() {
    return roomIndex;
  }

  @Override
  public void setRoomIndex(int roomIndex) {
    this.roomIndex = roomIndex;
  }

  @Override
  public PlayerType getPlayerType() {
    return playerType;
  }

  @Override
  public int getItemLimit() {
    return maxItems;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Player)) {
      return false;
    }
    Player that = (Player) o;
    return this.getPlayerName().equals(that.getPlayerName());
  }

  @Override
  public int hashCode() {
    return this.getPlayerName().hashCode();
  }

  @Override
  public String displayPlayerDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Player Name: %s\n", this.playerName));
    sb.append(String.format("Player Type: %s\n", this.playerType));
    sb.append(String.format("Room Index: %d\n", this.roomIndex));
    if (!this.itemsList.isEmpty()) {
      sb.append(String.format("Items: %s \n",
          this.itemsList.stream().map(Item::getName).collect(Collectors.joining(", "))));
    } else {
      sb.append("Not carrying any items. \n");
    }
    sb.append(String.format("MaxItems: %d", maxItems));
    return sb.toString();
  }

  @Override
  public String removeItem(Item item) {
    if (itemsList.contains(item)) {
      itemsList.remove(item);
      return "Item removed successfully.";
    } else {
      return "Item not found.";
    }
  }
}
