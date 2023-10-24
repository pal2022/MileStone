package gametest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import game.ComputerControlledGame;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import world.Character;
import world.MockMansion;
import world.Player;
import world.Space;

/**
 * This is the junit test case for computer controlled game players.
 */
public class ComputerControlledGameTest {

  
  Character target;
  Space room1;
  Space room2;
  Space room3;
  Space room4;
  Player player1;
  Player player2;
  List<Player> allPlayers;
  private ComputerControlledGame game;
  private MockMansion mockMansion;

  /**
   * This is the set basic setup.
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
    
    player1 = new Player("Player1", 1, mockMansion);
    player2 = new Player("Player2", 2, mockMansion);
    
    mockMansion.addPlayer(player1);
    mockMansion.addPlayer(player2);
    
    mockMansion.assignCharacter(target);
    game = new ComputerControlledGame();
    
  }

  /**
   * This test checks if the game starts properly.
   */
  @Test
  public void gameStart() {
    assertEquals(1, mockMansion.getTurnNumber());
    assertEquals(mockMansion.players.get(0), mockMansion.getCurrentPlayer());
    assertNotNull(mockMansion.targetCharacter);
    assertEquals(4, mockMansion.getRoomCount());
  }
  
  @Test
  public void testTargetCharacterMoves() {
    game.startGame();
    int expected = 0;
    assertEquals(expected, mockMansion.targetCharacter.getRoomId());
  }
  
  /**
   * Test is random numbers works properly.
   */
  @Test
  public void testRandomNumbers() {
    int numTrials = 10;
    for (int i = 0; i < numTrials; i++) {
      game.startGame();
      assertTrue("Random number out of range (random1)", validRandom1(game.getRandom1()));
      assertTrue("Random number out of range (random2)", validRandom2(game.getRandom2()));
    }
  }
  
  private boolean validRandom1(int random1) {
    return random1 >= 1 && random1 <= 6;
  }
  
  private boolean validRandom2(int random2) {
    return random2 >= 7 && random2 <= 16;
  }
  
  
}



