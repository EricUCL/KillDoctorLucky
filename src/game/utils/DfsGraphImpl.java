package game.utils;

import game.model.Room;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class DfsGraphImpl implements Graph {
  private final Stack<Room> stack;
  private final Set<Room> visited;

  public DfsGraphImpl() {
    stack = new Stack<>();
    visited = new HashSet<>();
  }

  @Override
  public void setStartingRoom(Room room) {
    visited.clear();
    stack.clear();
    stack.push(room);
  }

  @Override
  public Room getNextRoom() {
    if (stack.isEmpty()) {
      return null; // All rooms have been visited or no start room has been set
    }

    Room currentRoom = stack.pop();
    visited.add(currentRoom);

    stack.addAll(
        currentRoom.getNeighbours().stream().filter(neighbor -> !visited.contains(neighbor))
            .collect(Collectors.toList()));

    return currentRoom;
  }
}
