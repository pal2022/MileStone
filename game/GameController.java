package game;

import java.util.InputMismatchException;
import java.util.Scanner;
import view.WorldImage;
import world.Mansion;
import world.MockMansion;
import world.Player;
import world.WorldBuilder;

/**
 * This class starts the game and interacts with the uses by asking inputes.
 */
public class GameController implements GameControllerInterface {

  Scanner scanner = new Scanner(System.in);
  Mansion world;
  WorldImage display = new WorldImage(world);
  int roomId;
  String playerName;
  int maxTurns;
  String filePath;
  Player player;

  //for game controller test
  public GameController(MockMansion mockMansion) {
  }

  /**
   * This contructor takes mansion object and max number of turns.
   * @param world Mansion object
   * @param maxTurns maxt number of turns allowed
   * @param filePath file path of the text file
   */
  public GameController(Mansion world, int maxTurns, String filePath) {
    this.world = world;
    this.maxTurns = maxTurns;
    this.player = new Player(playerName, roomId, world);
    this.filePath = filePath;
  }

  /**
   * This method is used to get the file path through console.
   * @return file path
   */
  @Override
  public String getFilePath() {
    return this.filePath;
  }
  
  /**
   * This method starts the game.
   */
  @Override
  public void startGame() {
    System.out.println("Game Starts!");
    System.out.println("Enter the number of players playing the game: ");
    int numPlayers = scanner.nextInt();
    
    for (int i = 0; i < numPlayers; i++) {
      System.out.println("Enter player name : ");
      playerName = scanner.next();
      System.out.println("Enter starting room id : ");
      roomId = scanner.nextInt();
      Player player = new Player(playerName, roomId, world);
      world.addPlayer(player);
    }

    player = world.getCurrentPlayer();

    while (true) {
      displayMenu();
      try {
        
        System.out.println(" It's your turn " + player.getName());
        if (world.getTurnNumber() > maxTurns) {
          System.out.println("You have exceeded turn limit");
          break;
        }
        int choice = scanner.nextInt();
        switch (choice) {
          case 1:
            player.move(world, roomId, player);
            System.out.print(world.targetCharacter.getName() + " has moved from : "
                + world.getTargetRoomName());
            world.targetCharacter.movePlayer();
            System.out.print(" to : " + world.getTargetRoomName());
            player = world.playerTurn();
            break;
         
          case 2:
            player.pickItem();
            player = world.playerTurn();
            System.out.print(world.targetCharacter.getName() + " has moved from : "
                + world.getTargetRoomName());
            world.targetCharacter.movePlayer();
            System.out.print(" to : " + world.getTargetRoomName());
            break;
          
          case 3:
            player.displayNearbySpace(player);
            player = world.playerTurn();
            System.out.print(world.targetCharacter.getName() + " has moved from : "
                + world.getTargetRoomName());
            world.targetCharacter.movePlayer();
            System.out.print(" to : " + world.getTargetRoomName());
            break;
          
          case 4:
            player.displayPickedItems();
            break;
          
          case 5:
            player.displaySpacePath();
            break;
        
          case 6 :
            System.out.println("Enter player name: ");
            String playerName = scanner.next();
            for (Player player1 : world.players) {
              if (player1.getName().equals(playerName)) {
                System.out.println("Player name: " + player1.getName());
                System.out.println("Items List : ");
                player1.displayPickedItems();
                System.out.println("Player current space : " + player1.playerSpace().getName());
              }
            }
            break;
          
          case 7:
            world.displayPlayers();
            break;
          
          case 8:
            System.out.println("Enter space id ");
            int id = scanner.nextInt();
            System.out.println("Players present in the room " + player.playerSpace().getName() 
                +  "are: ");
            for (Player player : player.world.players) {
              if (player.playerSpace().getId() == id) {
                System.out.println(player.getName());
              }
            }
            break;
          
          case 9:
            System.out.println("World Image");
            display.saveImage("C:/Users/hp/Downloads/image.png");
            break;
            
          case 10:
            System.out.println("You have exited the game");
            return;
          
          default:
            System.out.println("Invalid input.");
          
        }
        
      } catch (InputMismatchException e) {
        System.out.println("Invalid input.");
        scanner.nextLine();  
      }
    }
  }

  /**
   * Menu show list of operations that can be performed by the user.
   */
  @Override
  public void displayMenu() {
    System.out.println("\n\nMenu\n\n");
    System.out.println("1. Move");
    System.out.println("2. Pick Item");
    System.out.println("3. Look around");
    System.out.println("4. Display picked items");
    System.out.println("5. Display player path");
    System.out.println("6. Display specific player information");
    System.out.println("7. Display all players information");
    System.out.println("8. Display players in a space using the space id");
    System.out.println("9. WorldImage");
    System.out.println("10. Exit Game");
  }

}
