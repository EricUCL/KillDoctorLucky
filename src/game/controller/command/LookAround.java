package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;

import java.util.List;
import java.util.Map;

public class LookAround implements Command {
  private String identifier;
  private KillDoctorLucky model;

  public LookAround(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }
  @Override
  public CommandResult execute(Map<String, String> params) {
    return new CommandResult(model.lookAround(), false);
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    return null;
  }

  @Override
  public String getDescription() {
    return "Look Around";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
