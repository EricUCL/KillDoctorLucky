package game.model;

import game.constants.PlayerType;

import java.util.List;

public interface Player {
  String getPlayerName();

  void setRoomIndex(int roomIndex);

  void movePlayer(int roomIdx);

  PlayerType getPlayerType();

  int getItemLimit();

  void removeItemByIndex(int itemIndex);

  void addItemByIndex(int itemIndex);

  List<Integer> getItemsIndexList();

  int getRoomIndex();

}
