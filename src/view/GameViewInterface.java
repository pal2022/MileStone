package view;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import world.PlayerImpl;

/**
 * This is the interface for view of GUI based game.
 */
public interface GameViewInterface {
  
  /**
   * This method sets the worldImage.
   */
  void setWorldImage(WorldImage worldImage);

  /**
   * This method initializes menu.
   */
  void initMenu();

  /**
   * Menu item for creating new game.
   */
  JMenuItem getNewGameWithNewWorldMenuItem();

  /**
   * Menu item for creating new game with saved world specification.
   */
  JMenuItem getNewGameWithCurrentWorldMenuItem();

  /**
   * Menu item for quitting game.
   */
  JMenuItem getQuitGameMenuItem();

  /**
   * Prompt user to enter the number of players playing the game.
   */
  int promptForNumberOfPlayers();

  /**
   * Prompts user to enter player name.
   */
  String promptForPlayerName(int playerNumber);


  /**
   * Prompts user to enter starting room id.
   */
  int promptForStartingRoomId(int playerNumber);

  /**
   * Method for displaying error messages.
   */
  void displayErrorMessage(String message);

  /**
   * Get popup menu.
   */
  JPopupMenu getPopupMenu();

  /**
   * Highlight neighbouring spaces.
   */
  void highlightNeighbours(PlayerImpl currentPlayer);

  /**
   * Clear highlighted spaces.
   */
  void clearHighlights();

  /**
   * Method for displaying message using JOptionPane.
   */
  void displayMessage(String message);

  /**
   * Make the screen visible.
   * @param b boolean value
   */
  void setVisible(boolean b);

}
