package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;

import java.util.List;
import java.util.Map;

/**
 * Represents a game command that can be executed within the game's context. Each command can be
 * executed based on provided parameters, has a set of required parameters, a description, and a
 * unique identifier.
 */
public interface Command {

  /**
   * Executes the command based on the provided parameters.
   *
   * @param params A map of parameter names to their corresponding values.
   * @return The result of the command execution, encapsulated as a {@link CommandResult}.
   */
  CommandResult execute(Map<String, String> params);

  /**
   * Retrieves the list of parameters required for the command's execution.
   *
   * @return A list of {@link ParameterRequest} objects representing the required parameters.
   */
  List<ParameterRequest> requiredParameters();

  /**
   * Provides a textual description of the command.
   * <p>
   * This can be useful for informing players or users about what the command does.
   *
   * @return The description of the command.
   */
  String getDescription();

  /**
   * Returns a unique identifier for the command.
   * <p>
   * The identifier can be used to distinguish and select the command among multiple commands.
   *
   * @return The unique identifier of the command.
   */
  String getIdentifier();
}
