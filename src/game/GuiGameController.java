package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import view.GameView;
import view.GameViewInterface;
import view.PopupMenuView;
import view.WorldImage;
import world.ItemImpl;
import world.MansionInterface;
import world.PlayerImpl;
import world.SpaceImpl;

/**
 * Controller for GUI based game.
 */
public class GuiGameController implements GameControllerInterface {

  private MansionInterface model;
  private GameViewInterface view;
  private WorldImage worldImage;
  private PopupMenuView popupMenuView;
  private int turn = 0;
  private int maxTurn;
  private volatile boolean isGameRunning = true;
  private List<String> players;
  private List<Integer> roomIds;
  private int numOfPlayers;

  /**
   * Constructor for the controller class.
   * @param model Model object
   * @param view View Object
   * @param maxTurn maxTurns of the game
   */
  public GuiGameController(MansionInterface model, GameViewInterface view, int maxTurn) {
    this.model = model;
    this.view = view;
    this.maxTurn = maxTurn;
    this.players = new ArrayList<String>();
    this.roomIds = new ArrayList<Integer>();
  }

  /**
   * Set the worldImage object.
   * @param worldImage worldImage object
   */
  public void setWorldImage(WorldImage worldImage) {
    if (worldImage != null) {
      this.worldImage = worldImage;
    }
    System.out.println("Setting the world image in guigamecontroller");
    System.out.println(this.worldImage);
  }

  /**
   * Setup actions listeners.
   */
  public void initController() {
    this.view.getNewGameWithNewWorldMenuItem().addActionListener(e -> startNewGameWithNewWorld());
    this.view.getNewGameWithCurrentWorldMenuItem()
        .addActionListener(e -> startNewGameWithCurrentWorld());
    this.view.getQuitGameMenuItem().addActionListener(e -> quitGame());
  }

  /**
   * Make the view visible.
   */
  public void showMainFrame() {
    this.view.setVisible(true);
  }

  /**
   * Start a new game with new world.
   */
  void startNewGameWithNewWorld() {
    turn = 0;
    this.model.clearPlayers();
    this.model.getTarget().setRoomId(0);
    numOfPlayers = view.promptForNumberOfPlayers();

    if (numOfPlayers < 1 || numOfPlayers > 9) {
      view.displayErrorMessage("The number of players must be between 1 and 9.");
      return;
    }
    for (int i = 1; i <= numOfPlayers; i++) {
      String playerName = view.promptForPlayerName(i);
      int roomId = view.promptForStartingRoomId(i);
      players.add(playerName);
      model.addPlayer(new PlayerImpl(playerName, roomId, model));
    }

    PlayerImpl computerPlayer = new PlayerImpl("Computer player", 15, model);
    model.addPlayer(computerPlayer);

    worldImage = new WorldImage(this.model, this, (GameView) this.view);
    if (this.worldImage.frame != null) {
      this.worldImage.frame.dispose();
    }
    this.worldImage.showImage();
    startTurn();
    turn = turn + 1;

  }

  /**
   * Start a new game with saved world specification.
   */
  private void startNewGameWithCurrentWorld() {
    turn = 0;
    this.model.clearPlayers();
    this.model.getTarget().setRoomId(0);

    String filePath = "C:\\Users\\hp\\Downloads\\ThisWorld.txt";
    StringBuilder contentBuilder = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        contentBuilder.append(line).append("\n");

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    String content = contentBuilder.toString();
    System.out.println(content);
    String[] lines = content.split("\\n");
    if (lines.length > 0) {
      numOfPlayers = Integer.parseInt(lines[0].trim());

      for (int i = 1; i < lines.length; i += 2) {
        String playerName = lines[i].trim();
        int roomId = Integer.parseInt(lines[i + 1].trim());
        PlayerImpl player = new PlayerImpl(playerName, roomId, model);
        model.addPlayer(player);
      }
    }

    PlayerImpl computerPlayer = new PlayerImpl("Computer player", 15, model);
    model.addPlayer(computerPlayer);

    worldImage = new WorldImage(this.model, this, (GameView) this.view);
    if (this.worldImage.frame != null) {
      this.worldImage.frame.dispose();
    }
    this.worldImage.showImage();
    startTurn();
    turn = turn + 1;
  }

  /**
   * Quit game.
   */
  void quitGame() {
    System.exit(0);
  }

  @Override
  public void displayMenu() {
  }

  @Override
  public void startGame(MansionInterface world) {

  }

  /**
   * Method for setting first player's turn.
   */
  public void startTurn() {
    PlayerImpl currentPlayer = model.getCurrentPlayer();
    if (currentPlayer == null) {
      view.displayErrorMessage("There are no players in the game.");
      return;
    }

    view.displayMessage("It's " + currentPlayer.getName() + "'s turn.");

    if (currentPlayer.getName().equals("Computer player")) {
      Random random = new Random();
      int choice = random.nextInt(1, 4);
      if (choice == 1) {
        currentPlayer.pickItem(currentPlayer);
        view.displayMessage("Player has picked item");
        System.out.println("Player items: " + currentPlayer.getItems());
        model.getTarget().movePlayer();
        model.movePet();
        System.out.println("\nPet moves from " + model.getRoomWithId(model.getPetRoomId()));
        model.movePet();
        System.out.print(" to " + model.getRoomWithId(model.getPetRoomId()));
        worldImage.repaint();
        endTurn();
      } else if (choice == 2) {
        SpaceImpl lookAroundSpace = currentPlayer.playerSpace();
        String lookAroundMessage = model.lookAround(lookAroundSpace);
        view.displayMessage(lookAroundMessage);
        System.out.println("Look around works");
        model.getTarget().movePlayer();
        model.movePet();
        System.out.println("\nPet moves from " + model.getRoomWithId(model.getPetRoomId()));
        model.movePet();
        System.out.print(" to " + model.getRoomWithId(model.getPetRoomId()));
        worldImage.repaint();
        endTurn();
      } else if (choice == 3) {
        attackTargetGui(currentPlayer);
        if (!(currentPlayer.getItems().isEmpty())) {
          worldImage.showAttackDialog(currentPlayer);
        }
        model.getTarget().movePlayer();
        model.movePet();
        System.out.println("\nPet moves from " + model.getRoomWithId(model.getPetRoomId()));
        model.movePet();
        System.out.print(" to " + model.getRoomWithId(model.getPetRoomId()));
        worldImage.repaint();
        endTurn();
      } 
    }
  }

  /**
   * For getting next players turn and endGame.
   */
  public void endTurn() {
    turn = turn + 1;
    if (turn > maxTurn || model.getTarget().tcDead()) {
      isGameRunning = false;
      String gameEnd = model.gameEndGui();
      view.displayMessage(gameEnd);
      worldImage.frame.dispose();
    } else {
      System.out.println("Turn****" + turn);
      model.playerTurn();
      startTurn();
    }
  }

  /**
   * Gives the player description in a message box.
   * @param currentPlayer currentPlayer
   */
  public void playerDescriptionGui(PlayerImpl currentPlayer) {
    view.displayMessage(model.playerDescription(currentPlayer));
  }

  /**
   * Attacking target method.
   * @param currentPlayer currentPlayer
   */
  public void attackTargetGui(PlayerImpl currentPlayer) {
    view.displayMessage(model.attackTargetGuiPart1(currentPlayer));
  }
  
  /**
   * Attacking target method.
   * @param currentPlayer currentPlayer
   * @param item item used to attack
   */
  public void attackTargetGui2(PlayerImpl currentPlayer, ItemImpl item) {
    model.attackTargetGuiPart2(currentPlayer, item);
  }


  /**
   * Get the max turns of the game.
   * @return maxTurn
   */
  public int getMaxTurn() {
    return maxTurn;
  }

  /**
   * Check if the game has ended.
   * @return false if game ended, else true
   */
  public boolean getIsGameRunning() {
    return isGameRunning;
  }

  /**
   * Get names of players playing the game.
   * @return list of players
   */
  public List<String> getPlayers() {
    return players;
  }

  /**
   * Get roomIds of players playing the game.
   * @return list of roomIds
   */
  public List<Integer> getRoomIds() {
    for (int i = 0; i < getNumberOfPlayers(); i++) {
      roomIds.add(model.getPlayers().get(i).playerSpace().getId());
    }
    return roomIds;
  }

  /**
   * Get number of players playing the game.
   * @return number of players
   */
  public int getNumberOfPlayers() {
    return numOfPlayers;
  }

}
