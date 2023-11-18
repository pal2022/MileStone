package run;

import game.ComputerControlledGame;
import game.GameController;
import java.util.InputMismatchException;
import java.util.Scanner;
import view.WorldImage;
import world.CharacterPetImpl;
import world.MansionImpl;
import world.PlayerImpl;
import world.SpaceImpl;
import world.WorldBuilder;

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

    //C:\Users\hp\eclipse-workspace\lab00_getting_started\MileStone1\model\src\run\PalMansion.txt

    System.out.println("Enter file path and max number of turns");
    if (args.length < 2) {
      System.out.println("Enter file path and max number of turns");
    }

    String filePath = args[0];
    int maxTurns = Integer.parseInt(args[1]);

    MansionImpl world = WorldBuilder.build(filePath, maxTurns);
  
    GameController game = new GameController(maxTurns, filePath);

    game.startGame(world);
    
    ComputerControlledGame cGame = new ComputerControlledGame();
    cGame.startGame();
  }
}
