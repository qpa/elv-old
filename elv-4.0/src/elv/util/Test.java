/*
 * Test.java
 */

package elv.util;

/**
 * Class for inserting.
 * @author ELV
 */
public class Test
{
  
  /**
   * Constructor.
   */
  public Test()
  {
  }
  
  /**
   * @param args the command line arguments
   */
  public static void main(String[] args)
  {
    java.sql.Connection dbConnection = null;
    java.sql.Statement dbStatement = null;
    try
    {
      java.lang.Class.forName("org.h2.Driver");
      
      java.lang.System.out.println("Connecting: " + new java.util.Date());
      dbConnection = java.sql.DriverManager.getConnection("jdbc:h2:d:/data/db/elv;LOG=2", "elv", "elv");
//      dbConnection = java.sql.DriverManager.getConnection("jdbc:h2:/home/elv/data/db/elv;LOG=2;DATABASE_EVENT_LISTENER='com.acme.Listener'", "elv", "elv");
      dbStatement = dbConnection.createStatement();
      java.lang.System.out.println("Connected: " + new java.util.Date());
//      
//      java.lang.String dropIndexString =
//        "DROP INDEX morbidity";
//      java.lang.System.out.println("Dropping index: " + new java.util.Date());
//      dbStatement.executeUpdate(dropIndexString);
//      dbConnection.commit();
//      
      
//      dbStatement.execute("SET LOG 0");
//      
//      java.lang.String createIndexString =
//      "SET LOG 0; " +
//        "CREATE INDEX IF NOT EXISTS morbidity_1 ON morbidity" +
//        "(settlement, addmission_year, gender, age); " +
//        "SET LOG 2";
//      java.lang.System.out.println("Creating index: " + new java.util.Date() + "\n" + createIndexString);
//      dbStatement.execute(createIndexString);
//      dbConnection.commit();
//      
//      java.lang.String createIndexString =
//        "SET LOG 0; " +
//        "CREATE INDEX IF NOT EXISTS mortality_2 ON mortality" +
//        "(gender, age, death_year); " +
//        "SET LOG 2";
//      java.lang.System.out.println("Creating index: " + new java.util.Date() + "\n" + createIndexString);
//      dbStatement.execute(createIndexString);
//      dbConnection.commit();
//      
//      java.lang.String createIndexString =
//        "SET LOG 0; " +
//        "CREATE INDEX IF NOT EXISTS population_1 ON population" +
//        "(settlement, year, gender, age); ";
//      java.lang.System.out.println("Creating index: " + new java.util.Date() + "\n" + createIndexString);
//      dbStatement.execute(createIndexString);
//      dbConnection.commit();
//      
//      dbStatement.execute("SET LOG 2");
      
      java.lang.System.out.println("Selecting: " + new java.util.Date());
      java.lang.String selectString =
        "SELECT DISTINCT diagnosis_1" +
        "  FROM mortality"+
        " WHERE death_year = 2002 AND gender = 1 AND age BETWEEN 34 AND 98 AND effective_residence = 1421" +
        " AND SUBSTRING(diagnosis_1 FROM 1 FOR 3) BETWEEN 'C00' AND 'C99'";
//        "SELECT diagnosis_1" +
//        "  FROM morbidity"+
//        " WHERE addmission_year = 2001 AND gender = 1 AND age BETWEEN 34 AND 98 AND settlement = 1114" +
//        " ORDER BY diagnosis_1";
//        "SELECT DISTINCT population" +
//        "  FROM population"+
//        " WHERE year = 2001 AND gender = 1 AND age BETWEEN 34 AND 98 AND settlement = 1421" +
//        " ORDER BY population";
//        "SELECT MIN(addmission_year), MAX(addmission_year)" +
//        "  FROM morbidity";
      java.sql.ResultSet rs = dbStatement.executeQuery(selectString);
      java.lang.System.out.println("Selected: " + new java.util.Date());

      java.lang.System.out.println("Printing: " + new java.util.Date());
      int rowCount = 0;
      java.sql.ResultSetMetaData meta = rs.getMetaData();
      while(rs.next())
      {
        java.lang.String printString = (++rowCount) + " " + rs.getObject(1);
        for(int i = 1; i < meta.getColumnCount(); i++)
        {
          java.lang.Object columnObject = rs.getObject(i + 1);
          printString += ", " + (columnObject instanceof java.lang.String ? "'" + columnObject + "'" : columnObject);
        }
        java.lang.System.out.println(printString);
        java.lang.Thread.yield();
      }
      rs.close();
      
//      java.lang.System.out.println("Exporting: " + new java.util.Date());
//      java.lang.String exportString = "CALL CSVWRITE('/home/elv/data/db/mortality.csv', 'SELECT * FROM mortality')";
//      dbStatement.execute(exportString);
//      
//      
      java.lang.System.out.println("Printing: " + new java.util.Date());
      java.sql.DatabaseMetaData metaData = dbConnection.getMetaData();
      java.sql.ResultSet resultSet = metaData.getTables(null, null, "%", new java.lang.String[]{"TABLE"});
//      java.sql.ResultSet resultSet = metaData.getIndexInfo(null, null, "mortality", false, false);
      while(resultSet.next())
      {
        java.lang.String tableName =resultSet.getString("TABLE_NAME");
        java.lang.System.out.println(tableName);
        java.sql.ResultSet indexResultSet = metaData.getIndexInfo(null, null, tableName, false, true);
        while(indexResultSet.next())
        {
          java.lang.String indexName = indexResultSet.getString("INDEX_NAME");
          java.lang.System.out.println("-> " + indexName);
          java.lang.System.out.println("   -> " + indexResultSet.getString("COLUMN_NAME"));
        }
      }
      resultSet.close();
      
      java.lang.System.out.println("Printed: " + new java.util.Date());
      
      java.lang.System.out.println("Closing: " + new java.util.Date());
//      dbStatement.close();
      dbConnection.close();
      dbConnection = null;
      java.lang.System.out.println("Closed: " + new java.util.Date());
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
