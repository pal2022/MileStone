package world;

import java.io.IOException;
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
public class MockMansion implements MansionInterface {

  public Map<Integer, SpaceImpl> rooms;
  public CharacterImpl targetCharacter;
  public CharacterPetImpl pet;
  public final List<PlayerImpl> players;
  private List<Integer> petVisitedRoomIds;
  private Stack<Integer> petVisitedPath;
  private int currentPlayer = 0;
  private int turnNumber = 1;
  private int maxTurns;
  private final StringBuilder log = new StringBuilder();
  private final Appendable appendable;
  private final Scanner scanner;

  /**
   * Constructor of the mock mansion.
   * 
   * @param readable   readable
   * @param appendable appendable
   * @param maxTurns   allowed turn in the game
   */
  public MockMansion(Readable readable, Appendable appendable, int maxTurns) {
    this.appendable = appendable;
    this.scanner = new Scanner(readable);
    this.players = new ArrayList<PlayerImpl>();
    this.rooms = new HashMap<Integer, SpaceImpl>();
    this.petVisitedRoomIds = new ArrayList<>();
    petVisitedPath = new Stack<>();
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
   * This method is for assigning the target character of the mansion.
   */
  public void assignCharacter(CharacterImpl targetCharacter) {
    this.targetCharacter = targetCharacter;
  }

  /**
   * This method is for assigning pet to the mansion.
   * 
   * @param pet the pet
   */
  public void assignPet(CharacterPetImpl pet) {
    this.pet = pet;
  }

  /**
   * This method is for getting room name of the room in which the player is.
   */
  @Override
  public String getTargetRoomName() {
    SpaceImpl room = rooms.get(this.targetCharacter.getRoomId());
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
    return 25;
  }

  /**
   * This method is for getting numbers of columns of the mansion.
   */
  @Override
  public int getColumns() {
    return 25;
  }

  /**
   * This method is for getting numbers of rooms of the mansion.
   */
  @Override
  public int getRoomCount() {
    return rooms.size();
  }

  /**
   * This method is for getting name of the mansion.
   */
  @Override
  public String getName() {
    return "MockMansion";
  }

  /**
   * This method is for getting list of rooms of the mansion.
   */
  @Override
  public Map<Integer, SpaceImpl> getRooms() {
    return rooms;
  }

  /**
   * This method is used to get map of rooms.
   * 
   * @return rooms
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
    players.add(player);
    log.append("Player " + player.getName() + " added to the mansion");
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
    if (currentPlayer + 1 >= players.size()) {
      this.turnNumber = this.turnNumber + 1;
    }
    currentPlayer = (currentPlayer + 1) % players.size();
    return getCurrentPlayer();
  }

  /**
   * Return the value of turn number.
   * 
   * @return turnNumber its value
   */
  public int getTurnNumber() {
    return turnNumber;
  }

  @Override
  public void displayPlayers() {
    try {
      appendable.append("Players in the mansion are : ");
      for (PlayerImpl player : players) {
        appendable.append("\nPlayer name : " + player.getName());
        appendable.append("\nPlayer Space : " + player.playerSpace());
        appendable.append("\nPlayer items : ");
        player.displayPickedItems();
        appendable.append("\nPlayer visited spaces : ");
        player.displaySpacePath();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public String toString() {
    return String.format(
        "Mansion name: %s, number of rows: %d, number of columns: %d " + "and number of rooms: %d",
        getName(), getRows(), getColumns(), getRoomCount());
  }

  /**
   * Get the string representation.
   * 
   * @return string representation
   */
  public String getLog() {
    return log.toString();
  }

  @Override
  public void attackTarget(PlayerImpl player) {
    PlayerImpl playerTurn = playerTurn();
    SpaceImpl currentPlayerSpace = playerTurn.playerSpace();
    boolean checkOtherPlayers = seenByPlayers(currentPlayerSpace, playerTurn);

    if (currentPlayerSpace.getId() == targetCharacter.getRoomId()) {
      if (checkOtherPlayers) {
        try {
          appendable.append("You are watched by another player. " + "You cannot attack "
              + "the target character");
          String name = seenByPlayers2(currentPlayerSpace, playerTurn);
          appendable.append("Player watching you : " + name);
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        List<ItemImpl> currentPlayerItems = playerTurn.getItems();

        if (currentPlayerItems.isEmpty()) {
          try {
            appendable.append("Player " + playerTurn.getName() + " , you have no items.");
            int damage = 1;
            targetCharacter.takenDamage(damage);
            appendable.append("Default attack : poking him in the eye!");
          } catch (IOException e) {
            e.printStackTrace();
          }

        } else {
          try {
            appendable.append("List of items : ");
            int i = 1;
            for (ItemImpl item : currentPlayerItems) {
              appendable
                  .append(i + " Item : " + item.getName() + ", damage : " + item.getDamage());
              i++;
            }
          } catch (IOException e) {
            e.printStackTrace();
          }

          Scanner scanner = new Scanner(System.in);

          if (playerTurn.getName().equals("Computer player")) {
            ItemImpl item = getHighestDamageItem(currentPlayerItems);
            int damage = item.getDamage();
            targetCharacter.takenDamage(damage);
            playerTurn.addPoints(damage);
            playerTurn.removePlayerItem(item);

            try {
              appendable.append(playerTurn.getName() + ", you have attacked the target character "
                  + targetCharacter.getName() + " with the item " + item.getName());
              appendable
                  .append("Current health of target character is " + targetCharacter.getHealth());
            } catch (IOException e) {
              e.printStackTrace();
            }

          } else {
            try {
              int choice = scanner.nextInt();
              if (choice >= 1 && choice <= currentPlayerItems.size()) {
                ItemImpl item = currentPlayerItems.get(choice - 1);
                int damage = item.getDamage();
                targetCharacter.takenDamage(damage);
                playerTurn.addPoints(damage);
                playerTurn.removePlayerItem(item);

                try {
                  appendable.append(
                      playerTurn.getName() + ", you have attacked " + "the target character "
                          + targetCharacter.getName() + " with the item " + item.getName());
                  appendable.append(
                      "Current health of target character is " + targetCharacter.getHealth());
                } catch (IOException e) {
                  e.printStackTrace();
                }

              } else {
                try {
                  appendable.append("Please select valid item number next time");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            } catch (InputMismatchException e) {
              try {
                appendable.append("Invalid input");
              } catch (IOException e1) {
                e1.printStackTrace();
              }
            }
          }
        }
      }
    } else {
      try {
        appendable.append("You are not in the same room as the target character. You "
            + "cannot attack the target character.");
        appendable.append(targetCharacter.getRoomId() + "  " + playerTurn.playerSpace().getId());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

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
      lookAround.append("\nLeft window view: Room : ");
      lookAround.append(leftNeighbour.getName());
      lookAround.append(" with room id: ").append(leftNeighbour.getId());
      lookAround.append("\n" + leftNeighbour.getItems());
    } else {
      lookAround.append("\nNo room through the left window.");
    }
    if (rightNeighbour != null) {
      lookAround.append("\nRight window view: Room : ");
      lookAround.append(rightNeighbour.getName());
      lookAround.append(" with room id: ").append(rightNeighbour.getId());
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

    return lookAround.toString();
  }

  @Override
  public String displayNeighbours(SpaceImpl room) {
    if (room.getNeighbours().size() == 0) {
      try {
        appendable.append("There are no neighbours for the room " + room.getName() + "\n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        appendable.append("Neighbours for this room are : \n");
      } catch (IOException e) {
        e.printStackTrace();
      }
      for (SpaceInterface neighbour : room.getNeighbours()) {
        if (neighbour.getId() != getPetRoomId()) {
          try {
            appendable.append(
                "Room : " + neighbour.getName() + " with room id: " + neighbour.getId() + "\n");
          } catch (IOException e) {
            e.printStackTrace();
          }
        } else {
          try {
            appendable.append("Pet effect");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return appendable.toString();
  }

  @Override
  public String gameEnd() {
    PlayerImpl winner = playerPoints();
    StringBuilder gameEnd = new StringBuilder();
    if (targetCharacter.tcDead()) {
      gameEnd.append("\nGame over and the player ").append(winner.getName()).append(" wins");
    } else if (this.turnNumber > maxTurns && !(targetCharacter.tcDead())) {
      gameEnd.append("\nGame over and the target has escaped");
    }
    return gameEnd.toString();
  }

  @Override
  public String playerDescription(PlayerImpl player1) {
    StringBuilder playerDescription = new StringBuilder();
    String playerName = player1.getName();
    for (PlayerImpl player2 : players) {
      if (player2.getName().equals(playerName)) {
        playerDescription.append("\nPlayer name: ").append(player2.getName());
        playerDescription.append("\nPlayer current space : ")
            .append(player2.playerSpace().getName());
        playerDescription.append("\nItems List : ").append(player2.displayPickedItems());
      }
    }
    return playerDescription.toString();
  }

  @Override
  public List getPlayers() {
    return players;
  }

  @Override
  public String getTargetName() {
    return targetCharacter.getName();
  }

  @Override
  public void moveTarget() {
    targetCharacter.movePlayer();
  }

  @Override
  public void movePet(int roomId) {
    pet.playerMovesPet(roomId);
  }

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

  @Override
  public int getTargetRoomId() {
    return targetCharacter.getRoomId();
  }

  @Override
  public void checkIfRoomMatchesWithTc(PlayerImpl player) {
    if (player.playerSpace().getId() == targetCharacter.getRoomId()) {
      try {
        appendable.append("Player " + player.getName() + " you are in the same room as "
            + "the target character " + targetCharacter.getName());
        appendable.append("Do you wish to attack the target character?");
        appendable.append("Enter y for yes and n for no!");
      } catch (IOException e) {
        e.printStackTrace();
      }

      Scanner scanner = new Scanner(System.in);
      String choice = scanner.next();
      if ("y".equals(choice)) {
        player.world.attackTarget(player);
      } else if ("n".equals(choice)) {
        try {
          appendable.append("You have missed a great oppurtunity.");
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else {
        try {
          appendable.append("How can you miss this opportunity by not typing the 'y'?");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public List<SpaceInterface> getNeighbours(SpaceImpl room) {
    List<SpaceInterface> neighbours = new ArrayList<>();
    for (SpaceInterface neighbour : room.getNeighbours()) {
      neighbours.add(neighbour);
    }
    return neighbours;
  }

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

  @Override
  public int getPetRoomId() {
    return pet.getPetRoomId();
  }

  @Override
  public String getPetName() {
    return pet.getName();
  }

  @Override
  public CharacterInterface getTarget() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String attackTargetGuiPart1(PlayerImpl playerTurn) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void clearPlayers() {
    // TODO Auto-generated method stub

  }

  @Override
  public CharacterPetInterface getPet() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String gameEndGui() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Boolean getCondition() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void attackTargetGuiPart2(PlayerImpl currentPlayer, ItemImpl item) {
    // TODO Auto-generated method stub
    
  }

}
