package world;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * This is class for the rooms of the mansion.
 * It stores the information of the room and adds items that belong in the room 
 * and adds neighbours of the room.
 */
public class SpaceImpl implements SpaceInterface {

  private int spaceId;
  private String spaceName;
  private int x1;
  private int x2;
  private int y1;
  private int y2;
  private List<SpaceImpl> neighbours;
  private List<ItemImpl> items;
  private JComponent graphicalComponent;
  
  /**
   * This constructor takes the required information obtained by reading the text file.
   * @param spaceId id of the room
   * @param roomInfo coordinates and room name
   */
  public SpaceImpl(int spaceId, String roomInfo) {
    this.spaceId = spaceId;
    parseRoomInformation(roomInfo);
    this.neighbours = new ArrayList<SpaceImpl>();
    this.items = new ArrayList<ItemImpl>();
  }
  
  /**
   * This method is for parsing the information taken from the text file.
   * @param roomInfo string to be parsed
   */
  @Override 
  public void parseRoomInformation(String roomInfo) {
    //try catch
    roomInfo = roomInfo.stripLeading();
    String[] roomInfoList = roomInfo.split("\\s+", 5);
    
    this.x1 = Integer.parseInt(roomInfoList[0]);
    this.y1 = Integer.parseInt(roomInfoList[1]);
    this.x2 = Integer.parseInt(roomInfoList[2]);
    this.y2 = Integer.parseInt(roomInfoList[3]);
    this.spaceName = roomInfoList[4];
    
    //just in case x2 > x1 then would cause problem in  WorldImage logic
    if (this.x1 > this.x2) {
      int temp = this.x1;
      this.x1 = this.x2;
      this.x2 = temp;
    }
    
    if (this.y1 > this.y2) {
      int temp = this.y1;
      this.y1 = this.y2;
      this.y2 = temp;
    }
  }

  /**
   * This method is for assigning neighbour to a room.
   * @param room room to be added as neighbour
   */
  @Override
  public void addNeighbour(SpaceImpl room) {
    //because when assigning neighbours, there is bidirectional adding
    // so to save only one copy of room in the list
    if (this.neighbours.contains(room)) {
      return;
    }
    this.neighbours.add(room);
  }

  /**
   * This method is used to add item in the space.
   * @param item item to be added
   */
  @Override
  public void addItem(ItemImpl item) {
    this.items.add(item);
  }
  
  /**
   * This method is for removing item from the space.
   * This is called when the player picks an item from the space.
   * @param item the item to be removed
   */
  @Override
  public void removeItem(ItemImpl item) {
    this.items.remove(item);
  }


  /**
   * This method is for getting the x1 coordinate of space.
   */
  @Override
  public int getX1() {
    return this.x1;
  }
  
  /**
   * Get X1.
   * @param s multiplication factor
   * @return value multiplied value
   */
  public int getX1(int s) {
    return s * this.x1;
  }

  /**
   * This method is for getting the x2 coordinate of space.
   */
  @Override
  public int getX2() {
    return this.x2;
  }
  
  
  /**
   * Get x2 * s.
   * @param s multiplication factor
   * @return value multiplied value
   */
  public int getX2(int s) {
    return s * this.x2;
  }

  /**
   * This method is for getting the y coordinate of space.
   */
  @Override
  public int getY1() {
    return this.y1;
  }
  
  /**
   * Get y1 * s.
   * @param s multiplication factor
   * @return value multiplied value
   */
  public int getY1(int s) {
    return s * this.y1;
  }

  /**
   * This method is for getting the y2 coordinate of space.
   */
  @Override
  public int getY2() {
    return this.y2;
  }
  
  /**
   * Get y2 * s.
   * @param s multiplication factor
   * @return value multiplied value
   */
  public int getY2(int s) {
    return s * this.y2;
  }
  

  /**
   * This method is for getting the id of space.
   */
  @Override
  public int getId() {
    return this.spaceId;
  }

  /**
   * This method is for getting the name of space.
   */
  @Override
  public String getName() {
    return this.spaceName;
  }

  /**
   * This method is for getting the neighbours of a given space.
   */
  @Override
  public List<SpaceInterface> getNeighbours() {
    List<SpaceInterface> neighbours = new ArrayList<>();
    for (SpaceImpl neighbour : this.neighbours) {
      neighbours.add(neighbour);
    }
    return neighbours;
  }

  /**
   * This method is for getting the items of a given space.
   */
  @Override
  public List<ItemImpl> getItems() {
    List<ItemImpl> items = new ArrayList<>();
    for (ItemImpl item : this.items) {
      items.add(item);
    }
    return items;
  }

  @Override
  public String toString() {
    return getName();
  }
  
}
