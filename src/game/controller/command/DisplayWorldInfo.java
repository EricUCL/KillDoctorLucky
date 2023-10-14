package game.controller.command;

import game.controller.Command;
import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DisplayWorldInfo implements Command {
  private String identifier;
  private KillDoctorLucky model;

  public DisplayWorldInfo(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public CommandResult execute(Map<String, String> params) {
    return new CommandResult(model.getWorldDesc(), false);
  }
//    return model.getWorldDesc();


  @Override
  public List<ParameterRequest> requiredParameters() {
    return Collections.emptyList();
//    return Collections.singletonList(new ParameterRequest("roomNumber", "Enter the room number: "));
  }

  @Override
  public String getDescription() {
    return "Displaying world info";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
