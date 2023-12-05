package game.view.listeners;

import game.controller.GuiGameControllerImpl;
import game.model.Player;
import game.model.Room;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

public class MouseClickListener extends MouseAdapter {
  private final Map<Ellipse2D, Player> playerShapes;
  private final GuiGameControllerImpl controller;
  private Map<Rectangle2D, Room> roomShapes = new HashMap<>();

  public MouseClickListener(Map<Ellipse2D, Player> playerShapes, Map<Rectangle2D, Room> roomShapes, GuiGameControllerImpl controller) {
    this.playerShapes = playerShapes;
    this.controller = controller;
    this.roomShapes = roomShapes;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    for (Map.Entry<Ellipse2D, Player> entry : playerShapes.entrySet()) {
      if (entry.getKey().contains(e.getPoint())) {
        Player clickedPlayer = entry.getValue();
        controller.handlePlayerClick(clickedPlayer);
        return;
      }
    }

    for (Map.Entry<Rectangle2D, Room> entry : roomShapes.entrySet()) {
      if (entry.getKey().contains(e.getPoint())) {
        Room clickedRoom = entry.getValue();
        controller.handleRoomClick(clickedRoom); // 调用控制器的方法处理房间点击
        return;
      }
    }
  }
}
