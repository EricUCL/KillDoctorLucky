package game.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the KillDoctorLuckyImpl class. It implements the KillDoctorLucky interface
 * and has all the methods which are required for the game to be played.
 */
public class KillDoctorLuckyImpl implements KillDoctorLucky {
  List<Item> items;
  private List<Room> rooms;
  private int numRows;
  private int numColumns;
  private int maxTurns;
  private String worldName;
  private Target target;

  /**
   * Constructor for the KillDoctorLuckyImpl class.
   *
   * @param filePath path of the file
   * @param maxTurns maximum number of turns
   * @throws IOException if file not found
   */
  public KillDoctorLuckyImpl(int maxTurns) {
    if (maxTurns < 0) {
      throw new IllegalArgumentException("Max turns can't be negative");
    }
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();
    this.maxTurns = maxTurns;
    //    readFile(filePath);
  }

  /**
   * Constructor for the KillDoctorLuckyImpl class.
   */
  public KillDoctorLuckyImpl() {
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();
    //    this.maxTurns = 100;
  }

  @Override
  public void initialMap() {
    for (Room room1 : rooms) {
      //        System.out.println(room1);
      List<Integer> neighbors = new ArrayList<>();
      for (Room room2 : rooms) {
        if (room1.equals(room2)) {
          continue;
        }
        if (overlaps(room1, room2)) {
          throw new IllegalArgumentException(
              "Rooms overlap: " + room1.getName() + " conflicts with " + room2.getName());
        }
        if (isNeighbor(room1, room2)) {
          neighbors.add(room2.getIndex());
        }
      }
      room1.setNeighborRooms(neighbors);
    }
  }

  @Override
  public boolean overlaps(Room current, Room other) {
    if (current == null || other == null) {
      throw new IllegalArgumentException("Room object can't be null");
    }
    return current.getUpperLeftRow() <= other.getLowerRightRow()
        && current.getLowerRightRow() >= other.getUpperLeftRow()
        && current.getUpperLeftCol() <= other.getLowerRightCol()
        && current.getLowerRightCol() >= other.getUpperLeftCol();
  }

  @Override
  public boolean isNeighbor(Room current, Room other) {
    if (current == null || other == null) {
      throw new IllegalArgumentException("Room object can't be null");
    }

    int currentLowerRightRow = current.getLowerRightRow();
    int currentLowerRightCol = current.getLowerRightCol();
    int currentUpperLeftRow = current.getUpperLeftRow();
    int currentUpperLeftCol = current.getUpperLeftCol();

    int otherLowerRightRow = other.getLowerRightRow();
    int otherLowerRightCol = other.getLowerRightCol();
    int otherUpperLeftRow = other.getUpperLeftRow();
    int otherUpperLeftCol = other.getUpperLeftCol();

    boolean rowNeighbor = (currentLowerRightRow + 1 == otherUpperLeftRow
        && currentLowerRightCol + 1 >= otherUpperLeftCol
        && currentUpperLeftCol - 1 <= otherLowerRightCol) || (
        currentUpperLeftRow - 1 == otherLowerRightRow
            && currentLowerRightCol + 1 >= otherUpperLeftCol
            && currentUpperLeftCol - 1 <= otherLowerRightCol);

    boolean colNeighbor = (currentLowerRightCol + 1 == otherUpperLeftCol
        && currentLowerRightRow + 1 >= otherUpperLeftRow
        && currentUpperLeftRow - 1 <= otherLowerRightRow) || (
        currentUpperLeftCol - 1 == otherLowerRightCol
            && currentLowerRightRow + 1 >= otherUpperLeftRow
            && currentUpperLeftRow - 1 <= otherLowerRightRow);

    return rowNeighbor || colNeighbor;
  }

  @Override
  public String displayRoomDescription(int roomIdx) {
    return rooms.get(roomIdx).toString();
  }

  @Override
  public int getLocationOfTarget() {
    return this.target.getRoomIdx();
  }

  @Override
  public List<Integer> getNeighboursOfRoom(int idx) {
    return this.rooms.get(idx).getNeighbours();
  }

  @Override
  public String createBufferedImage() {
    int scale = 25;
    int width = numColumns * (scale + 3);
    int height = numRows * (scale + 10);

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g = image.createGraphics();

    g.setColor(Color.WHITE); // set background to white
    g.fillRect(0, 0, width, height);
    g.setStroke(new BasicStroke(2));
    g.setColor(Color.BLACK); // set color to black

    for (Room room : rooms) {
      int scaledY1 = room.getUpperLeftRow() * scale;
      int scaledX1 = room.getUpperLeftCol() * scale;
      int rectangleWidth = (room.getLowerRightCol() - room.getUpperLeftCol() + 1) * scale;
      int rectangleHeight = (room.getLowerRightRow() - room.getUpperLeftRow() + 1) * scale;
      g.drawRect(scaledX1, scaledY1, rectangleWidth, rectangleHeight);

      final String text = String.format("%s-%s", room.getIndex(), room.getName());
      int fontSize = 14;
      FontMetrics metrics;
      g.setFont(g.getFont().deriveFont((float) fontSize));
      metrics = g.getFontMetrics(g.getFont());

      final int x = scaledX1 + (rectangleWidth - metrics.stringWidth(text) + 3) / 2;
      final int y = scaledY1 + ((rectangleHeight - metrics.getHeight()) / 2) + metrics.getAscent();
      g.setFont(new Font("", Font.PLAIN, 15));
      g.drawString(text, x, y);
    }

    String outputPath = "./image.png";

    try {
      ImageIO.write(image, "png", new File(outputPath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    return "Successfully generate to " + outputPath;
  }

  @Override
  public String moveTarget() {
    this.target.setRoom((this.target.getRoomIdx() + 1) % this.rooms.size());
    return "Target moved to room " + this.target.getRoomIdx();
  }

  @Override
  public String displayItemInfo(int itemIdx) {
    if (itemIdx < 0 || itemIdx >= items.size()) {
      throw new IllegalArgumentException("Invalid item index");
    }
    return items.get(itemIdx).toString();
  }

  @Override
  public String getWorldDesc() {
    StringBuilder sb = new StringBuilder();
    sb.append("World Name: ").append(this.worldName).append("\n");
    sb.append("Target Name: ").append(this.target.getName()).append("\n");
    sb.append("Target Health: ").append(this.target.getHealth()).append("\n");
    //    sb.append("Max Turns: ").append(this.maxTurns).append("\n");
    sb.append("Number of Rooms: ").append(this.rooms.size()).append("\n");
    sb.append("Number of Items: ").append(this.items.size()).append("\n");
    return sb.toString();
  }

  @Override
  public String displayTargetInfo() {
    return target.toString();
  }

  @Override
  public void setNumRows(int numRows) {
    this.numRows = numRows;
  }

  @Override
  public void setNumCols(int numColumns) {
    this.numColumns = numColumns;
  }

  @Override
  public void setWorldName(String worldName) {
    this.worldName = worldName;
  }

  @Override
  public void setTarget(Target target) {
    this.target = target;
  }

  @Override
  public void addRooms(Room room) {
    this.rooms.add(room);
  }

  @Override
  public void addItems(int itemDamage, String itemName, int itemRoomIndex) {
    if (itemRoomIndex < 0 || itemRoomIndex >= rooms.size()) {
      throw new IllegalArgumentException("Invalid Room Index!");
    }
    Item item = new ItemImpl(itemDamage, itemName, itemRoomIndex);
    this.items.add(item);
    rooms.get(itemRoomIndex).addItem(item);
  }
}
