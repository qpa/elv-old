package org.oki.elv.client.access;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;

import org.oki.elv.common.face.Analysis;
import org.oki.elv.common.net.Request;

/**
 * Analysis access methods.
 * @author Elv
 */
public final class Analysises {
  /**
   * Creator of analysis.
   * @param analysis an analysis.
   * @throws Exception
   */
  public static void create(Analysis analysis) throws Exception {
    Request create = new Request(Request.TYPE.CREATE_FOLDER_AND_ENCODE_BEAN, analysis.getPath(), analysis, Analysis.PROPERTY_FILE_NAME, analysis.getPersistenceDelegate());
    Request createPropertyFoder = new Request(Request.TYPE.CREATE_FOLDER, analysis.getPropertyFolderPath());
    Request createExecutionFolder = new Request(Request.TYPE.CREATE_FOLDER, analysis.getExecutionFolderPath());
    String[] responses = Requests.post(create, createPropertyFoder, createExecutionFolder);
    String analysisPath = responses[0]; // The created path: numbered if necessary!
    analysis.setPath(analysisPath);
  }
  
  /**
   * Mover of the analysis to the target path.
   * @param analysis
   * @param targetPath 
   * @throws Exception
   */
  public static void move(Analysis analysis, String targetPath) throws Exception {
    Request move = new Request(Request.TYPE.MOVE_FOLDER, analysis.getPath(), targetPath);
    String[] responses = Requests.post(move);
    String analysisPath = responses[0]; // The moved path: numbered if necessary!
    analysis.setPath(analysisPath);
  }
  
  /**
   * Copier of the analysis to the target path.
   * @param analysis
   * @param targetPath
   * @throws Exception
   */
  public static void copy(Analysis analysis, String targetPath) throws Exception {
    Request copy = new Request(Request.TYPE.COPY_FOLDER, analysis.getPath(), targetPath);
    String[] responses = Requests.post(copy);
    String analysisPath = responses[0]; // The copied path: numbered if necessary!
    analysis.setPath(analysisPath);
  }
  

  /**
   * Deleter of the analysis.
   * @param analysis
   * @throws Exception
   */
  public static void delete(Analysis analysis) throws Exception {
    Request delete = new Request(Request.TYPE.DELETE_FOLDER, analysis.getPath());
    Requests.post(delete);
  }
  
  /**
   * Storer of the analysis.
   * @param analysis
   * @throws Exception
   */
  public static void store(Analysis analysis) throws Exception {
    HttpURLConnection connection = Requests.openPUTConnection(analysis.getPath() + "/" + Analysis.PROPERTY_FILE_NAME, true);
    XMLEncoder xmlEncoder = new XMLEncoder(new BufferedOutputStream(connection.getOutputStream()));
    xmlEncoder.setPersistenceDelegate(Analysis.class, analysis.getPersistenceDelegate());
    try {
      xmlEncoder.writeObject(analysis);
    }
    finally {
      xmlEncoder.close();
    }
    connection.getOutputStream().close();
    Requests.closeConnection(connection);
  }
  
  /**
   * Cleaner of the execution folder.
   * @param analysis 
   * @throws Exception 
   */
  public static void clean(Analysis analysis) throws Exception {
    Request delete = new Request(Request.TYPE.DELETE_FOLDER, analysis.getExecutionFolderPath());
    Request create = new Request(Request.TYPE.CREATE_FOLDER, analysis.getExecutionFolderPath());
    Requests.post(delete, create);
  }
}
