package org.oki.elv.server.engine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.oki.elv.common.parameter.Interval;

/**
 * Database action dispatcher.
 * @author Elv
 */
public class Database {
  /** The database URL. */
  private String url;
  /** The database connection. */
  private Connection connection;
  
  /** Singleton instance of this class. */
  private static Database instance;
  
  /**
   * Getter of the instance.
   * @param url 
   * @param connection 
   * @return the singleton instance of this class.
   */
  static Database getInstance(String url, Connection connection){
    if(instance == null) {
      instance = new Database(url, connection);
    }
    return instance;
  }

  /**
   * Contructor.
   * @param url 
   * @param connection 
   */
  private Database(String url, Connection connection) {
    this.url = url;
    this.connection = connection;
  }
  
  /**
   * Getter of the URL.
   * @return the URL.
   */
  public String getURL(){
    return url;
  }

  /**
   * Getter of the database connection.
   * @return the connection
   */
  public Connection getConnection() {
    return connection;
  }
  
  /**
   * Getter of the existing year-intervals.
   * @return the interval list with values for MORBIDITY, MORTALITY and POPULATION.
   * @throws SQLException 
   */
  public List<Interval> getExistingYearIntervals() throws SQLException {
    List<Interval> existingYearIntervals = new ArrayList<Interval>();
    
    Statement statement = connection.createStatement();
    // Morbidity.
    ResultSet resultSet = statement.executeQuery("SELECT MIN(addmission_year), MAX(addmission_year) FROM morbidity");
    resultSet.next();
    int min = resultSet.getInt(1);
    int max= resultSet.getInt(2);
    existingYearIntervals.add(new Interval.Morbidity(min, max));
    // Mortality.
    resultSet = statement.executeQuery("SELECT MIN(death_year), MAX(death_year) FROM mortality");
    resultSet.next();
    min = resultSet.getInt(1);
    max= resultSet.getInt(2);
    existingYearIntervals.add(new Interval.Mortality(min, max));
    // Population.
    resultSet = statement.executeQuery("SELECT MIN(year), MAX(year) FROM population");
    resultSet.next();
    min = resultSet.getInt(1);
    max= resultSet.getInt(2);
    existingYearIntervals.add(new Interval.Population(min, max));
    
    resultSet.close();
    statement.close();
    return existingYearIntervals;
  }
}
