package game.view.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import game.controller.GuiGameControllerImpl;
import game.model.KillDoctorLucky;
import game.model.Player;
import game.model.Room;
import game.model.Target;
import game.view.listeners.MouseClickListener;

/**
 * Panel that displays the game board.
 */
public class GameMapPanel extends JPanel {
  private List<Room> rooms;
  private int numRows;
  private Target target;
  private int numColumns;
  private Color[] playerColors = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE,
      Color.PINK, Color.CYAN };
  private Map<Ellipse2D, Player> playerShapes = new HashMap<>();

  private Map<Rectangle2D, Room> roomShapes = new HashMap<>();
  private Image targetImage;

  public GameMapPanel(KillDoctorLucky model, GuiGameControllerImpl controller) {
    this.rooms = model.getRooms();
    this.numRows = model.getNumRows();
    this.numColumns = model.getNumCols();
    this.target = model.getTarget();

    try {
      targetImage = ImageIO.read(new File("res/target.png"));
    } catch (IOException e) {
      System.err.println("Error loading target image: " + e.getMessage());
      targetImage = null;
    }

    MouseClickListener clickListener = new MouseClickListener(playerShapes, roomShapes, controller);
    addMouseListener(clickListener);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics = (Graphics2D) g;
    playerShapes.clear();
    roomShapes.clear();

    int scaleW = getWidth() / numColumns;
    int scaleH = getHeight() / numRows;
    int scale = Math.min(scaleW, scaleH);

    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, getWidth(), getHeight());

    graphics.setStroke(new BasicStroke(2));
    graphics.setColor(Color.BLACK);
    graphics.setFont(new Font("", Font.PLAIN, scale / 3));

    for (Room room : rooms) {
      int scaledY1 = room.getUpperLeftRow() * scale;
      int scaledX1 = room.getUpperLeftCol() * scale;
      int rectangleWidth = (room.getLowerRightCol() - room.getUpperLeftCol() + 1) * scale;
      int rectangleHeight = (room.getLowerRightRow() - room.getUpperLeftRow() + 1) * scale;

      graphics.setColor(Color.BLACK);
      graphics.drawRect(scaledX1, scaledY1, rectangleWidth, rectangleHeight);

      String text = room.getIndex() + "-" + room.getName();
      FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());
      int x = scaledX1 + (rectangleWidth - metrics.stringWidth(text)) / 2;
      int y = scaledY1 + ((rectangleHeight - metrics.getHeight()) / 2) + metrics.getAscent();
      graphics.drawString(text, x, y);

      Rectangle2D rect = new Rectangle2D.Float(scaledX1, scaledY1, rectangleWidth, rectangleHeight);
      roomShapes.put(rect, room);

      int playerSize = Math.max(scale / 4, 10);
      int playerX = scaledX1 + 5;
      int playerY = scaledY1 + rectangleHeight - playerSize - 5;

      int playerIndex = 0;
      for (Player player : room.getPlayers()) {
        Ellipse2D shape = new Ellipse2D.Float(playerX, playerY, playerSize, playerSize);
        playerShapes.put(shape, player);

        graphics.setColor(playerColors[playerIndex % playerColors.length]); // 使用玩家索引来获取颜色
        graphics.fill(shape);
        playerIndex++;

        playerX += playerSize + 5;
      }
    }

    if (targetImage != null && target != null) {
      Room targetRoom = rooms.get(target.getRoomIndex());
      int targetX = targetRoom.getUpperLeftCol() * scale;
      int targetY = targetRoom.getUpperLeftRow() * scale;

      int targetSize = Math.max(scale / 4, 20);
      Image scaledImage = targetImage.getScaledInstance(targetSize, targetSize, Image.SCALE_SMOOTH);

      targetX += (scale - targetSize) / 2;
      targetY += (scale - targetSize) / 2;

      graphics.drawImage(scaledImage, targetX, targetY, this);
    }
  }

}