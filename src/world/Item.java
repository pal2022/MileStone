package world;

/**
 * The class Item implements the interface ItemInterface.
 * This class is used to assign the room for the items using the data given in the
 * text file and store information of the item. 
 */
public class Item implements ItemInterface {

  private Space room;
  private int roomId;
  private String itemName;
  private int damage;
  
  /**
   * This constructor is used for parsing information from the file using itemInfo.
   * @param itemInfo gets the value from the file.
   */
  public Item(String itemInfo) {
    try {
      //to remove extra spaces
      itemInfo = itemInfo.stripLeading();
      String[] itemInfoList = itemInfo.split("\\s+", 3);
      this.roomId = Integer.parseInt(itemInfoList[0]); 
      this.damage = Integer.parseInt(itemInfoList[1]); 
      this.itemName = itemInfoList[2];
    } catch (NumberFormatException e) {
      System.out.println("Invalid information" + itemInfo);
    }
    
  }
  
  /**
   * This method assigns the room to the item.
   */
  @Override
  public void assignRoom(Space room) {
    this.room = room;
  }
  
  /**
   * This method is used to get the name of the item.
   * @return item name
   */
  @Override
  public String getName() {
    return this.itemName;
  }
  
  /**
   * This method is used to get the damage power of the item.
   * @return item damage
   */
  @Override
  public int getDamage() {
    return this.damage;
  }
  
  /**
   * This method is used to get the id of the room the item is in.
   * @return room id
   */
  @Override
  public int getRoomId() {
    return this.roomId;
  }

  @Override
  public String toString() {
    return String.format("Item name: %s, item damage power: %d, item roomId: %s", 
    getName(), getDamage(), getRoomId());
  }
}
