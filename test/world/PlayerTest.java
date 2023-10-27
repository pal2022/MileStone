package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

  private Mansion world;
  private Player player;
  private Player player1;
  Space room1;
  Space room2;
  Space room3;
  Space room4;
  @Before
  public void setUp() {
	world = new Mansion(10, 15, 14, "Pal's Mansion");  
    
    room1 = new Space(0, "0 3 8 12 Nursery");
    room2 = new Space(1, "2 12 5 21 Gym");
    room3 = new Space(2, "24 27 27 33 Wine seller");
    room4 = new Space(3, "21 27 24 33 Secret room");
    world.addRoom(room1);
    world.addRoom(room2);
    world.addRoom(room3);
    world.addRoom(room4);
    player = new Player("player1", 1, world);
    world.addPlayer(player);
  }
  
  /**
   * Test to check the player starts at correct position.
   */
  @Test
  public void testPlayerStartingPosition() {
    int expected = 1;
    assertEquals(expected, player.playerSpace().getId());
  }
  
  /**
   * Test to check the player is moving correctly.
   */
  @Test
  public void testMove() {
    Space currentSpace = player.playerSpace();
    List<SpaceInterface> neighbors = currentSpace.getNeighbours();
    if (neighbors.size() > 0) {
      SpaceInterface space = neighbors.get(0);
      int spaceId = space.getId();
      player.move(world, spaceId, player);
      assertEquals(space, player.playerSpace());
    }
  }

  /**
   * Test player does not move when the space is not a neighbouring space.
   */
  @Test
  public void testMoveWhenNotNeighbour() {
    Space currentSpace = player.playerSpace();
    int invalidSpaceId = 999;
    player.move(world, invalidSpaceId, player);
    assertEquals(currentSpace, player.playerSpace());
  }
  
  /**
   * Test to check it player is able to pick item correctly.
   */
  @Test
  public void testPickItem() {
    Item item = new Item("Healing potion");
    Space playerSpace = player.playerSpace();
    playerSpace.addItem(item);
    player.pickItem();
    assertTrue(player.getItems().contains(item));
    assertTrue(playerSpace.getItems().isEmpty());
  }

  /**
   * Test to check it player is not able to pick item more than designated amount.
   */
  @Test
  public void testMaxPickItem() {
    Item item = new Item("Healing potion");
    Item item1 = new Item("Healing potion");
    Item item2 = new Item("Healing potion");
    Item item3 = new Item("Healing potion");
    Space playerSpace = player.playerSpace();
    playerSpace.addItem(item);
    playerSpace.addItem(item1);
    playerSpace.addItem(item2);
    playerSpace.addItem(item3);
    player.pickItem();
    player.pickItem();
    player.pickItem();
    player.pickItem();
    assertFalse(player.getItems().contains(item3));
  }
  
  
}
