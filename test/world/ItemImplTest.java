package world;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This test for testing the methods of the item class. It also check the valid and invalid inputs.
 */
public class ItemImplTest {

  /**
   * This test checks the output when valid input is inserted.
   */
  @Test
  public void validInput() {
    ItemImpl item = new ItemImpl("5 15 Wand");
    assertEquals("Wand", item.getName());
    assertEquals(15, item.getDamage());
    assertEquals(5, item.getRoomId());
  }
  
  /**
   * This test throws AssertionError if string is added in place of number.
   */
  @Test(expected = AssertionError.class) 
  public void invalidInput() {
    ItemImpl item = new ItemImpl("Wand 15 7");
    assertEquals(7, item.getName());
    assertEquals(15, item.getDamage());
    assertEquals("Wand", item.getRoomId());
  }
  
  /**
   * This test checks if the method getName returns correct name of the item.
   */
  @Test
  public void testGetName() {
    ItemImpl item = new ItemImpl("5 5 Wand");
    assertEquals("Wand", item.getName());
  }

  /**
   * This test checks if the method getDamage returns correct damage power of the item.
   */
  @Test
  public void testGetDamage() {
    ItemImpl item = new ItemImpl("5 7 Wand");
    assertEquals(7, item.getDamage());
  }

  /**
   * This test checks if the method getRoomid returns correct roomid of the room of the item.
   */
  @Test
  public void testGetRoomId() {
    ItemImpl item = new ItemImpl("5 5 Wand");
    assertEquals(5, item.getRoomId());
  }
  
  /**
   * Test to check to string method implementation.
   */
  @Test
  public void testToString() {
    ItemImpl item = new ItemImpl("5 5 Wand");
    String expected = "Item name: Wand, item damage power: 5, item roomId: 5";
    assertEquals(expected, item.toString());
  }
}
