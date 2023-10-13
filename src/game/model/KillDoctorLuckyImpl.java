package game.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * This class represents the KillDoctorLuckyImpl class. It implements the KillDoctorLucky interface
 * and has all the methods which are required for the game to be played.
 */
public class KillDoctorLuckyImpl implements KillDoctorLucky {
  List<Item> items;
  private List<Room> rooms;
  private int numRows;
  private int numColumns;
//  private int maxTurns;
  private String worldName;
  private Target target;

  /**
   * Constructor for the KillDoctorLuckyImpl class.
   *
   * @param filePath path of the file
   * @param maxTurns maximum number of turns
   * @throws IOException if file not found
   */
  public KillDoctorLuckyImpl(String filePath, int maxTurns) {
    if (filePath == null || filePath.isEmpty()) {
      throw new IllegalArgumentException("File path can't be empty");
    }
    if (maxTurns < 0) {
      throw new IllegalArgumentException("Max turns can't be negative");
    }
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();
//    this.maxTurns = maxTurns;
    readFile(filePath);
  }

  /**
   * Constructor for the KillDoctorLuckyImpl class.
   *
   * @param filePath path of the file
   * @throws IOException if file not found
   */
  public KillDoctorLuckyImpl(String filePath) {
    if (filePath == null || filePath.isEmpty()) {
      throw new IllegalArgumentException("File path can't be empty");
    }
    this.rooms = new ArrayList<>();
    this.items = new ArrayList<>();
//    this.maxTurns = 100;
    readFile(filePath);
  }

  /**
   * Function for reading the file and populating the data structures.
   *
   * @param filePath path of the file
   * @throws IllegalArgumentException if file not found
   */
  private void readFile(String filePath) {
    try {
      BufferedReader br = new BufferedReader(new FileReader(filePath));
      String line;

      line = br.readLine();

      String[] worldInfo = line.trim().split("\\s+");
      this.numColumns = Integer.parseInt(worldInfo[0]);
      this.numRows = Integer.parseInt(worldInfo[1]);
      worldName = line.substring(worldInfo[0].length() + worldInfo[1].length() + 2);
      line = br.readLine();
      String[] targetInfo = line.trim().split("\\s+");
      int targetHealth = Integer.parseInt(targetInfo[0]);
      String targetName = line.substring(targetInfo[0].length() + 1);
      if (targetHealth <= 0) {
        throw new IllegalArgumentException("Target health can't be negative");
      }
      this.target = new TargetImpl(targetName, targetHealth, 0);

      // check numRooms not null or empty
      int numRooms = Integer.parseInt(br.readLine());
      if (numRooms <= 0) {
        throw new IllegalArgumentException("No rooms in the world");
      }
      for (int i = 0; i < numRooms; i++) {
        //        System.out.println("Reading room " + i);
        line = br.readLine();
        String[] roomInfo = line.trim().split("\\s+");
        int upperLeftRow = Integer.parseInt(roomInfo[0]);
        int upperLeftCol = Integer.parseInt(roomInfo[1]);
        int lowerRightRow = Integer.parseInt(roomInfo[2]);
        int lowerRightCol = Integer.parseInt(roomInfo[3]);
        StringBuilder roomNameBuilder = new StringBuilder();
        for (int j = 4; j < roomInfo.length; j++) {
          roomNameBuilder.append(roomInfo[j]);
          if (j < roomInfo.length - 1) {
            roomNameBuilder.append(" ");
          }
        }
        String roomName = roomNameBuilder.toString();
        //        System.out.println("Room name: " + roomName);
        rooms.add(
            new RoomImpl(i, roomName, upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol));
      }
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

      int numItems = Integer.parseInt(br.readLine());
      for (int i = 0; i < numItems; i++) {
        //        System.out.println("Reading item " + i);
        line = br.readLine();
        String[] itemInfo = line.trim().split("\\s+");
        int itemRoomIndex = Integer.parseInt(itemInfo[0]);
        int itemDamage = Integer.parseInt(itemInfo[1]);
        StringBuilder itemNameBuilder = new StringBuilder();
        for (int j = 2; j < itemInfo.length; j++) {
          itemNameBuilder.append(itemInfo[j]);
          if (j < itemInfo.length - 1) {
            itemNameBuilder.append(" ");
          }
        }
        String itemName = itemNameBuilder.toString();
        //        System.out.println("Item name: " + itemName);
        if (itemRoomIndex < 0 || itemRoomIndex >= rooms.size()) {
          throw new IllegalArgumentException("Invalid room index");
        }
        items.add(new ItemImpl(itemDamage, itemName));
        rooms.get(itemRoomIndex).addItem(items.get(i));
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    } catch (IOException e) {
      throw new RuntimeException(e);
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
}
