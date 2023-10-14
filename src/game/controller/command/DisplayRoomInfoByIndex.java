package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DisplayRoomInfoByIndex implements Command {
  private String identifier;
  private KillDoctorLucky model;

  public DisplayRoomInfoByIndex(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    String roomIndex = params.getOrDefault("roomIndex", "");
    return new CommandResult(model.displayRoomDescription(Integer.parseInt(roomIndex)), false);
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    return Collections.singletonList(new ParameterRequest("roomIndex", "Enter the room index: "));
  }

  @Override
  public String getDescription() {
    return "Display Room Info By Index";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
