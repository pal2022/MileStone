package world;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import run.Main;

/**
 * This test case is to check if the DocReader class is reading the file correctly.
 */
public class DocReaderTest {

  /**
   * This test reads the file and compares if the file is reading the correct value.
   */
  @Test
  public void testRead() {
    String filePath = Main.class.getResource("/run/PalMansion.txt").getPath();
    DocReader docReader = new DocReader(filePath);
    List<String> strlist = docReader.read();
    assertEquals(52, strlist.size());
    assertEquals("36 39 Pal's Mansion", strlist.get(0));
  }

}
