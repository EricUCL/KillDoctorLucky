package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;

import java.util.Collections;
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
    try {
      return new CommandResult(model.lookAround(), false);
    } catch (Exception e) {
      return new CommandResult(e.getMessage(), true);
    }
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    return Collections.emptyList();
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
