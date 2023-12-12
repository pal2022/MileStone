package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This class is for creating the about panel that is displayed at the start of the game.
 */
public class AboutPanel extends JPanel {

  private static final long serialVersionUID = 1L;

  /**
   * Create the about panel.
   */
  public AboutPanel() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(Box.createRigidArea(new Dimension(0, 50)));

    JLabel welcomeLabel = new JLabel("Welcome to the Game Kill Doctor Lucky!");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 25));
    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel creatorLabel = new JLabel("Created by: Palkan Motwani");
    creatorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
    creatorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel creditsLabel = new JLabel(
        "<html><center>Resources:<br>" + "Javapoint, StackOverflow</center></html>");
    creditsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    creditsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    add(welcomeLabel);
    add(Box.createRigidArea(new Dimension(0, 20)));
    add(creatorLabel);
    add(Box.createRigidArea(new Dimension(0, 10)));
    add(Box.createVerticalGlue());
    add(creditsLabel);
  }
}
