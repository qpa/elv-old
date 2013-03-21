/*
 * InsertPopulation.java
 */

package elv.util;

/**
 * Class for inserting.
 * @author Qpa
 */
public class InsertPopulation
{
  
  /**
   * Constructor.
   */
  public InsertPopulation()
  {
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    java.sql.Connection dbConnection = null;
    java.sql.Statement dbStatement = null;
    java.sql.Connection csvConnection = null;
    java.sql.Statement csvStatement = null;
    try
    {
      java.lang.String tableName = "population";
//      java.lang.Class.forName("org.h2.Driver");
      java.lang.Class.forName("org.hsqldb.jdbcDriver");
      
      java.lang.System.out.println("Connecting: " + new java.util.Date());
//      dbConnection = java.sql.DriverManager.getConnection("jdbc:h2:d:/data/elv;CACHE_SIZE=4096", "elv", "elv");
      dbConnection = java.sql.DriverManager.getConnection("jdbc:hsqldb:file:d:/data/hsqldb/elv", "elv", "elv");
      dbStatement = dbConnection.createStatement();
      
      java.util.Properties csvProperties = new java.util.Properties();
//      csvProperties.put("separator","|");              // separator is a bar
      csvProperties.put("suppressHeaders","true");     // first line contains data
//      csvProperties.put("fileExtension",".txt");       // file extension is .txt
//      csvProperties.put("charset","ISO-8859-2");       // file encoding is "ISO-8859-2"
      java.lang.Class.forName("org.relique.jdbc.csv.CsvDriver");
      java.lang.String dataDirectoryPath = "d:/data";
      csvConnection = java.sql.DriverManager.getConnection("jdbc:relique:csv:" + dataDirectoryPath, csvProperties);
      csvStatement = csvConnection.createStatement();
      for(int year = 1986; year <= 2004; year++)
      {
        java.lang.String fileBaseName = "population_" + year;
        java.lang.System.out.println(fileBaseName);
        
        java.lang.System.out.println("Counting: " + new java.util.Date());
        int lineCount = 0;
        java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(dataDirectoryPath + java.lang.System.getProperty("file.separator") + fileBaseName + ".csv")));
        while(fileReader.readLine() != null)
        {
          lineCount++;
        }
        fileReader.close();
        
        java.lang.System.out.println("Loading: " + new java.util.Date());
        java.sql.ResultSet rs = csvStatement.executeQuery("SELECT * FROM " + fileBaseName);

        int count = 0;
        while(rs.next())
        {
          count++;
          java.lang.String insertString = "INSERT INTO " + tableName + " VALUES(" + year + ", " + rs.getInt(1) + ", " + rs.getInt(2) + ", " + rs.getInt(3) + ", " + rs.getInt(4) + ")";
          dbStatement.executeUpdate(insertString);
          dbConnection.commit();
          if(count % 50000 == 0)
          {
            java.lang.System.out.println("Loaded: " + new java.util.Date() + " : " + count + " / " + lineCount);
          }
          java.lang.Thread.yield();
        }
        rs.close();
        java.lang.System.out.println(fileBaseName + ": " + count + " lines inserted");
      }
      csvStatement.close();
      csvConnection.close();
      
      java.lang.System.out.println("Closing: " + new java.util.Date());
      dbStatement.executeUpdate("SHUTDOWN");
      dbStatement.close();
      dbConnection.close();
      dbConnection = null;
      java.lang.System.out.println("Done: " + new java.util.Date());
    }
    catch(java.lang.Exception exc)
    {
      exc.printStackTrace();
    }
    finally
    {
      // Always make sure result sets and statements are closed, and the connection is returned to the pool.
      if (dbStatement != null)
      {
        try
        {
          dbStatement.close();
        } catch (java.sql.SQLException e)
        {
        }
        dbStatement = null;
      }
      if (dbConnection != null)
      {
        try
        {
          dbConnection.close();
        } catch (java.sql.SQLException e)
        {
        }
        dbConnection = null;
      }
    }
  }
  
}
