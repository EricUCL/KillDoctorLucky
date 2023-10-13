package game.controller;

import game.model.KillDoctorLucky;

import java.io.IOException;

/**
 * This interface represents all the operations offered by a GameController. GameController
 * interface take cares of starting the game.
 */
public interface GameController {
  void startGame() throws IOException;
}
