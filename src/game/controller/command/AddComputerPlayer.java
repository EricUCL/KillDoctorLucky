package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to add a computer player.
 */
public class AddComputerPlayer implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  public AddComputerPlayer(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    return new CommandResult(
        model.addComputerPlayer(), false);
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    return Collections.emptyList();
  }

  @Override
  public String getDescription() {
    return "Add Computer Player";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
