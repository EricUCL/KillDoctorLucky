package game.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for RoomImpl class.
 */
public class RoomImplTest {
  private RoomImpl room;

  @Before
  public void setUp() {
    room = new RoomImpl(1, "room", 0, 0, 1, 1);
  }

  @Test
  public void testEquals() {
    Room room1 = new RoomImpl(1, "room", 0, 0, 1, 1);
    assertTrue(room.equals(room1));
    Room room2 = new RoomImpl(2, "room", 0, 0, 1, 1);
    assertFalse(room.equals(room2));
    Room room3 = new RoomImpl(1, "room1", 0, 0, 1, 1);
    assertFalse(room.equals(room3));
    Room room4 = new RoomImpl(1, "room", 1, 0, 1, 1);
    assertFalse(room.equals(room4));
    Room room5 = new RoomImpl(1, "room", 0, 1, 1, 1);
    assertFalse(room.equals(room5));
    Room room6 = new RoomImpl(1, "room", 0, 0, 2, 1);
    assertFalse(room.equals(room6));
    Room room7 = new RoomImpl(1, "room", 0, 0, 1, 2);
    assertFalse(room.equals(room7));
  }

}