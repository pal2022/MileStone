package world;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CharacterPetImpl implements CharacterPetInterface {

  private String petName;
  private int roomId;
  MansionImpl world;
  
  public CharacterPetImpl(MansionImpl world, String petName) {
    this.petName = petName;
    this.roomId = 0;
    this.world = world;
  }
    
  @Override
  public void movePet() {
    System.out.println("move pet");
  }


  @Override
  public String getName() {
    return this.petName;
  }

  @Override
  public void playerMovesPet(int givenRoomId) {
    this.roomId = givenRoomId;
  }
  
  @Override
  public int getPetRoomId() {
    return this.roomId;
  }

}
