package org.oki.elv.server.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLConnection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oki.elv.common.io.Streams;
import org.oki.elv.common.net.FileQuery;
import org.oki.elv.common.net.Request;
import org.oki.elv.server.io.Files;
import org.oki.elv.server.io.Logs;

import de.schlichtherle.io.ArchiveException;
import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;
import de.schlichtherle.io.FileOutputStream;

/**
 * Network action responder.
 * @author Elv
 */
 public final class Responder extends HttpServlet implements Servlet {
  /** Serial ID.*/
  private static final long serialVersionUID = -5201672749774618776L;
  
  /** Constructor. */
	public Responder() {
		super();
	} 
	
  
  @Override
  public void init() throws ServletException {
    try {
      Dispatcher.getInstance().initiate();
    } catch (NamingException e) {
      Logs.exc(e);
      return;
    } catch (SQLException e) {
      Logs.exc(e);
      return;
    }
    super.init();
  }
  
	@Override
	public void destroy() {
    try {
      Dispatcher.getInstance().terminate();
      File.umount();
    } catch (SQLException e) {
      Logs.exc(e);
    } catch (ArchiveException e) {
      Logs.exc(e);
    }
		super.destroy();
	}   	
	
  @Override
	protected void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
    try {
      if(httpRequest.getQueryString() != null) {
        synchronized (Files.LOCK) {
          FileQuery fileQuery = FileQuery.fromString(httpRequest.getQueryString());
          String filePath = fileQuery.getFilePath();
          if(FileQuery.FileType.HTML.equals(fileQuery.getFileType())) {
            httpResponse.setContentType("text/html");
            Streams.streamCsvToHtml(new FileInputStream(filePath), httpResponse.getOutputStream(), Files.CSV_SEPARATOR, Files.FILE_ENCODING);
          }
          else {
            URLConnection connection = new File(filePath).toURI().toURL().openConnection();
            httpResponse.setContentType(connection.getContentType());
            Streams.streamBin(connection.getInputStream(), httpResponse.getOutputStream());
          }
          File.umount();
        }
      }
      else {
        httpResponse.setContentType("text/html");
        streamSignature(httpResponse.getWriter());
      }
    }
    catch(Exception exc) {
      ObjectOutputStream outputStream = new ObjectOutputStream(httpResponse.getOutputStream());
      outputStream.writeObject(exc);
      outputStream.close();
    }
	}  	
	
  @Override
	protected void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
    try {
      synchronized (Files.LOCK) {
        ObjectInputStream inputStream = new ObjectInputStream(httpRequest.getInputStream());
        Request[] requests = (Request[])inputStream.readObject();
        inputStream.close();
        
        Object response = Dispatcher.getInstance().dispatch(requests);
        
        ObjectOutputStream outputStream = new ObjectOutputStream(httpResponse.getOutputStream());
        outputStream.writeObject(response);
        outputStream.close();
      }
    }
    catch (Exception e) {
      ObjectOutputStream outputStream = new ObjectOutputStream(httpResponse.getOutputStream());
      outputStream.writeObject(e);
      outputStream.close();
    }
	}   	
	
  @Override
	protected void doPut(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ServletException, IOException {
    try {
      if(httpRequest.getQueryString() != null) {
        synchronized (Files.LOCK) {
          FileQuery fileQuery = FileQuery.fromString(httpRequest.getQueryString());
          if(FileQuery.FileType.TEXT.equals(fileQuery.getFileType())) {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(httpRequest.getInputStream(), Files.FILE_ENCODING));
            PrintWriter fileWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileQuery.getFilePath()), Files.FILE_ENCODING));
            Streams.streamTxt(fileReader, fileWriter);
          }
          else if(FileQuery.FileType.BIN.equals(fileQuery.getFileType())) {
            Streams.streamBin(httpRequest.getInputStream(), new FileOutputStream(fileQuery.getFilePath()));
          }
          File.umount();
        }
        // Streaming done: write nothing to the response.
        ObjectOutputStream outputStream = new ObjectOutputStream(httpResponse.getOutputStream());
        outputStream.writeObject(null);
        outputStream.close();
      }
    }
    catch(Exception exc) {
      ObjectOutputStream outputStream = new ObjectOutputStream(httpResponse.getOutputStream());
      outputStream.writeObject(exc);
      outputStream.close();
    }
	}

  /**
   * Streams out the signature of the Responder servlet.
   * @param printWriter the writeable stream.
   */
  private void streamSignature(PrintWriter printWriter) {
    printWriter.println("<html>");
    printWriter.println("  <head>");
    printWriter.println("    <title>");
    printWriter.println("      Responder");
    printWriter.println("    </title>");
    printWriter.println("  </head>");
    printWriter.println("  <body>");
    printWriter.println("    <p>");
    printWriter.println("      Responder");
    printWriter.println("    </p>");
    printWriter.println("  </body>");
    printWriter.println("</html>");
    printWriter.close();
  }
}