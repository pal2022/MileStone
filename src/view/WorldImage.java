package view;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import world.Character;
import world.Item;
import world.Mansion;
import world.Player;
import world.PlayerInterface;
import world.Space;


/**
 * This class is used to create and display buffered image from a text file.
 */
public class WorldImage extends JPanel {

  Mansion world;
  BufferedImage latestImage;
  JFrame frame;
  Player player;
  Character targetCharacter;
  
  /**
   * Takes the object of class Mansion to create an image.
   * @param mansion object of class mansion
   */
  public WorldImage(Mansion mansion) {
    this.world = mansion;
  } 

  /**
   * This method is used to draw the image.
   * @param g for drawing image
   */
  public void paint(Graphics g) {
    Image img = buildImage();
    g.drawImage(img, 50, 50, this);
  }

  /**
   * This method is used to display the image of the mansion.
   */
  public void showImage() {
    this.frame = new JFrame();
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.getContentPane().add(this);
      
    this.frame.pack();
    this.frame.setVisible(true);
  }

  /**
   * This method is used to build image while reading the text file. It loops from space having
   * index 0 to the last space and draws rectangles for each room.
   * @return image
   */
  public Image buildImage() {
    int s = 20;
    BufferedImage img = new BufferedImage(this.world.getColumns() * s, this.world.getRows() * s,
        BufferedImage.TYPE_INT_RGB);
    Graphics g = img.getGraphics();
    g.fillRect(0, 0, this.world.getColumns() * s, this.world.getRows() * s);

    g.setColor(java.awt.Color.black);
    for (int i = 0; i < this.world.getRoomCount(); i++) {
      Space room = this.world.rooms.get(i);
      int width = room.getX2() - room.getX1();
      int height = room.getY2() - room.getY1();

      g.drawRect(room.getY1() * s, room.getX1() * s, height * s, width * s);
      FontMetrics fm = g.getFontMetrics();
      //sw string width sh string height
      int sw = fm.stringWidth(room.getName());
      int sh = fm.getAscent();
      int sx1 = ((room.getX2() + room.getX1()) * s) / 2;
      int sy1 = ((room.getY1()) * s) + 1; 
      g.drawString(room.getName(), sy1, sx1);
      
      for (Item item : room.getItems()) {
        g.drawString(item.getName(),  sy1,  sx1 + sh);
        sx1 += sh;
      }
      
      //pw playername width ph playername height
      for (PlayerInterface player : world.players) {
        if (room.getId() == player.playerSpace().getId()) {
          g.drawString(player.getName(),  sy1,  sx1 + sh);
          sx1 += sh;  
        }
      }
      
      if (room.getId() == world.targetCharacter.getRoomId()) {
        g.drawString(world.targetCharacter.getName(),  sy1,  sx1 + sh);
      }
      
    }
    
    g.dispose();
    return img;
  }
  
  /**
   * This method saves the images as png file.
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
}
