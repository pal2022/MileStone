package world;

import java.util.List;

/**
 * This is the interface for the class player. 
 */
public interface PlayerInterface {
 
  /**
   * This method is for moving the player to one of the neighbouring spaces.
   * @param world the world of game
   * @param roomId player space room id
   * @param player the player that wants to move
   */
  void move(MansionInterface world, int roomId, PlayerImpl player);
  
  /**
   * This method is to pick item available in the room.
   */
  void pickItem(PlayerImpl player);
  
  /**
   * This method displays nearby method of a space.
   * @param player the player
   */
  void displayNearbySpace(PlayerImpl player, SpaceImpl space);
  
  /**
   * This method is used to get name of the player.
   * @return player name;
   */
  String getName();
  
  /**
   * This method is used to get the space of the player.
   * @return player space
   */
  SpaceImpl playerSpace();
  
  /**
   * This method sets the max quantity of items that can be picked by the player.
   * @param player the player
   * @return maxValue
   */
  int setItemQuantity(PlayerImpl player);
  
  /**
   * This methods displays the items picked by the player.
   * @return list of items
   */
  String displayPickedItems();
  
  /**
   * This method shows all the spaces visited by a player in order.
   */
  void displaySpacePath();

  /**
   * This method is for removing the item from the items player is carrying
   * when that particular item is used for attacking the target.
   * @param item the item to be removed
   */
  void removePlayerItem(ItemImpl item);

  /**
   * This method adds the points made by the player i.e damage power of the item
   * used to attack the target character.
   * @param damagePoints damage power of the item
   */
  void addPoints(int damagePoints);

  /**
   * This method counts the total points (total damage power of all the items 
   * used to attack the target.
   * @return total points
   */
  int returnTotalPoints();
  
}
