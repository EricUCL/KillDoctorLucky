package game.utils;

import game.model.Room;

/**
 * This interface represents a graph.
 */
public interface Graph {
  /**
   * Function for setting the starting room.
   *
   * @param room
   */
  void setStartingRoom(Room room);

  /**
   * Function for getting the next room.
   *
   * @return
   */
  Room getNextRoom();
}

