package game.controller;

import java.io.IOException;

/**
 * This interface represents all the operations offered by a GameController. GameController
 * interface take cares of starting the game.
 */
public interface GameController {
  /**
   * Method to start the game.
   */
  void startGame() throws IOException;
}
