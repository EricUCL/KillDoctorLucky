package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import game.model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to move player.
 */
public class MovePlayer implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  /**
   * This class represents the command to move player.
   *
   * @param identifier The identifier of the command.
   * @param model      The model to move player.
   */
  public MovePlayer(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    try {
      return new CommandResult(model.movePlayer(Integer.parseInt(params.get("roomIndex"))), false);
    } catch (IllegalArgumentException e) {
      return new CommandResult(e.getMessage(), true);
    }
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    Player currentPlayer = model.getCurrentPlayer();
    List<Integer> neighboursIndex = model.getNeighboursOfRoom(currentPlayer.getRoomIndex());
    StringBuilder promptMessage = new StringBuilder();
    promptMessage.append("All rooms you can move to: ");
    for (int roomIndex : neighboursIndex) {
      promptMessage.append(roomIndex).append(" ");
    }
    promptMessage.append("\nEnter the room index you want to move to: ");
    List<ParameterRequest> requests = new ArrayList<>();
    requests.add(new ParameterRequest("roomIndex", promptMessage.toString()));
    return requests;
  }

  @Override
  public String getDescription() {
    return "Move Player";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
