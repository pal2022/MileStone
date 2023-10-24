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
  void move(Mansion world, int roomId, Player player);
  
  /**
   * This method is for moving the player to one of the neighbouring spaces for 
   *     ComputerControlledGame class.
   * @param world the world of game
   * @param roomId player space room id
   * @param player the player that wants to move
   */
  void computerMove(Mansion world, int roomId, Player player);
  
  /**
   * This method is to pick item available in the room.
   */
  void pickItem();
  
  /**
   * This method displays nearby method of a space.
   * @param player the player
   */
  void displayNearbySpace(Player player);
  
  /**
   * This method is used to get name of the player.
   * @return playername;
   */
  String getName();
  
  /**
   * This method is used to get the space of the player.
   * @return player space
   */
  Space playerSpace();
  
  /**
   * This method sets the max quantity of items that can be picked by the player.
   * @return maxValue
   */
  int setItemQuantity();
  
  /**
   * This methods displays the items picked by the player.
   */
  void displayPickedItems();
  
  /**
   * This method shows all the spaces visited by a player in order.
   */
  void displaySpacePath();
  
  void setItems(List<Item> items);

}
