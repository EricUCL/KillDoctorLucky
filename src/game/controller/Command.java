package game.controller;

import game.model.KillDoctorLucky;

public interface Command {
  String execute();
  String getDescription();
  String getIdentifier();
}
