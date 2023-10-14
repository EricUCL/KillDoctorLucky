package game.controller;
import game.controller.command.Command;

import java.util.*;

class CommandRegistry {
  private Map<ProgramState, List<Command>> commandsByState = new HashMap<>();

  public void registerCommand(ProgramState state, Command command) {
    commandsByState
        .computeIfAbsent(state, k -> new ArrayList<>())
        .add(command);
  }

  public List<Command> getCommands(ProgramState state) {
    return commandsByState.getOrDefault(state, Collections.emptyList());
  }
}
