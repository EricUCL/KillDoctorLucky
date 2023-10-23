package game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import game.constants.PlayerType;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit4 test class for {@link PlayerImpl}.
 */
public class PlayerImplTest {

  private PlayerImpl player;

  /**
   * Sets up a fresh PlayerImpl instance before each test.
   */
  @Before
  public void setUp() {
    player = new PlayerImpl("John", 1, 5, PlayerType.HUMAN);
  }

  /**
   * Test that an item can be successfully added to the player's item list.
   */
  @Test
  public void testAddItem() {
    Item item = new MockItem("Sword");
    player.addItem(item);
    assertEquals(1, player.getItemsList().size());
    assertTrue(player.getItemsList().contains(item));
  }

  /**
   * Test that adding items beyond the player's limit throws an exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddItemBeyondLimit() {
    for (int i = 0; i < 6; i++) {
      Item item = new MockItem("Item" + i);
      player.addItem(item);
    }
  }

  /**
   * Test that the player's name is correctly returned.
   */
  @Test
  public void testGetPlayerName() {
    assertEquals("John", player.getPlayerName());
  }

  /**
   * Test that setting and getting room index works correctly.
   */
  @Test
  public void testSetAndGetRoomIndex() {
    player.setRoomIndex(3);
    assertEquals(3, player.getRoomIndex());
  }

  /**
   * Test that the player's type is correctly returned.
   */
  @Test
  public void testGetPlayerType() {
    assertEquals(PlayerType.HUMAN, player.getPlayerType());
  }

  /**
   * Test that the equals method correctly identifies equal players based on their name.
   */
  @Test
  public void testEquals() {
    PlayerImpl anotherPlayer = new PlayerImpl("John", 2, 5, PlayerType.HUMAN);
    assertTrue(player.equals(anotherPlayer));
  }

  /**
   * Test that players with different names are correctly identified as not equal.
   */
  @Test
  public void testNotEquals() {
    PlayerImpl anotherPlayer = new PlayerImpl("Jane", 2, 5, PlayerType.HUMAN);
    assertFalse(player.equals(anotherPlayer));
  }

  /**
   * Test the correct display description of a player without any items.
   */
  @Test
  public void testDisplayPlayerDescriptionWithoutItems() {
    String description = player.displayPlayerDescription();
    assertTrue(description.contains("Player Name: John"));
    assertTrue(description.contains("Player Type: HUMAN"));
    assertTrue(description.contains("Room Index: 1"));
    assertTrue(description.contains("Not carrying any items."));
  }
}

