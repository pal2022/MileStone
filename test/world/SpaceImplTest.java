package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;


/**
 * This test class to check methods of the class Space.
 */
public class SpaceImplTest {

  private SpaceImpl space;
  
  /**
   * This method set values of a room to compare it with the values from the methods of
   * the class and then to compare if they the same.
   */
  @Before
  public void setValues() {
    int spaceId = 4;
    String roomInfo = "5 12 8 21 Treasure room";
    space = new SpaceImpl(spaceId, roomInfo);
    MansionImpl world = new MansionImpl(10, 15, 14, "Pal's Mansion", null, null, 10);
  }
  
  /**
   * This test checks if correct information is parsed through the test class.
   */
  @Test
  public void testParseRoomInformation() {
    assertEquals(5, space.getX1());
    assertEquals(12, space.getY1());
    assertEquals(8, space.getX2());
    assertEquals(21, space.getY2());
    assertEquals("Treasure room", space.getName());
  }

  /**
   * This test checks if the neighbours of a room are added or not.
   */
  @Test
  public void testAddNeighbour() {
    SpaceImpl space = new SpaceImpl(0, "0 3 8 12 Nursery");
    SpaceImpl neighbour1 = new SpaceImpl(1, "2 12 5 21 Gym");
    space.addNeighbour(neighbour1);
    SpaceImpl neighbour2 = new SpaceImpl(4, "5 12 8 21 Treasure room");
    space.addNeighbour(neighbour2);
    assertEquals(2, space.getNeighbours().size());
    assertTrue(space.getNeighbours().contains(neighbour1));
    assertTrue(space.getNeighbours().contains(neighbour2));
  }
  
  /**
   * This test checks if item is added in the proper room.
   */
  @Test
  public void testAddItem() {
    ItemImpl item = new ItemImpl("1 1 Poison"); 
    ItemImpl item2 = new ItemImpl("1 9 Healing Potion");
    space.addItem(item);
    space.addItem(item2);
    assertEquals(2, space.getItems().size());
    assertTrue(space.getItems().contains(item));
    assertTrue(space.getItems().contains(item2));
  }
  
  /**
   * This test checks if the x1 coordinate of the space is what is expected.
   */
  @Test
  public void testGetX1() {
    int expected = 5;
    int actual = space.getX1();
    assertEquals(expected, actual);
  }

  /**
   * This test checks if the x2 coordinate of the space is what is expected.
   */
  @Test
  public void testGetX2() {
    int expected = 8;
    int actual = space.getX2();
    assertEquals(expected, actual);
  }

  /**
   * This test checks if the y1 coordinate of the space is what is expected.
   */
  @Test
  public void testGetY1() {
    int expected = 12;
    int actual = space.getY1();
    assertEquals(expected, actual);
  }

  /**
   * This test checks if the y2 coordinate of the space is what is expected.
   */
  @Test
  public void testGetY2() {
    int expected = 21;
    int actual = space.getY2();
    assertEquals(expected, actual);
  }

  /**
   * This test checks if the id of the space is what is expected.
   */
  @Test
  public void testGetId() {
    int expected = 4; 
    int actual = space.getId();
    assertEquals(expected, actual);
  }

  /**
   * This test checks if the name of the space is what is expected.
   */
  @Test
  public void testGetName() {
    String expected = "Treasure room";
    String actual = space.getName();
    assertEquals(expected, actual);
  }
  
}
