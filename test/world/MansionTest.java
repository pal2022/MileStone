package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;




/**
 * This test class is to test the methods of the Mansion class.
 */
public class MansionTest {

  private Mansion mansion;

  /**
   * Setting the values of the object of the mansion to use it for other test methods.
   */
  @Before
  public void setValues() {
    mansion = new Mansion(10, 15, 14, "Pal's Mansion");
  }
  
  /**
   * This test checks if the mansion class adds room.
   */
  @Test
  public void testAddRoom() {
    mansion = new Mansion(10, 15, 14, "Pal's Mansion");
    String roomInfo = "5 12 8 21 Treasure room";
    int spaceId = 4;
    Space room = new Space(spaceId, roomInfo);
    mansion.addRoom(room);
    Map<Integer, Space> rooms = mansion.getRooms();
    assertTrue(rooms.containsValue(room));
  }
  
  /**
   * This test checks if the mansion class assigns the target character properly. 
   */
  @Test
  public void testAssignCharacter() {
    mansion = new Mansion(10, 15, 14, "Pal's Mansion");
    String roomInfo = "5 12 8 21 Treasure room";
    Character character = new Character("50 Palkan Motwani");
    mansion.assignCharacter(character);
    assertEquals(character, mansion.targetCharacter);
  }
  
  /**
   * This test is to check if the mansion class provide correct number of rows.
   */
  @Test
  public void testGetRows() {
    assertEquals(10, mansion.getRows());
  }

  /**
   * This test is to check if the mansion class provide correct number of columns.
   */
  @Test
  public void testGetColumns() {
    assertEquals(15, mansion.getColumns());
  }

  /**
   * This test is to check if the mansion class provide correct amount of rooms in the mansion.
   */
  @Test
  public void testGetRoomCount() {
    assertEquals(14, mansion.getRoomCount());
  }

  /**
   * This test is to check if the mansion class provide correct name of the mansion.
   */
  @Test
  public void testGetName() {
    assertEquals("Pal's Mansion", mansion.getName());
  }

  @Test
  public void testToString() {
    String expected = "Mansion name: Pal's Mansion, number of rows: 10, number of columns: "
        + "15 and number of rooms: 14";
    assertEquals(expected, mansion.toString());
  }
}
