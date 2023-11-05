//package game.model;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
//
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Test for TargetImpl class.
// */
//public class TargetImplTest {
//  private TargetImpl target;
//
//  @Test
//  public void reduceHealthWithInvalidDamage() {
//    try {
//      target.reduceHealth(-1);
//      fail("Expected IllegalArgumentException");
//    } catch (IllegalArgumentException e) {
//      assertEquals("Damage can't be negative!", e.getMessage());
//    }
//  }
//
//  @Test
//  public void testToString() {
//    String expected = "Name: target, Health: 50, Room: 1";
//    assertEquals(expected, target.toString());
//  }
//
//  @Before
//  public void setUp() {
//    target = new TargetImpl("target", 50, 1);
//  }
//}