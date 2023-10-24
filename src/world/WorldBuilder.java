package world;

import java.util.List;

/**
 * This class build the world from the data collected from the file.
 * It assigns necessary information to other classes.
 */
public class WorldBuilder {

  /**
   * This method passes the filepath from the main class.
   * @param filePath path of the text file.
   * @return world object of Mansion class
   */
  public static Mansion build(String filePath) {

    DocReader docreader = new DocReader(filePath);

    List<String> strlist = docreader.read();

    String mansionInfo = strlist.get(0);
    String characterInfo = strlist.get(1);
    String roomCountInfo = strlist.get(2);

    String[] mansionInfoList = mansionInfo.split("\\s+", 3);
    int mansionRows = Integer.parseInt(mansionInfoList[0]);
    int mansionCols = Integer.parseInt(mansionInfoList[1]);
    String mansionName = mansionInfoList[2];
    
    int roomCount = Integer.parseInt(roomCountInfo);
    //for skipping first 3 lines + the lines containing room information as next line has itemCount 
    final int itemCount = Integer.parseInt(strlist.get(3 + roomCount));
    Mansion world = new Mansion(mansionRows, mansionCols, roomCount, mansionName);
    Character player = new Character(characterInfo, roomCount);

    world.assignCharacter(player);

    //in constructor of space
    for (int i = 0; i < roomCount; i++) {
      Space room = new Space(i, strlist.get(i + 3));
      world.addRoom(room);
    }
    
    world.assignNeighbours();

    for (int i = 0; i < itemCount; i++) {
      Item item = new Item(strlist.get(4 + roomCount + i));
      int roomId = item.getRoomId();
      if (roomId >= 0 && roomId < roomCount) {
        Space room = world.rooms.get(item.getRoomId());
        if (room != null) {
          item.assignRoom(room);
          room.addItem(item);
        } else {
          System.out.println("*"
              + "Invalid roomid: " + roomId);
        }
      } else {
        System.out.println("**Invalid roomid: " + roomId);
      }
    }
    
    return world;
  }

}
