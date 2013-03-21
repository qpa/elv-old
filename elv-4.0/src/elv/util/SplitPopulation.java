/*
 * SplitPopulation.java
 */

package elv.util;

/**
 * Class for splitting.
 * @author Elv
 */
public class SplitPopulation
{
  
  /**
   * Constructor.
   */
  public SplitPopulation()
  {
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    try
    {
      java.lang.String inputFileName = "D:/elv-3.0/data/population_2004mal_999.csv";
      java.lang.String outputFileName = "D:/elv-3.0/data/population_2004mal_999_split.csv";
      java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(inputFileName), elv.util.Util.FILE_ENCODING));
      java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(outputFileName), elv.util.Util.FILE_ENCODING));
      java.lang.String line;
      while((line = fileReader.readLine()) != null)
      {
        java.lang.String[] lineSplit = line.split(java.util.regex.Pattern.quote(";"));
        java.lang.String settlement = lineSplit[0].substring(0, 3);
        for(int i = 1; i < lineSplit.length; i++)
        {
          fileWriter.println(settlement + ",1," + (i - 1) + "," + lineSplit[i]);
        }
      }
      fileReader.close();
      fileWriter.flush();
      fileWriter.close();
    }
    catch(java.lang.Exception exc)
    {
      exc.printStackTrace();
    }
  }
  
}
