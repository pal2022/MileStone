package world;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * This is the test class for world builder class.
 */
public class WorldBuilderTest {

  @Test
  public void test() {
    String testWorldfile = "C:\\Users\\hp\\eclipse-workspace\\"
        + "lab00_getting_started\\MileStone1\\model\\src\\run\\PalMansion.txt";
    MansionImpl world = WorldBuilder.build(testWorldfile, 10);
    assertNotNull(world);

    assertEquals("Pal's Mansion", world.getName());
    assertEquals(23, world.getRoomCount());
    assertNotNull(world.targetCharacter);
    assertNotNull(world.pet);
  }

}
