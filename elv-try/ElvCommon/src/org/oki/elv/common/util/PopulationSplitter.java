package org.oki.elv.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.regex.Pattern;

/**
 * Class for splitting.
 * @author Elv
 */
public class PopulationSplitter {
  
  /**
   * Constructor.
   */
  public PopulationSplitter() {
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    try {
      String separator = ";";
      int currentYear = 2010;
      int gender = 2;
      String inputFileName = "e:/ELV/data/population/" + currentYear + (gender == 1 ? "_mal" : "_fem") + ".csv";
      String outputFileName = "e:/ELV/data/population/" + currentYear + ".csv";
      BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName)));
      PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFileName, true)));
      int lineCount = 0;
      String line;
      while((line = fileReader.readLine()) != null) {
        try {
          lineCount++;
          String[] lineSplit = line.split(Pattern.quote(separator));
          int settlement = Integer.parseInt(lineSplit[0]) / 10;
          for(int i = 1; i < lineSplit.length; i++) {
            fileWriter.println(currentYear + "," + settlement + "," + gender + "," + (i - 1) + "," + lineSplit[i].replace(" ", ""));
          }
        }
        catch(Exception exc) {
          System.out.println("ERROR: Line " + lineCount + ": " + line);
          System.out.println("ERROR: Message: " + exc.getMessage());
        }
      }
      fileReader.close();
      fileWriter.flush();
      fileWriter.close();
    }
    catch(Exception exc) {
      exc.printStackTrace();
    }
  }
  
}
