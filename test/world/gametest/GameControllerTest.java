package gametest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import game.GameController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import world.Character;
import world.Item;
import world.MockMansion;
import world.Player;
import world.Space;

/**
 * This is the junit test for game controller class.
 */
public class GameControllerTest {

  
  Character target;
  Space room1;
  Space room2;
  Space room3;
  Space room4;
  Player player1;
  Player player2;
  List<Player> allPlayers;
  private GameController game;
  private MockMansion mockMansion;

  /**
   * This is basic setup for test cases.
   */
  @Before
  public void setUp() {
    mockMansion = new MockMansion();
    target = new Character("100 TargetCharacter", 0);
    allPlayers = new ArrayList<>();
    
    room1 = new Space(0, "0 3 8 12 Nursery");
    room2 = new Space(1, "2 12 5 21 Gym");
    room3 = new Space(2, "24 27 27 33 Wine seller");
    room4 = new Space(3, "21 27 24 33 Secret room");
    
    mockMansion.addRoom(room1);
    mockMansion.addRoom(room2);
    mockMansion.addRoom(room3);
    mockMansion.addRoom(room4);
    
    room1.getItems().add(new Item("1 9 Healing Potion"));
    room2.getItems().add(new Item("1 9 Healing Potion"));
    room3.getItems().add(new Item("1 9 Healing Potion"));
    room4.getItems().add(new Item("1 9 Healing Potion"));
    
    player1 = new Player("Player1", 1, mockMansion);
    player2 = new Player("Player2", 2, mockMansion);
    
    player1.setItems(new ArrayList<>());
    player2.setItems(new ArrayList<>());
    
    mockMansion.addPlayer(player1);
    mockMansion.addPlayer(player2);
    
    mockMansion.assignCharacter(target);
    game = new GameController(mockMansion);
    
  }
  
  
  /**
   * This test is to check if the room are added in the world.
   */
  @Test
  public void testAddRoom() {
    assertTrue(mockMansion.rooms.containsValue(room1));
  }
  
  /**
   * This test is to check if the players are added in the world.
   */
  @Test
  public void testAddPlayer() {
    assertTrue(mockMansion.players.contains(player1));
  }
  
  /**
   * This test is to check if the target character is added in the world.
   */
  @Test
  public void testAssignCharacter() {
    String expected = "TargetCharacter";
    assertEquals(expected, mockMansion.targetCharacter.getName());
  }

  /**
   * This test is to check that the correct room is assigned to the target character.
   */
  @Test
  public void testGetPlayerRoomName() {
    String expected = room1.getName();
    assertEquals(expected, mockMansion.getTargetRoomName());
  }
  
  /**
   * This test checks if the total numbers in the mansion are same as the number of rooms added.
   */
  @Test
  public void testGetRoomCount() {
    int expected = 4;
    assertEquals(expected, mockMansion.getRoomCount());
  }
  
  /**
   * This test checks if the world returns all the rooms.
   */
  @Test
  public void testGetRooms() {
    String expected = "{0=Nursery, 1=Gym, 2=Wine seller, 3=Secret room}";
    assertEquals(expected, mockMansion.rooms.toString());
  }
  
  /**
   * This checks the if rooms are returned correctly according to id.
   */
  @Test
  public void testGetRoomId() {
    String expected = "Nursery";
    assertEquals(expected, mockMansion.getRoomWithId(0).getName());
  }
  
  /**
   * This test is to check if playerTurn methods give turns to players correctly.
   */
  @Test
  public void testPlayerTurn() {
    assertEquals(player2, mockMansion.playerTurn());
    assertEquals(player1, mockMansion.playerTurn());
    assertEquals(player2, mockMansion.playerTurn());
    assertEquals(player1, mockMansion.playerTurn());
  }

  //test if game starts correctly
  @Test
  public void testGameStart() {
    assertEquals(1, mockMansion.getTurnNumber());
    assertEquals(mockMansion.players.get(0), mockMansion.getCurrentPlayer());
    assertNotNull(mockMansion.targetCharacter);
    assertEquals(4, mockMansion.getRoomCount());
  }
  
  
  /**
   * This tests the toString method of mockmansion.
   */
  @Test
  public void testToString() {
    String expectedToString = "Mansion name: MockMansion, number of rows: 25, number of columns: "
        + "25 and number of rooms: 4";
    assertEquals(expectedToString, mockMansion.toString());
  }
  
  /**
   * test if neighbours assigned properly.
   */
  @Test
  public void testAssignNeighbours() {
    Space room5 = new Space(4, "8 9 11 15 Craft room");
    mockMansion.addRoom(room5);
    mockMansion.assignNeighbours();
    assertTrue(room1.getNeighbours().contains(room5));
    assertTrue(room5.getNeighbours().contains(room1));

    assertTrue(!room2.getNeighbours().contains(room3));
    assertTrue(!room3.getNeighbours().contains(room2));
  }

  @Test
  public void testGetRows() {
    int expectedRows = 25;
    assertEquals(expectedRows, mockMansion.getRows());
  }

  @Test
  public void testGetColumns() {
    int expectedColumns = 25;
    assertEquals(expectedColumns, mockMansion.getColumns());
  }

  @Test
  public void testGetFilePathEmpty() {
    String emptyFilePath = "";
    InputStream is = System.in;
    System.setIn(new ByteArrayInputStream(emptyFilePath.getBytes()));
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos);
    PrintStream ps2 = System.out;
    System.setOut(ps);
    String filePath = game.getFilePath();
    System.setIn(is);
    System.setOut(ps2);
    assertEquals("", baos.toString().trim());
  }
    
}
