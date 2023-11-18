package view;

import java.awt.Color;
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
import world.CharacterImpl;
import world.ItemImpl;
import world.MansionImpl;
import world.MansionInterface;
import world.PlayerImpl;
import world.PlayerInterface;
import world.SpaceImpl;


/**
 * This class is used to create and display buffered image from a text file.
 */
public class WorldImage extends JPanel {

  MansionInterface world;
  BufferedImage latestImage;
  JFrame frame;
  PlayerImpl player;
  CharacterImpl targetCharacter;
  
  /**
   * Takes the object of class Mansion to create an image.
   * @param world2 object of class mansion
   */
  public WorldImage(MansionInterface world2) {
    this.world = world2;
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
    if (this.world == null) {
      throw new IllegalStateException("World data is not initialized.");
    }
    int s = 25;
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
      //sw string width sh string height
      int sw = fm.stringWidth(room.getName());
      int sh = fm.getAscent();
      int sx1 = ((room.getX2() + room.getX1()) * s) / 2;
      int sy1 = ((room.getY1()) * s) + 1; 
      g.setColor(Color.black);
      g.drawString(room.getName(), sy1, sx1);
      
      for (ItemImpl item : room.getItems()) {
        g.setColor(Color.red);
        g.drawString(item.getName(),  sy1,  sx1 + sh);
        sx1 += sh;
      }
      
      //pw playername width ph playername height
      for (int i2 = 0; i2 < world.getPlayers().size(); i2++) {
        PlayerInterface player = (PlayerInterface) world.getPlayers().get(i2);
        if (room.getId() == player.playerSpace().getId()) {
          g.setColor(Color.orange);
          g.drawString(player.getName(),  sy1,  sx1 + sh);
          sx1 += sh;  
        }
      }
      
      if (room.getId() == world.getTargetRoomId()) {
        g.setColor(Color.blue);
        g.drawString(world.getTargetName(),  sy1,  sx1 + sh);
      }
      
      if (room.getId() == world.getPetRoomId()) {
        g.setColor(Color.blue);
        g.drawString(world.getPetName(),  sy1,  sx1 + 2 * sh);
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
