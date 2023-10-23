package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to create the world image.
 */
public class CreateWorldImage implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  /**
   * This class represents the command to create the world image.
   *
   * @param identifier The identifier of the command.
   * @param model      The model to create the world image.
   */
  public CreateWorldImage(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    return new CommandResult(model.createWorldImage(), false);
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    return Collections.emptyList();
  }

  @Override
  public String getDescription() {
    return "Create World Image";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
