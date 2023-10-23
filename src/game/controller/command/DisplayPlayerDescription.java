package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;
import game.model.Player;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents the command to display player description.
 */
public class DisplayPlayerDescription implements Command {
  private final String identifier;
  private final KillDoctorLucky model;

  /**
   * Constructs a display player description command.
   *
   * @param identifier the identifier of the command
   * @param model      the model to update
   */
  public DisplayPlayerDescription(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    String playerName = params.getOrDefault("playerName", "");
    try {
      return new CommandResult(model.displayPlayerDescription(playerName), false);
    } catch (IllegalArgumentException e) {
      return new CommandResult(e.getMessage(), true);
    }
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    StringBuilder promptMessage = new StringBuilder();
    List<Player> players = model.getPlayers();
    for (Player player : players) {
      promptMessage.append(player.getPlayerName()).append("\n");
    }
    promptMessage.append("Enter the player name: ");
    return Collections.singletonList(new ParameterRequest("playerName", promptMessage.toString()));
  }

  @Override
  public String getDescription() {
    return "Display Player Description";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
