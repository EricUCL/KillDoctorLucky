package game.controller.command;

import game.controller.Command;
import game.model.KillDoctorLucky;

public class DisplayWorldInfo implements Command {
  private String identifier;
  private KillDoctorLucky model;

  public DisplayWorldInfo(String identifier, KillDoctorLucky model) {
    this.identifier = identifier;
    this.model = model;
  }

  @Override
  public String execute() {
    return model.getWorldDesc();
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
