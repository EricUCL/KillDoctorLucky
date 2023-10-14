package game.controller;

import game.model.KillDoctorLucky;

import java.util.List;
import java.util.Map;

public interface Command {
  CommandResult execute(Map<String, String> params);
  List<ParameterRequest> requiredParameters();
  String getDescription();
  String getIdentifier();
}
