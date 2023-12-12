package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * This class for storing the information of the mansion. It has methods assign
 * character and rooms to the mansion and get neighbours of the room.
 */
public class MansionImpl implements MansionInterface {

  public Map<Integer, SpaceImpl> rooms;
  public CharacterInterface targetCharacter;
  public CharacterPetImpl pet;
  public List<PlayerImpl> players;
  private List<Integer> petVisitedRoomIds;
  private Stack<Integer> petVisitedPath;
  private int rows;
  private int columns;
  private int roomCount;
  private String mansionName;
  private int currentPlayer;
  private int turnNumber;
  private int maxTurns;
  private Boolean condition;

  /**
   * This constructor takes the required values of the fields obtained from the
   * file.
   * 
   * @param rows            rows of mansion
   * @param columns         columns of mansion
   * @param roomCount       number of rooms in mansion
   * @param mansionName     name of the mansion
   * @param pet             the pet
   * @param targetCharacter the target character
   * @param maxTurns        max turns of a game
   */
  public MansionImpl(int rows, int columns, int roomCount, String mansionName,
      CharacterImpl targetCharacter, CharacterPetImpl pet, int maxTurns) {
    this.rows = rows;
    this.columns = columns;
    this.roomCount = roomCount;
    this.mansionName = mansionName;
    this.currentPlayer = 0;
    this.turnNumber = 1;
    this.players = new ArrayList<>();
    this.rooms = new HashMap<>();
    this.petVisitedRoomIds = new ArrayList<>();
    petVisitedPath = new Stack<>();
    this.targetCharacter = targetCharacter;
    this.pet = pet;
    this.maxTurns = maxTurns;
  }

  /**
   * This method is for adding room in the mansion.
   */
  @Override
  public void addRoom(SpaceImpl room) {
    this.rooms.put(room.getId(), room);
  }

  /**
   * This method is for getting room name of the room in which the player is.
   */
  @Override
  public String getTargetRoomName() {
    SpaceImpl room = rooms.get(targetCharacter.getRoomId());
    return room.getName();
  }

  /**
   * This method is for assigning neighbours of a room.
   */
  @Override
  public void assignNeighbours() {
    for (SpaceImpl room1 : rooms.values()) {
      for (SpaceImpl room2 : rooms.values()) {
        if (room1.getId() == room2.getId()) {
          continue;
        }
        if (room1.getX2() == room2.getX1() || room1.getX1() == room2.getX2()) {
          if (room1.getY1() < room2.getY2() && room1.getY2() > room2.getY1()) {
            room1.addNeighbour(room2);
          }
        }
        if (room1.getY2() == room2.getY1() || room1.getY1() == room2.getY2()) {
          if (room1.getX1() < room2.getX2() && room1.getX2() > room2.getX1()) {
            room1.addNeighbour(room2);
          }
        }
      }
    }
  }

  /**
   * This method is for getting numbers of rows of the mansion.
   */
  @Override
  public int getRows() {
    return this.rows;
  }

  /**
   * This method is for getting numbers of columns of the mansion.
   */
  @Override
  public int getColumns() {
    return this.columns;
  }

  /**
   * This method is for getting numbers of rooms of the mansion.
   */
  @Override
  public int getRoomCount() {
    return this.roomCount;
  }

  /**
   * This method is for getting name of the mansion.
   */
  @Override
  public String getName() {
    return this.mansionName;
  }

  /**
   * This method is for getting list of rooms of the mansion.
   */
  @Override
  public Map<Integer, SpaceImpl> getRooms() {
    return this.rooms;
  }

  /**
   * This method is for getting the space using space id.
   * 
   * @return Space the space of the mansion
   */
  @Override
  public SpaceImpl getRoomWithId(int id) {
    return rooms.get(id);
  }

  /**
   * This method is for adding player in the mansion.
   * 
   * @param player the player to be added
   */
  @Override
  public void addPlayer(PlayerImpl player) {
    synchronized (players) {
      players.add(player);
    }
  }

  /**
   * This method is for getting the current player. It uses the updated current
   * player from playerTurn method.
   */
  @Override
  public PlayerImpl getCurrentPlayer() {
    return players.get(currentPlayer);
  }

  /**
   * This method ensures that the players get their turn one by one.
   */
  @Override
  public PlayerImpl playerTurn() {
    if (players.isEmpty()) {
      return null;
    }

    if (currentPlayer + 1 >= players.size()) {
      this.turnNumber = this.turnNumber + 1;
    }
    currentPlayer = (currentPlayer + 1) % players.size();
    return getCurrentPlayer();
  }

  /**
   * This method gets the turn number.
   * 
   * @return turnNumber the turn number
   */
  @Override
  public int getTurnNumber() {
    return this.turnNumber;
  }

  /**
   * This method displays the information of the players.
   */
  @Override
  public void displayPlayers() {
    System.out.println("Players in the mansion are : ");
    for (PlayerImpl player : players) {
      System.out.println("Player name : " + player.getName());
      SpaceImpl playerSpace = player.playerSpace();
      System.out.println("Player space : " + playerSpace);
      System.out.println("Picked items : ");
      player.displayPickedItems();
      System.out.println("Display neighbours:");
      player.displayNearbySpace(player, playerSpace);

    }
  }

  /**
   * This method is for checking if a player is seen by any other player.
   * 
   * @return true if player is seen, else false
   */
  @Override
  public boolean seenByPlayers(SpaceImpl currentPlayerSpace, PlayerImpl playerTurn) {
    if (players.size() == 1) {
      return false;
    }
    for (PlayerImpl player : players) {
      SpaceImpl playerSpace = player.playerSpace();
      if (player != playerTurn && (playerSpace.getId() == currentPlayerSpace.getId())) {
        return true;
      } else if (player != playerTurn
          && currentPlayerSpace.getNeighbours().contains(playerSpace)) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method returns the name of the player that is seeing the player who
   * wants to attack the target character.
   * 
   * @return player name name of the player who is watching
   */
  @Override
  public String seenByPlayers2(SpaceImpl currentPlayerSpace, PlayerImpl playerTurn) {
    for (PlayerImpl player : players) {
      SpaceImpl playerSpace = player.playerSpace();
      if (player != playerTurn && (playerSpace.getId() == currentPlayerSpace.getId())) {
        return player.getName();
      } else if (player != playerTurn
          && currentPlayerSpace.getNeighbours().contains(playerSpace)) {
        return player.getName();
      }
    }
    return "null";
  }

  /**
   * This method returns the item with the highest damage power from a given list
   * of items.
   * 
   * @return maxDamageItem item with most damage power
   */
  @Override
  public ItemImpl getHighestDamageItem(List<ItemImpl> items) {
    ItemImpl maxDamageItem = items.get(0);
    for (ItemImpl item : items) {
      if (item.getDamage() > maxDamageItem.getDamage()) {
        maxDamageItem = item;
      }
    }
    return maxDamageItem;
  }

  /**
   * This method is for returning the player with the highest damage power.
   * 
   * @return winningPlayer the winner of the game
   */
  @Override
  public PlayerImpl playerPoints() {
    PlayerImpl winningPlayer = null;
    int highestPoints = Integer.MIN_VALUE;
    for (PlayerImpl player : this.players) {
      int totalPoints = player.returnTotalPoints();
      if (totalPoints > highestPoints) {
        highestPoints = totalPoints;
        winningPlayer = player;
      }
    }
    return winningPlayer;

  }

  /**
   * This method is for attacking the target character.
   * 
   * @param player the player attacking the target character
   */
  @Override
  public void attackTarget(PlayerImpl playerTurn) {
    SpaceImpl currentPlayerSpace = playerTurn.playerSpace();
    boolean checkOtherPlayers = seenByPlayers(currentPlayerSpace, playerTurn);

    if (currentPlayerSpace.getId() == targetCharacter.getRoomId()) {
      if (checkOtherPlayers) {
        System.out.println(
            "You are watched by another player. " + "You cannot attack " + "the target character");
        String name = seenByPlayers2(currentPlayerSpace, playerTurn);
        System.out.println("Player watching you : " + name);
      } else {
        List<ItemImpl> currentPlayerItems = playerTurn.getItems();

        if (currentPlayerItems.isEmpty()) {
          System.out.println("Player " + playerTurn.getName() + " , you have no items.");
          int damage = 1;
          targetCharacter.takenDamage(damage);
          playerTurn.addPoints(damage);
          gameEnd();
          System.out.println("Default attack : poking him in the eye!");
        } else {
          System.out.println("List of items : ");
          int i = 1;
          for (ItemImpl item : currentPlayerItems) {
            System.out.println(i + " Item : " + item.getName() + ", damage : " + item.getDamage());
            i++;
          }

          Scanner scanner = new Scanner(System.in);

          if (playerTurn.getName().equals("Computer player")) {
            ItemImpl item = getHighestDamageItem(currentPlayerItems);
            int damage = item.getDamage();
            targetCharacter.takenDamage(damage);
            playerTurn.addPoints(damage);
            playerTurn.removePlayerItem(item);
            gameEnd();
            System.out.println(playerTurn.getName() + ", you have attacked the target character "
                + targetCharacter.getName() + " with the item " + item.getName());
            System.out
                .println("Current health of target character is " + targetCharacter.getHealth());
          } else {
            try {
              int choice = scanner.nextInt();
              if (choice >= 1 && choice <= currentPlayerItems.size()) {
                ItemImpl item = currentPlayerItems.get(choice - 1);
                int damage = item.getDamage();
                targetCharacter.takenDamage(damage);
                playerTurn.addPoints(damage);
                playerTurn.removePlayerItem(item);
                System.out.println(playerTurn.getName() + ", you have attacked the target "
                    + "character " + targetCharacter.getName() + " with item " + item.getName());
                System.out.println(
                    "Current health of target character is " + targetCharacter.getHealth());
                gameEnd();
              } else {
                System.out.println("Please select valid item number next time");
              }
            } catch (InputMismatchException e) {
              System.out.println("Invalid input");
            }
          }
        }
      }
    } else {
      System.out.println("You are not in the same room as the target character. "
          + "You cannot attack the target character.");
      System.out.println("Target character room id : " + targetCharacter.getRoomId());
      System.out.print(" Player room id" + playerTurn.playerSpace().getId());
    }
  }

  /**
   * String representation for the class.
   */
  @Override
  public String toString() {
    return String.format(
        "Mansion name: %s, number of rows: %d, number of columns: %d " + "and number of rooms: %d",
        getName(), getRows(), getColumns(), getRoomCount());
  }

  /**
   * This method is for getting the list of neighbours for a given room.
   * 
   * @param room the room to find neighbours for
   * @return neighbours list of neighbours for that room
   */
  @Override
  public List<SpaceInterface> getNeighbours(SpaceImpl room) {
    List<SpaceInterface> neighbours = new ArrayList<>();
    for (SpaceInterface neighbour : room.getNeighbours()) {
      neighbours.add(neighbour);
    }
    return neighbours;
  }

  /**
   * This method is for looking around the room. Looking around shows the details
   * of the the room and some details of the neighbouring room.
   * 
   * @param room the room to be looked around
   * @return lookAround string containing the information to be printed
   */
  @Override
  public String lookAround(SpaceImpl room) {
    StringBuilder lookAround = new StringBuilder();
    lookAround.append("\nName of the room is ").append(room.getName());

    if (room.getItems().isEmpty()) {
      lookAround.append("\nThere are no items present in this room");
    } else {
      lookAround.append("\nItems present in this room are:\n");
      for (ItemImpl item : room.getItems()) {
        lookAround.append(item.getName()).append("\n");
      }
    }
    SpaceImpl leftNeighbour = null;
    SpaceImpl rightNeighbour = null;

    for (SpaceImpl neighbour : rooms.values()) {
      if (neighbour.getId() == room.getId()) {
        continue;
      }
      boolean overlap = room.getX1() < neighbour.getX2() && neighbour.getX1() < room.getX2();
      if (overlap && neighbour.getY2() == room.getY1()) {
        leftNeighbour = neighbour;
      }
      if (overlap && neighbour.getY1() == room.getY2()) {
        rightNeighbour = neighbour;
      }
    }

    if (leftNeighbour != null) {
      if (leftNeighbour.getId() != pet.getPetRoomId()) {
        lookAround.append("\nLeft window view: Room : ");
        lookAround.append(leftNeighbour.getName());
        lookAround.append(" with room id: ").append(leftNeighbour.getId());
        lookAround.append("\n" + leftNeighbour.getItems());
      } else {
        lookAround.append("Pet effect");
      }

    } else {
      lookAround.append("\nNo room through the left window.");
    }
    if (rightNeighbour != null) {
      if (rightNeighbour.getId() != pet.getPetRoomId()) {
        lookAround.append("\nRight window view: Room : ");
        lookAround.append(rightNeighbour.getName());
        lookAround.append(" with room id: ").append(rightNeighbour.getId());
      } else {
        lookAround.append("Pet effect");
      }
    } else {
      lookAround.append("\nNo room through the right window.");
    }

    lookAround.append("\nPlayers present in this room are:");
    for (PlayerImpl player : players) {
      SpaceImpl space = player.playerSpace();
      if (space == room) {
        lookAround.append("Player ");
        lookAround.append(player.getName()).append("\n");
      }
    }

    return lookAround.toString().trim();
  }

  /**
   * This method is for displaying neighbours of a room.
   */
  @Override
  public String displayNeighbours(SpaceImpl room) {
    StringBuilder displayNeighbours = new StringBuilder();
    if (room.getNeighbours().size() == 0) {
      displayNeighbours.append("There are no neighbours for the room " + room.getName() + "\n");
    } else {
      displayNeighbours.append("Neighbours for this room are : \n");
      for (SpaceInterface neighbour : room.getNeighbours()) {
        if (neighbour.getId() != pet.getPetRoomId()) {
          displayNeighbours.append(
              "Room : " + neighbour.getName() + " with room id: " + neighbour.getId() + "\n");
        } else {
          displayNeighbours.append("Pet effect");
        }
      }
    }
    return displayNeighbours.toString().trim();
  }

  /**
   * This method is for checking if a player is in the same room as the target
   * character. It is for giving the player a special change to attack the target
   * character.
   * 
   * @param player the player
   */
  @Override
  public void checkIfRoomMatchesWithTc(PlayerImpl player) {
    if (player.playerSpace().getId() == targetCharacter.getRoomId()) {
      System.out.println("Player " + player.getName() + " you are in the same room as "
          + "the target character " + targetCharacter.getName());
      System.out.println("Do you wish to attack the target character?");
      System.out.println("Enter y for yes and n for no!");
      Scanner scanner = new Scanner(System.in);
      String choice = scanner.next();
      if ("y".equals(choice)) {
        player.world.attackTarget(player);
      } else if ("n".equals(choice)) {
        System.out.println("You have missed a great oppurtunity.");
      } else {
        System.out.println("How can you miss this opportunity by not typing the 'y'?");
      }
    }
  }

  /**
   * This method gives the message according to how the game has ended.
   * 
   * @return gameEnd representing the end of the game
   */
  @Override
  public String gameEnd() {
    PlayerImpl winner = playerPoints();
    StringBuilder gameEnd = new StringBuilder();
    if (targetCharacter.tcDead()) {
      gameEnd.append("\nGame over and the player ").append(winner.getName()).append(" wins");
    } else if (this.turnNumber >= maxTurns - 1 && !(targetCharacter.tcDead())) {
      gameEnd.append("\nGame over and the target has escaped");
    }
    return gameEnd.toString().trim();
  }

  @Override
  public String gameEndGui() {
    PlayerImpl winner = playerPoints();
    StringBuilder gameEnd = new StringBuilder();
    if (targetCharacter.tcDead()) {
      gameEnd.append("\nGame over and the player ").append(winner.getName()).append(" wins");
    } else if (!(targetCharacter.tcDead())) {
      gameEnd.append("\nGame over and the target has escaped");
    }
    return gameEnd.toString().trim();
  }

  /**
   * This method is for the details of the player.
   * 
   * @param player the player
   * @return playerDescription information of the player
   */
  @Override
  public String playerDescription(PlayerImpl player) {
    StringBuilder playerDescription = new StringBuilder();
    String playerName = player.getName();
    for (PlayerImpl player1 : players) {
      if (player1.getName().equals(playerName)) {
        playerDescription.append("\nPlayer name: ").append(player1.getName());
        playerDescription.append("\nPlayer current space : ")
            .append(player1.playerSpace().getName());
        playerDescription.append("\nItems List : ").append(player1.displayPickedItems());
      }
    }
    return playerDescription.toString().trim();
  }

  /**
   * This method gives the list of the players.
   * 
   * @return players list of players
   */
  @Override
  public List<PlayerImpl> getPlayers() {
    return players;
  }

  /**
   * This method is for getting the name of the target character.
   * 
   * @return targetName name of the target
   */
  @Override
  public String getTargetName() {
    return targetCharacter.getName();
  }

  /**
   * This method is for moving the target character.
   */
  @Override
  public void moveTarget() {
    targetCharacter.movePlayer();
  }

  /**
   * This method is for moving the target character's pet.
   * 
   * @param newSpace space id of the room to be moved to
   */
  @Override
  public void movePet(int newSpace) {
    pet.playerMovesPet(newSpace);
  }

  /**
   * This method moves pet using dfs.
   */
  @Override
  public void movePet() {
    if (petVisitedPath.isEmpty()) {
      petVisitedPath.push(pet.getPetRoomId());
    }

    SpaceImpl currentRoom = getRoomWithId(pet.getPetRoomId());
    List<SpaceInterface> neighbours = currentRoom.getNeighbours();
    List<SpaceInterface> unvisitedNeighbours = neighbours.stream()
        .filter(n -> !petVisitedRoomIds.contains(n.getId())).collect(Collectors.toList());

    if (!unvisitedNeighbours.isEmpty()) {
      SpaceInterface nextRoom = unvisitedNeighbours.get(0);
      pet.playerMovesPet(nextRoom.getId());
      petVisitedRoomIds.add(nextRoom.getId());
      petVisitedPath.push(nextRoom.getId());
    } else if (!petVisitedPath.isEmpty()) {
      petVisitedPath.pop();
      if (!petVisitedPath.isEmpty()) {
        int previousRoomId = petVisitedPath.peek();
        pet.playerMovesPet(previousRoomId);
      }
    }

  }

  /**
   * This method is for getting the room id of the target character.
   * 
   * @return roomid target character's roomid
   */
  @Override
  public int getTargetRoomId() {
    return targetCharacter.getRoomId();
  }

  /**
   * This method is for getting the room id of the target character's pet.
   * 
   * @return roomid target character's pet's roomid
   */
  @Override
  public int getPetRoomId() {
    return pet.getPetRoomId();
  }

  /**
   * This method is for getting the name of the target character's pet.
   * 
   * @return name target character's pet's name
   */
  @Override
  public String getPetName() {
    return pet.getName();
  }

  @Override
  public CharacterInterface getTarget() {
    return targetCharacter;
  }

  @Override
  public CharacterPetInterface getPet() {
    return pet;
  }

  @Override
  public String attackTargetGuiPart1(PlayerImpl playerTurn) {
    StringBuilder attackTarget = new StringBuilder();
    SpaceImpl currentPlayerSpace = playerTurn.playerSpace();
    boolean checkOtherPlayers = seenByPlayers(currentPlayerSpace, playerTurn);

    if (currentPlayerSpace.getId() == targetCharacter.getRoomId()) {
      if (checkOtherPlayers) {
        attackTarget
            .append("You are watched by another player. You cannot attack the target character");
        String name = seenByPlayers2(currentPlayerSpace, playerTurn);
        attackTarget.append("Player watching you : " + name);
      } else {
        List<ItemImpl> currentPlayerItems = playerTurn.getItems();

        if (currentPlayerItems.isEmpty()) {
          attackTarget.append("Player " + playerTurn.getName() + " , you have no items.");
          int damage = 1;
          targetCharacter.takenDamage(damage);
          playerTurn.addPoints(damage);
          gameEnd();
          attackTarget.append("Default attack : poking him in the eye!");
        } else {
          attackTarget.append("Choose one of the items");
          condition = true;
        }
      }
    } else {
      attackTarget.append("You are not in the same room as the target character. "
          + "You cannot attack the target character.");
      attackTarget.append("Target character room id : " + targetCharacter.getRoomId());
      attackTarget.append(" Player room id" + playerTurn.playerSpace().getId());
    }
    return attackTarget.toString().trim();
  }

  @Override 
  public void attackTargetGuiPart2(PlayerImpl currentPlayer, ItemImpl item) {
    int damage = item.getDamage();
    getTarget().takenDamage(damage);
    currentPlayer.addPoints(damage);
    currentPlayer.removePlayerItem(item);
    System.out.println(currentPlayer.getName() + ", you have attacked the target " + "character "
        + getTarget().getName() + " with item " + item.getName());
    System.out.println("Current health of target character is " + getTarget().getHealth());

  }
  
  @Override
  public void clearPlayers() {
    this.players.clear();
  }

  /**
   * Checks if player can attack target and then only items list is displayed.
   */
  @Override
  public Boolean getCondition() {
    return condition;
  }

  /**
   * Get world state to check.
   * @return string to compare
   */
  public String getWorldState() {
    StringBuilder stateBuilder = new StringBuilder();
    for (PlayerImpl player : this.getPlayers()) {
      SpaceImpl playerRoom = player.playerSpace();
      stateBuilder.append(player.getName()).append(" is in ").append(playerRoom.getName())
          .append(". ");
    }
    return stateBuilder.toString();
  }

}