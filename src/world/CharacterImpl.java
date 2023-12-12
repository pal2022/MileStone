package world;

/**
 * This Character class is for the target character. It is used to display the character
 * information and move the the character between space starting from the space having index 0;
 */
public class CharacterImpl implements CharacterInterface {

  Boolean tcDead = false; 
  private String name;
  private int health;
  private int roomId;
  private int count;
 
  
  /**
   * Passes the information from characterInfo to this class.
   * @param health health of the character
   * @param name name of the character
   * @param roomCount total rooms
   */
  public CharacterImpl(int health, String name, int roomCount) {
    this.count = roomCount;
    this.health = health;
    this.name = name;
    this.roomId = 0;
  }

  /**
   * This method is used to move the target character from one space to the next space.
   * When the last room is reached it sends the target character to the first room.
   */
  @Override
  public void movePlayer() {
    this.roomId = this.roomId + 1;
    if (roomId > (count - 1)) {
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
   * This method decreases the health of the target character.
   * @param damage the amount of health to be reduced
   */
  @Override
  public void takenDamage(int damage) {
    this.health = this.health - damage;
    if (this.health <= 0) {
      this.health = 0;
      tcDead = true;
    }
  }
  
  /**
   * This method is for knowing if target is dead or not.
   * @return true if target is dead else false
   */
  @Override
  public boolean tcDead() {
    return tcDead;
  }
  
  /**
   * A string representation of the character containing character's  name and health.
   * @return String
   */
  @Override
  public String toString() {
    return String.format("Character name: %s, health: %d", getName(), getHealth());
  }

  @Override
  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }

}
