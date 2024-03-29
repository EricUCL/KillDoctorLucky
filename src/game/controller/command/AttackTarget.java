package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to pick item.
 */
public class AttackTarget implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  /**
   * This class represents the command to pick item.
   *
   * @param identifier The identifier of the command.
   * @param model      The model to pick item.
   */
  public AttackTarget(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    try {
      return new CommandResult((model.attackTarget(params.get("itemIndex")).getDetails()), false);
    } catch (IllegalArgumentException e) {
      return new CommandResult(e.getMessage(), true);
    }
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    List<ParameterRequest> requests = new ArrayList<>();
    String promptMessage = "Items in inventory: \n" + model.displayItemsByCurrentPlayer()
        + "\nEnter the item index you want to use or enter \"p\" for "
        + "poking the target in the eye which does 1 point of damage: ";
    requests.add(new ParameterRequest("itemIndex", promptMessage));
    return requests;
  }

  @Override
  public String getDescription() {
    return "Attack Target";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
