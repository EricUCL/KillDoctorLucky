package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to move pet.
 */
public class MovePet implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  /**
   * This class represents the command to move pet.
   *
   * @param identifier The identifier of the command.
   * @param model      The model to pick item.
   */
  public MovePet(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    try {
      return new CommandResult(model.movePet(Integer.parseInt(params.get("roomIndex"))), false);
    } catch (IllegalArgumentException e) {
      return new CommandResult(e.getMessage(), true);
    }
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    List<ParameterRequest> requests = new ArrayList<>();
    String promptMessage = "\nEnter the room index you want the pet move to: ";
    requests.add(new ParameterRequest("roomIndex", promptMessage));
    return requests;
  }

  @Override
  public String getDescription() {
    return "Move Pet";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}

