package org.oki.elv.server.engine;

import java.beans.PersistenceDelegate;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.oki.elv.common.face.Root;
import org.oki.elv.common.net.Request;
import org.oki.elv.server.io.Files;
import org.oki.elv.server.io.Logs;

/**
 * Dispacher of servlet commands.
 * @author Elv
 */
public final class Dispatcher {
  /** The root. */
  private Root root;
  /** The database. */
  private Database database;
  
  /** Singleton instance of this class. */
  private static Dispatcher instance = new Dispatcher();
  
  /**
   * Getter of the instance.
   * @return the singleton instance of this class.
   */
  public static Dispatcher getInstance() {
    return instance;
  }
  
  /** Private constructor. */
  private Dispatcher() {
  }
  
  /**
   * Initiator.
   * @throws NamingException 
   * @throws SQLException 
   */
  void initiate() throws NamingException, SQLException {
    // Create the Root. 
    Context initContext = new InitialContext();
    Context envContext  = (Context)initContext.lookup("java:/comp/env");
    String version = (String)envContext.lookup("application/version");
    int concurrentTaskCount = (Integer)envContext.lookup("application/concurrent-count");
    String systemPath = "";
    try {
      systemPath = (String)envContext.lookup("folder/work") + "/.elv/" + version;
    }
    catch (NamingException exc ) {
      systemPath = System.getProperty("user.home") + "/.elv/" + version;
    }
    root = new Root(systemPath, version, concurrentTaskCount);
    // Create the Database. 
    BasicDataSource dataSource = (BasicDataSource)envContext.lookup("jdbc/db");
    String databaseURL = dataSource.getUrl();
    Logs.out(" Connecting to database...");
    Connection databaseConnection = dataSource.getConnection();
    Logs.out(" Connected to database.");
    database = Database.getInstance(databaseURL, databaseConnection);
  }
  
  /**
   * Terminator.
   * @throws SQLException 
   */
  void terminate() throws SQLException {
    if(database.getConnection() != null) {
      Logs.out(" Disconnecting from database...");
      database.getConnection().close();
      Logs.out(" Disconnected from database.");
    }
  }
  
  /**
   * Getter of the root.
   * @return the root.
   */
  public Root getRoot() {
    return root;
  }

  /**
   * Getter of the database.
   * @return the database.
   */
  public Database getDatabase() {
    return database;
  }

  /**
   * Dispatcher of the given requests.
   * @param requests the dispatchable requests.
   * @return the responses for the requests.
   * @throws Exception 
   */
  public Object[] dispatch(Request... requests) throws Exception {
    Object[] responses = new Object[requests.length];
    for (int i = 0; i < requests.length; i++) {
      if(Request.TYPE.GET_EXISTING_YEAR_INTERVALS == requests[i].getType()) {
        responses[i] = database.getExistingYearIntervals();
      }
      else if(Request.TYPE.CREATE_FOLDER == requests[i].getType()) {
        String folderPath = (String)requests[i].getArguments()[0];
        responses[i] = Files.createFolder(folderPath);
      }
      else if(Request.TYPE.MOVE_FOLDER == requests[i].getType()) {
        String sourceFolderPath = (String)requests[i].getArguments()[0];
        String targetFolderPath = (String)requests[i].getArguments()[1];
        responses[i] = Files.moveFolder(sourceFolderPath, targetFolderPath);
      }
      else if(Request.TYPE.COPY_FOLDER == requests[i].getType()) {
        String sourceFolderPath = (String)requests[i].getArguments()[0];
        String targetFolderPath = (String)requests[i].getArguments()[1];
        responses[i] = Files.copyFolder(sourceFolderPath, targetFolderPath);
      }
      else if(Request.TYPE.DELETE_FOLDER == requests[i].getType()) {
        String folderPath = (String)requests[i].getArguments()[0];
        Files.deleteFolder(folderPath);
      }
      else if(Request.TYPE.CREATE_FOLDER_AND_ENCODE_BEAN == requests[i].getType()) {
        String folderPath = (String)requests[i].getArguments()[0];
        Object bean = requests[i].getArguments()[1];
        responses[i] = Files.createFolder(folderPath);
        String fileName = (String)requests[i].getArguments()[2];
        PersistenceDelegate pD = (PersistenceDelegate)requests[i].getArguments()[3];
        Files.encode(bean, folderPath + "/" + fileName, pD);
      }
//TODO: write dispatching code here.
    }
    return responses;
  }
}
