package game;

/**
 * This is the interface for the gamecontroller.
 */
public interface GameControllerInterface {
  
  /**
   * This method is used to get the file path through console.
   * @return file path
   */
  public String getFilePath();
  
  /**
   * This method starts the game.
   */
  public void startGame();
  
  /**
   * Menu show list of operations that can be performed by the user.
   */
  public void displayMenu();
  
}
