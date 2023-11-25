package game;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import view.WorldImage;
import world.CharacterImpl;
import world.CharacterPetImpl;
import world.MansionImpl;
import world.MansionInterface;
import world.MockMansion;
import world.PlayerImpl;
import world.WorldBuilder;

/**
 * This class starts the game and interacts with the uses by asking inputes.
 */
public class GameController implements GameControllerInterface {

  Scanner scanner = new Scanner(System.in);
  MansionInterface world;
  CharacterPetImpl pet;
  PlayerImpl player;
  WorldImage display;
  private int roomId;
  private String playerName;
  private int maxTurns;
  private String filePath;
  

  
  /**
   * This contructor takes mansion object and max number of turns.
   * @param maxTurns max number of turns allowed
   * @param filePath file path of the text file
   */
  public GameController(int maxTurns, String filePath) {
    this.maxTurns = maxTurns;
    this.player = new PlayerImpl(playerName, roomId, world);
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
  public void startGame(MansionInterface world) {
    this.display = new WorldImage(world);
    System.out.println("Game Starts!");
    System.out.println("Enter the number of players playing the game: ");
    int numPlayers = 0;
    while (true) {
      try {
        numPlayers = scanner.nextInt();
        if (numPlayers >= 0 && numPlayers <= 10) {
          break;
        } else {
          System.out.println("Invalid input. Number of players must be between 0 and 10.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a valid integer.");
        scanner.nextLine();
      }
    }

    for (int i = 0; i < numPlayers; i++) {
      System.out.println("Enter player name : ");
      playerName = scanner.next();
      System.out.println("Enter starting room id : ");
      while (true) {
        try {
          roomId = scanner.nextInt();
          if (roomId >= 0 && roomId < (world.getRoomCount() - 1)) {
            break;
          } else {
            System.out.println("Invalid input. Room must be between 0 and "
                + (world.getRoomCount() - 1));
          }
        } catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter a valid integer.");
          scanner.nextLine();
        }
      }
      PlayerImpl player = new PlayerImpl(playerName, roomId, world);
      world.addPlayer(player);
    }

    PlayerImpl computerPlayer = new PlayerImpl("Computer player", 5, world);
    world.addPlayer(computerPlayer);
    player = world.getCurrentPlayer();
    int turn = 0;
    
    while (turn < maxTurns) {
      displayMenu();
      try {
        world.checkIfRoomMatchesWithTc(player);
        int choice;
        System.out.println("It's your turn " + player.getName());
        
        if (player.getName().equals("Computer player")) {
          Random random = new Random();
          choice = random.nextInt(1, 5);  
        } else {
          choice = scanner.nextInt();
          scanner.nextLine();
        }
        String tcName = world.getTargetName();
        switch (choice) {
          case 1:
            player.move(world, roomId, player);
            System.out.print("\n" + tcName + " has moved from : "
                + world.getTargetRoomName());
            world.moveTarget();
            System.out.print(" to : " + world.getTargetRoomName());
            System.out.println("\nPet moves from " + world.getRoomWithId(world.getPetRoomId()));
            world.movePet();
            System.out.print(" to " + world.getRoomWithId(world.getPetRoomId()));
            player = world.playerTurn();
            turn = turn + 1;
            break;
         
          case 2:
            player.pickItem(player);
            player = world.playerTurn();
            System.out.print("\n" + tcName + " has moved from : "
                    + world.getTargetRoomName());
            world.moveTarget();
            System.out.print(" to : " + world.getTargetRoomName());
            System.out.println("\nPet moves from " + world.getRoomWithId(world.getPetRoomId()));
            world.movePet();
            System.out.print(" to " + world.getRoomWithId(world.getPetRoomId()));
            turn = turn + 1;
            break;
          
          case 3:
            String lookAroundInfo = world.lookAround(player.playerSpace());
            System.out.println(lookAroundInfo);
            player = world.playerTurn();
            System.out.print("\n" + tcName + " has moved from : "
                    + world.getTargetRoomName());
            world.moveTarget();
            System.out.print(" to : " + world.getTargetRoomName());
            System.out.println("\nPet moves from " + world.getRoomWithId(world.getPetRoomId()));
            world.movePet();
            System.out.print(" to " + world.getRoomWithId(world.getPetRoomId()));
            turn = turn + 1;
            break;
          
          case 4:
            System.out.println("Player " + player.getName() + " you have chosen to "
                + "attack the target character.");
            player.world.attackTarget(player);
            player = world.playerTurn();
            System.out.print("\n" + tcName + " has moved from : "
                    + world.getTargetRoomName());
            world.moveTarget();
            System.out.print(" to : " + world.getTargetRoomName());
            System.out.println("\nPet moves from " + world.getRoomWithId(world.getPetRoomId()));
            world.movePet();
            System.out.print(" to " + world.getRoomWithId(world.getPetRoomId()));
            turn = turn + 1;
            break;
            
          case 5:
            System.out.println("Enter room id of the room where you want to move the pet.");
            int roomId = scanner.nextInt();
            world.movePet(roomId);
            System.out.println("Pet has been moved to the room: " + world.getRoomWithId(roomId));
            player = world.playerTurn();
            System.out.print("\n" + tcName + " has moved from : "
                    + world.getTargetRoomName());
            world.moveTarget();
            System.out.print(" to : " + world.getTargetRoomName());
            turn = turn + 1;
            break;
           
          case 6:
            System.out.println("World Image");
            display.saveImage("C:/Users/hp/Downloads/image.png");
            System.out.println("Image saved");
            break;
            
          case 7:
            System.out.println("You have exited the game");
            return;
          
          default:
            System.out.println("Invalid input.");
          
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input.");
        scanner.nextLine();  
      }
      String gameEndMessage = world.gameEnd();
      if (!gameEndMessage.isEmpty()) {
        System.out.println("\n" + gameEndMessage);
        return; 
      }
      if (turn == maxTurns) {
        System.out.println("\nMaximum number of turns reached");
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
    System.out.println("4. Attack target character");
    System.out.println("5. Move pet");
    System.out.println("6. WorldImage");
    System.out.println("7. Exit Game");
    
  }

} 