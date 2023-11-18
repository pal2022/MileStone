package world;

/**
 * This interface is for defining the methods of a target character. 
 */
public interface CharacterInterface {
  
  boolean tcDead = false;

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
  
  /**
   * This method decreases the health of the target character.
   * @param damage the amount of health to be reduced
   */
  void takenDamage(int damage);

  /**
   * This method is for knowing if target is dead or not.
   * @return true if target is dead else false
   */
  boolean tcDead();
    
}