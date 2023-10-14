package game.controller.command;

import game.controller.CommandResult;
import game.controller.ParameterRequest;
import game.model.KillDoctorLucky;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DisplayItemInfoByIndex implements Command{
  private String identifier;
  private KillDoctorLucky model;

  public DisplayItemInfoByIndex(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }
  @Override
  public CommandResult execute(Map<String, String> params) {
    String itemIndex = params.getOrDefault("itemIndex", "");
    return new CommandResult(model.displayItemInfo(Integer.parseInt(itemIndex)), false);
  }

  @Override
  public List<ParameterRequest> requiredParameters() {
    return Collections.singletonList(new ParameterRequest("itemIndex", "Enter the item index: "));
  }

  @Override
  public String getDescription() {
    return "Display Item Info By Index";
  }

  @Override
  public String getIdentifier() {
    return identifier;
  }
}
