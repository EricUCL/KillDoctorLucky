package game.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test for ItemImpl class.
 */
public class ItemImplTest {

  @Test
  public void invalidDamage() {
    try {
      Item item = new ItemImpl(1, -1, "item");
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertEquals("Damage can't be negative!", e.getMessage());
    }
  }

}