package world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to read the text file line by line.  
 */
public class DocReader {

  private String filePath;

  /**
   * It takes the value of filepath as input.
   * @param filePath the path of the file to be read, taken from the Main class.
   */
  public DocReader(String filePath) {
    this.filePath = filePath;
  }

  /**
   * It reads the text file line by line and returns list of all the lines.
   * @return string list
   */
  public List<String> read() {
    
    List<String> strlist = new ArrayList<String>();
    FileReader reader;
    try {
      reader = new FileReader(filePath);
      BufferedReader buffer = new BufferedReader(reader);
      String str;
      while ((str = buffer.readLine()) != null) {
        strlist.add(str);
      }

      buffer.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return strlist;
  }
}
