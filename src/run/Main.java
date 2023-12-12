package run;

import game.GameController;
import game.GuiGameController;
import java.util.Scanner;
import view.GameView;
import world.MansionInterface;
import world.WorldBuilder;

/**
 * This is the main class for running the application.
 */
public class Main {

  /**
   * This method read data from the text file. The data is then used in different
   * classes for the respective tasks. Here there are four options for calling
   * four methods called in this class.
   * 
   * @param args pass parameters from command-in-line argument.
   */
  public static void main(String[] args) {

    String filePath = "C:\\Users\\hp\\eclipse-workspace\\lab00_getting_started\\"
        + "MileStone1\\model\\src\\run\\PalMansion.txt";
    int maxTurns = 14;

    MansionInterface world = WorldBuilder.build(filePath, maxTurns);

    GameView view = new GameView(world);
    GuiGameController controller = new GuiGameController(world, view, maxTurns);
    GameController game = new GameController(maxTurns, filePath);
    
    System.out.println("What type of game do you wanna play?");
    System.out.println("1. Gui Based Game");
    System.out.println("2. Text Based Game");
    int choice = 0;
    Scanner scanner = new Scanner(System.in);
    choice = scanner.nextInt();
    boolean check = false;
    
    if (choice == 1 || choice == 2) {
      check = true;
    }
    
    while (check) {
      switch (choice) {
        case 1:
          controller.initController();
          controller.showMainFrame();
          check = false;
          break;
        case 2:
          game.startGame(world);
          check = false;
          break;
        default:
          System.out.println("Invalid input.");
          break;
      } 
    }
  }
}
