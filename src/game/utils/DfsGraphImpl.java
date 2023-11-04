package game.utils;

import game.model.Room;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DfsGraphImpl implements Graph {
  private Stack<Room> stack;
  private Set<Room> visited;
  private Room startRoom;

  public DfsGraphImpl() {
    stack = new Stack<>();
    visited = new HashSet<>();
  }

  @Override
  public void setStartingRoom(Room room) {
    startRoom = room;
    visited.clear();
    stack.clear();
    stack.push(startRoom);
  }

  @Override
  public Room getNextRoom() {
    if (stack.isEmpty()) {
      return null; // All rooms have been visited or no start room has been set
    }

    Room currentRoom = stack.pop();
    visited.add(currentRoom);

    for (Room neighbor : currentRoom.getNeighbours()) {
      if (!visited.contains(neighbor)) {
        stack.push(neighbor);
      }
    }

    return currentRoom;
  }
}
