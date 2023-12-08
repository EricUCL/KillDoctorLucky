package game.controller;

import game.model.Player;
import game.model.Room;

/**
 * GuiGameController interface extends the GameController interface. It provides methods to handle
 * player and room clicks in the GUI.
 */
public interface GuiGameController extends GameController {

  /**
   * Handles the event when a player is clicked in the GUI.
   *
   * @param player The player that was clicked.
   */
  void handlePlayerClick(Player player);

  /**
   * Handles the event when a room is clicked in the GUI.
   *
   * @param room The room that was clicked.
   */
  void handleRoomClick(Room room);
}