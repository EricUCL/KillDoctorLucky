package game.model;

import game.constants.PlayerType;

import java.util.List;

public class PlayerImpl implements Player {
  private final String playerName;
  private int roomIndex;
  private final int maxItems;
  private List<Integer> itemsIndexList;
  private final PlayerType playerType;
  PlayerImpl(String playerName, int roomIndex, int maxItems, PlayerType playerType) {
    this.playerName = playerName;
    this.roomIndex = roomIndex;
    this.maxItems = maxItems;
    this.playerType = playerType;
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
  public int getRoom() {
    return roomIndex;
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
}
