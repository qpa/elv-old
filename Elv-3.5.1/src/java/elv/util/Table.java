/**
 * Table.java
 */

package elv.util;

/**
 * Class for reprezenting the tables.
 * @author Qpa
 */
public class Table implements Visualizable, java.io.Serializable
{
  
  // Constants.
  /** The title of the class. */
  public final static java.lang.String TITLE = "Table";
  /** The tables name. */
  public final static java.lang.String TABLES_TITLE = "Tables";
  // Indices in the possibleYearIntervals array.
  /** Index in the <code>possibleYearIntervals</code> array. */
  public final static int MORBIDITY = 0;
  /** Index in the <code>possibleYearIntervals</code> array. */
  public final static int MORTALITY = 1;
  /** Index in the <code>possibleYearIntervals</code> array. */
  public final static int POPULATION = 2;
  
  
  // Variables.
  /** The name of the table. */
  private java.lang.String name;
  
  /**
   * Constructor.
   */
  public Table(java.lang.String name)
  {
    this.name = name;
  }
  
  /**
   * Method for getting the <CODE>name</CODE> variable.
   * @return the name of this table.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method for getting the icon.
   * @return the icon of visualization.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/table.gif"));
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   */
  public String toString()
  {
    return name;
  }

  /**
   * Method for getting the tables.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Vector<Table> getAllTables() throws java.lang.Exception
  {
    java.util.Vector<Table> tables = new java.util.Vector<Table>();
    java.sql.Connection connection = null;
    connection = elv.util.server.Servlet.getDataBaseConnection();
    java.sql.DatabaseMetaData metaData = connection.getMetaData();
    java.sql.ResultSet resultSet = metaData.getTables(null, null, "%", new java.lang.String[]{"TABLE"});
    while(resultSet.next())
    {
      java.lang.String tableName = resultSet.getString("TABLE_NAME");
      if(tableName.matches(".+_\\d{4}"))
      {
        tables.add(new Table(tableName));
      }
    }
    resultSet.close();
    return tables;
  }
  
  /**
   * Method for getting the possible year intervals.
   * @return an array of year intervals.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static elv.util.parameters.YearInterval[] getPossibleYearIntervals() throws java.lang.Exception
  {
    elv.util.parameters.YearInterval[] possibleYearIntervals = new elv.util.parameters.YearInterval[3];
    java.sql.Statement statement = elv.util.server.Servlet.getDataBaseConnection().createStatement();
    // Morbidity.
    java.sql.ResultSet resultSet = statement.executeQuery("SELECT MIN(addmission_year), MAX(addmission_year) FROM morbidity");
    resultSet.next();
    int min = resultSet.getInt(1);
    int max= resultSet.getInt(2);
    possibleYearIntervals[MORBIDITY] = new elv.util.parameters.YearInterval(min, max);
    // Mortality.
    resultSet = statement.executeQuery("SELECT MIN(death_year), MAX(death_year) FROM mortality");
    resultSet.next();
    min = resultSet.getInt(1);
    max= resultSet.getInt(2);
    possibleYearIntervals[MORTALITY] = new elv.util.parameters.YearInterval(min, max);
    // Population.
    resultSet = statement.executeQuery("SELECT MIN(year), MAX(year) FROM population");
    resultSet.next();
    min = resultSet.getInt(1);
    max= resultSet.getInt(2);
    possibleYearIntervals[POPULATION] = new elv.util.parameters.YearInterval(min, max);
    resultSet.close();
    statement.close();
    return possibleYearIntervals;
  }
  
}
