package world;

import java.awt.Choice;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This class for the players/users of the game.
 * It has users information, moves player method and pick item.
 */
public class PlayerImpl implements PlayerInterface {

  public MansionInterface world;
  private final String playerName;
  private int roomId;
  private SpaceImpl space;
  private List<ItemImpl> items;
  private List<String> visitedSpaces;
  private List<Integer> points;
  private int playerFirstSpace;
  private int x1;
  private int x2;
  private int y1;
  private int y2;
  

  /**
   * This constructor passes player name, starting space id and the world
   * the player is in.
   * @param playerName name of the player
   * @param spaceid id of the space of the player's first space 
   * @param world2 the world the player is in
   */
  public PlayerImpl(String playerName, int spaceid, MansionInterface world2) {
    this.playerName = playerName;
    this.roomId = spaceid;
    this.world = world2;
    this.items = new ArrayList<>();
    this.visitedSpaces = new ArrayList<>();
    this.points = new ArrayList<>();
    this.playerFirstSpace = spaceid;
  }

  /**
   * This method is for moving the player to one of the neighbouring spaces.
   * @param world2 the world of game
   * @param roomid player space room id
   * @param player the player that wants to move
   */
  @Override
  public void move(MansionInterface world2, int roomid, PlayerImpl player) {
    if (roomid >= 0 && roomid < world2.getRoomCount()) {
      
      List<SpaceInterface> neighbours = playerSpace().getNeighbours();
      int choice;
      
      System.out.println("Neighbouring spaces for " + playerSpace().getName() 
              + " of the player " + player.getName() + " are : ");
      for (int i = 0; i < neighbours.size(); i++) {
        if (world2.getPetRoomId() != neighbours.get(i).getId()) {
          System.out.println("\n" + neighbours.get(i).getId() + " : " 
                + neighbours.get(i).getName());
        }
      }
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter the room id of the room you wish to go to : ");
      
      if (player.getName().equals("Computer player")) {
        try {
          Random random = new Random();
          int i = random.nextInt(neighbours.size());
          SpaceInterface space = neighbours.get(i);
           
          if (space != null) {
            roomId = space.getId();
            System.out.println("Player " + this.playerName + " has moved to the room " 
                + space.getName());
          } else {
            System.out.println("Invalid input.");
          }

        } catch (IllegalArgumentException e) {
          System.out.println("Invalid input.");
        }
      } else {
        try {
          choice  = scanner.nextInt();
          SpaceInterface space = findSpaceById(neighbours, choice);
         
          if (space != null) {
            roomId = choice;
            System.out.println("Player " + this.playerName + " has moved to the room " 
                + space.getName());
          } else {
            System.out.println("Invalid input.");
          }

        } catch (IllegalArgumentException e) {
          System.out.println("Invalid input.");
          scanner.nextLine();
        }
      }
    }
    if (visitedSpaces != null) {
      visitedSpaces.add(world2.getRoomWithId(this.roomId).getName());
    } else {
      visitedSpaces.add(world2.getRoomWithId(this.playerFirstSpace).getName());
    }
  }
  
  /**
   * This method is to get the space having the id choden by
   * the player.
   * @param spaces list of spaces
   * @param id choice
   * @return space
   */
  private SpaceInterface findSpaceById(List<SpaceInterface> spaces, int id) {
    for (SpaceInterface space : spaces) {
      if (space.getId() == id) {
        return space;
      }
    }
    return null;
  }
  
  /**
   * This method is to pick item available in the room.
   */
  @Override
  public void pickItem(PlayerImpl player) {
    synchronized (items) {
      SpaceImpl currentRoom = world.getRoomWithId(this.roomId);
      if (currentRoom == null) {
        System.out.println("Invalid room ID: " + this.roomId);
        return;
      }
      List<ItemImpl> roomItems = world.getRoomWithId(this.roomId).getItems();
      if (roomItems.isEmpty()) {
        System.out.println("This room has no items.");
      } else {
        if (items.size() <= setItemQuantity(player)) {
          ItemImpl item = roomItems.get(0);
          world.getRoomWithId(this.roomId).removeItem(item);
          items.add(item);
          System.out.println(item.getName() + " has been picked by the player " + playerName);
        } else {
          System.out.println("You have reached your limit for picking items.");
        }
      }
    }
  }
  
  /**
   * This method is for removing the item from the items player is carrying
   * when that particular item is used for attacking the target.
   * @param item the item to be removed
   */
  @Override
  public void removePlayerItem(ItemImpl item) {
    this.items.remove(item);
  }
  
  /**
   * This method displays nearby method of a space.
   */

  @Override
  public void displayNearbySpace(PlayerImpl player, SpaceImpl space) {
    player.world.displayNeighbours(space);
    player.world.lookAround(space);
  }
  

  /**
   * This method is used to get name of the player.
   */
  @Override
  public String getName() {
    return this.playerName;
  }

  /**
   * This method is used to get the space of the player.
   */
  @Override
  public SpaceImpl playerSpace() {
    return this.world.getRoomWithId(this.roomId);
  }

  /**
   * This method sets the max quantity of items that can be picked by the player.
   */
  @Override
  public int setItemQuantity(PlayerImpl player) {
    if (player.playerName.startsWith("^[a-p].*")) {
      return 4;
    } else if (player.playerName.startsWith("^[q-y].*")) {
      return 2;
    }
    return 3;
  }

  /**
   * This methods displays the items picked by the player.
   */
  @Override
  public String displayPickedItems() {
    StringBuilder playerDescription = new StringBuilder();
    if (items.isEmpty()) {
      playerDescription.append("You haven't picked any items yet.");
    }
    for (ItemImpl item : items) {
      playerDescription.append("Item : ").append(item.getName());
    }
    return playerDescription.toString().trim();
  }
  
  /**
   * This methods gives the list of items.
   * @return items list of items
   */
  public List<ItemImpl> getItems() {
    return items; 
  }

  /**
   * This method shows all the spaces visited by a player in order.
   */
  @Override
  public void displaySpacePath() {
    System.out.println("Rooms visited by the player " + this.playerName + " are : ");
    for (String roomName : visitedSpaces) {
      System.out.println(roomName);
    }
  }

  /**
   * This method adds the points made by the player i.e damage power of the item
   * used to attack the target character.
   * @param damagePoints damage power of the item
   */
  @Override
  public void addPoints(int damagePoints) {
    this.points.add(damagePoints);
  }

  /**
   * This method counts the total points (total damage power of all the items 
   * used to attack the target.
   * @return total points
   */
  @Override
  public int returnTotalPoints() {
    int totalPoints = 0;
    for (int i = 0; i < points.size(); i++) {
      totalPoints += points.get(i);
    }
    return totalPoints;
  }
 
  /**
   * Sets roomId.
   * @param roomId to be set
   */
  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }
  
  /**
   * Get x2.
   * @return x2 value
   */
  public int getX2() {
    return x2;
  }

  /**
   * Set x2.
   */
  public void setX2(int x2) {
    this.x2 = x2;
  }
  
  /**
   * Get x1.
   * @return x1 value
   */
  public int getX1() {
    return x1;
  }

  /**
   * Set x1.
   * @param x1 value to be set
   */
  public void setX1(int x1) {
    this.x1 = x1;
  }

  /**
   * Get y2.
   * @return y2 value
   */
  public int getY2() {
    return y2;
  }

  /**
   * Set y2.
   * @param y2 value to be set
   */
  public void setY2(int y2) {
    this.y2 = y2;
  }
  
  /**
   * Get y1.
   * @return y1 value
   */
  public int getY1() {
    return y1;
  }

  /**
   * Set y1.
   * @param y1 value to be set
   */
  public void setY1(int y1) {
    this.y1 = y1;
  }
  
}
