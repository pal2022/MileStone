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

/**
 * This is the test class for playerImpl.
 */
public class PlayerImplTest {

  SpaceImpl room1;
  SpaceImpl room2;
  SpaceImpl room3;
  SpaceImpl room4;
  private MansionImpl world;
  private PlayerImpl player;
  private PlayerImpl player1;
  private CharacterImpl tc;
  private CharacterPetImpl pet;
  
  /**
   * Stting values to use them in the tests below.
   */
  @Before
  public void setUp() {
    world = new MansionImpl(10, 15, 14, "Pal's Mansion", tc, pet, 10);
    room1 = new SpaceImpl(0, "0 3 8 12 Nursery");
    room2 = new SpaceImpl(1, "2 12 5 21 Gym");
    room3 = new SpaceImpl(2, "24 27 27 33 Wine seller");
    room4 = new SpaceImpl(3, "21 27 24 33 Secret room");
    world.addRoom(room1);
    world.addRoom(room2);
    world.addRoom(room3);
    world.addRoom(room4);
    player = new PlayerImpl("player1", 1, world);
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
    SpaceImpl currentSpace = player.playerSpace();
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
    SpaceImpl currentSpace = player.playerSpace();
    int invalidSpaceId = 999;
    player.move(world, invalidSpaceId, player);
    assertEquals(currentSpace, player.playerSpace());
  }
  
  /**
   * Test to check it player is able to pick item correctly.
   */
  @Test
  public void testPickItem() {
    ItemImpl item = new ItemImpl("16 5 Badger Saw");
    SpaceImpl playerSpace = player.playerSpace();
    playerSpace.addItem(item);
    player.pickItem(player);
    assertTrue(player.getItems().contains(item));
    assertTrue(playerSpace.getItems().isEmpty());
  }

  /**
   * Test to check it player is not able to pick item more than designated amount.
   */
  @Test
  public void testMaxPickItem() {
    ItemImpl item = new ItemImpl("16 5 Badger Saw");
    ItemImpl item1 = new ItemImpl("16 5 Badger Saw");
    ItemImpl item2 = new ItemImpl("16 5 Badger Saw");
    ItemImpl item3 = new ItemImpl("16 5 Badger Saw");
    ItemImpl item4 = new ItemImpl("16 5 Badger Saw");
    SpaceImpl playerSpace = player.playerSpace();
    playerSpace.addItem(item);
    playerSpace.addItem(item1);
    playerSpace.addItem(item2);
    playerSpace.addItem(item3);
    player.pickItem(player);
    player.pickItem(player);
    player.pickItem(player);
    player.pickItem(player);
    player.pickItem(player);
    assertFalse(player.getItems().contains(item4));
  }
  
  /**
   * A test that verifies that your implementation can properly handle if a user tries to 
   * pick up an item that isn't available.
   */
  @Test
  public void testPickUnavailable() {
    ItemImpl item = new ItemImpl("1 5 Badger Saw");
    PlayerImpl player = new PlayerImpl("player1", 3, world);
    room2.addItem(item);
    player.pickItem(player);
    assertFalse(player.getItems().contains(item));
  }
  
  
}
