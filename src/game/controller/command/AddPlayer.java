package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddPlayer implements Command {
  private String identifier;
  private KillDoctorLucky model;

  public AddPlayer(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    return new CommandResult(
        model.addPlayer(params.get("playerName"), Integer.parseInt(params.get("roomIndex")),
            Integer.parseInt(params.get("maxItemsLimit"))), false);
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    List<ParameterRequest> requests = new ArrayList<>();
    requests.add(new ParameterRequest("playerName", "Enter the player name: "));
    requests.add(new ParameterRequest("roomIndex", "Enter the room index: "));
    requests.add(new ParameterRequest("maxItemsLimit", "Enter the max items limit:"));
    return requests;
  }

  @Override
  public String getDescription() {
    return "Add Player";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
