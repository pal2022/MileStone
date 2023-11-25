package game;

import java.io.InputStream;
import world.MansionInterface;

/**
 * This is the interface for the game controller.
 */
public interface GameControllerInterface {
  
  /**
   * This method is used to get the file path through console.
   * @return file path
   */
  public String getFilePath();
 
  /**
   * Menu show list of operations that can be performed by the user.
   */
  public void displayMenu();

  /**
   * This method starts the game.
   */
  void startGame(MansionInterface world);

}
