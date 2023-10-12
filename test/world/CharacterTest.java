package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;


/**
 * This class is for creating test cases of the class Character.
 */
public class CharacterTest {

  /**
   * This test checks for valid inputs.
   */
  @Test
  public void validInput() {
    Character targetCharacter = new Character("50 Palkan Motwani");
    assertEquals("Palkan Motwani", targetCharacter.getName());
    assertEquals(50, targetCharacter.getHealth());
  }
  
  /**
   * This test throws AssertionError if the format of input is not correct.
   */
  @Test(expected = AssertionError.class) 
  public void invalidInput() {
    Character targetCharacter = new Character("Palkan 50");
    assertEquals("Palkan", targetCharacter.getHealth());
    assertEquals(50, targetCharacter.getName());
  }

  /**
   * This test is to check if the movePlayer method moves the character to the next room.
   */
  @Test
  public void testMovePlayer() {
    Character targetCharacter = new Character("50 Palkan Motwani");
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
    Character targetCharacter = new Character("50 Palkan Motwani");
    assertEquals(50, targetCharacter.getHealth());
  }

  /**
   * This test is to check the method getRoomId returns correct room id of the 
   * room the character is in.
   */
  @Test
  public void testGetRoomId() {
    Character targetCharacter = new Character("50 Palkan Motwani");
    assertEquals(0, targetCharacter.getRoomId());
  }

  /**
   * This test is to check the method getName returns correct name of the character.
   */
  @Test
  public void testGetName() {
    Character targetCharacter = new Character("50 Palkan Motwani");
    assertEquals("Palkan Motwani", targetCharacter.getName());
  }

  /**
   * This test is to check if tostring method returns correct string.
   */
  @Test
  public void testToString() {
    Character targetCharacter = new Character("50 Palkan Motwani");
    assertEquals("Character name: Palkan Motwani, health: 50", targetCharacter.toString());
  }
}