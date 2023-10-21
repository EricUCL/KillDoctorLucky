package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import game.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PickItem implements Command {
  private String identifier;
  private KillDoctorLucky model;

  public PickItem(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    try {
      return new CommandResult(model.pickItem(Integer.parseInt(params.get("itemIndex"))), false);
    } catch (Exception e) {
      return new CommandResult(e.getMessage(), true);
    }
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    List<ParameterRequest> requests = new ArrayList<>();
    StringBuilder promptMessage = new StringBuilder();
    promptMessage.append(model.getItemsInCurrentRoom());
    promptMessage.append("\nEnter the item index you want to pick up: ");
    requests.add(new ParameterRequest("itemIndex", promptMessage.toString()));
    return requests;
  }

  @Override
  public String getDescription() {
    return "Pick up Item";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
