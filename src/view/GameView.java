package view;

import game.GuiGameController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import world.MansionInterface;
import world.PlayerImpl;
import world.SpaceImpl;

/**
 * This class is for defining the view part of the GUI game.
 */
public class GameView extends JFrame implements GameViewInterface {
  private static final long serialVersionUID = 1L;
  private JMenuBar menuBar;
  private JMenu menu;
  private JMenuItem newGameWithNewWorld;
  private JMenuItem newGameWithCurrentWorld;
  private JMenuItem quitGame;
  private PopupMenuView popupMenuView;
  private WorldImage worldImagePanel;
  private GuiGameController controller;
  private MansionInterface world;
  private int maxTurn;
  private int numPlayers;

  /**
   * Constructor for GameView class.
   * @param world model
   */
  public GameView(MansionInterface world) {
    this.world = world;

    setTitle("Kill Doctor Lucky");
    setMinimumSize(new Dimension(300, 300)); 
    setLocationRelativeTo(null); 

    setLayout(new BorderLayout());
    AboutPanel aboutPanel = new AboutPanel();
    getContentPane().add(aboutPanel, BorderLayout.CENTER);
    initMenu();

    this.controller = new GuiGameController(world, this, maxTurn);
    pack();
    this.maxTurn = controller.getMaxTurn();
  }

  /**
   * This method sets the worldImage.
   */
  @Override
  public void setWorldImage(WorldImage worldImage) {
    if (worldImage != null) {
      this.worldImagePanel = worldImage;
    }
    System.out.println("Setting the world image in gameview");
    System.out.println(this.worldImagePanel);
  }

  /**
   * This method initializes menu.
   */
  @Override
  public void initMenu() {
    menuBar = new JMenuBar();
    menu = new JMenu("Menu");
    menuBar.setVisible(true);
    setJMenuBar(menuBar);
    menuBar.add(menu);
    newGameWithNewWorld = new JMenuItem("New Game with New World");
    newGameWithCurrentWorld = new JMenuItem("New Game with Current World");
    quitGame = new JMenuItem("Quit Game");

    menu.add(newGameWithNewWorld);
    menu.add(newGameWithCurrentWorld);
    menu.addSeparator();
    menu.add(quitGame);

  }

  /**
   * Menu item for creating new game.
   */
  @Override
  public JMenuItem getNewGameWithNewWorldMenuItem() {
    return newGameWithNewWorld;
  }

  /**
   * Menu item for creating new game with saved world specification.
   */
  @Override
  public JMenuItem getNewGameWithCurrentWorldMenuItem() {
    return newGameWithCurrentWorld;
  }

  /**
   * Menu item for quitting game.
   */
  @Override
  public JMenuItem getQuitGameMenuItem() {
    return quitGame;
  }

  /**
   * Prompt user to enter the number of players playing the game.
   */
  @Override
  public int promptForNumberOfPlayers() {
    while (true) {
      String numPlayersStr = JOptionPane.showInputDialog(this,
          "Enter the number of players (1-9):");
      int numPlayers = Integer.parseInt(numPlayersStr);
      if (numPlayers >= 1 && numPlayers <= 9) {
        return numPlayers;
      } else {
        displayErrorMessage("Number of players must be between 1 and 9.");
      }
    } 
  }

  /**
   * Prompts user to enter player name.
   */
  @Override
  public String promptForPlayerName(int playerNumber) {
    while (true) {
      try {
        String playerName = JOptionPane.showInputDialog(this, "Enter name for player " 
            + playerNumber + ":");
        if (playerName == null || playerName.isEmpty()) {
          displayErrorMessage("Enter player name. Player name cannot be empty.");
            
        } else {
          return playerName;
        }
      } catch (HeadlessException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Prompts user to enter starting room id.
   */
  @Override
  public int promptForStartingRoomId(int playerNumber) {
    while (true) {
      try {
        String roomStr = JOptionPane.showInputDialog(this,
            "Enter starting room ID for player " + playerNumber + ":");
        int roomId = Integer.parseInt(roomStr);
        if (roomId >= 0 && roomId <= world.getRoomCount()) {
          return roomId;
        } else {
          displayErrorMessage("Invalid room ID. Please enter a valid room ID.");
        }
      } catch (NumberFormatException e) {
        displayErrorMessage("Invalid input. Please enter a numeric value for room ID.");
      }
    }
  }

  /**
   * Method for displaying error messages.
   */
  @Override
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Get popup menu.
   */
  @Override
  public JPopupMenu getPopupMenu() {
    return popupMenuView.createPopupMenu();
  }

  /**
   * Highlight neighbouring spaces.
   */
  public void highlightNeighbours(PlayerImpl currentPlayer) {
    worldImagePanel.clearHighlights();

    SpaceImpl currentSpace = currentPlayer.playerSpace();

    worldImagePanel.highlightSpace((SpaceImpl) currentSpace);

  }

  /**
   * Clear highlighted spaces.
   */
  public void clearHighlights() {
    worldImagePanel.clearHighlights();
  }


  /**
   * Method for displaying message using JOptionPane.
   * @param message to be displayed
   */
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(null, message);
  }

  /**
   * Get number of  players playing the game.
   * @return number of players
   */
  public int getNumPlayers() {
    return numPlayers;
  }

}
