package world;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * This test class is to test the methods of the Mansion class.
 */
public class MansionImplTest {

  private MansionImpl mansion;
  private PlayerImpl player;
  private SpaceImpl space;
  private SpaceImpl space2;
  private CharacterImpl targetCharacter;
  private CharacterPetImpl pet;
  private ItemImpl item;
  private ItemImpl item9;
  private PlayerImpl computerPlayer;
  private SpaceImpl space3;

  /**
   * Setting the values of the object of the mansion to use it for other test methods.
   */
  @Before
  public void setValues() {
    pet = new CharacterPetImpl("pet", 10);
    targetCharacter = new CharacterImpl(100, "Target Character", 10);
    mansion = new MansionImpl(10, 15, 14, "Pal's Mansion", targetCharacter, pet, 5);
    space = new SpaceImpl(0, "0 3 8 12 Nursery");
    space2 = new SpaceImpl(1, "2 12 5 21 Gym");
    player = new PlayerImpl("player1", 0, mansion);
    computerPlayer = new PlayerImpl("Computer player", 0, mansion);
    item = new ItemImpl("1 19 Cursed item");
    mansion.addRoom(space);
    space.addItem(item);
    space3 = new SpaceImpl(3, "2 12 5 21 Gym");
    mansion.addRoom(space3);
  }
  
  /**
   * This test checks if the mansion class adds room.
   */
  @Test
  public void testAddRoom() {
    String roomInfo = "5 12 8 21 Treasure room";
    int spaceId = 4;
    SpaceImpl room = new SpaceImpl(spaceId, roomInfo);
    mansion.addRoom(room);
    Map<Integer, SpaceImpl> rooms = mansion.getRooms();
    assertTrue(rooms.containsValue(room));
    System.out.println("code" + mansion.getRooms());
    System.out.println("code " + mansion.getRoomWithId(4));
    System.out.println("code   " + mansion);
  }
  
  /**
   * This test checks if the mansion class assigns the target character properly. 
   */
  @Test
  public void testAssignCharacter() {
    assertEquals(targetCharacter, mansion.targetCharacter);
  }
  
  /**
   * This test is to check if the mansion class provide correct number of rows.
   */
  @Test
  public void testGetRows() {
    assertEquals(10, mansion.getRows());
  }

  /**
   * This test is to check if the mansion class provide correct number of columns.
   */
  @Test
  public void testGetColumns() {
    assertEquals(15, mansion.getColumns());
  }

  /**
   * This test is to check if the mansion class provide correct amount of rooms in the mansion.
   */
  @Test
  public void testGetRoomCount() {
    assertEquals(14, mansion.getRoomCount());
  }

  /**
   * This test is to check if the mansion class provide correct name of the mansion.
   */
  @Test
  public void testGetName() {
    assertEquals("Pal's Mansion", mansion.getName());
  }

  /**
   * This is a simple test for toString method.
   */
  @Test
  public void testToString() {
    String expected = "Mansion name: Pal's Mansion, number of rows: 10, number of columns: "
        + "15 and number of rooms: 14";
    assertEquals(expected, mansion.toString());
  }
  
  /**
   * This test is to check that target characters pet has been assigned properly.
   */
  @Test
  public void testAssignCharacterPet() {
    assertEquals(pet, mansion.pet);
  }
  
  /**
   * This test check that seenByPlayers method returns true when a player is seen by
   * any other player i.e. if any other player is present in the same room.
   */
  @Test
  public void testSeenByPlayerSeenSameRoom() {
    PlayerImpl otherPlayer = new PlayerImpl("Other Player", 0, mansion);
    mansion.addRoom(space);
    mansion.addPlayer(player);
    mansion.addPlayer(otherPlayer);
    boolean isSeen = mansion.seenByPlayers(space, player);
    assertTrue(isSeen);
  }
  
  /**
   * This test check that seenByPlayers method returns true when a player is not seen by
   * any other player i.e. if any other player is present in the neighbouring room.
   */
  @Test
  public void testSeenByPlayerSeenNeighborRoom() {
    SpaceImpl otherSpace = new SpaceImpl(1, "2 12 5 21 Gym");
    PlayerImpl otherPlayer = new PlayerImpl("Other Player", 1, mansion);
    mansion.addRoom(space);
    mansion.addRoom(otherSpace);
    space.addNeighbour(otherSpace);
    mansion.addPlayer(player);
    mansion.addPlayer(otherPlayer);
    boolean isSeen = mansion.seenByPlayers(space, player);
    assertTrue(isSeen);
  }
  
  /**
   * This test check that seenByPlayers method returns false when a player is not seen by
   * any other player i.e. if any other player is not present in any neighbouring room
   * or in the same room.
   */
  @Test
  public void testSeenByPlayerNotSeen() {
    SpaceImpl otherSpace = new SpaceImpl(1, "2 12 5 21 Gym");
    PlayerImpl otherPlayer = new PlayerImpl("Other Player", 1, mansion);
    mansion.addRoom(otherSpace);
    mansion.addPlayer(otherPlayer);
    mansion.addRoom(space);
    boolean isSeen = mansion.seenByPlayers(otherSpace, player);
    assertFalse(isSeen);
  }
  
  /**
   * A test to check that player can only attack the target character if they are in 
   * the same room.
   */
  @Test
  public void testAttackWorksOnlyInSameRoom() {
    PlayerImpl p = new PlayerImpl("player1", 0, mansion);
    PlayerImpl p2 = new PlayerImpl("player1", 1, mansion);
    mansion.addRoom(space2);
    int health = 100;
    assertEquals(health, targetCharacter.getHealth());
    mansion.attackTarget(p);
    int newHealth = 99;
    assertEquals(newHealth, targetCharacter.getHealth());
    mansion.attackTarget(p2);
    int finalHealth = 99;
    assertEquals(finalHealth, targetCharacter.getHealth());
  }
  
  /**
   * A test to check that player cannot attack the target character if they are in 
   * different rooms.
   */
  @Test
  public void testAttackDoesnotWorkInDifferentRoom() {
    PlayerImpl p2 = new PlayerImpl("player1", 1, mansion);
    mansion.addRoom(space2);
    int health = 100;
    assertEquals(health, targetCharacter.getHealth());
    mansion.attackTarget(p2);
    int finalHealth = 100;
    assertEquals(finalHealth, targetCharacter.getHealth());
  }
  
  /**
   * A test to check that attack target method works correctly.
   */
  @Test
  public void testAttackTargetCharacter() {
    String input = "1\n"; 
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    InputStream systemIn = System.in;
    try {
      System.setIn(inputStream);
      mansion.addPlayer(player);
      player.pickItem(player);
      assertTrue(player.getItems().contains(item));
      int expected = 100;
      assertEquals(expected, mansion.targetCharacter.getHealth());
      mansion.attackTarget(player);
      int newExpected = 81;
      assertEquals(newExpected, mansion.targetCharacter.getHealth());
    } finally {
      System.setIn(systemIn);
    }
  }
  
  
  /**
   * A test to check that the item used to attack the target by the player is
   * removed from the items owned by the player. 
   */
  @Test
  public void testItemRemovedAfterAttack() {
    String input = "1\n"; 
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    InputStream systemIn = System.in;
    try {
      System.setIn(inputStream);
      player.pickItem(player);
      assertTrue(player.getItems().contains(item));
      mansion.attackTarget(player);
      assertFalse(player.getItems().contains(item));
    } finally {
      System.setIn(systemIn);
    }
    
  }
  
  /**
   * Test to check that the attack fails when a player tries to attack the target character
   * but is seen by the other player.
   */
  @Test
  public void testAttackFailsWhenSeenByOtherPlayer() {
    mansion.addPlayer(player);
    mansion.addPlayer(computerPlayer);
    player.pickItem(player);
    assertTrue(player.getItems().contains(item));
    int expected = 100;
    assertEquals(expected, mansion.targetCharacter.getHealth());
    mansion.attackTarget(player);
    int newExpected = 100;
    assertEquals(newExpected, mansion.targetCharacter.getHealth());
  }
  
  /**
   * Test to check that the player attacks the target using the default attack 
   * that causes 1 damage to the target's health if the player doesn't own
   * ant item.
   */
  @Test
  public void testPlayerDefaultAttack() {
    int health = 100;
    assertEquals(health, targetCharacter.getHealth());
    mansion.attackTarget(player);
    int newHealth = 99;
    assertEquals(newHealth, targetCharacter.getHealth());
  }
  
  /**
   * Test to check that the computer player attacks the target correctly
   * by picking the item with highest damage power from the items owned
   * by the computer player.
   */
  @Test
  public void testComputerPlayerAttack() { 
    CharacterImpl tc = new CharacterImpl(100, "Target Character", 10);
    mansion = new MansionImpl(10, 15, 14, "Pal's Mansion", tc, pet, 10);
    ItemImpl item2 = new ItemImpl("20 7 Excavator");
    ItemImpl item3 = new ItemImpl("3 9 Blade");
    space.addItem(item2);
    space.addItem(item3);
    int health = 100;
    assertEquals(health, tc.getHealth());
    computerPlayer.pickItem(computerPlayer);
    computerPlayer.pickItem(computerPlayer);
    computerPlayer.pickItem(computerPlayer);
    mansion.attackTarget(computerPlayer);
    //19 is the highest damage power of all the items
    int newHealth = 81;
    assertEquals(newHealth, tc.getHealth());
    assertFalse(computerPlayer.getItems().contains(item));
    //9 is the second highest damage power of all the items
    mansion.attackTarget(computerPlayer);
    int newHealth2 = 72;
    assertEquals(newHealth2, tc.getHealth());
    assertFalse(computerPlayer.getItems().contains(item3));
  }
  
  /**
   * This test is to check that the game ends when the target dies.
   */
  @Test
  public void testGameEnd_TargetCharacterDead() {
    String input = "1\n"; 
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    InputStream systemIn = System.in;
    try {
      System.setIn(inputStream);
      CharacterImpl targetCharacter = new CharacterImpl(100, "Target Character", 10);
      PlayerImpl player3 = new PlayerImpl("player1", 3, mansion);
      MansionImpl mansion = new MansionImpl(10, 15, 14, "Pal's Mansion", targetCharacter, pet, 3);
      mansion.addRoom(space3);
      item9 = new ItemImpl("3 100 Cursed item2");
      space3.addItem(item9);
      mansion.addPlayer(player3);
      targetCharacter.movePlayer();
      targetCharacter.movePlayer();
      targetCharacter.movePlayer();     
      player3.pickItem(player3);
      assertTrue(player3.getItems().contains(item9));
      assertEquals(100, targetCharacter.getHealth());
      
      mansion.attackTarget(player3);
      assertEquals(0, targetCharacter.getHealth());
      player3.addPoints(100);
      String gameEnd = mansion.gameEnd();
      
      assertEquals("Game over and the player " + player3.getName() + " wins", gameEnd);
    } finally {
      System.setIn(systemIn);
    }
      
  }
  
  /**
   * A test to check that the target escapes if max number of turns
   * are completed and target health is greater than 0.
   */
  @Test
  public void testGameEnd_TargetCharacterEscapes() {
    String input = "1\n"; 
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    InputStream systemIn = System.in;
    try {
      System.setIn(inputStream);
      CharacterImpl targetCharacter = new CharacterImpl(100, "Target Character", 4);
      PlayerImpl player3 = new PlayerImpl("player1", 3, mansion);
      MansionImpl mansion3 = new MansionImpl(10, 15, 14, "Pal's Mansion", targetCharacter, pet, 3);
      mansion3.addRoom(space3);
      item9 = new ItemImpl("3 10 Cursed item2");
      space3.addItem(item9);
      mansion3.addPlayer(player3);
      targetCharacter.movePlayer();
      targetCharacter.movePlayer();
      targetCharacter.movePlayer();     
      player3.pickItem(player3);
      assertTrue(player3.getItems().contains(item9));
      assertEquals(100, targetCharacter.getHealth());
      mansion3.attackTarget(player3);
      assertEquals(90, targetCharacter.getHealth());
      player3.addPoints(100);
      mansion3.playerTurn();
      mansion3.playerTurn();
      mansion3.playerTurn();
      String gameEnd = mansion3.gameEnd();
      assertEquals("Game over and the target has escaped", gameEnd);
    } finally {
      System.setIn(systemIn);
    }
  }
  
  /**
   * Test to check that the player with most points wins.
   */
  @Test
  public void testPlayerWithMostPointsWins() {
    String input = "1\n" + "1\n"; 
    InputStream inputStream = new ByteArrayInputStream(input.getBytes());
    InputStream systemIn = System.in;
    try {
      System.setIn(inputStream);
      CharacterImpl targetCharacter = new CharacterImpl(100, "Target Character", 4);
      
      MansionImpl mansion3 = new MansionImpl(10, 15, 14, "Pal's Mansion", targetCharacter, pet, 3);
      PlayerImpl player3 = new PlayerImpl("player1", 1, mansion3);
      PlayerImpl player4 = new PlayerImpl("player2", 3, mansion3);
      mansion3.addRoom(space3);
      mansion3.addRoom(space2);
      mansion3.addPlayer(player4);
      mansion3.addPlayer(player3);
      ItemImpl item2 = new ItemImpl("1 10 Cursed item2");
      ItemImpl item21 = new ItemImpl("1 15 Cursed item2");
      ItemImpl item22 = new ItemImpl("1 5 Cursed item2");
      space2.addItem(item22);
      player3.pickItem(player3);
      mansion3.attackTarget(player3);
      space2.addItem(item21);
      player3.pickItem(player3);
      mansion3.attackTarget(player3);
      space2.addItem(item2);
      player3.pickItem(player3);
      mansion3.attackTarget(player3);
      targetCharacter.movePlayer(); 
      targetCharacter.movePlayer(); 
      ItemImpl item3 = new ItemImpl("1 50 Cursed item2");
      ItemImpl item31 = new ItemImpl("1 15 Cursed item2");
      ItemImpl item32 = new ItemImpl("1 5 Cursed item2");
      space3.addItem(item32);
      player4.pickItem(player4);
      mansion3.attackTarget(player4);
      space3.addItem(item31);
      player4.pickItem(player4);
      mansion3.attackTarget(player4);
      space3.addItem(item3);
      player4.pickItem(player4);
      mansion3.attackTarget(player4);
      player4.addPoints(50);
      player4.addPoints(15);
      player4.addPoints(5);
      assertEquals(70, player4.returnTotalPoints());
      assertEquals(player4, mansion3.playerPoints());

    } finally {
      System.setIn(systemIn);
    }
  }
  
  /**
   * A test that verifies that the description of a space includes the 
   * players that are in that space.
   */
  @Test
  public void testSpaceDescriptionIncludesPlayerInThatSpace() {
    space = new SpaceImpl(0, "0 3 8 12 Nursery");
    mansion.addRoom(space);
    player = new PlayerImpl("player1", 0, mansion);
    mansion.addPlayer(player);
    PlayerImpl player2 = new PlayerImpl("player2", 2, mansion);
    mansion.addPlayer(player2);
    String roomDescription = mansion.lookAround(space);
      
    assertTrue(roomDescription.contains("Player " + player.getName()));
    assertFalse(roomDescription.contains("Player " + player2.getName()));
  }

  /**
   * A test that verifies that your implementation can look around the currently 
   * occupied space when it has no one item.
   */
  @Test
  public void testLookAroundNoItems() {
    PlayerImpl player = new PlayerImpl("player", 10, mansion);
    SpaceImpl space = new SpaceImpl(10, "30 30 36 36 Green House");
    mansion.addRoom(space);
    assertEquals(0, space.getItems().size());
    String roomDescription = mansion.lookAround(player.playerSpace());
    assertTrue(roomDescription.contains(player.playerSpace().getName()));
    assertTrue(roomDescription.contains("There are no items present in this room"));
  }
  
  /**
   * A test that verifies that your implementation can look around the currently 
   * occupied space when it has at least one item.
   */
  @Test
  public void testLookAroundAtleastOneItems() {
    PlayerImpl player10 = new PlayerImpl("player", 10, mansion);
    SpaceImpl space10 = new SpaceImpl(10, "30 30 36 36 Green House");
    mansion.addRoom(space10);
    mansion.addPlayer(player10);
    space10.addItem(item);
    assertEquals(1, space10.getItems().size());
    String roomDescription = mansion.lookAround(space10);
    assertTrue(roomDescription.contains(player10.playerSpace().getName()));
    String expected = "Name of the room is " + space10.getName() + "\nItems present"
        + " in this room are:\n" + item.getName() + ("\n")
        + "\nNo room through the left window."
        + "\nNo room through the right window."
        + "\nPlayers present in this room are:"
        + "Player " + player10.getName();
    assertEquals(expected, roomDescription);
  }
  
  /**
   * A test that verifies that your implementation can look around the 
   * currently occupied space has the target character.
   */
  @Test
  public void testLookAroundSpaceTargetCharacter() {
    SpaceImpl space9 = new SpaceImpl(9, "30 30 36 36 Green House");
    CharacterImpl targetCharacter = new CharacterImpl(90, "Target Character", 10);
    CharacterPetImpl pet = new CharacterPetImpl("Pet", 9);
    MansionImpl mansion2 = new MansionImpl(9, 15, 14, "Pal's Mansion", targetCharacter, pet, 10);
    PlayerImpl player9 = new PlayerImpl("player9", 9, mansion2);
    mansion2.addRoom(space9);
    mansion2.addPlayer(player9);
    space9.addItem(item);
    assertEquals(1, space9.getItems().size());
    System.out.println("**" + player9.playerSpace());
    assertEquals(space9, player9.playerSpace());
    String roomDescription = mansion2.lookAround(space9);
    String expected = "Name of the room is " + space9.getName() + "\nItems present"
        + " in this room are:\n" + item.getName() + ("\n")
        + "\nNo room through the left window."
        + "\nNo room through the right window."
        + "\nPlayers present in this room are:"
        + "Player " + player9.getName();
    assertEquals(expected, roomDescription);
  }
  
  /**
   * Test to check if getHighestDamageItem returns the item with most damage is
   * selected from the items carried by the player.
   */
  @Test
  public void testGetHighestDamageItem() {
    ItemImpl item = new ItemImpl("10 19 Cursed item");
    ItemImpl item2 = new ItemImpl("10 29 Cursed item");
    ItemImpl item3 = new ItemImpl("10 59 Cursed item");
    SpaceImpl space10 = new SpaceImpl(10, "30 30 36 36 Green House");
    PlayerImpl player10 = new PlayerImpl("player", 10, mansion);
    CharacterImpl targetCharacter = new CharacterImpl(100, "Target Character", 10);
    CharacterPetImpl pet = new CharacterPetImpl("Pet", 10);
    MansionImpl mansion2 = new MansionImpl(10, 15, 14, "Pal's Mansion", targetCharacter, pet, 10);
    mansion2.addRoom(space10);
    mansion2.addPlayer(player10);
    space.addItem(item);
    space.addItem(item2);
    space.addItem(item3);
    ItemImpl strongestItem = mansion.getHighestDamageItem(space.getItems());
    assertEquals(item3, strongestItem);
  }
  
  /**
   *  A test that verifies that your implementation can look around the currently occupied 
   *  space when it has another player in the space.
   */
  @Test
  public void testSpaceWithNeighbourNotPrinted() {
    SpaceImpl space9 = new SpaceImpl(9, "30 30 36 36 Green House");
    CharacterImpl targetCharacter = new CharacterImpl(90, "Target Character", 10);
    CharacterPetImpl pet = new CharacterPetImpl("Pet", 10);
    MansionImpl mansion2 = new MansionImpl(9, 15, 14, "Pal's Mansion", targetCharacter, pet, 10);
    PlayerImpl player9 = new PlayerImpl("player9", 9, mansion2);
    pet.playerMovesPet(9);
    mansion2.addRoom(space9);
    mansion2.addRoom(space);
    mansion.addRoom(space2);
    mansion2.addPlayer(player9);
    assertEquals(space9, player9.playerSpace());
    String roomDescription = mansion2.lookAround(space9);
    String expected = "Name of the room is " + space9.getName() 
        + "\nThere are no items present in this room"
        + "\nNo room through the left window."
        + "\nNo room through the right window."
        + "\nPlayers present in this room are:"
        + "Player " + player9.getName();
    assertEquals(expected, roomDescription);
  }
  
  /**
   * A test that verifies that your implementation displays the correct information with 
   * the player's description when the player is carrying one or more items.
   */
  @Test
  public void testPlayerDescriptionWithOneOrMoreItems() {
    ItemImpl item = new ItemImpl("1 5 Badger Saw");
    ItemImpl item2 = new ItemImpl("1 1 Poison");
    SpaceImpl room2 = new SpaceImpl(1, "2 12 5 21 Gym");
    PlayerImpl player1 = new PlayerImpl("player1", 1, mansion);
    mansion.addPlayer(player1);
    mansion.addRoom(room2);
    room2.addItem(item);
    room2.addItem(item2);
    player1.pickItem(player1);
    player1.pickItem(player1);
    assertTrue(player1.getItems().contains(item));
    assertTrue(player1.getItems().contains(item2));
    String expected = "Player name: " + player1.getName() 
        + "\nPlayer current space : " + player1.playerSpace().getName()
        + "\nItems List : " + player1.displayPickedItems();
    String playerDescription = mansion.playerDescription(player1);
    assertEquals(expected, mansion.playerDescription(player1));
  }
  
  /**
   * A test that verifies that your implementation displays the correct information with the 
   * player's description when the player is not carrying any items.
   */
  @Test
  public void testPlayerDescriptionWithOneNoItems() {
    ItemImpl item = new ItemImpl("1 5 Badger Saw");
    ItemImpl item2 = new ItemImpl("1 1 Poison");
    SpaceImpl room2 = new SpaceImpl(1, "2 12 5 21 Gym");
    PlayerImpl player1 = new PlayerImpl("player1", 1, mansion);
    mansion.addPlayer(player1);
    mansion.addRoom(room2);
    room2.addItem(item);
    room2.addItem(item2);
    String expected = "Player name: " + player1.getName() 
        + "\nPlayer current space : " + player1.playerSpace().getName()
        + "\nItems List : " + player1.displayPickedItems();
    assertEquals(expected, mansion.playerDescription(player1));
  }
  
  /**
   * Test to check that pet and the target character have the same initial room.
   */
  @Test
  public void testComparePetAndTargetInitialRoom() {
    assertEquals(pet.getPetRoomId(), targetCharacter.getRoomId());
  }
  
  /**
   * A test to check that the room containing pet is not displayed as one of the neighbouring 
   * rooms when printing neighbouring spaces of a room.
   */
  @Test
  public void testSpaceWithPetNotPrinted() {
    SpaceImpl space9 = new SpaceImpl(9, "30 30 36 36 Green House");
    CharacterImpl targetCharacter = new CharacterImpl(90, "Target Character", 10);
    CharacterPetImpl pet = new CharacterPetImpl("Pet", 10);
    MansionImpl mansion2 = new MansionImpl(9, 15, 14, "Pal's Mansion", targetCharacter, pet, 10);
    pet.playerMovesPet(9);
    mansion2.addRoom(space9);
    mansion2.addRoom(space);
    mansion2.addRoom(space2);
    space.addNeighbour(space9);
    space.addNeighbour(space2);
    assertTrue(mansion2.displayNeighbours(space).contains("Pet effect"));
  }
  
  /**
   *  A test that verifies that your implementation get the correct neighbors for 
   *  specific space when the space only has one neighbor.
   */
  @Test
  public void testGetNeighboursForSpaceWithOneNeighbour() {
    mansion.addRoom(space);
    mansion.addRoom(space2);
    mansion.assignNeighbours();
    List<SpaceInterface> neighbors = mansion.getNeighbours(space);
    assertEquals(2, neighbors.size());
    assertTrue(neighbors.contains(space2));
  }
 
}