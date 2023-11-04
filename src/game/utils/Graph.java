package game.utils;

import game.model.Room;

public interface Graph {
  void setStartingRoom(Room room);

  Room getNextRoom();
}

