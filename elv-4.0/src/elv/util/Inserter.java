/*
 * Inserter.java
 */

package elv.util;

/**
 * Class for inserting.
 * @author Elv
 */
public class Inserter
{
  
  /**
   * Constructor.
   */
  public Inserter()
  {
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    java.sql.Connection connection = null;
    java.sql.Statement statement = null;
    try
    {
      connection = elv.util.server.ServerStub.getDataBaseConnection();
      statement = connection.createStatement();
      java.lang.String inputFileName = "/home/db2inst/elv-3.0/data/morb_1999.txt";
      int lineCount = 2850396;
      java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(inputFileName), elv.util.Util.FILE_ENCODING));
      java.lang.String tableName = "morbidity_1999";
      java.lang.String insertString =
        "INSERT INTO " + tableName + " ( INSTITUTE, INSURANCE_CODE, BIRTH_YEAR, BIRTH_MONTH, BIRTH_DAY, SETTLEMENT, GENDER, AGE, OCCUPATION," +
        " ADDMISSION_TYPE, ADDMISSION_YEAR, ADDMISSION_MONTH, ADDMISSION_DAY, ADDMISSION_HOUR, ADDMISSION_MINUTE," +
        " DISMISSAL_TYPE, DISMISSAL_YEAR, DISMISSAL_MONTH, DISMISSAL_DAY, DISMISSAL_HOUR, DISMISSAL_MINUTE," +
        " DIAGNOSIS_TYPE_1, DIAGNOSIS_1, DIAGNOSIS_TYPE_2, DIAGNOSIS_2, DIAGNOSIS_TYPE_3, DIAGNOSIS_3, DIAGNOSIS_TYPE_4, DIAGNOSIS_4," +
        " DIAGNOSIS_TYPE_5, DIAGNOSIS_5, DIAGNOSIS_TYPE_6, DIAGNOSIS_6, DIAGNOSIS_TYPE_7, DIAGNOSIS_7, DIAGNOSIS_TYPE_8, DIAGNOSIS_8," +
        " DIAGNOSIS_TYPE_9, DIAGNOSIS_9, DIAGNOSIS_TYPE_10, DIAGNOSIS_10, DIAGNOSIS_TYPE_11, DIAGNOSIS_11, DIAGNOSIS_TYPE_12, DIAGNOSIS_12," +
        " DIAGNOSIS_TYPE_13, DIAGNOSIS_13, DIAGNOSIS_TYPE_14, DIAGNOSIS_14, DIAGNOSIS_TYPE_15, DIAGNOSIS_15, DIAGNOSIS_TYPE_16, DIAGNOSIS_16 )" +
        " VALUES (";
      java.lang.String line;
      int count = 0;
      while((line = fileReader.readLine()) != null)
      {
        count++;
        java.lang.String[] lineSplit = line.split(java.util.regex.Pattern.quote(";"));
        java.lang.String newLine = lineSplit[0] + ", '" + lineSplit[3] + "', " +
          lineSplit[4].substring(0, 4) + ", " + lineSplit[4].substring(4, 6) + ", " + lineSplit[4].substring(6, 8) + ", " +
          lineSplit[5] + ", " + lineSplit[6] + ", " +
          lineSplit[7].substring(0, lineSplit[7].indexOf(",")) + ", '" +
          lineSplit[8] + "', '" +
          lineSplit[10].substring(0, 1) + "', " +
          lineSplit[9].substring(0, 4) + ", " + lineSplit[9].substring(4, 6) + ", " + lineSplit[9].substring(6, 8) + ", " + lineSplit[9].substring(8, 10) + ", " + lineSplit[9].substring(10, 12) + ", '" +
          lineSplit[12] + "', " +
          lineSplit[11].substring(0, 4) + ", " + lineSplit[11].substring(4, 6) + ", " + lineSplit[11].substring(6, 8) + ", " + lineSplit[11].substring(8, 10) + ", " + lineSplit[11].substring(10, 12);
        for(int i = 13; i < lineSplit.length; i++)
        {
          newLine += ", '" + lineSplit[i] + "'";
        }
        java.lang.String[] newLineSplit = newLine.split(java.util.regex.Pattern.quote(","));
        for(int i = newLineSplit.length; i < 53; i++)
        {
          newLine += ", ''";
        }
        java.lang.String sqlString = insertString + newLine + " )";
        java.lang.System.out.println(sqlString);
        statement.executeUpdate(sqlString);
        connection.commit();
        java.lang.System.out.println(count + " / " + lineCount);
      }
      fileReader.close();
      statement.close();
      connection.close();
      connection = null;
    }
    catch(java.lang.Exception exc)
    {
      exc.printStackTrace();
    }
    finally
    {
      // Always make sure result sets and statements are closed, and the connection is returned to the pool.
      if (statement != null)
      {
        try
        {
          statement.close();
        } catch (java.sql.SQLException e)
        {
        }
        statement = null;
      }
      if (connection != null)
      {
        try
        {
          connection.close();
        } catch (java.sql.SQLException e)
        {
        }
        connection = null;
      }
    }
  }
  
}
