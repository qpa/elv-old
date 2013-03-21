/*
 * ClientStub.java
 */
package elv.util.client;

import elv.task.Task;

/**
 * Class for imlementing client actions.
 * @author Qpa
 */
public class ClientStub
{
  
  /**
   * Method for loading all descendants of the system root.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized static elv.util.Root loadAllDescendants() throws java.lang.Exception
  {
    elv.util.Root root = null;
    
    java.lang.Object object = post(new ClientAction(ClientAction.LOAD_ALL_DESCENDANTS, null));
    if(object instanceof elv.util.Root)
    {
      root = (elv.util.Root)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return root;
  }
  
  /**
   * Method for loading the children of parentables.
   * @param parentables the vector of parentable objects which can have children.
   * @return a vector of children vector.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized static java.util.Vector<java.util.Vector> loadChildren(java.util.Vector<elv.util.Parentable> parentables) throws java.lang.Exception
  {
    java.util.Vector<java.util.Vector> vectorOfChildren = null;
    
    java.lang.Object result = post(new ClientAction(ClientAction.LOAD_CHILDREN, parentables));
    if(result instanceof java.util.Vector)
    {
      vectorOfChildren = (java.util.Vector<java.util.Vector>)result;
    }
    if(result instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)result;
    }
    return vectorOfChildren;
  }
  
  /**
   * Method for creating a user.
   * @param user an <CODE>elv.util.User</CODE> object.
   */
  public synchronized static void create(elv.util.User user) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.CREATE_USER, user));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for move a user.
   * @param movableUser the movable user.
   * @param newUser the new user.
   * @throws java.lang.Exception if there is any problem with moving.
   */
  public synchronized static void move(elv.util.User movableUser, elv.util.User newUser) throws java.lang.Exception
  {
    java.util.Vector transport = new java.util.Vector();
    transport.add(movableUser);
    transport.add(newUser);
    java.lang.Object object = post(new ClientAction(ClientAction.MOVE_USER, transport));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for deleting a user.
   * @param user an <CODE>elv.util.User</CODE> object.
   */
  public synchronized static void delete(elv.util.User user) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.DELETE_USER, user));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for copying an existing user's folder structure to a new user.
   * @param user the existing <CODE>elv.util.User</CODE> object.
   * @param newUser the new <CODE>elv.util.User</CODE> object.
   */
  public synchronized static void copy(elv.util.User user, elv.util.User newUser) throws java.lang.Exception
  {
    java.util.Vector transport = new java.util.Vector();
    transport.add(user);
    transport.add(newUser);
    java.lang.Object object = post(new ClientAction(ClientAction.COPY_USER, transport));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for creating a container.
   * @param container an <CODE>elv.task.Container</CODE> object.
   */
  public synchronized static void create(elv.task.Container container) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.CREATE_CONTAINER, container));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for moving a container.
   * @param movableContainer the movable container.
   * @param newContainer the new container.
   */
  public synchronized static void move(elv.task.Container movableContainer, elv.task.Container newContainer) throws java.lang.Exception
  {
    java.util.Vector transport = new java.util.Vector();
    transport.add(movableContainer);
    transport.add(newContainer);
    java.lang.Object object = post(new ClientAction(ClientAction.MOVE_CONTAINER, transport));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for deleting the container.
   * @param container an <CODE>elv.task.Container</CODE> object.
   */
  public synchronized static void delete(elv.task.Container container) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.DELETE_CONTAINER, container));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for copying an existing container's folder structure to a new container.
   * @param container the existing <CODE>elv.task.Container</CODE> object.
   * @param newUser the new <CODE>elv.task.Container</CODE> object.
   */
  public synchronized static void copy(elv.task.Container container, elv.task.Container newContainer) throws java.lang.Exception
  {
    java.util.Vector transport = new java.util.Vector();
    transport.add(container);
    transport.add(newContainer);
    java.lang.Object object = post(new ClientAction(ClientAction.COPY_CONTAINER, transport));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for creating the task folder structure.
   * @param task An <CODE>elv.task.Task</CODE> object.
   */
  public synchronized static void create(elv.task.Task task) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.CREATE_TASK, task));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for moving a task.
   * @param movableTask the movable task.
   * @param newTask the new task.
   * @throws java.lang.Exception if there is any problem with moving.
   */
  public synchronized static void move(elv.task.Task movableTask, elv.task.Task newTask) throws java.lang.Exception
  {
    java.util.Vector transport = new java.util.Vector();
    transport.add(movableTask);
    transport.add(newTask);
    java.lang.Object object = post(new ClientAction(ClientAction.MOVE_TASK, transport));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for deleting the task folder structure.
   * @param task An <CODE>elv.task.Task</CODE> object.
   */
  public synchronized static void delete(elv.task.Task task) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.DELETE_TASK, task));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for copying the an existing task's folder structure to a new task.
   * @param task The existing <CODE>elv.task.Task</CODE> object.
   * @param newTask The new <CODE>elv.task.Task</CODE> object.
   */
  public synchronized static void copy(elv.task.Task task, elv.task.Task newTask) throws java.lang.Exception
  {
    java.util.Vector transport = new java.util.Vector();
    transport.add(task);
    transport.add(newTask);
    java.lang.Object object = post(new ClientAction(ClientAction.COPY_TASK, transport));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for load properties.
   * @param containerPath the full path name of the property container (which could be a directory or an archive file).
   * @param name the property file name (the name of the file in case of a directory, or the zip entry name in case if an archive).
   * @return a vector of properties.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized static java.util.Vector<elv.util.Property> loadProperties(java.lang.String containerPath, java.lang.String name) throws java.lang.Exception
  {
    java.util.Vector<elv.util.Property> properties = null;
    java.util.Vector transport = new java.util.Vector();
    transport.add(containerPath);
    transport.add(name);
    java.lang.Object object = post(new ClientAction(ClientAction.LOAD_PROPERTIES, transport));
    if(object instanceof java.util.Vector)
    {
      properties = (java.util.Vector<elv.util.Property>)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    
    return properties;
  }
  
  /**
   * Method for store properties.
   * @param file the property file.
   * @param a vector of properties.
   * @throws java.lang.Exception if there is any problem with storing.
   */
  public synchronized static void storeProperties(java.lang.String file, java.util.Vector<elv.util.Property> properties) throws java.lang.Exception
  {
    java.util.Vector transport = new java.util.Vector();
    transport.add(file);
    transport.add(properties);
    java.lang.Object object = post(new ClientAction(ClientAction.STORE_PROPERTIES, transport));
    
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for load parameters.
   * @param containerPath the full path name of the parameter container (which could be a directory or an archive file).
   * @param name the parameter file name (the name of the file in case of a directory, or the zip entry name in case if an archive).
   * @param parameterType the type of parameter, which has to be loaded.
   * @return a vector of parameters.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized static <P extends elv.util.parameters.Parameter> java.util.Vector<P> loadParameters(java.lang.String containerPath, java.lang.String name, P parameterType) throws java.lang.Exception
  {
    java.util.Vector<P> parameters = null;
    java.util.Vector transport = new java.util.Vector();
    transport.add(containerPath);
    transport.add(name);
    transport.add(parameterType);
    java.lang.Object object = post(new ClientAction(ClientAction.LOAD_PARAMETERS, transport));
    if(object instanceof java.util.Vector)
    {
      parameters = (java.util.Vector<P>)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return parameters;
  }
  
  /**
   * Method for store parameters.
   * @param file the parameter file.
   * @param parameters a vector of parameters.
   * @throws java.lang.Exception if there is any problem with storing.
   */
  public synchronized static void storeParameters(java.lang.String file, java.util.Vector<? extends elv.util.parameters.Parameter> parameters) throws java.lang.Exception
  {
    java.util.Vector transport = new java.util.Vector();
    transport.add(file);
    transport.add(parameters);
    java.lang.Object object = post(new ClientAction(ClientAction.STORE_PARAMETERS, transport));
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for getting the executed tasks.
   * @return a stack of tasks.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Stack<elv.task.Task> getExecutedTasks() throws java.lang.Exception
  {
    java.util.Stack<elv.task.Task> message = null;
    java.lang.Object object = post(new ClientAction(ClientAction.GET_EXECUTED_TASKS, null));
    if(object instanceof java.util.Stack)
    {
      message = (java.util.Stack<elv.task.Task>)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return message;
  }
  
  /**
   * Method for getting the execution progresses of a task.
   * @param task the owner task.
   * @return a vector with the current executable and the stack of progresses.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Vector getExecutionProgresses(elv.task.Task task) throws java.lang.Exception
  {
    java.util.Vector executableAndProgresses = null;
    java.lang.Object object = post(new ClientAction(ClientAction.GET_EXECUTION_PROGRESSES, task));
    if(object instanceof java.util.Vector)
    {
      executableAndProgresses = (java.util.Vector)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return executableAndProgresses;
  }
  
  /**
   * Method for getting the execution error of a task.
   * @param task the owner task.
   * @return an error string.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.lang.String getExecutionError(elv.task.Task task) throws java.lang.Exception
  {
    java.lang.String message = null;
    java.lang.Object object = post(new ClientAction(ClientAction.GET_EXECUTION_ERROR, task));
    if(object instanceof java.lang.String)
    {
      message = (java.lang.String)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return message;
  }
  
  /**
   * Method for stopping the execution of a task.
   * @param task An <CODE>elv.task.Task</CODE> object.
   * @throws java.lang.Exception if there is any problem with stopping.
   */
  public synchronized static void stop(elv.task.Task task) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.STOP_EXECUTION, task));
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for cleaning the execution of a task.
   * @param task the owner task.
   * @throws java.lang.Exception if there is any problem with cleaning.
   */
  public synchronized static void clean(elv.task.Task task) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.CLEAN_EXECUTION, task));
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for rewinding the execution of a task.
   * @param task the owner task.
   * @throws java.lang.Exception if there is any problem with rewinding.
   */
  public synchronized static void rewind(elv.task.Task task) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.REWIND_EXECUTION, task));
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for getting the server date and time.
   * @return the actual date and time on the server.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Date getDate() throws java.lang.Exception
  {
    java.util.Date date = null;
    java.lang.Object object = post(new ClientAction(ClientAction.GET_DATE, null));
    if(object instanceof java.util.Date)
    {
      date = (java.util.Date)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return date;
  }
  
  /**
   * Method for getting the server environment variables.
   * @return a vector of variables.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Vector<java.lang.String> getEnvironmentVariables() throws java.lang.Exception
  {
    java.util.Vector<java.lang.String> environmentVariables = null;
    java.lang.Object object = post(new ClientAction(ClientAction.GET_ENVIRONMENT_VARIABLES, null));
    if(object instanceof java.util.Vector)
    {
      environmentVariables = (java.util.Vector<java.lang.String>)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return environmentVariables;
  }
  
  /**
   * Method for getting the server environment.
   * @return the server-side environment of application.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Hashtable getEnvironment() throws java.lang.Exception
  {
    java.util.Hashtable environment = null;
    java.lang.Object object = post(new ClientAction(ClientAction.GET_ENVIRONMENT, null));
    if(object instanceof java.util.Hashtable)
    {
      environment = (java.util.Hashtable)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return environment;
  }
  
  /**
   * Method for deleting a file.
   * @param file the file name.
   * @throws java.lang.Exception if there is any problem with deleting.
   */
  public synchronized static void delete(java.lang.String fileName) throws java.lang.Exception
  {
    java.lang.Object object = post(new ClientAction(ClientAction.DELETE_FILE, fileName));
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
  }
  
  /**
   * Method for getting the tables.
   * @return a vector of tables.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Vector<elv.util.Table> getTables() throws java.lang.Exception
  {
    java.util.Vector<elv.util.Table> tables = null;
    java.lang.Object object = post(new ClientAction(ClientAction.GET_TABLES, null));
    if(object instanceof java.util.Vector)
    {
      tables = (java.util.Vector<elv.util.Table>)object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return tables;
  }
  
  /**
   * Method for getting the possible year intervals.
   * @return an array of year intervals.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static elv.util.parameters.YearInterval[] getPossibleYearIntervals() throws java.lang.Exception
  {
    elv.util.parameters.YearInterval[] possibleYearIntervals = null;
    java.lang.Object object = post(new ClientAction(ClientAction.GET_POSSIBLE_YEAR_INTERVALS, null));
    if(object instanceof elv.util.parameters.YearInterval[])
    {
      possibleYearIntervals = (elv.util.parameters.YearInterval[])object;
    }
    if(object instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)object;
    }
    return possibleYearIntervals;
  }
  
  /**
   * Method for connecting to the server.
   * @param URLName the name of url
   * @throws java.lang.Exception if there is any problem with connecting.
   */
  private static void connect() throws java.lang.Exception
  {
    boolean haveToConnect = true;
    int timeOutCount = 0;
    while(haveToConnect)
    {
      haveToConnect = false;
      timeOutCount++;
      
      try
      {
        java.net.URL servletURL = new java.net.URL(elv.util.Util.getServletURL());
        java.net.HttpURLConnection connection = (java.net.HttpURLConnection)servletURL.openConnection();
      }
      catch(java.lang.Exception exc)
      {
        // Give up after 10 trys.
        if(timeOutCount > 10)
        {
          haveToConnect = false;
          throw exc;
        }
        else
        {
          haveToConnect = true;
        }
      }
      
      // Wait 1 second before try to connect again.
      if(haveToConnect)
      {
        try
        {
          java.lang.Thread.sleep(1000);
        }
        catch(java.lang.Exception exc)
        {
        }
      }
    }
  }
  
  /**
   * Method for resolving a POST action on the server.
   * @param action a <CODE>ClientAction</CODE> object.
   * @throws java.lang.Exception if there is any problem with POSTing.
   */
  private synchronized static java.lang.Object post(ClientAction action) throws java.lang.Exception
  {
    java.net.URL servletURL = new java.net.URL(elv.util.Util.getServletURL());
    java.net.HttpURLConnection connection = (java.net.HttpURLConnection)servletURL.openConnection();
    connection.setRequestMethod("POST");
    connection.setDoInput(true);
    connection.setDoOutput(true);
    java.io.ObjectOutputStream output = new java.io.ObjectOutputStream(connection.getOutputStream());
    output.writeObject(action);
    output.close();
    
    java.io.ObjectInputStream input = new java.io.ObjectInputStream(connection.getInputStream());
    java.lang.Object result = input.readObject();
    input.close();
    return result;
  }
  
  /**
   * Method for resolving a PUT action to the server.
   * @param localFilePath the path name of transferable file.
   * @param remoteFilePath the path name of the creatable file.
   * @param isText if true, the type of the transfer is text, binary otherwise.
   * @throws java.lang.Exception if there is any problem with PUTting.
   */
  public synchronized static void put(java.lang.String localFilePath, java.lang.String remoteFilePath, boolean isText) throws java.lang.Exception
  {
    // Construct URL.
    java.lang.String file = elv.util.server.Servlet.FILE + elv.util.server.Servlet.EQUALS + java.net.URLEncoder.encode(remoteFilePath, elv.util.server.Servlet.URL_ENCODING);
    java.lang.String type = elv.util.server.Servlet.TYPE + elv.util.server.Servlet.EQUALS + (isText ? elv.util.server.Servlet.TEXT : elv.util.server.Servlet.BIN);
    java.lang.String query = file + elv.util.server.Servlet.AND + type;
    java.net.URL putURL = new java.net.URL(elv.util.Util.getServletURL() + elv.util.server.Servlet.QUERY + query);
    java.net.HttpURLConnection connection = (java.net.HttpURLConnection)putURL.openConnection();
    connection.setRequestMethod("PUT");
    connection.setDoInput(true);
    connection.setDoOutput(true);
    // Streaming.
    java.io.OutputStream outputStream = connection.getOutputStream();
    java.io.FileInputStream inputStream = new java.io.FileInputStream(localFilePath);
    int count;
    byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
    while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
    {
      outputStream.write(buffer, 0,  count);
    }
    inputStream.close();
    outputStream.close();
    
    java.io.ObjectInputStream input = new java.io.ObjectInputStream(connection.getInputStream());
    java.lang.Object result = input.readObject();
    input.close();
    if(result != null && result instanceof java.lang.Exception)
    {
      throw (java.lang.Exception)result;
    }
  }
  
}
