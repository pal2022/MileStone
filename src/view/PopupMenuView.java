package view;

import game.GuiGameController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import world.MansionInterface;
import world.PlayerImpl;

/**
 * This class is for designing a menu for the GUI application.
 */
public class PopupMenuView {
  
  private JMenuItem move;
  private JMenuItem movePet;
  private JMenuItem exit;
  private MansionInterface model;
  private GuiGameController gui;
  private WorldImage worldImage; 

  /**
   * Initializes a new popup menu.
   * @param model define model
   * @param view define view
   * @param gui define controller
   * @param worldImage define worldImage
   */
  public PopupMenuView(MansionInterface model, GameView view, GuiGameController gui, 
      WorldImage worldImage) {
    this.model = model;
    this.gui = gui;
    this.worldImage = worldImage;
    move = new JMenuItem("Move");
    movePet = new JMenuItem("Move Pet");
    exit = new JMenuItem("Exit Game");

    move.setActionCommand("MOVE");
    movePet.setActionCommand("MOVE_PET");
    exit.setActionCommand("EXIT");

    /**
     * Inner class for handling move action.
     */
    class MoveActionHandler extends AbstractAction {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("MOVE BUTTON CLICK");
        int i = 0;
        PlayerImpl player = model.getCurrentPlayer();
        System.out.println(player.getName());
        System.out.println("OUTPUT!!!!!!!!");

        view.highlightNeighbours(player);
      }
    }

    /**
     * Inner class for handling move pet action.
     */
    class MovePetActionHandler extends AbstractAction {
      private static final long serialVersionUID = 1L;

      @Override
      public void actionPerformed(ActionEvent e) {
        // Button pressed logic goes here
        System.out.println("MOVE PET BUTTON CLICK");
        // TODO take input from user
        int roomId = worldImage.promptForMovingPet();
        model.getPet().playerMovesPet(roomId);
        view.displayMessage("Pet moved to the room " + model.getPet().getPetRoomId());
        gui.endTurn();
        System.out.println("Pet moved to the room " + model.getPet().getPetRoomId());
      }
    }
    
    move.addActionListener(new MoveActionHandler());
    movePet.addActionListener(new MovePetActionHandler());
  }

  /**
   * Create an give a JPopupMenu.
   * @return JPopupMenu
   */
  public JPopupMenu createPopupMenu() {
    JPopupMenu popupMenu = new JPopupMenu();
    popupMenu.add(move);
    popupMenu.add(movePet);
    popupMenu.add(exit);
    return popupMenu;
  }

}
