package world;

/**
 * This interface if for defining the methods required by the pet.
 */
public interface CharacterPetInterface {

  /**
   * This method moves the pet with every turn following a depth-first 
   * traversal of the spaces in the world.
   */
  void movePet();//depth first traversal
  
  /**
   * This method is used for getting the pet's name.
   * @return pet name
   */
  String getName();
  
  /**
   * This method is for moving the pet as per the players choice.
   */
  void playerMovesPet(int givenRoomId);
  
  int getPetRoomId();
}
