package world;

import java.util.List;
import java.util.Map;

/**
 * This is the interface for mansion and has all the required methods needed by
 * the mansion class.
 */
public interface MansionInterface {

  /**
   * This method is used to add room in mansion.
   * 
   * @param room room of the mansion
   */
  void addRoom(SpaceImpl room);

  /**
   * This method is used to get the room the target character is present in.
   * 
   * @return room name
   */
  String getTargetRoomName();

  /**
   * This method is used to assign neighbours of a given space.
   */
  void assignNeighbours();

  /**
   * This method is used to get number of rows in the mansion.
   * 
   * @return rows
   */
  int getRows();

  /**
   * This method is used to get number of columns in the mansion.
   * 
   * @return columns
   */
  int getColumns();

  /**
   * This method is used to get the room count in the mansion.
   * 
   * @return room count
   */
  int getRoomCount();

  /**
   * This method is used to get name of the mansion.
   * 
   * @return name
   */
  String getName();

  /**
   * This method is used to get map of rooms.
   * 
   * @return rooms
   */
  Map<Integer, SpaceImpl> getRooms();

  /**
   * This method is for getting the space using space id.
   * 
   * @return Space the space of the mansion
   */
  SpaceImpl getRoomWithId(int id);

  /**
   * This method is for adding player in the mansion.
   * 
   * @param player the player to be added
   */
  void addPlayer(PlayerImpl player);

  /**
   * This method is for getting the current player. It uses the updated current
   * player from playerTurn method.
   */
  PlayerImpl getCurrentPlayer();

  /**
   * This method ensures that the players get their turn one by one.
   */
  PlayerImpl playerTurn();

  /**
   * This method displays the information of the players.
   */
  void displayPlayers();

  /**
   * This method is for attacking the target character.
   * 
   * @param player the player attacking the target character
   */
  void attackTarget(PlayerImpl player);

  /**
   * This method is for looking around the room. Looking around shows the details
   * of the the room and some details of the neighbouring room.
   * 
   * @param room the room to be looked around
   * @return lookAround string containing the information to be printed
   */
  String lookAround(SpaceImpl room);

  /**
   * This method displays neighbours for a given space.
   * 
   * @param space whose neighbours are needed to be found
   * @return string showing neighbours
   */
  String displayNeighbours(SpaceImpl space);

  /**
   * This method is for the details of the player.
   * 
   * @param player the player
   * @return playerDescription information of the player
   */
  String playerDescription(PlayerImpl player1);

  /**
   * This method gives the list of the players.
   * 
   * @return players list of players
   */
  List<PlayerImpl> getPlayers();

  /**
   * This method is for getting the name of the target character.
   * 
   * @return targetName name of the target
   */
  String getTargetName();

  /**
   * This method is for moving the target character.
   */
  void moveTarget();

  /**
   * This method is for moving the target character's pet.
   * 
   * @param newSpace space id of the room to be moved to
   */
  void movePet(int roomId);

  /**
   * This method moves pet using dfs.
   */
  void movePet();

  /**
   * This method is for getting the room id of the target character.
   * 
   * @return roomid target character's roomid
   */
  int getTargetRoomId();

  /**
   * This method checks if the player and target are in the same room.
   * 
   * @param player the player to be checked
   */
  void checkIfRoomMatchesWithTc(PlayerImpl player);

  /**
   * This method is for getting the list of neighbours for a given room.
   * 
   * @param room the room to find neighbours for
   * @return neighbours list of neighbours for that room
   */
  List<SpaceInterface> getNeighbours(SpaceImpl room);

  /**
   * This method is for returning the player with the highest damage power.
   * 
   * @return winningPlayer the winner of the game
   */
  PlayerImpl playerPoints();

  /**
   * This method returns the item with the highest damage power from a given list
   * of items.
   * 
   * @return maxDamageItem item with most damage power
   */
  ItemImpl getHighestDamageItem(List<ItemImpl> items);

  /**
   * This method returns the name of the player that is seeing the player who
   * wants to attack the target character.
   * 
   * @return player name name of the player who is watching
   */
  String seenByPlayers2(SpaceImpl currentPlayerSpace, PlayerImpl playerTurn);

  /**
   * This method is for checking if a player is seen by any other player.
   * 
   * @return true if player is seen, else false
   */
  boolean seenByPlayers(SpaceImpl currentPlayerSpace, PlayerImpl playerTurn);

  /**
   * This method gives the turn number.
   * 
   * @return turn number the turn number
   */
  int getTurnNumber();

  /**
   * This method is for getting the room id of the target character's pet.
   * 
   * @return roomid target character's pet's roomid
   */
  int getPetRoomId();

  /**
   * This method gives the message according to how the game has ended.
   * 
   * @return gameEnd representing the end of the game
   */
  String gameEnd();

  /**
   * This method is for getting the name of the target character's pet.
   * 
   * @return name target character's pet's name
   */
  String getPetName();

  /**
   * This method gets the target of the world.
   * @return target character
   */
  CharacterInterface getTarget();

  /**
   * Attack target in GUI part 1.
   * @param playerTurn currentPlayer
   * @return string 
   */
  String attackTargetGuiPart1(PlayerImpl playerTurn);

  /**
   * Remove the players.
   */
  void clearPlayers();

  /**
   * This method gets the pet of the world.
   * @return target character' pet
   */
  CharacterPetInterface getPet();

  /**
   * Game end method for GUI game.
   * @return string displays message
   */
  String gameEndGui();

  /**
   * Checks if player can attack target and then only items list is displayed.
   */
  Boolean getCondition();

  /**
   * Attack target in GUI part 2.
   * @param playerTurn currentPlayer
   * @param item to attack
   * @return string 
   */
  void attackTargetGuiPart2(PlayerImpl currentPlayer, ItemImpl item);

}