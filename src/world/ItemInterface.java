package world;

import java.util.List;

/**
 * This interface for defining methods for items in the world.
 */
public interface ItemInterface {
  
  /**
   * This method is used for assigning the room the item is in according to the coordinates
   * in the text file.
   * @param room the object of the Space class
   */
  void assignRoom(SpaceImpl room);
  
  /**
   * This method is used to get the id of the room in which the item is.
   * @return room id
   */
  int getRoomId();
  
  /**
   * This method is used to get the name of the item.
   * @return item name
   */
  String getName();  
  
  /**
   * This method is used to get the damage power of the item.
   * @return item damage
   */
  int getDamage();  
    
}