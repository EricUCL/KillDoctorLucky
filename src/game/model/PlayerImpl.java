package game.model;

import game.constants.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerImpl implements Player {
  private final String playerName;
  private int roomIndex;
  private final int maxItems;
  private List<Item> itemsList;
  private final PlayerType playerType;

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

  PlayerImpl(String playerName, int roomIndex, int maxItems, PlayerType playerType) {
    this.playerName = playerName;
    this.roomIndex = roomIndex;
    this.maxItems = maxItems;
    this.playerType = playerType;
    this.itemsList = new ArrayList<>();
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public void addItemByIndex(int itemIndex) {

  }

  @Override
  public List<Integer> getItemsIndexList() {
    return null;
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
  public void movePlayer(int roomIdx) {

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
  public void removeItemByIndex(int itemIndex) {

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
  public String displayPlayerDescription(){
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Player Name: %s\n", this.playerName));
    sb.append(String.format("Player Type: %s\n", this.playerType));
    sb.append(String.format("Room Index: %d\n", this.roomIndex));
    if (!this.itemsList.isEmpty()) {
      sb.append(String.format("Items: %s \n", this.itemsList.stream()
              .map(Item::getName)
              .collect(Collectors.joining(", "))));
    } else {
      sb.append("Not carrying any items. \n");
    }
    sb.append(String.format("MaxItems: %d", maxItems));
    return sb.toString();
  }
}
