package world;

import java.util.List;

/**
 * This interface has all the methods required by the spaces in the world.
 */
public interface SpaceInterface {
  
  /**
   * This method is for parsing the information from the text file.
   * @param roomInfo get information by reading the file
   */
  void parseRoomInformation(String roomInfo);
  
  /**
   * This method is for adding neighbours to the room.
   * @param room room of the mansion
   */
  void addNeighbour(SpaceImpl room);
  
  /**
   * This method is for adding item in the room.
   * @param item item of the room
   */
  void addItem(ItemImpl item);
  
  /**
   * This method is for removing item from the room.
   * @param item item to be removed
   */
  void removeItem(ItemImpl item);
  
  /**
   * Returns the x1 coordinate of the space.
   * @return x1 
   */
  int getX1();

  /**
   * Returns the x2 coordinate of the space.
   * 
   * @return x2 
   */
  int getX2();

  /**
   * Returns the y1 coordinate of the space.
   * 
   * @return y1
   */
  int getY1();
    
  /**
   * Returns the y2 coordinate of the space.
   * 
   * @return y2
   */
  int getY2();
  
  /**
   * Returns the id of the room.
   * 
   * @return space id
   */
  int getId();
  
  /**
   * Returns the name of the room.
   * 
   * @return space name
   */
  String getName();
   
  /**
   * Returns the list of neighbours of the room.
   * 
   * @return neighbours
   */
  List<SpaceInterface> getNeighbours();
  
  /** 
   * Returns the list of items of the room.
   * 
   * @return items
   */
  List<ItemImpl> getItems();

}