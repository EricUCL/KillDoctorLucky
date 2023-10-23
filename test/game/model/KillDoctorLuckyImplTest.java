package game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import game.constants.PlayerType;
import game.constants.ProgramState;
import game.utils.RandomGenerator;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for KillDoctorLuckyImpl class.
 */
public class KillDoctorLuckyImplTest {
  private KillDoctorLuckyImpl killDoctorLucky;
  private KillDoctorLuckyImpl game;
  private RandomGenerator randomGenerator;

  @Before
  public void setUp() {
    randomGenerator = new RandomGenerator(1, 1, 2, 3, 2, 1);
    killDoctorLucky = new KillDoctorLuckyImpl(10, randomGenerator);
    game = new KillDoctorLuckyImpl(10, randomGenerator);
    readFile(killDoctorLucky);
    readFile(game);
  }

  void readFile(KillDoctorLucky killDoctorLucky) {
    try {
      String line;
      Readable fileReader = new InputStreamReader(new FileInputStream("res/mansion.txt"));
      BufferedReader br = new BufferedReader((Reader) fileReader);
      line = br.readLine();

      String[] worldInfo = line.trim().split("\\s+");
      killDoctorLucky.setNumCols(Integer.parseInt(worldInfo[0]));
      killDoctorLucky.setNumRows(Integer.parseInt(worldInfo[1]));
      killDoctorLucky.setWorldName(
          line.substring(worldInfo[0].length() + worldInfo[1].length() + 2));
      line = br.readLine();
      String[] targetInfo = line.trim().split("\\s+");
      int targetHealth = Integer.parseInt(targetInfo[0]);
      String targetName = line.substring(targetInfo[0].length() + 1);
      if (targetHealth <= 0) {
        throw new IllegalArgumentException("Target health can't be negative");
      }
      killDoctorLucky.setTarget(new TargetImpl(targetName, targetHealth, 0));

      // check numRooms not null or empty
      int numRooms = Integer.parseInt(br.readLine());
      if (numRooms <= 0) {
        throw new IllegalArgumentException("No rooms in the world");
      }
      for (int i = 0; i < numRooms; i++) {
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
        killDoctorLucky.addRooms(
            new RoomImpl(i, roomName, upperLeftRow, upperLeftCol, lowerRightRow, lowerRightCol));
      }

      int numItems = Integer.parseInt(br.readLine());
      for (int i = 0; i < numItems; i++) {
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
        killDoctorLucky.addItems(i, itemDamage, itemName, itemRoomIndex);
      }
      killDoctorLucky.initialMap();
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("File not found");
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (IllegalArgumentException e) {
      System.out.append(e.getMessage());
    }
  }

  //  @Test(expected = IllegalArgumentException.class)
  //  public void testOverlapX() throws IOException {
  //    new KillDoctorLuckyImpl("res/tests/x-overlap.txt", 2);
  //  }
  //
  //  @Test(expected = IllegalArgumentException.class)
  //  public void testOverlapY() {
  //    new KillDoctorLuckyImpl("res/tests/y-overlap.txt", 2);
  //  }
  //
  //  /**
  //   * Test to make sure any space overlaps in xy-axis.
  //   */
  //  @Test(expected = IllegalArgumentException.class)
  //  public void testOverlapXy() {
  //    new KillDoctorLuckyImpl("res/tests/xy-overlap.txt", 2);
  //  }
  //
  //  /**
  //   * Test to make sure any space overlaps with spaceInSpace.
  //   */
  //  @Test(expected = IllegalArgumentException.class)
  //  public void testOverlapRoomInRoom() {
  //    new KillDoctorLuckyImpl("res/tests/spaceInSpaceOverlap.txt", 2);
  //  }
  //
  //  /**
  //   * Test to make sure any space overlaps in xy-axis.
  //   */
  //  @Test(expected = IllegalArgumentException.class)
  //  public void testTargetHealth() {
  //    new KillDoctorLuckyImpl("res/tests/zeroTargetHealth.txt", 2);
  //  }
  //
  //  @Test(expected = IllegalArgumentException.class)
  //  public void testNegativeTargetHealth() {
  //    new KillDoctorLuckyImpl("res/tests/negativeTargetHealth.txt", 2);
  //  }
  //
  //  @Test(expected = IllegalArgumentException.class)
  //  public void testMoreRoomsThanExcepted() {
  //    new KillDoctorLuckyImpl("res/tests/moreSpaces.txt", 2);
  //  }
  //
  //  /**
  //   * Test to make sure target is initialized in the 0th index space.
  //   */
  //  @Test
  //  public void testTargetInitializedAtZero() {
  //    killDoctorLucky = new KillDoctorLuckyImpl("res/tests/fileToTest.txt", 2);
  //    int locationOfTarget = killDoctorLucky.getLocationOfTarget();
  //    int expectedSpaceName = 0;
  //    assertEquals(locationOfTarget, expectedSpaceName);
  //  }

  @Test
  public void overlaps() {
    Room room1 = new RoomImpl(1, "room1", 0, 1, 1, 3);
    Room room2 = new RoomImpl(2, "room2", 0, 1, 2, 2);
    assertTrue(killDoctorLucky.overlaps(room1, room2));
    Room room3 = new RoomImpl(3, "room3", 1, 3, 4, 4);
    assertTrue(killDoctorLucky.overlaps(room1, room3));
    Room room4 = new RoomImpl(4, "room4", 0, 3, 1, 4);
    assertTrue(killDoctorLucky.overlaps(room1, room4));
    // neighboring rooms are not overlapping
    Room room5 = new RoomImpl(5, "room5", 0, 4, 1, 5);
    assertFalse(killDoctorLucky.overlaps(room1, room5));
  }

  @Test
  public void isNeighbor() {
    Room room1 = new RoomImpl(1, "room1", 28, 0, 35, 5);
    Room room2 = new RoomImpl(2, "room2", 28, 12, 35, 19);
    assertFalse(killDoctorLucky.isNeighbor(room1, room2));

    Room room3 = new RoomImpl(3, "room3", 30, 6, 35, 11);
    assertTrue(killDoctorLucky.isNeighbor(room1, room3));

    Room room4 = new RoomImpl(4, "room4", 22, 19, 23, 26);
    Room room5 = new RoomImpl(5, "room5", 28, 26, 35, 29);
    assertFalse(killDoctorLucky.isNeighbor(room4, room5));

    Room room6 = new RoomImpl(6, "room6", 0, 3, 5, 8);
    Room room7 = new RoomImpl(7, "room7", 6, 3, 9, 8);
    assertTrue(killDoctorLucky.isNeighbor(room6, room7));
  }

  @Test
  public void getLocationOfTarget() {
    int expectedIdx = 0;
    assertEquals(expectedIdx, killDoctorLucky.getLocationOfTarget());
  }

  @Test
  public void getNeighboursOfRoom() {
    List<Integer> expected = new ArrayList<>();
    expected.add(11);
    expected.add(16);
    assertEquals(expected, killDoctorLucky.getNeighboursOfRoom(9));
  }

  @Test
  public void createBufferedImage() {
    String expected = "Successfully generate to ./image.png";
    assertEquals(expected, killDoctorLucky.createWorldImage());
  }

  @Test
  public void moveTarget() {
    String expected = "Target moved to room 1";
    assertEquals(expected, killDoctorLucky.moveTarget());
  }

  @Test
  public void displayItemInfo() {
    String expected = "Id: 0, Name: Crepe Pan, Damage: 3";
    assertEquals(expected, killDoctorLucky.displayItemInfo(0));
  }

  @Test
  public void getWorldDesc() {
    String expected = "World Name: Doctor Lucky's Mansion\n" + "Target Name: Doctor Lucky\n"
        + "Target Health: 50\n" + "Max Turns: 10\n" + "Number of Rooms: 21\n"
        + "Number of Items: 20\n" + "Number of Players: 0\n";
    assertEquals(expected, killDoctorLucky.getWorldDesc());
  }

  @Test
  public void displayTargetInfo() {
    String expected = "Name: Doctor Lucky, Health: 50, Room: 0";
    assertEquals(expected, killDoctorLucky.displayTargetInfo());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeMaxTurns() {
    new KillDoctorLuckyImpl(-1, randomGenerator);
  }

  @Test
  public void testInitialConditions() {
    assertEquals(1, killDoctorLucky.getTurnCount());
    assertEquals(10, killDoctorLucky.getMaxTurns());
    assertEquals(ProgramState.INIT, killDoctorLucky.getProgramState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartGameWithoutPlayers() {
    killDoctorLucky.startGame();
  }

  @Test
  public void testStartGameWithPlayers() {
    killDoctorLucky.addPlayer("John", 1, 5, PlayerType.HUMAN);
    killDoctorLucky.startGame();
    assertEquals(ProgramState.RUNNING, killDoctorLucky.getProgramState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePlayerToNonNeighborRoom() {
    killDoctorLucky.addPlayer("John", 1, 5, PlayerType.HUMAN);
    killDoctorLucky.startGame();
    killDoctorLucky.movePlayer(10);
  }

  @Test
  public void testPickItem() {
    killDoctorLucky.addPlayer("John", 1, 5, PlayerType.HUMAN);
    killDoctorLucky.startGame();
    String message = killDoctorLucky.pickItem(8);
    assertEquals("Item is picked successfully!", message);
  }

  @Test
  public void testLookAround() {
    killDoctorLucky.addPlayer("John", 1, 5, PlayerType.HUMAN);
    killDoctorLucky.startGame();
    String message = killDoctorLucky.lookAround();
    assertTrue(message.contains("Details on neighbor rooms of player are:"));
  }

  @Test
  public void testGetTurnCount() {
    assertEquals(1, killDoctorLucky.getTurnCount());
  }

  @Test
  public void testGetMaxTurns() {
    assertEquals(10, killDoctorLucky.getMaxTurns());
  }

  @Test
  public void testGetProgramState() {
    assertEquals(ProgramState.INIT, killDoctorLucky.getProgramState());
  }

  @Test
  public void testGetPlayer() {
    Player p = new PlayerImpl("John", 1, 5, PlayerType.HUMAN);
    killDoctorLucky.addPlayer("John", 1, 5, PlayerType.HUMAN);
    assertEquals(p, killDoctorLucky.getPlayer("John"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetNonexistentPlayer() {
    killDoctorLucky.getPlayer("Jane");
  }

  @Test
  public void testGetCurrentPlayer() {
    Player p1 = new PlayerImpl("John", 1, 5, PlayerType.HUMAN);
    killDoctorLucky.addPlayer("John", 1, 5, PlayerType.HUMAN);
    killDoctorLucky.addPlayer("Jane", 2, 5, PlayerType.HUMAN);
    assertEquals(p1, killDoctorLucky.getCurrentPlayer());
  }

  @Test
  public void testComputerPlayerTurn() {
    killDoctorLucky.addPlayer("Bot", 0, 5, PlayerType.COMPUTER);
    killDoctorLucky.startGame();
    String message = killDoctorLucky.computerPlayerTurn();
    assertNotNull(message);
  }

  @Test
  public void testDisplayPrepareMessage() {
    killDoctorLucky.addPlayer("John", 0, 5, PlayerType.HUMAN);
    String message = killDoctorLucky.displayPrepareMessage();
    assertTrue(message.contains("Turn Counter:"));
    assertTrue(message.contains("Current turn: John"));
  }

  @Test
  public void testGetItemsInCurrentRoom() {
    killDoctorLucky.addPlayer("John", 0, 5, PlayerType.HUMAN);
    String message = killDoctorLucky.getItemsInCurrentRoom();
    assertTrue(message.contains("Items in current room are:"));
  }

  @Test
  public void testStartGame() {
    game.addPlayer("John", 0, 5, PlayerType.HUMAN);
    String result = game.startGame();
    assertEquals("Start game successfully!", result);
    assertEquals(ProgramState.RUNNING, game.getProgramState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMovePlayerToInvalidRoomIndex() {
    game.addPlayer("John", 1, 5, PlayerType.HUMAN);
    game.movePlayer(999); // Assume 999 is an invalid room index
  }

  @Test
  public void testMovePlayer() {
    game.addPlayer("John", 1, 5, PlayerType.HUMAN);
    // Assume room 1 has a neighbor room 2
    String result = game.movePlayer(3);
    assertEquals("Player moved successfully", result);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPickNonexistentItem() {
    game.addPlayer("John", 0, 5, PlayerType.HUMAN);
    game.pickItem(999); // Assume 999 is an invalid item ID
  }

  @Test
  public void testUpdateTurnWithMultiplePlayers() {
    Player p2 = new PlayerImpl("John", 0, 5, PlayerType.HUMAN);
    Player p1 = new PlayerImpl("Jane", 1, 5, PlayerType.HUMAN);
    game.addPlayer("Jane", 1, 5, PlayerType.HUMAN);
    game.addPlayer("John", 0, 5, PlayerType.HUMAN);
    assertEquals(p1, game.getCurrentPlayer());
    game.updateTurn();
    assertEquals(p2, game.getCurrentPlayer());
    game.updateTurn();
    assertEquals(p1, game.getCurrentPlayer());
  }

  @Test
  public void testEndOfMaxTurns() {
    KillDoctorLuckyImpl tempGame = new KillDoctorLuckyImpl(1, randomGenerator);
    readFile(tempGame);
    tempGame.addPlayer("John", 0, 5, PlayerType.HUMAN);
    tempGame.updateTurn();
    assertEquals(ProgramState.FINALIZING, tempGame.getProgramState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddItemsToInvalidRoom() {
    game.addItems(1, 5, "Knife", 999); // Assuming 999 is an invalid room index
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerToInvalidRoom() {
    game.addPlayer("John", 999, 5, PlayerType.HUMAN); // Assuming 999 is an invalid room index
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddPlayerWithNegativeMaxItems() {
    game.addPlayer("John", 0, -1, PlayerType.HUMAN); // Assuming room 0 exists
  }

  @Test
  public void testMaxPlayerLimit() {
    for (int i = 0; i < game.getMaxPlayerLimit(); i++) {
      game.addComputerPlayer();
    }
    String result = game.addComputerPlayer();
    assertEquals("Max players limit reached!", result);
  }
}