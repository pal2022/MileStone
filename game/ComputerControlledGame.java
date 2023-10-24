package game;

import java.util.Random;
import java.util.Scanner;
import view.WorldImage;
import world.Mansion;
import world.Player;
import world.WorldBuilder;

/**
 * This class for the computer controlled game. It show a series of different outputs for
 * a fixed number of players;
 */
public class ComputerControlledGame {
  Scanner scanner = new Scanner(System.in);
  Mansion world = WorldBuilder.build(getFilePath());
  WorldImage display = new WorldImage(world);
  int roomId;
  String playerName;
  Player player = new Player(playerName, roomId, world);
  int random1;
  int random2;
  
  /**
   * This method is used to get the file path of the test file.
   * @return file path
   */
  public String getFilePath() {
    System.out.println("Enter file path: ");
    String filePath = scanner.nextLine();
    return filePath;
  }
  
  /**
   * This method starts the game.
   */
  public void startGame() {
    
    System.out.println("Game Starts!");
    int numPlayers = 5;
    String[] names = {"Ram Charan", "Shah Rukh Khan", "Akshay Kumar", "Paresh Raval",
        "Kartik Aryan"};
    int[] spaces = {1, 4, 7, 9, 12};
    for (int i = 0; i < numPlayers; i++) {
      playerName = names[i];
      roomId = spaces[i];
      Player player = new Player(playerName, roomId, world);
      world.addPlayer(player);
    }
    
    Random random = new Random();   
    player = world.getCurrentPlayer();
    
    for (int i = 0; i < 10; i++) {
      System.out.println(" It's your turn " + player.getName());
      random1 = random.nextInt(1, 6);
      random2 = random.nextInt(7, 16);
      
      if (random1 == 1 || random1 == 4) {
        System.out.println("This moves the player to one of the neighbouring spaces.");
        player.computerMove(world, roomId, player);
        System.out.print(world.targetCharacter.getName() + " has moved from : "
            + world.getTargetRoomName());
        world.targetCharacter.movePlayer();
        System.out.print(" to : " + world.getTargetRoomName());
        player = world.playerTurn();
        System.out.println("\n");
      } else if (random1 == 2 || random1 == 6) {
        System.out.println("An item is picked by the player.");
        player.pickItem();
        player = world.playerTurn();
        System.out.println("\n");
      } else if (random1 == 3 || random1 == 5) {
        System.out.println("Neighbours of the space the player currently is in are displayed.");
        player.displayNearbySpace(player);
        player = world.playerTurn();
        System.out.println("\n");
      } 
   
      if (random2 == 7 || random2 == 15) {
        System.out.println("Items picked by the player are displayed.");
        player.displayPickedItems();
        System.out.println("\n");
      } else if (random2 == 8 || random2 == 9) {
        System.out.println("List of all spaces visited by the player are displayed.");
        player.displaySpacePath();
        System.out.println("\n");
      } else if (random2 == 10 || random2 == 11) {
        System.out.println("A random players details are displayed.");
        String playerName = names[random.nextInt(names.length)];
        for (Player player1 : world.players) {
          if (player1.getName().equals(playerName)) {
            System.out.println("Player name: " + player1.getName());
            System.out.println("Items List : ");
            player1.displayPickedItems();
            System.out.println("Player current space : " + player1.playerSpace().getName());
          }
        }
        System.out.println("\n");
      } else if (random2 == 12 || random2 == 16) {
        System.out.println("All players information are displayed.");
        world.displayPlayers();
        System.out.println("\n");
      } else if (random2 == 13 || random2 == 14) {
        System.out.println("For a random space players present in the room are displayed.");
        int id = spaces[random.nextInt(spaces.length)];
        System.out.println("Players present in the room " + player.playerSpace().getName() 
            +  "are: ");
        for (Player player : player.world.players) {
          if (player.playerSpace().getId() == id) {
            System.out.println(player.getName());
          }
        }
        System.out.println("\n");
      }
   
    }
    
  }

  public int getRandom1() {
    return random1;
  }

  public int getRandom2() {
    return random2;
  }
  
}
