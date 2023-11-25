package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

 
/**
 * This class is for creating test cases of the class Character.
 */
public class CharacterImplTest {

  private CharacterImpl targetCharacter;
  
  @Before
  public void setUp() {
    targetCharacter = new CharacterImpl(50, "Palkan Motwani", 22);
  }
  
  /**
   * This test checks for valid inputs.
   */
  @Test
  public void validInput() {
    assertEquals("Palkan Motwani", targetCharacter.getName());
    assertEquals(50, targetCharacter.getHealth());
  }
  
  /**
   * This test throws AssertionError if the format of input is not correct.
   */
  @Test(expected = AssertionError.class) 
  public void invalidInput() {
    CharacterImpl targetCharacter = new CharacterImpl(50, "44", 22);
    assertEquals("Palkan", targetCharacter.getHealth());
    assertEquals(50, targetCharacter.getName());
  }
  
  /**
   * This test is to check if the index of the space where the target character initially is is 0.
   */
  @Test
  public void testStartingIndex() {
    assertEquals(0, targetCharacter.getRoomId());
  }

  /**
   * This test is to check if the movePlayer method moves the character to the next room.
   */
  @Test
  public void testMovePlayer() {
    targetCharacter.movePlayer();
    targetCharacter.movePlayer();
    targetCharacter.movePlayer();
    assertEquals(3, targetCharacter.getRoomId());
  }

  /**
   * This test is to check the method getHealth returns correct health of the character. 
   */
  @Test
  public void testGetHealth() {
    assertEquals(50, targetCharacter.getHealth());
  }

  /**
   * This test is to check the method getRoomId returns correct room id of the 
   * room the character is in.
   */
  @Test
  public void testGetRoomId() {
    assertEquals(0, targetCharacter.getRoomId());
  }

  /**
   * This test is to check the method getName returns correct name of the character.
   */
  @Test
  public void testGetName() {
    assertEquals("Palkan Motwani", targetCharacter.getName());
  }

  /**
   * This test is to check if tostring method returns correct string.
   */
  @Test
  public void testToString() {
    assertEquals("Character name: Palkan Motwani, health: 50", targetCharacter.toString());
  }
  
  /**
   * This test verifies that the character moves from the last room in the index
   * list to room 0.
   */
  @Test
  public void endStart() {
    CharacterImpl targetCharacter = new CharacterImpl(50, "Target Character", 4);
    targetCharacter.movePlayer();
    targetCharacter.movePlayer();
    targetCharacter.movePlayer();
    targetCharacter.movePlayer();
    assertEquals(0, targetCharacter.getRoomId());
  }
  
  /**
   * This test is used to check if the health of the target character decreases correctly.
   */
  @Test
  public void takenDamage() {
    CharacterImpl tc = new CharacterImpl(50, "Target Character", 4);
    tc.takenDamage(20);
    int expected = 30;
    assertEquals(expected, tc.getHealth());
  }
  
  /**
   * A test that checks if the target health goes below 0 it becomes zero and tcDead
   * become true.
   */
  @Test
  public void testTcdead() {
    CharacterImpl tc = new CharacterImpl(50, "Target Character", 4);
    tc.takenDamage(60);
    int expected = 0;
    assertEquals(expected, tc.getHealth());
    assertTrue(tc.tcDead());
  }
}