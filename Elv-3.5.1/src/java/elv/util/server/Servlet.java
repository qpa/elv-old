/*
 * Servlet.java
 */
package elv.util.server;

import java.sql.SQLException;

/**
 * Class for server implementation.
 * @author Qpa
 */
public class Servlet extends javax.servlet.http.HttpServlet
{
  // Constants.
  /** The "csv" separator character. */
  public final static char SEPARATOR_CHAR = ';';
  /** The "file" key in the query part of the HTML request. */
  public final static java.lang.String FILE = "file";
  /** The "type" key in the query part of the HTML request. */
  public final static java.lang.String TYPE = "type";
  /** TEXT type request. */
  public final static java.lang.String TEXT = "text";
  /** HTML type request. */
  public final static java.lang.String HTML = "html";
  /** Binary type request. */
  public final static java.lang.String BIN = "bin";
  /** The entry in the archive file. */
  public final static java.lang.String ENTRY = "entry";
  /** The "EQUALS" key for merging the parameter (key=value) sections of the query part of the HTML request. */
  public final static java.lang.String EQUALS = "=";
  /** The "AND" key for merging the sections of the query part of the HTML request. */
  public final static java.lang.String AND = "&";
  /** The "QUERY" key determining the query part of the HTML request. */
  public final static java.lang.String QUERY = "?";
  /** The character set for URL encoding and decoding. */
  public final static java.lang.String URL_ENCODING = "UTF-8";
  /** Maximum lines for HTML request. */
  private final int MAX_HTML_LINES = 500;
  
  // Variables.
  /** The executor of the scheduled tasks. */
  private static elv.util.Executor executor;
  /** The database connection. */
  private static java.sql.Connection dbConnection;
  
  /**
   * Method for getting the executor.
   * @return the system executor.
   */
  public static elv.util.Executor getExecutor()
  {
    return executor;
  }
  
  /**
   * Method for getting an SQL connection.
   * @return the SQL connection to the database.
   */
  public static java.sql.Connection getDataBaseConnection()
  {
      return dbConnection;
  }
  
  /**
   * Initializes the servlet.
   * @param config the servlet configuration.
   * @throws javax.servlet.ServletException if there is any initialization error.
   */
  public void init(javax.servlet.ServletConfig config) throws javax.servlet.ServletException
  {
    super.init(config);
    try
    {
      // Create the executor.
      executor = new elv.util.Executor();
      // Create the database connection. 
      javax.naming.Context initContext = new javax.naming.InitialContext();
      javax.naming.Context envContext  = (javax.naming.Context)initContext.lookup("java:/comp/env");
      javax.sql.DataSource dataSource = (javax.sql.DataSource)envContext.lookup("jdbc/db");
      dbConnection = dataSource.getConnection();
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.logServerError(exc);
      return;
    }
  }
  
  /**
   * Destroys the servlet.
   */
  public void destroy()
  {
    try
    {
      if(dbConnection != null)
      {
        dbConnection.close();
      }
    }
    catch(java.sql.SQLException exc)
    {
      elv.util.Error.logServerError(exc);
    }
    catch(java.lang.Exception exc)
    {
      elv.util.Error.logServerError(exc);
    }
  }
  
  /**
   * Method for the HTTP <CODE>GET</CODE> handling.
   * @param request the servlet request.
   * @param response the servlet response.
   * @throws javax.servlet.ServletException if there is any servlet error.
   * @throws java.io.IOException if there is any input/output error.
   */
  protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException
  {
    if(request.getQueryString() != null) // GET file.
    {
      java.lang.String[] parameters = request.getQueryString().split(java.util.regex.Pattern.quote(AND));
      java.lang.String file = "";
      java.lang.String type = BIN;
      java.lang.String entry = "";
      for(java.lang.String iteratorParameter : parameters)
      {
        java.lang.String[] keyValues = iteratorParameter.split(java.util.regex.Pattern.quote(EQUALS));
        if(keyValues[0].equals(FILE))
        {
          file = java.net.URLDecoder.decode(keyValues[1], URL_ENCODING);
        }
        else if(keyValues[0].equals(TYPE))
        {
          type = keyValues[1];
        }
        else if(keyValues[0].equals(ENTRY))
        {
          entry = java.net.URLDecoder.decode(keyValues[1], URL_ENCODING);
        }
      }
      if(type.equals(HTML))
      {
        response.setContentType("text/html");
        java.io.InputStream inputStream = null;
        java.util.zip.ZipFile archiveZipFile = null;
        if(entry.equals(""))
        {
          inputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(file));
        }
        else
        {
          archiveZipFile = new java.util.zip.ZipFile(file);
          for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
          {
            java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
            if(!iteratorEntry.isDirectory())
            {
              if(iteratorEntry.getName().equals(entry))
              {
                inputStream = new java.io.BufferedInputStream(archiveZipFile.getInputStream(iteratorEntry));
                break;
              }
            }
          }
        }
        java.io.OutputStream outputStream = response.getOutputStream();
        outputStream.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 3.2//EN\">".getBytes());
        outputStream.write("<html lang=\"hu\"> ".getBytes());
        outputStream.write("<head>".getBytes());
        outputStream.write(("<meta http-equiv=\"content-type\" content=\"text/html; charset=" + elv.util.Util.FILE_ENCODING + "\">").getBytes());
        outputStream.write("<style type=\"text/css\">".getBytes());
        outputStream.write("th {background: #E0E0E0}".getBytes());
        outputStream.write("td {background: #FFFFFF}".getBytes());
        outputStream.write("</style>".getBytes());
        outputStream.write("</head>".getBytes());
        outputStream.write("<body><table bgcolor=#000000 border=0 cellpadding=2 cellspacing=1> <tr>".getBytes());
        int lineCount = 0;
        java.util.Vector<java.lang.Byte> bytes =  new java.util.Vector<java.lang.Byte>();
        int aByte;
        while((aByte = inputStream.read()) != -1)
        {
          if(aByte == SEPARATOR_CHAR || aByte == '\n')
          {
            byte[] byteArray = new byte[bytes.size()];
            for(int i = 0; i < bytes.size(); i++)
            {
              byteArray[i] = bytes.get(i);
            }
            bytes =  new java.util.Vector<java.lang.Byte>();
            
            if(lineCount == 0) // Header line.
            {
              outputStream.write("<th>".getBytes());
              outputStream.write(byteArray);
              outputStream.write("</th>".getBytes());
            }
            else
            {
              outputStream.write("<td>".getBytes());
              outputStream.write(byteArray);
              outputStream.write("</td>".getBytes());
            }
            if(aByte == '\n')
            {
              outputStream.write("</tr> <tr>".getBytes());
              lineCount++;
              if(lineCount == MAX_HTML_LINES) // Too big html file.
              {
                break;
              }
            }
          }
          else
          {
            bytes.add((byte)aByte);
          }
        }
        outputStream.write("</tr> </table>".getBytes());
        if(lineCount == MAX_HTML_LINES)
        {
          outputStream.write("<b color=\"#800000\">File too big! Lines omited in this view!</b>".getBytes());
        }
        outputStream.write("</body> </html>".getBytes());
        inputStream.close();
        outputStream.close();
        if(archiveZipFile != null)
        {
          archiveZipFile.close();
        }
      }
      else
      {
        java.io.InputStream inputStream = null;
        java.util.zip.ZipFile archiveZipFile = null;
        if(entry.equals(""))
        {
          java.net.URLConnection connection = new java.io.File(file).toURI().toURL().openConnection();
          response.setContentType(connection.getContentType());
          inputStream = new java.io.BufferedInputStream(connection.getInputStream());
        }
        else
        {
          archiveZipFile = new java.util.zip.ZipFile(file);
          for(java.util.Enumeration entries = archiveZipFile.entries(); entries.hasMoreElements(); )
          {
            java.util.zip.ZipEntry iteratorEntry = (java.util.zip.ZipEntry)entries.nextElement();
            if(!iteratorEntry.isDirectory())
            {
              if(iteratorEntry.getName().equals(entry))
              {
                inputStream = new java.io.BufferedInputStream(archiveZipFile.getInputStream(iteratorEntry));
                break;
              }
            }
          }
        }
        java.io.OutputStream outputStream = response.getOutputStream();
        int count;
        byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
        while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
        {
          outputStream.write(buffer, 0,  count);
        }
        inputStream.close();
        outputStream.close();
        if(archiveZipFile != null)
        {
          archiveZipFile.close();
        }
      }
    }
    else
    {
      response.setContentType("text/html");
      java.io.PrintWriter printWriter = response.getWriter();
      printWriter.println("<html>");
      printWriter.println("  <head>");
      printWriter.println("    <title>");
      printWriter.println("      Elv-Servlet");
      printWriter.println("    </title>");
      printWriter.println("  </head>");
      printWriter.println("  <body>");
      printWriter.println("    <p>");
      printWriter.println("      <font face=\"Arial, Helvetica, sans-serif\" color=\"#666666\" size=\"+4\">");
      printWriter.println("        <b>Elv</b><i><font color=\"#990033\">-Servlet</font></i>");
      printWriter.println("      <font>");
      printWriter.println("    <p>");
      printWriter.println("  </body>");
      printWriter.println("</html>");
      printWriter.close();
    }
  }
  
  /**
   * Method for the HTTP <CODE>POST</CODE> handling.
   * @param request the servlet request.
   * @param response the servlet response.
   * @throws javax.servlet.ServletException if there is any servlet error.
   * @throws java.io.IOException if there is any input/output error.
   */
  protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException
  {
    try
    {
      java.io.ObjectInputStream inputStream = new java.io.ObjectInputStream(request.getInputStream());
      elv.util.client.ClientAction clientAction = (elv.util.client.ClientAction)inputStream.readObject();
      inputStream.close();
      
      java.lang.Object result = null;
      java.lang.String newName = null;
      switch(clientAction.getActionType())
      {
        case elv.util.client.ClientAction.LOAD_ALL_DESCENDANTS:
          result = ServerStub.loadAllDescendants();
          break;
        case elv.util.client.ClientAction.LOAD_CHILDREN:
          result = ServerStub.loadChildren((java.util.Vector<elv.util.Parentable>)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.CREATE_USER:
          ServerStub.create((elv.util.User)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.MOVE_USER:
          elv.util.User user = (elv.util.User)((java.util.Vector)clientAction.getTransport()).get(0);
          elv.util.User newUser = (elv.util.User)((java.util.Vector)clientAction.getTransport()).get(1);
          ServerStub.move(user, newUser);
          break;
        case elv.util.client.ClientAction.DELETE_USER:
          ServerStub.delete((elv.util.User)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.COPY_USER:
          user = (elv.util.User)((java.util.Vector)clientAction.getTransport()).get(0);
          newUser = (elv.util.User)((java.util.Vector)clientAction.getTransport()).get(1);
          ServerStub.copy(user, newUser);
          break;
        case elv.util.client.ClientAction.CREATE_CONTAINER:
          ServerStub.create((elv.task.Container)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.MOVE_CONTAINER:
          elv.task.Container container = (elv.task.Container)((java.util.Vector)clientAction.getTransport()).get(0);
          elv.task.Container newContainer = (elv.task.Container)((java.util.Vector)clientAction.getTransport()).get(1);
          ServerStub.move(container, newContainer);
          break;
        case elv.util.client.ClientAction.DELETE_CONTAINER:
          ServerStub.delete((elv.task.Container)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.COPY_CONTAINER:
          container = (elv.task.Container)((java.util.Vector)clientAction.getTransport()).get(0);
          newContainer = (elv.task.Container)((java.util.Vector)clientAction.getTransport()).get(1);
          ServerStub.copy(container, newContainer);
          break;
        case elv.util.client.ClientAction.CREATE_TASK:
          ServerStub.create((elv.task.Task)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.MOVE_TASK:
          elv.task.Task task = (elv.task.Task)((java.util.Vector)clientAction.getTransport()).get(0);
          elv.task.Task newTask = (elv.task.Task)((java.util.Vector)clientAction.getTransport()).get(1);
          ServerStub.move(task, newTask);
          break;
        case elv.util.client.ClientAction.DELETE_TASK:
          ServerStub.delete((elv.task.Task)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.COPY_TASK:
          task = (elv.task.Task)((java.util.Vector)clientAction.getTransport()).get(0);
          newTask = (elv.task.Task)((java.util.Vector)clientAction.getTransport()).get(1);
          ServerStub.copy(task, newTask);
          break;
        case elv.util.client.ClientAction.LOAD_PROPERTIES:
          java.lang.String containerPath = (java.lang.String)((java.util.Vector)clientAction.getTransport()).get(0);
          java.lang.String name = (java.lang.String)((java.util.Vector)clientAction.getTransport()).get(1);
          result = ServerStub.loadProperties(containerPath, name);
          break;
        case elv.util.client.ClientAction.STORE_PROPERTIES:
          java.lang.String file = (java.lang.String)((java.util.Vector)clientAction.getTransport()).get(0);
          java.util.Vector<elv.util.Property> properties = (java.util.Vector<elv.util.Property>)((java.util.Vector)clientAction.getTransport()).get(1);
          ServerStub.storeProperties(file, properties);
          break;
        case elv.util.client.ClientAction.LOAD_PARAMETERS:
          containerPath = (java.lang.String)((java.util.Vector)clientAction.getTransport()).get(0);
          name = (java.lang.String)((java.util.Vector)clientAction.getTransport()).get(1);
          elv.util.parameters.Parameter parameter = (elv.util.parameters.Parameter)((java.util.Vector)clientAction.getTransport()).get(2);
          result = ServerStub.loadParameters(containerPath, name, parameter);
          break;
        case elv.util.client.ClientAction.STORE_PARAMETERS:
          file = (java.lang.String)((java.util.Vector)clientAction.getTransport()).get(0);
          java.util.Vector<elv.util.parameters.Parameter> parameters = (java.util.Vector<elv.util.parameters.Parameter>)((java.util.Vector)clientAction.getTransport()).get(1);
          ServerStub.storeParameters(file, parameters);
          break;
        case elv.util.client.ClientAction.GET_EXECUTED_TASKS:
          result = ServerStub.getExecutedTasks();
          break;
        case elv.util.client.ClientAction.GET_EXECUTION_PROGRESSES:
          result = ServerStub.getExecutionProgresses((elv.task.Task)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.GET_EXECUTION_ERROR:
          result = ServerStub.getExecutionError((elv.task.Task)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.STOP_EXECUTION:
          ServerStub.stop((elv.task.Task)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.CLEAN_EXECUTION:
          ServerStub.clean((elv.task.Task)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.REWIND_EXECUTION:
          ServerStub.rewind((elv.task.Task)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.GET_DATE:
          result = ServerStub.getDate();
          break;
        case elv.util.client.ClientAction.GET_ENVIRONMENT:
          result = ServerStub.getEnvironment();
          break;
        case elv.util.client.ClientAction.GET_ENVIRONMENT_VARIABLES:
          result = ServerStub.getEnvironmentVariables();
          break;
        case elv.util.client.ClientAction.DELETE_FILE:
          ServerStub.delete((java.lang.String)clientAction.getTransport());
          break;
        case elv.util.client.ClientAction.GET_TABLES:
          result = ServerStub.getTables();
          break;
        case elv.util.client.ClientAction.GET_POSSIBLE_YEAR_INTERVALS:
          result = ServerStub.getPossibleYearIntervals();
          break;
      }
      
      java.io.ObjectOutputStream outputStream = new java.io.ObjectOutputStream(response.getOutputStream());
      outputStream.writeObject(result);
      outputStream.close();
    }
    catch(java.lang.Exception exc)
    {
      java.io.ObjectOutputStream outputStream = new java.io.ObjectOutputStream(response.getOutputStream());
      outputStream.writeObject(exc);
      outputStream.close();
    }
  }
  
  /**
   * Method for the HTTP <CODE>PUT</CODE> handling.
   * @param request the servlet request.
   * @param response the servlet response.
   * @throws javax.servlet.ServletException if there is any servlet error.
   * @throws java.io.IOException if there is any input/output error.
   */
  protected void doPut(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException
  {
    try
    {
      if(request.getQueryString() != null) // GET file.
      {
        java.lang.String[] parameters = request.getQueryString().split(java.util.regex.Pattern.quote(AND));
        java.lang.String file = "";
        java.lang.String type = BIN;
        for(java.lang.String iteratorParameter : parameters)
        {
          java.lang.String[] keyValues = iteratorParameter.split(java.util.regex.Pattern.quote(EQUALS));
          if(keyValues[0].equals(FILE))
          {
            file = java.net.URLDecoder.decode(keyValues[1], URL_ENCODING);
          }
          else if(keyValues[0].equals(TYPE))
          {
            type = keyValues[1];
          }
        }
        if(type.equals(TEXT))
        {
          java.lang.String line;
          java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(request.getInputStream(), elv.util.Util.FILE_ENCODING));
          java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(file), elv.util.Util.FILE_ENCODING));
          while((line = fileReader.readLine()) != null)
          {
            fileWriter.println(line);
          }
          fileWriter.close();
          fileReader.close();
        }
        else if(type.equals(BIN))
        {
          java.io.InputStream inputStream = request.getInputStream();
          java.io.OutputStream outputStream = new java.io.FileOutputStream(file);
          int count;
          byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
          while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
          {
            outputStream.write(buffer, 0,  count);
          }
          outputStream.close();
          inputStream.close();
        }
        // Write nothing to the response.
        java.io.ObjectOutputStream outputStream = new java.io.ObjectOutputStream(response.getOutputStream());
        outputStream.writeObject(null);
        outputStream.close();
      }
    }
    catch(java.lang.Exception exc)
    {
      java.io.ObjectOutputStream outputStream = new java.io.ObjectOutputStream(response.getOutputStream());
      outputStream.writeObject(exc);
      outputStream.close();
    }
  }
  
}
