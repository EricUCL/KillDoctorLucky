package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import game.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovePlayer implements Command {
  private String identifier;
  private KillDoctorLucky model;

  public MovePlayer(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    return new CommandResult(model.movePlayer(Integer.parseInt(params.get("roomIndex"))), false);
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    List<ParameterRequest> requests = new ArrayList<>();
    Player currentPlayer = model.getCurrentPlayer();
    List<Integer> neighbours = model.getNeighboursOfRoom(currentPlayer.getRoomIndex());
    StringBuilder promptMessage = new StringBuilder();
    promptMessage.append("All rooms you can move to: ");
    for (Integer roomIndex : neighbours) {
      promptMessage.append(roomIndex).append(" ");
    }
    promptMessage.append("\nEnter the room index you want to move to: ");
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
