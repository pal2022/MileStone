package world;

import java.util.HashMap;
import java.util.Map;

/** This class for storing the information of the mansion.
 * It has methods assign character and rooms to the mansion and get neighbours of the room.
 */
public class Mansion implements MansionInterface {

  public Map<Integer, Space> rooms = new HashMap<Integer, Space>();
  public Character targetCharacter;

  private int rows;
  private int columns;
  private int roomCount;
  private String mansionName;
  
  
  
  /**
   * This construct takes the required values of the fields obtained from the file.
   * @param rows rows of mansion
   * @param columns columns of mansion
   * @param roomCount number of rooms in mansion
   * @param mansionName name of the mansion
   */
  public Mansion(int rows, int columns, int roomCount, String mansionName) {
    this.rows = rows;
    this.columns = columns;
    this.roomCount = roomCount;
    this.mansionName = mansionName;
  }
  
  /**
   * This method is for adding room in the mansion.
   */
  @Override
  public void addRoom(Space room) {
    this.rooms.put(room.getId(), room);
  }

  /**
   * This method is for assigning the target character of the mansion.
   */
  @Override
  public void assignCharacter(Character targetCharacter) {
    this.targetCharacter = targetCharacter;
  }

  /**
   * This method is for getting room name of the room in which the player is.
   */
  @Override
  public String getPlayerRoomName() {
    Space room = rooms.get(this.targetCharacter.getRoomId());
    return room.getName();
  }

  /**
   * This method is for assigning neighbours of a room.
   */
  @Override
  public void assignNeighbours() {
    for (int i = 0; i < this.roomCount; i++) {
      Space room1 = rooms.get(i);
      for (int j = 0; j < getRoomCount(); j++) {
        Space room2 = rooms.get(j);
        if (room2.getId() == room1.getId()) {
          continue;
        }
        int x1 = room1.getX1();
        int x2 = room1.getX2();
        int x3 = room2.getX1();
        int x4 = room2.getX2();
        
        int y1 = room1.getY1();
        int y2 = room1.getY2();
        int y3 = room2.getY1();
        int y4 = room2.getY2();
        
        //+1 to account wall width
        if ((x1 <= (x4 + 1)) && (x3 <= (x2 + 1)) 
            && (y1 <= y4 + 1) && (y3 <= y2 + 1)) {
          room1.addNeighbour(room2);
          room2.addNeighbour(room1);
        }
      }
    }
  }

  /**
   * This method is for getting numbers of rows of the mansion.
   */
  @Override
  public int getRows() {
    return this.rows;
  }

  /**
   * This method is for getting numbers of columns of the mansion.
   */
  @Override
  public int getColumns() {
    return this.columns;
  }

  /**
   * This method is for getting numbers of rooms of the mansion.
   */
  @Override
  public int getRoomCount() {
    return this.roomCount;
  }

  /**
   * This method is for getting name of the mansion.
   */
  @Override
  public String getName() {
    return this.mansionName;
  }

  /**
   * This method is for getting list of rooms of the mansion.
   */
  @Override
  public Map<Integer, Space> getRooms() {
    return this.rooms;
  }

  @Override 
  public  String toString() {
    return String.format("Mansion name: %s, number of rows: %d, number of columns: %d "
    + "and number of rooms: %d", getName(), getRows(), getColumns(), getRoomCount());  
  }
  
}
