package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This class for storing the information of the mansion.
 * It has methods assign character and rooms to the mansion and get neighbours of the room.
 */
public class MockMansion implements MansionInterface {

  public Map<Integer, Space> rooms = new HashMap<Integer, Space>();
  public Character targetCharacter;
  public final List<Player> players = new ArrayList<Player>();
  private int currentPlayer = 0;
  private int turnNumber = 1;
  private final StringBuilder log = new StringBuilder();
  
  
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
  public String getTargetRoomName() {
    Space room = rooms.get(this.targetCharacter.getRoomId());
    return room.getName();
  }

  /**
   * This method is for assigning neighbours of a room.
   */
  @Override
  public void assignNeighbours() {
    for (int i = 0; i < getRoomCount(); i++) {
      Space room1 = rooms.get(i);
      for (int j = 0; j < getRoomCount(); j++) {
        Space room2 = rooms.get(j);
        if (room2.getId() == room1.getId()) {
          continue;
        }
        if (room1.getX2() == room2.getX1() 
            && room1.getY1() <= room2.getY2() 
            && room2.getY1() <= room1.getY2()) {
          room1.addNeighbour(room2);
          room2.addNeighbour(room1);
        } else if (room1.getY2() == room2.getY1() 
            && room1.getX1() <= room2.getX2() 
            && room2.getX1() <= room1.getX2()) {
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
    return 25;
  }

  /**
   * This method is for getting numbers of columns of the mansion.
   */
  @Override
  public int getColumns() {
    return 25;
  }

  /**
   * This method is for getting numbers of rooms of the mansion.
   */
  @Override
  public int getRoomCount() {
    return rooms.size();
  }

  /**
   * This method is for getting name of the mansion.
   */
  @Override
  public String getName() {
    return "MockMansion";
  }

  /**
   * This method is for getting list of rooms of the mansion.
   */
  @Override
  public Map<Integer, Space> getRooms() {
    return rooms;
  }
  
  @Override
  public Space getRoomWithId(int id) {
    return rooms.get(id);
  }

  @Override
  public void addPlayer(Player player) {
    players.add(player);
    log.append("Player " + player.getName() + " added to the mansion");
  }
  
  @Override
  public Player getCurrentPlayer() {
    return players.get(currentPlayer);
  }
  
  @Override 
  public Player playerTurn() {
    if (currentPlayer + 1 >= players.size()) {
      this.turnNumber = this.turnNumber + 1;
    }
    currentPlayer = (currentPlayer + 1) % players.size();
    return getCurrentPlayer();
  } 
  
  public int getTurnNumber() {
    return turnNumber;
  }
  
  @Override
  public void displayPlayers() {
    System.out.println("Players in the mansion are : ");
    for (Player player : players) {
      log.append("\nPlayer name : " + player.getName());
      log.append("\nPlayer Space : " + player.playerSpace());
      log.append("\nPlayer items : ");
      player.displayPickedItems();
      log.append("\nPlayer visited spaces : ");
      player.displaySpacePath();
      
    }
  }
  
  @Override 
  public  String toString() {
    return String.format("Mansion name: %s, number of rows: %d, number of columns: %d "
    + "and number of rooms: %d", getName(), getRows(), getColumns(), getRoomCount());  
  }
  
  public String getLog() {
    return log.toString();
  }
  
}
