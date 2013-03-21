package org.oki.elv.client.access;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import org.oki.elv.common.io.Streams;
import org.oki.elv.common.net.FileQuery;
import org.oki.elv.common.net.Request;

/**
 * Network access methods.
 * @author Elv
 */
public final class Requests {
  /**
   * POST request.
   * @param <R> the type of the response.
   * @param requests an array of request objects.
   * @return the responses for the requests.
   * @throws Exception 
   */
  @SuppressWarnings("unchecked")
  public static <R> R[] post(Request... requests) throws Exception {
    URL servletURL = new URL(getServletURL());     
    HttpURLConnection connection = (HttpURLConnection)servletURL.openConnection();      
    connection.setRequestMethod("POST");
    connection.setDoInput(true);
    connection.setDoOutput(true);
    
    ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());      
    output.writeObject(requests);
    output.close();      

    ObjectInputStream input = new ObjectInputStream(connection.getInputStream());      
    Object response = input.readObject();      
    input.close();      
    if(response != null && response instanceof Exception) {
      throw (Exception)response;
    }
    return (R[])response;
  }
  
  /**
   * PUT request.
   * @param localFilePath the path name of transferable file.
   * @param remoteFilePath the path name of the creatable file.
   * @param isText if true, the type of the transfer is text, binary otherwise.
   * @throws Exception 
   */
  public static void put(String localFilePath, String remoteFilePath, boolean isText) throws Exception {
    FileQuery fileQuery = new FileQuery(isText ? FileQuery.FileType.TEXT : FileQuery.FileType.BIN, remoteFilePath);
    URL connectionURL = new URL(getServletURL() + fileQuery.toString());
    HttpURLConnection connection = (HttpURLConnection)connectionURL.openConnection();
    connection.setRequestMethod("PUT");
    connection.setDoInput(true);
    connection.setDoOutput(true);
    
    Streams.streamBin(new FileInputStream(localFilePath), connection.getOutputStream());
    
    ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
    Object result = input.readObject();
    input.close();
    connection.disconnect();
    if(result != null && result instanceof Exception) {
      throw (Exception)result;
    }
  }
  
  /**
   * Opener of a GET connection.
   * @param remoteFilePath the path name of the readable file.
   * @param isText if true, the type of the transfer is text, binary otherwise.
   * @return a connection for GET.
   * @throws UnavailableServiceException 
   * @throws IOException 
   */
  public static HttpURLConnection openGETConnection(String remoteFilePath, boolean isText) throws UnavailableServiceException, IOException {
    FileQuery fileQuery = new FileQuery(isText ? FileQuery.FileType.TEXT : FileQuery.FileType.BIN, remoteFilePath);
    URL connectionURL = new URL(getServletURL() + fileQuery.toString());
    HttpURLConnection connection = (HttpURLConnection)connectionURL.openConnection();
    connection.setRequestMethod("GET");
    connection.setDoInput(true);
    connection.setDoOutput(true);
    return connection;
  }

  /**
   * Opener of a PUT connection.
   * @param remoteFilePath the path name of the writeable file.
   * @param isText if true, the type of the transfer is text, binary otherwise.
   * @return a connection for PUT.
   * @throws UnavailableServiceException 
   * @throws IOException 
   */
  public static HttpURLConnection openPUTConnection(String remoteFilePath, boolean isText) throws UnavailableServiceException, IOException {
    FileQuery fileQuery = new FileQuery(isText ? FileQuery.FileType.TEXT : FileQuery.FileType.BIN, remoteFilePath);
    URL connectionURL = new URL(getServletURL() + fileQuery.toString());
    HttpURLConnection connection = (HttpURLConnection)connectionURL.openConnection();
    connection.setRequestMethod("PUT");
    connection.setDoInput(true);
    connection.setDoOutput(true);
    return connection;
  }

  /**
   * Closer of the connection.
   * @param connection
   * @throws Exception
   */
  public static void closeConnection(HttpURLConnection connection) throws Exception {
    ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
    Object result = input.readObject();
    input.close();
    if(result != null && result instanceof Exception) {
      throw (Exception)result;
    }
    connection.disconnect();
  }
  
  /**
   * Getter of the URL string of the application.
   * @return an URL string.
   * @throws UnavailableServiceException
   */
  public static String getBaseURL() throws UnavailableServiceException {
    BasicService basicService = (BasicService)ServiceManager.lookup("javax.jnlp.BasicService"); 
    return basicService.getCodeBase().toString();
  }
  
  /**
   * Getter the URL string of the servlet.
   * @return an URL string.
   * @throws UnavailableServiceException
   */
  public static String getServletURL() throws UnavailableServiceException {
    String servletMapping = "servlet"; // The url-pattern mapped Servlet.
    return getBaseURL() + servletMapping;
  }
}
