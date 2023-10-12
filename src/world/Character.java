package world;

/**
 * This Character class is for the target character. It is used to display the character
 * information and move the the character between space starting from the space having index 0;
 */
public class Character implements CharacterInterface {
  
  private String name;
  private int health;
  private int roomId;
  
  /**
   * Passes the information from characterInfo to this class.
   * @param characterInfo contains health and name of the character
   */
  public Character(String characterInfo) {
    parseCharacterInformation(characterInfo);
    this.roomId = 0;
  }
  
  /**
   * It is used for parsing the information from the file to the fields.
   */
  @Override
  public void parseCharacterInformation(String characterInfo) {  
    try {
      String[] characterInfoList = characterInfo.split("\\s+", 2);
      this.health = Integer.parseInt(characterInfoList[0]);
      this.name = characterInfoList[1];
    } catch (NumberFormatException e) {
      System.out.println("Invalid information" + characterInfo);
    }
  }

  /**
   * This method is used to move the target character from one space to the next space.
   * When the last room is reached it sends the target character to the first room.
   */
  @Override
  public void movePlayer() {
    this.roomId = this.roomId + 1;
    //22 is the last room id
    if (roomId > 22) {
      this.roomId = 0;
    }
  }

  /**
   * This method is used to get the health of the character.
   * @return health of the character
   */
  @Override
  public int getHealth() {
    return this.health;
  }

  /**
   * This method is used to get the id of the room the character is in.
   * @return room id
   */
  @Override
  public int getRoomId() {
    return this.roomId;
  }

  /**
   * This method is used to get the name of the character.
   * @return name of the character
   */
  @Override
  public String getName() {
    return this.name;
  }
  
  /**
   * A string representation of the character containing character's  name and health.
   * @return String
   */
  @Override
  public String toString() {
    return String.format("Character name: %s, health: %d", getName(), getHealth());
  }

}
