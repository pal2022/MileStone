package gametest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import game.GameController;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import world.CharacterImpl;
import world.CharacterPetImpl;
import world.ItemImpl;
import world.MansionInterface;
import world.MockMansion;
import world.PlayerImpl;
import world.SpaceImpl;

/**
 * This is the junit test for game controller class.
 */
public class GameControllerTest {

  
  CharacterImpl target;
  CharacterPetImpl pet;
  SpaceImpl room1;
  SpaceImpl room2;
  SpaceImpl room3;
  SpaceImpl room4;
  ItemImpl item1;
  ItemImpl item2;
  ItemImpl item3;
  ItemImpl item4;
  PlayerImpl player1;
  PlayerImpl player2;
  List<PlayerImpl> allPlayers;
  Readable readable;
  Appendable appendable;
  String input;
  private GameController game;
  private MansionInterface mockMansion;
  
  

  /**
   * This is basic setup for test cases.
   */
  @Before
  public void setUp() {
    input = "1\n" + "palkan\n" + "2\n" + Integer.parseInt("2") + "\n\n" 
       + Integer.parseInt("2") + "\n";

    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    InputStreamReader in = new InputStreamReader(inputStream);

    System.setIn(inputStream);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    mockMansion = new MockMansion(in, printStream, 3);
    target = new CharacterImpl(100, "TargetCharacter", 0);
    pet = new CharacterPetImpl("Pet", 22);
    allPlayers = new ArrayList<>();
    ((MockMansion) mockMansion).assignCharacter(target);
    ((MockMansion) mockMansion).assignPet(pet);
    
    room1 = new SpaceImpl(0, "0 3 8 12 Nursery");
    room2 = new SpaceImpl(1, "2 12 5 21 Gym");
    room3 = new SpaceImpl(2, "24 27 27 33 Wine seller");
    room4 = new SpaceImpl(3, "21 27 24 33 Secret room");
    
    mockMansion.addRoom(room1);
    mockMansion.addRoom(room2);
    mockMansion.addRoom(room3);
    mockMansion.addRoom(room4);
    
    item1 = new ItemImpl("1 9 Healing Potion");
    item2 = new ItemImpl("1 9 Healing Potion");
    item3 = new ItemImpl("2 9 Healing Potion");
    item4 = new ItemImpl("2 9 Healing Potion");
    
    room2.addItem(item1);
    room2.addItem(item2);
    room3.addItem(item3);
    room3.addItem(item4);
    
    player1 = new PlayerImpl("Player1", 1, mockMansion);
    player2 = new PlayerImpl("Player2", 2, mockMansion);
    
    game = new GameController(10, "file.txt");
  }
  
  
  /**
   * This test is to check if the room are added in the world.
   */
  @Test
  public void testAddRoom() {
    assertTrue(mockMansion.getRooms().containsValue(room1));
  }
  
  /**
   * This test is to check if the players are added in the world.
   */
  @Test
  public void testAddPlayer() {
    mockMansion.addPlayer(player1);
    assertTrue(mockMansion.getPlayers().contains(player1));
  }
  
  /**
   * This test is to check if the target character is added in the world.
   */
  @Test
  public void testAssignCharacter() {
    String expected = "TargetCharacter";
    assertEquals(expected, mockMansion.getTargetName());
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
    assertEquals(expected, mockMansion.getRooms().toString());
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
    mockMansion.addPlayer(player1);
    mockMansion.addPlayer(player2);
    assertEquals(player2, mockMansion.playerTurn());
    assertEquals(player1, mockMansion.playerTurn());
    assertEquals(player2, mockMansion.playerTurn());
    assertEquals(player1, mockMansion.playerTurn());
  }

  @Test
  public void testGameStart() {
    assertEquals(1, mockMansion.getTurnNumber());
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
    SpaceImpl room5 = new SpaceImpl(4, "8 9 11 15 Craft room");
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
  
  @Test
  public void loopEndsWhenMaxTurnsReached() {
    mockMansion.addPlayer(player1);
    mockMansion.addPlayer(player2);
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    InputStream systemIn = System.in;
    try {
      System.setIn(inputStream);
      game.startGame(mockMansion);     
    } finally {
      System.setIn(systemIn);
    }
  }
  
}
