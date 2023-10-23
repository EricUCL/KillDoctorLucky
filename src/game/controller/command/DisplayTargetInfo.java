package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to display target information.
 */
public class DisplayTargetInfo implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  /**
   * Constructs a display target info command.
   *
   * @param identifier the identifier of the command
   * @param model      the model to update
   */
  public DisplayTargetInfo(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    return new CommandResult(model.displayTargetInfo(), false);
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    return Collections.emptyList();
  }

  @Override
  public String getDescription() {
    return "Display Target Info";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
