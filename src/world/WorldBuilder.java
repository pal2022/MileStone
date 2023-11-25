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
   * @param maxTurns max turns allowed
   * @return world object of Mansion class
   */
  public static MansionImpl build(String filePath, int maxTurns) {

    DocReader docreader = new DocReader(filePath);

    List<String> strlist = docreader.read();

    String mansionInfo = strlist.get(0);
    String characterInfo = strlist.get(1);
    String roomCountInfo = strlist.get(3);
    String petInfo = strlist.get(2);

    String[] mansionInfoList = mansionInfo.split("\\s+", 3);
    int mansionRows = Integer.parseInt(mansionInfoList[0]);
    int mansionCols = Integer.parseInt(mansionInfoList[1]);
    String mansionName = mansionInfoList[2];
    
    int roomCount = Integer.parseInt(roomCountInfo);
    //for skipping first 4 lines + the lines containing room information as next line has itemCount 
    final int itemCount = Integer.parseInt(strlist.get(4 + roomCount));
    int health = 0;
    String name = null;
    try {
      String[] characterInfoList = characterInfo.split("\\s+", 2);
      health = Integer.parseInt(characterInfoList[0]);
      name = characterInfoList[1];
    } catch (NumberFormatException e) {
      System.out.println("Invalid information" + characterInfo);
    }
    
    CharacterImpl target = new CharacterImpl(health, name, roomCount);
    CharacterPetImpl pet = new CharacterPetImpl(petInfo, roomCount);

    MansionImpl world = new MansionImpl(mansionRows, mansionCols, roomCount, mansionName,
        target, pet, maxTurns);

    for (int i = 0; i < roomCount; i++) {
      SpaceImpl room = new SpaceImpl(i, strlist.get(i + 4));
      world.addRoom(room);
    }
    
    world.assignNeighbours();

    for (int i = 0; i < itemCount; i++) {
      ItemImpl item = new ItemImpl(strlist.get(5 + roomCount + i));
      int roomId = item.getRoomId();
      if (roomId >= 0 && roomId < roomCount) {
        SpaceImpl room = world.rooms.get(item.getRoomId());
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
