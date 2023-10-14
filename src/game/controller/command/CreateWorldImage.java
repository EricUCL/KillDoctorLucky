package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CreateWorldImage implements Command{
  private String identifier;
  private KillDoctorLucky model;
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
