package game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for KillDoctorLuckyImpl class.
 */
public class KillDoctorLuckyImplTest {
  private KillDoctorLuckyImpl killDoctorLucky;

  @Before
  public void setUp() {
    killDoctorLucky = new KillDoctorLuckyImpl("res/mansion.txt", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapX() throws IOException {
    new KillDoctorLuckyImpl("res/tests/x-overlap.txt", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverlapY() {
    new KillDoctorLuckyImpl("res/tests/y-overlap.txt", 2);
  }

  /**
   * Test to make sure any space overlaps in xy-axis.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapXy() {
    new KillDoctorLuckyImpl("res/tests/xy-overlap.txt", 2);
  }

  /**
   * Test to make sure any space overlaps with spaceInSpace.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testOverlapRoomInRoom() {
    new KillDoctorLuckyImpl("res/tests/spaceInSpaceOverlap.txt", 2);
  }

  /**
   * Test to make sure any space overlaps in xy-axis.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testTargetHealth() {
    new KillDoctorLuckyImpl("res/tests/zeroTargetHealth.txt", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeTargetHealth() {
    new KillDoctorLuckyImpl("res/tests/negativeTargetHealth.txt", 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoreRoomsThanExcepted() {
    new KillDoctorLuckyImpl("res/tests/moreSpaces.txt", 2);
  }

  /**
   * Test to make sure target is initialized in the 0th index space.
   */
  @Test
  public void testTargetInitializedAtZero() {
    killDoctorLucky = new KillDoctorLuckyImpl("res/tests/fileToTest.txt", 2);
    int locationOfTarget = killDoctorLucky.getLocationOfTarget();
    int expectedSpaceName = 0;
    assertEquals(locationOfTarget, expectedSpaceName);
  }

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
    assertEquals(expected, killDoctorLucky.createBufferedImage());
  }

  @Test
  public void moveTarget() {
    String expected = "Target moved to room 1";
    assertEquals(expected, killDoctorLucky.moveTarget());
  }

  @Test
  public void displayItemInfo() {
    String expected = "name: Crepe Pan, damage: 3";
    assertEquals(expected, killDoctorLucky.displayItemInfo(0));
  }

  @Test
  public void getWorldDesc() {
    String expected = "World Name: Doctor Lucky's Mansion\n" + "Target Name: Doctor Lucky\n"
        + "Target Health: 50\n" + "Max Turns: 2\n" + "Number of Rooms: 21\n"
        + "Number of Items: 20\n";
    assertEquals(expected, killDoctorLucky.getWorldDesc());
  }

  @Test
  public void displayTargetInfo() {
    String expected = "Name: Doctor Lucky, Health: 50, Room: 0";
    assertEquals(expected, killDoctorLucky.displayTargetInfo());
  }
}