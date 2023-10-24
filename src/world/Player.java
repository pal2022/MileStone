package world;

import java.awt.Choice;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This class for the players/users of the game.
 * It has users information, moves player method and pick item.
 */
public class Player implements PlayerInterface {

  public Mansion world;
  private String playerName;
  private int roomId;
  private Space space;
  private List<Item> items;
  private List<String> visitedSpaces;
  

  /**
   * This constructor passes player name, starting space id and the world
   * the player is in.
   * @param playerName name of the player
   * @param spaceid id of the space of the player's first space 
   * @param world the world the player is in
   */
  public Player(String playerName, int spaceid, Mansion world) {
    this.playerName = playerName;
    this.roomId = spaceid;
    this.world = world;
    this.items = new ArrayList<>();
    this.visitedSpaces = new ArrayList<>();
    this.visitedSpaces.add(world.getRoomWithId(roomId).getName());
  }
  
  //for controller test
  public Player(String playerName2, int spaceid, MockMansion mockMansion) {
  }

  /**
   * This method is for moving the player to one of the neighbouring spaces.
   * @param world the world of game
   * @param roomid player space room id
   * @param player the player that wants to move
   */
  @Override
  public void move(Mansion world, int roomid, Player player) {
    if (roomid >= 0 && roomid < world.getRoomCount()) {
      
      List<SpaceInterface> neighbours = playerSpace().getNeighbours();
      int choice;
      System.out.println("Neighbouring spaces for " + playerSpace().getName() 
          + " of the player " + player.getName() + " are : ");
      for (int i = 0; i < neighbours.size(); i++) {
        System.out.println("\n" + neighbours.get(i).getId() + " : " 
            + neighbours.get(i).getName());
      }
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter the room id of the room you wish to go to : ");
      try {
        choice  = scanner.nextInt();
        SpaceInterface space = findSpaceById(neighbours, choice);
        
        if (space != null) {
          roomId = choice;
          System.out.println("Player " + playerName + " has moved to the room " + space.getName());
        } else {
          System.out.println("Invalid input.");
        }

      } catch (IllegalArgumentException e) {
        System.out.println("Invalid input.");
        scanner.nextLine();
      }
      
    }
    
    visitedSpaces.add(world.getRoomWithId(roomId).getName());
  }

  /**
   * This method is for moving the player to one of the neighbouring spaces for 
   *     ComputerControlledGame class.
   * @param world the world of game
   * @param roomId player space room id
   * @param player the player that wants to move
   */
  @Override
  public void computerMove(Mansion world, int roomId, Player player) {
    if (roomId >= 0 && roomId < world.getRoomCount()) {
      
      List<SpaceInterface> neighbours = playerSpace().getNeighbours();
      int choice;
      System.out.println("Neighbouring spaces for " + playerSpace().getName() 
          + " of the player " + player.getName() + " are : ");
      for (int i = 0; i < neighbours.size(); i++) {
        System.out.println("\n" + neighbours.get(i).getId() + " : " 
            + neighbours.get(i).getName());
      }
      System.out.println("Enter the room id of the room you wish to go to : ");
      try {
        Random random = new Random();
        int i = random.nextInt(neighbours.size());
        SpaceInterface space = neighbours.get(i);
        
        if (space != null) {
          roomId = space.getId();
          System.out.println("Player " + playerName + " has moved to the room " + space.getName());
        } else {
          System.out.println("Invalid input.");
        }

      } catch (IllegalArgumentException e) {
        System.out.println("Invalid input.");
      }
      
    }
    
    visitedSpaces.add(world.getRoomWithId(roomId).getName());  
  }
  
  
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
  public void pickItem() {
    synchronized (items) {
      List<Item> roomItems = world.getRoomWithId(roomId).getItems();
      if (roomItems.isEmpty()) {
        System.out.println("This room has no items.");
      } else {
        if (items.size() < setItemQuantity() + 1) {
          Item item = roomItems.get(0);
          world.getRoomWithId(roomId).removeItem(item);
          items.add(item);
          System.out.println(item.getName() + " has been picked by the player " + playerName);
        }
      }
    }
  }
  
  //for test
  @Override
  public void setItems(List<Item> items) {
    this.items = items;
  }

  /**
   * This method displays nearby method of a space.
   */
  @Override
  public void displayNearbySpace(Player player) {
    playerSpace().displayNeighbours();
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
  public Space playerSpace() {
    return this.world.getRoomWithId(roomId);
  }

  /**
   * This method sets the max quantity of items that can be picked by the player.
   */
  @Override
  public int setItemQuantity() {
    return 3;
  }

  /**
   * This methods displays the items picked by the player.
   */
  @Override
  public void displayPickedItems() {
    if (items.isEmpty()) {
      System.out.println("You haven't picked any items yet.");
    }
    for (Item item : items) {
      System.out.println("Item : " + item.getName());
    }
  }

  /**
   * This method shows all the spaces visited by a player in order.
   */
  @Override
  public void displaySpacePath() {
    System.out.println("Rooms visited by the player " + playerName + " are : ");
    for (String roomName : visitedSpaces) {
      System.out.println(roomName);
    }
  }


}
