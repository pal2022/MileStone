package world;

import java.util.Map;

/**
 * This is the interface for mansion and has all the required methods needed by the 
 * mansion class.
 */
public interface MansionInterface {
  
  /**
   * This method is used to add room in mansion.
   * @param room room of the mansion
   */
  void addRoom(Space room);
    
  /**
   * This method is for assigning the target character.
   * @param targetCharacter target character of the mansion
   */
  void assignCharacter(Character targetCharacter);
  
  /**
   * This method is used to get the room the target character is present in.
   * @return room name
   */
  String getTargetRoomName();
  
  /**
   * This method is used to assign neighbours of a given space.
   */
  void assignNeighbours();
    
  /**
   * This method is used to get number of rows in the mansion.  
   * @return rows
   */
  int getRows();
  
  /**
   * This method is used to get number of columns in the mansion.
   * @return columns
   */
  int getColumns();
  
  /**
   * This method is used to get the room count in the mansion.  
   * @return room count
   */
  int getRoomCount();
  
  /**
   * This method is used to get name of the mansion.
   * @return name
   */
  String getName();
  
  /**
   * This method is used to get map of rooms.  
   * @return rooms
   */
  Map<Integer, Space> getRooms();

  Space getRoomWithId(int id);
  
  void addPlayer(Player player);
  
  Player getCurrentPlayer();
  
  Player playerTurn();
  
  void displayPlayers();
  
}