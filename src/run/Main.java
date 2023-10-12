package run;

import java.util.InputMismatchException;
import java.util.Scanner;
import world.Mansion;
import world.Space;
import world.WorldBuilder;
import world.WorldImage;

/**
 * This is the main class for running the application.
 */
public class Main {

  /**
   * This method read data from the text file. The data is then used in different classes for
   * the respective tasks. Here there are four options for calling four methods called 
   * in this class.
   * @param args pass parameters from command-in-line argument.
   */
  public static void main(String[] args) {
    String filePath = Main.class.getResource("PalMansion.txt").getPath();
    //    String filePath2 = Main.class.getResource("Pal.txt").getPath();    
    Mansion world = WorldBuilder.build(filePath);
    //    Mansion world2 = WorldBuider.build(filePath2);
    WorldImage display = new WorldImage(world); 

    Scanner scanner = new Scanner(System.in);

    while (true) {
      displayMenu();
      try {
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
          System.out.print("Player has moved from : " + world.getPlayerRoomName());
          world.targetCharacter.movePlayer();
          System.out.print(" to : " + world.getPlayerRoomName());
        } else if (choice == 2) {
          System.out.println("The world image display");
          display.showImage();
        } else if (choice == 3) {
          System.out.print("Enter room index\n");
          try {
            int id = scanner.nextInt();
            scanner.nextLine();
            if (id < 0 || id > world.getRoomCount()) {
              throw new IllegalArgumentException("Invalid room index.");
            }
            Space room = world.rooms.get(id);
            room.displayInformation();
          } catch (IllegalArgumentException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
          }
          
          
        } else if (choice == 4) {
          System.out.print("Enter room index\n");
          try {
            int id = scanner.nextInt();
            scanner.nextLine();
            if (id < 0 || id > world.getRoomCount()) {
              throw new IllegalArgumentException("Invalid room index.");
            }
            Space room = world.rooms.get(id);
            room.displayNeighbours();
          } catch (IllegalArgumentException e) {
            System.out.println("Invalid input.");
            scanner.nextLine();
          }
        } else if (choice == 5) {
          break;
        } else {
          System.out.println("Invalid input.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input.");
        scanner.nextLine();
      }
    }
  }

  /**
   * This method has the possible options the user can choose from.
   */
  public static void displayMenu() {
    System.out.println("\n\nMenu\n\n");
    System.out.println("1. Move Character");
    System.out.println("2. Image Output");
    System.out.println("3. Display room");
    System.out.println("4. Display neighbours of room");
    System.out.println("5. Exit menu");
    
  }

}
