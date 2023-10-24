package world;

import java.util.ArrayList;
import java.util.List;

/**
 * This is class for the rooms of the mansion.
 * It stores the information of the room and adds items that belong in the room 
 * and adds neighbours of the room.
 */
public class Space implements SpaceInterface {

  private int spaceId;
  private String spaceName;
  private int x1;
  private int x2;
  private int y1;
  private int y2;
  private List<Space> neighbours;
  private List<Item> items;
  
  /**
   * This constructor takes the required information obtained by reading the text file.
   * @param spaceId id of the room
   * @param roomInfo coordinates and room name
   */
  public Space(int spaceId, String roomInfo) {
    this.spaceId = spaceId;
    parseRoomInformation(roomInfo);
    this.neighbours = new ArrayList<Space>();
    this.items = new ArrayList<Item>();
  }
  
  public Space(int spaceid) {
    this.spaceId = spaceId;
  }
  
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

  @Override
  public void addNeighbour(Space room) {
    //because when assigning neighbours, there is bidirectional adding
    // so to save only one copy of room in the list
    if (this.neighbours.contains(room)) {
      return;
    }
    this.neighbours.add(room);
  }

  @Override
  public void addItem(Item item) {
    this.items.add(item);
  }
  
  @Override
  public void removeItem(Item item) {
    this.items.remove(item);
  }

  @Override
  public void displayInformation() {
    System.out.print("Name of the room is " + this.spaceName + "\n");
    if (this.items.size() == 0) {
      System.out.print("There are no items present in this room");
    } else {
      System.out.print("Items present in this room are : \n");
      for (int i = 0; i < this.items.size(); i++) {
        Item item = this.items.get(i);
        System.out.print(item.getName() + "\n");
      }
    }
    displayNeighbours();
    System.out.println("Players present in this room are :");
  }

  @Override
  public void displayNeighbours() {
    if (this.neighbours.size() == 0) {
      System.out.print("There are no neighbours for the room " + this.getName() + "\n");
    } else {
      System.out.print("Neighbours for this room are : \n");
      for (int i = 0; i < getNeighbours().size(); i++) {
        Space room = this.neighbours.get(i);
        System.out.print("Room : " + room.getName() + " with room id: " + room.getId() + "\n");
      }
    }
  }

  @Override
  public int getX1() {
    return this.x1;
  }

  @Override
  public int getX2() {
    return this.x2;
  }

  @Override
  public int getY1() {
    return this.y1;
  }

  @Override
  public int getY2() {
    return this.y2;
  }

  @Override
  public int getId() {
    return this.spaceId;
  }

  @Override
  public String getName() {
    return this.spaceName;
  }

  @Override
  public List<SpaceInterface> getNeighbours() {
    List neighbours = new ArrayList<>();
    for (Space neighbour : this.neighbours) {
      neighbours.add(neighbour);
    }
    return neighbours;
  }

  //I have added this method in item class should i remove it from here?
  @Override
  public List<Item> getItems() {
    List items = new ArrayList<>();
    for (Item item : this.items) {
      items.add(item);
    }
    return items;
  }

  @Override
  public String toString() {
    return getName();
  }
}
