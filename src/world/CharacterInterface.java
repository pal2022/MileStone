package world;

/**
 * This interface is for defining the methods of a target character. 
 */
public interface CharacterInterface {
  
  /**
   * This method is used to pass the information from the text file to the fields of the class.
   * @param characterInfo stores the information of the line from the text file that holds the 
   *     information of the character.
   */
  void parseCharacterInformation(String characterInfo);

  /**
   * This method is used to move the target character from one space to the next space.
   */
  void movePlayer();
  
  /**
   * This method is used to get the name of the character.
   * @return character name
   */
  String getName();
  
  /**
   * This method is used to get the value of the health of the character.
   * @return health
   */
  int getHealth();
    
  /**
   * This method is used to get the room id of the room the character is in. 
   * @return room id
   */
  int getRoomId();
    
}