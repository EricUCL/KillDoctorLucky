package game.view.panels;

import game.model.Room;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;

/**
 * Panel that displays the game board.
 */
public class GameMapPanel extends JPanel {
  private List<Room> rooms;
  private int numRows;
  private int numColumns;

  public GameMapPanel(List<Room> rooms, int numRows, int numColumns) {
    this.rooms = rooms;
    this.numRows = numRows;
    this.numColumns = numColumns;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D graphics = (Graphics2D) g;

    // 计算缩放比例
    int scaleW = getWidth() / numColumns;
    int scaleH = getHeight() / numRows;
    int scale = Math.min(scaleW, scaleH); // 取最小值以保持房间比例

    // 设置背景颜色和填充
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, getWidth(), getHeight());

    // 设置边框和字体样式
    graphics.setStroke(new BasicStroke(2));
    graphics.setColor(Color.BLACK);
    graphics.setFont(new Font("", Font.PLAIN, scale / 3)); // 根据缩放调整字体大小

    for (Room room : rooms) {
      // 计算房间的位置和大小
      int scaledY1 = room.getUpperLeftRow() * scale;
      int scaledX1 = room.getUpperLeftCol() * scale;
      int rectangleWidth = (room.getLowerRightCol() - room.getUpperLeftCol() + 1) * scale;
      int rectangleHeight = (room.getLowerRightRow() - room.getUpperLeftRow() + 1) * scale;

      // 绘制房间的边框
      graphics.drawRect(scaledX1, scaledY1, rectangleWidth, rectangleHeight);

      // 绘制房间的名称和索引
      String text = room.getIndex() + "-" + room.getName();
      FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());
      int x = scaledX1 + (rectangleWidth - metrics.stringWidth(text)) / 2;
      int y = scaledY1 + ((rectangleHeight - metrics.getHeight()) / 2) + metrics.getAscent();
      graphics.drawString(text, x, y);
    }
  }
}
