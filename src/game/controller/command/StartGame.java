package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to start the game.
 */
public class StartGame implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  /**
   * Constructs a start game command.
   *
   * @param identifier the identifier of the command
   * @param model      the model to update
   */
  public StartGame(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    try {
      return new CommandResult(model.startGame(), false);
    } catch (IllegalArgumentException e) {
      return new CommandResult(e.getMessage(), true);
    }
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    return Collections.emptyList();
  }

  @Override
  public String getDescription() {
    return "Start Game";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
