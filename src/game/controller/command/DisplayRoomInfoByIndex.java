package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to display room information.
 */
public class DisplayRoomInfoByIndex implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  /**
   * Constructs a display room info command.
   *
   * @param identifier the identifier of the command
   * @param model      the model to update
   */
  public DisplayRoomInfoByIndex(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    String roomIndex = params.getOrDefault("roomIndex", "");
    try {
      return new CommandResult(model.displayRoomDescription(Integer.parseInt(roomIndex)), false);
    } catch (IllegalArgumentException e) {
      return new CommandResult(e.getMessage(), true);
    }
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
