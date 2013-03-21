/*
 * Splitter.java
 */

package elv.util;

/**
 * Class for splitting.
 * @author Qpa
 */
public class Splitter
{
  
  /**
   * Constructor.
   */
  public Splitter()
  {
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    try
    {
      java.lang.String inputFileName = "/home/db2inst/elv-3.0/data/morb_1999.txt";
      java.lang.String outputFileName = "/home/db2inst/elv-3.0/data/morbidity_1999.txt";
      java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(inputFileName), elv.util.Util.FILE_ENCODING));
      java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(outputFileName), elv.util.Util.FILE_ENCODING));
      java.lang.String line;
      while((line = fileReader.readLine()) != null)
      {
        java.lang.String[] lineSplit = line.split(java.util.regex.Pattern.quote(";"));
        java.lang.String newLine = lineSplit[0];
        for(int i = 1; i < lineSplit.length; i++)
        {
          java.lang.String iteratorSplit = lineSplit[i];
          if(i == 1 || i == 2)
          {
          }
          else if(i == 4)
          {
            newLine += ";" + iteratorSplit.substring(0, 4) + ";" + iteratorSplit.substring(4, 6) + ";" + iteratorSplit.substring(6, 8);
          }
          else if(i == 7)
          {
            newLine += ";" + iteratorSplit.substring(0, iteratorSplit.indexOf(","));
          }
          else if(i==9 || i==11)
          {
            newLine += ";" + iteratorSplit.substring(0, 4) + ";" + iteratorSplit.substring(4, 6) + ";" + iteratorSplit.substring(6, 8) +
                       ";" + iteratorSplit.substring(8, 10) + ";" + iteratorSplit.substring(10, 12);
          }
          else if(i == 10)
          {
            newLine += ";" + iteratorSplit.substring(0, 1);
          }
          else if(iteratorSplit.equals(""))
          {
            newLine += "; ";
          }
          else
          {
            newLine += ";" + iteratorSplit;
          }
        }
        java.lang.String[] newLineSplit = newLine.split(java.util.regex.Pattern.quote(";"));
        for(int i = newLineSplit.length; i < 53; i++)
        {
          newLine += "; ";
        }
        fileWriter.println(newLine);
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
