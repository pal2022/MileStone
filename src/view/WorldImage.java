package view;

import game.GuiGameController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import world.CharacterInterface;
import world.ItemImpl;
import world.MansionInterface;
import world.PlayerImpl;
import world.SpaceImpl;

/**
 * This class is used to create and display buffered image from a text file.
 */

public class WorldImage extends JPanel implements KeyListener {

  
  private static final int width = 25;
  public JFrame frame;
  MansionInterface world;
  BufferedImage latestImage;
  PlayerImpl player;
  CharacterInterface targetCharacter;
  private final Color highlightColor = new Color(255, 255, 0, 127);
  private final int height = 25;
  private BufferedImage targetImage;
  private List<BufferedImage> playerImages;
  private boolean coordinatesPrinted = false;
  private Set<SpaceImpl> highlightedRooms = new HashSet<>();
  private GuiGameController controller;
  private JLabel label;
  private GameView view;
  private int s = 25;
  private boolean actionSelectBox = false;

  /**
   * Takes the object of class Mansion to create an image.
   * 
   * @param world2 object of class mansion
   * @param view view class object
   */
  public WorldImage(MansionInterface world2, GuiGameController gui, GameView view) {
    this.world = world2;
    this.targetCharacter = world.getTarget();

    addKeyListener(this);
    setFocusable(true);

    gui.setWorldImage(this);
    view.setWorldImage(this);

    targetImage = generateImageForTarget();
    playerImages = generateImagesForPlayers(world.getPlayers());
    this.controller = gui;
    this.view = view;

  }

  /**
   * Not used.
   */
  @Override
  public void keyTyped(KeyEvent e) {
  }

  /**
   * For key pressed events for picking item, attacking target and looking around.
   */
  @Override
  public void keyPressed(KeyEvent e) {

    if (e.getKeyCode() == KeyEvent.VK_P) {
      System.out.println("P key pressed");
      PlayerImpl currentPlayer = world.getCurrentPlayer();
      if (currentPlayer != null) {
        currentPlayer.pickItem(currentPlayer);
        view.displayMessage("Player has picked item");
        System.out.println("Player items: " + currentPlayer.getItems());
        world.getTarget().movePlayer();
        world.movePet();
        System.out.println("\nPet moves from " + world.getRoomWithId(world.getPetRoomId()));
        world.movePet();
        System.out.print(" to " + world.getRoomWithId(world.getPetRoomId()));
        repaint();
        controller.endTurn();
      } else {
        System.out.println("No Player");
      }
    } else if (e.getKeyCode() == KeyEvent.VK_L) {
      System.out.println("L key pressed");
      PlayerImpl currentPlayer = world.getCurrentPlayer();
      if (currentPlayer != null) {
        SpaceImpl lookAroundSpace = currentPlayer.playerSpace();
        String lookAroundMessage = world.lookAround(lookAroundSpace);
        view.displayMessage(lookAroundMessage);
        System.out.println("Look around works");
        world.getTarget().movePlayer();
        world.movePet();
        System.out.println("\nPet moves from " + world.getRoomWithId(world.getPetRoomId()));
        world.movePet();
        System.out.print(" to " + world.getRoomWithId(world.getPetRoomId()));
        repaint();
        controller.endTurn();
      } else {
        System.out.println("Look around doesn't work");
      }
    } else if (e.getKeyCode() == KeyEvent.VK_A) {
      System.out.println("A key pressed");
      PlayerImpl currentPlayer = world.getCurrentPlayer();
      // to check that attack works
      currentPlayer.pickItem(currentPlayer);
      controller.attackTargetGui(currentPlayer);

      if (!(currentPlayer.getItems().isEmpty()) && world.getCondition()) {
        showAttackDialog(currentPlayer);
      }
      world.getTarget().movePlayer();
      world.movePet();
      System.out.println("\nPet moves from " + world.getRoomWithId(world.getPetRoomId()));
      world.movePet();
      System.out.print(" to " + world.getRoomWithId(world.getPetRoomId()));
      repaint();
    }
  }

  /**
   * Not used.
   */
  @Override
  public void keyReleased(KeyEvent e) {

  }

  /**
   * For getting the preferred size of the panel.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(world.getColumns() * s, world.getRows() * s);
  }

  /**
   * This method is used to display the image of the mansion.
   */
  public void showImage() {
    this.frame = new JFrame();
    frame.setMinimumSize(new Dimension(1000, 1000));
    JScrollPane scrollPane = new JScrollPane(this);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

    this.frame.getContentPane().add(scrollPane);

    JMenuBar menuBar = new JMenuBar();

    JMenu menu = new JMenu("Menu");
    menuBar.add(menu);

    JMenuItem menuItem1 = new JMenuItem("Save Game");
    menu.add(menuItem1);
    JMenuItem menuItem2 = new JMenuItem("Game Instruction");
    menu.add(menuItem2);
    JMenuItem menuItem3 = new JMenuItem("Game Rules");
    menu.add(menuItem3);
    JMenuItem menuItem4 = new JMenuItem("Exit");
    menu.add(menuItem4);

    File file = new File("C:\\Users\\hp\\Downloads\\LoadData.txt");
    menuItem1.addActionListener(e -> saveGame());
    menuItem2.addActionListener(e -> gameInstructions());
    menuItem3.addActionListener(e -> gameRules());
    menuItem4.addActionListener(e -> exitGame());

    this.frame.setJMenuBar(menuBar);

    this.frame.pack();
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);

    this.addMouseListener(new MouseAdapter() {

      public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
          createPopupMenu().show(e.getComponent(), e.getX(), e.getY());
        }
      }

      public void mousePressed(MouseEvent e) {
        PlayerImpl currentPlayer = world.getCurrentPlayer();
        System.out.println(e.getX());
        System.out.println(e.getY());
        System.out.println(actionSelectBox);
        int x = e.getX();
        int y = e.getY();

        System.out.println(e.getX());
        System.out.println(e.getY());
        System.out.println(actionSelectBox);
        if (actionSelectBox) {
          System.out.println("Make player select a box and check cord");
          System.out.println("[+] Player selected a box");
          System.out.println("Player clicked at " + x + "," + y);

          for (SpaceImpl room : highlightedRooms) {
            System.out.println("Room=" + room.getName());
            System.out.println(room.getX1(s) + "," + room.getX2(s));
            System.out.println(room.getY1(s) + "," + room.getY2(s));
            int x1 = room.getX1(s) + 25;
            int x2 = room.getX2(s) + 25;
            int y1 = room.getY1(s) + 25;
            int y2 = room.getY2(s) + 25;
            if (x1 <= y && y <= x2 && y1 <= x && x <= y2) {
              System.out.println("User selected the room=" + room.getName());
              currentPlayer.setRoomId(room.getId());
              view.displayMessage(
                  "User has moved to the room " + currentPlayer.playerSpace().getName());
              break;
            }
          }

          System.out.println("Release actionSelectBox flag");
          highlightedRooms.clear();

          world.getTarget().movePlayer();
          world.movePet();
          System.out.println("\nPet moves from " + world.getRoomWithId(world.getPetRoomId()));
          world.movePet();
          System.out.print(" to " + world.getRoomWithId(world.getPetRoomId()));
          repaint();
          controller.endTurn();
          actionSelectBox = false;
        }

        if (e.isPopupTrigger()) {
          createPopupMenu().show(e.getComponent(), e.getX(), e.getY());
        }

      }

      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        System.out.println("Mouse clicked at: " + e.getX() + ", " + e.getY());
        PlayerImpl clickedPlayer = getPlayerAt(e.getX(), e.getY());
        if (clickedPlayer != null) {
          System.out.println("Player " + clickedPlayer.getName() + " was clicked.");
          controller.playerDescriptionGui(clickedPlayer);
        } else {
          System.out.println("No player in this position");
        }
      }
    });

  }

  /**
   * This method is used to build image while reading the text file. It loops from
   * space having index 0 to the last space and draws rectangles for each room.
   * 
   * @return image
   */
  public Image buildImage() {
    if (this.world == null) {
      throw new IllegalStateException("World data is not initialized.");
    }

    s = 25;
    BufferedImage img = new BufferedImage(this.world.getColumns() * s, this.world.getRows() * s,
        BufferedImage.TYPE_INT_RGB);
    Graphics g = img.getGraphics();
    g.fillRect(0, 0, this.world.getColumns() * s, this.world.getRows() * s);

    g.setColor(java.awt.Color.black);

    for (int i = 0; i < this.world.getRoomCount(); i++) {
      SpaceImpl room = this.world.getRooms().get(i);
      int width = room.getX2() - room.getX1();
      int height = room.getY2() - room.getY1();

      g.setColor(Color.black);
      g.drawRect(room.getY1() * s, room.getX1() * s, height * s, width * s);
      FontMetrics fm = g.getFontMetrics();
      // sw string width sh string height
      int sw = fm.stringWidth(room.getName());
      int sh = fm.getAscent();
      int sx1 = ((room.getX2() + room.getX1()) * s) / 2;
      int sy1 = ((room.getY1()) * s) + 1;
      g.setColor(Color.black);
      int sw1 = fm.stringWidth("ID" + room.getId());
      g.drawString("ID" + room.getId(), sy1, sx1);
      g.drawString(room.getName(), sy1 + sw1 + 5, sx1);

    }

    g.dispose();
    return img;
  }

  /**
   * This method is used to draw the image.
   * 
   * @param g for drawing image
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Image img = buildImage();
    g.drawImage(img, height, width, this);

    drawTarget(g);
    drawPlayers(g, world.getPlayers());
    System.out.println(highlightedRooms);
    for (SpaceImpl room : highlightedRooms) {
      int width = room.getX2() - room.getX1();
      int height = room.getY2() - room.getY1();

      g.setColor(highlightColor); // Highlight color
      g.fillRect(room.getY1() * s + 25, room.getX1() * s + 25, height * s, width * s);
      actionSelectBox = true;
    }

  }

  /**
   * This method is for drawing target on map.
   * @param g Graphics
   */
  private void drawTarget(Graphics g) {

    int roomId = targetCharacter.getRoomId();
    SpaceImpl targetRoom = world.getRoomWithId(roomId);

    int roomCenterX = (targetRoom.getX1() + targetRoom.getX2()) / 2;
    int roomCenterY = (targetRoom.getY1() + targetRoom.getY2()) / 2;
    int x = roomCenterY * s;
    int y = roomCenterX * s;
    g.drawImage(targetImage, x, y, this);
  }

  /**
   * Get random value between two value.
   * @param maximum max limit
   * @param minimum min limit
   * @return value any random value from the range
   */
  public static int getRandomInteger(int maximum, int minimum) {
    return ((int) (Math.random() * (maximum - minimum))) + minimum;
  }

  /**
   * This method is for drawing players on map.
   * @param g Graphics
   * @param players players list
   */
  private void drawPlayers(Graphics g, List<PlayerImpl> players) {

    for (PlayerImpl player : players) {
      SpaceImpl playerRoom = player.playerSpace();
      double roomCenterX = (playerRoom.getX1() + playerRoom.getX2()) / 2;
      double roomCenterY = (playerRoom.getY1() + playerRoom.getY2()) / 2;
      int index = players.indexOf(player);
      BufferedImage playerImage = playerImages.get(index % playerImages.size());

      int x = (int) (roomCenterY * s) + getRandomInteger(8, 25);
      int y = (int) (roomCenterX * s) + getRandomInteger(8, 25);
      player.setX1(x);
      player.setX2(x + width);
      player.setY1(y);
      player.setY2(y + height);
      System.out.println("Player Located at " + x + "," + y);
      g.drawImage(playerImage, x, y, this);
    }
  }

  
  private BufferedImage generateImageForTarget() {
    BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
    Graphics g = img.createGraphics();
    g.setColor(Color.BLUE);
    g.fillOval(0, 0, img.getWidth(), img.getHeight());
    g.dispose();
    return img;
  }

  private List<BufferedImage> generateImagesForPlayers(List<PlayerImpl> players) {
    Color[] colorOptions = new Color[] { new Color(0, 0, 0), new Color(255, 0, 0),
        new Color(0, 255, 0), new Color(0, 0, 255), new Color(120, 255, 0), new Color(0, 120, 255),
        new Color(0, 255, 120), new Color(255, 255, 0), new Color(0, 255, 255),
        new Color(255, 0, 255), };

    List<BufferedImage> images = new ArrayList<>();
    for (int i = 0; i < Math.min(players.size(), 10); i++) {
      BufferedImage img = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
      Graphics g = img.createGraphics();

      g.setColor(colorOptions[i]); // Unique color for each player
      g.fillRect(0, 0, img.getWidth(), img.getHeight());
      g.dispose();
      images.add(img);
    }
    return images;
  }

  /**
   * This method saves the images as png file.
   * 
   * @param filePath file path of the text file
   */
  public void saveImage(String filePath) {
    Image img = buildImage();
    try {
      File file = new File(filePath);
      ImageIO.write((BufferedImage) img, "png", file);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Create JPopup menu.
   * @return menu
   */
  public JPopupMenu createPopupMenu() {
    PopupMenuView pop = new PopupMenuView(world, view, controller, this);
    return pop.createPopupMenu();
  }

  /**
   * Method to highlight a specific space.
   * @param space space whose neighbours are to be highlighted
   */
  public void highlightSpace(SpaceImpl space) {
    highlightedRooms.clear();
    highlightedRooms
        .addAll(space.getNeighbours().stream().filter(neighbour -> neighbour instanceof SpaceImpl)
            .map(neighbour -> (SpaceImpl) neighbour).collect(Collectors.toSet()));
    repaint();
  }

  /**
   * Clear highlighted rooms.
   */
  public void clearHighlights() {
    highlightedRooms.clear();
    repaint();
  }

  private PlayerImpl getPlayerAt(int x, int y) {
    for (PlayerImpl player : world.getPlayers()) {
      if (x >= player.getX1() && x <= player.getX2() && y >= player.getY1()
          && y <= player.getY2()) {
        return player;
      }

    }
    return null;
  }

  /**
   * Exit game option.
   */
  private void exitGame() {
    frame.dispose();
  }

  /**
   * Game instruction option from menu.
   */
  private void gameInstructions() {
    view.displayMessage(
        "Basic Instructions\nKey P - for Picking item" + "\nKey L - for looking around"
            + "\nKey A - for attacking target" + "\nMenu on right click");
  }

  /**
   * This method saves the current state of the game.
   */
  public void saveGame() {
    String filePath = "C:\\Users\\hp\\Downloads\\ThisWorld.txt";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(String.valueOf(controller.getNumberOfPlayers()) + "\n");
      for (int i = 0; i < controller.getNumberOfPlayers(); i++) {
        writer.write(controller.getPlayers().get(i) + "\n");
        writer.write(String.valueOf(controller.getRoomIds().get(i)) + "\n");
      }
      JOptionPane.showConfirmDialog(frame, "Save game state successfully.", "Kill Doctor Lucky",
          JOptionPane.DEFAULT_OPTION);
    } catch (IOException e) {
      JOptionPane.showConfirmDialog(frame, "Failed to save game state: " + e.getMessage(),
          "Snake Game", JOptionPane.ERROR_MESSAGE);
    }
  }


  /**
   * Game rules option.
   */
  private void gameRules() {
    view.displayMessage("Game Rules"
        + "\nKill Taret Character to win the game \nPlayer with most points wins the game");
  }

  /**
   * The prompt that asks for roomId for moving pet.
   * @return roomId
   */
  public int promptForMovingPet() {
    while (true) {
      try {
        String roomStr = JOptionPane.showInputDialog(this,
            "Enter room ID of the room for moving pet ");
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
   * Display message for error.
   * @param message message to be displayed
   */
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Gives list of items carried by the player to attack the target.
   * @param player currentPlayer
   */
  public void showAttackDialog(PlayerImpl player) {
    JDialog attackDialog = new JDialog();
    attackDialog.setLayout(new BorderLayout());

    DefaultListModel<ItemImpl> listModel = new DefaultListModel<>();
    player.getItems().forEach(listModel::addElement);
    JList<ItemImpl> itemList = new JList<>(listModel);
    itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane scrollPane = new JScrollPane(itemList);
    attackDialog.add(scrollPane, BorderLayout.CENTER);

    JButton attackButton = new JButton("Attack with selected item");
    attackButton.addActionListener(e -> {
      ItemImpl selectedItem = itemList.getSelectedValue();
      if (selectedItem != null) {
        controller.attackTargetGui2(player, selectedItem);
        attackDialog.dispose();
        controller.endTurn();
      } else {
        JOptionPane.showMessageDialog(attackDialog, "Please select an item to attack.");
      }
    });

    attackDialog.add(attackButton, BorderLayout.SOUTH);
    attackDialog.pack();
    attackDialog.setLocationRelativeTo(this);
    attackDialog.setVisible(true);
  }

}
