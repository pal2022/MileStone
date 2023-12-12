package world;

/**
 * This Character class is for the target character's pet. 
 * It has the pet information and the method to move pet.
 */
public class CharacterPetImpl implements CharacterPetInterface {

  private String petName;
  private int roomId;
  private int roomCount;
  
  /**
   * Passes the name of the pet.
   * @param petName name of the pet
   */
  public CharacterPetImpl(String petName, int roomCount) {
    this.petName = petName;
    this.roomId = 0;
    this.roomCount = roomCount;
  }
  
  /**
   * This method is used for getting the pet's name.
   * @return pet name
   */
  @Override
  public String getName() {
    return this.petName;
  }

  /**
   * This method is for moving the pet as per the players choice.
   * @param givenRoomId the id of the room where player wants to send pet
   */
  @Override
  public void playerMovesPet(int givenRoomId) {
    if (givenRoomId >= 0 && givenRoomId < this.roomCount) {
      this.roomId = givenRoomId;
    } else {
      System.out.println("Invalid room id");
    }
  }
  
  /**
   * This method is used to get the id of the room the pet is in.
   * @return room id
   */
  @Override
  public int getPetRoomId() {
    return this.roomId;
  }

}
