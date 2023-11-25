package world;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is the test class for target character's pet.
 */
public class CharacterPetImplTest {
  String filePath = "C:\\Users\\hp\\eclipse-workspace\\lab00_getting_started\\"
      + "MileStone1\\model\\src\\run\\PalMansion.txt";
  MansionImpl world = WorldBuilder.build(filePath, 10);
  CharacterPetImpl pet = new CharacterPetImpl("Hello Kitty", 10);
  
  /**
   * Test to check getPetRoomId method returns correct room id.
   */
  @Test
  public void testGetPetRoomId() {
    int expected = 0;
    assertEquals(expected, pet.getPetRoomId());
  } 
  
  /**
   * Test to check that the pet moves to the correct room i.e 
   * to the room the player wants the pet to go.
   */
  @Test
  public void testPlayerMovePet() {
    pet.playerMovesPet(10);
    int expected = 10;
    assertEquals(expected, pet.getPetRoomId());
  }
  
  @Test
  public void testPetName() {
    String expected = "Hello Kitty";
    assertEquals(expected, pet.getName());
  }
  
}