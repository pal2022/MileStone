package world;

/**
 * This interface if for defining the methods required by the pet.
 */
public interface CharacterPetInterface {

  /**
   * This method is used for getting the pet's name.
   * @return pet name
   */
  String getName();
  
  /**
   * This method is for moving the pet as per the players choice.
   * @param givenRoomId the id of the room where player wants to send pet
   */
  void playerMovesPet(int givenRoomId);
  
  /**
   * This method is used to get the id of the room the pet is in.
   * @return room id
   */
  int getPetRoomId();
}
