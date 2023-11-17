package game.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for PetImpl class.
 */
public class PetImplTest {
  private Pet pet;

  @Before
  public void setUp() {
    pet = new PetImpl("Pet", 5);
  }

  @Test
  public void testUpdateRoom_ValidIndex() {
    pet.updateRoom(3);
    assertEquals(pet.getRoomIndex(), 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpdateRoom_NegativeIndex() {
    int roomIndex = -1;
    pet.updateRoom(roomIndex);
  }
}
