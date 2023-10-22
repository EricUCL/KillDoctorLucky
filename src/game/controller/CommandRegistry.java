package game.controller;

import game.constants.ProgramState;
import game.controller.command.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

/**
 * Maintains a registry of commands mapped to specific game states. Allows the association of
 * multiple commands with a particular game state and retrieval of commands associated with a given
 * state.
 */
class CommandRegistry {

  private final Map<ProgramState, List<Command>> commandsByState = new HashMap<>();

  /**
   * Registers a command to be associated with a specific game state.
   *
   * If the state is already associated with some commands, the new command is appended to the list.
   * Otherwise, a new list is created for the state and the command is added to it.
   *
   * @param state   The game state with which the command is to be associated.
   * @param command The command to be associated with the given state.
   */
  public void registerCommand(ProgramState state, Command command) {
    commandsByState.computeIfAbsent(state, k -> new ArrayList<>()).add(command);
  }

  /**
   * Retrieves the list of commands associated with a specific game state.
   *
   * If no commands are associated with the provided state, an empty list is returned.
   *
   * @param state The game state for which to retrieve associated commands.
   * @return List of commands associated with the specified state or an empty list if none exist.
   */
  public List<Command> getCommands(ProgramState state) {
    return commandsByState.getOrDefault(state, Collections.emptyList());
  }
}
