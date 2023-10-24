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
public class SpaceTest {

  private Space space;
  
  /**
   * This method set values of a room to compare it with the values from the methods of
   * the class and then to compare if they the same.
   */
  @Before
  public void setValues() {
    int spaceId = 4;
    String roomInfo = "5 12 8 21 Treasure room";
    space = new Space(spaceId, roomInfo);
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
    Space space = new Space(0, "0 3 8 12 Nursery");
    Space neighbour1 = new Space(1, "2 12 5 21 Gym");
    space.addNeighbour(neighbour1);
    Space neighbour2 = new Space(4, "5 12 8 21 Treasure room");
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
    Item item = new Item("1 1 Poison"); 
    Item item2 = new Item("1 9 Healing Potion");
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
  
  /**
   * A test to verify that implementation can provide a description of a specific space
   * that does not have any neighbours.
   */
  @Test
  public void testNoNeighbours() {
    int spaceId = 30;
    String roomInfo = "13 3 16 6 Extra";
    Space space1 = new Space(spaceId, roomInfo);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();  
    PrintStream ps = new PrintStream(baos);
    PrintStream sout = System.out;
    System.setOut(ps);
    space1.displayNeighbours();
    String expected = "There are no neighbours for the room Extra\n";
    assertEquals(expected, baos.toString());
  }
}
