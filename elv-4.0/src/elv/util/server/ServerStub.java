/**
 * ServerStub.java
 */
package elv.util.server;

/**
 * Class for implementing server actions.
 * @author Elv
 */
public class ServerStub
{
  
  // Variables.
  /** The version of the application. */
  private java.lang.String version;
  /** The count of concurrently executed tasks. */
  private int concurrentCount;
  /** The work folder. */
  private java.lang.String workFolder;
  /** The external binaries folder. */
  private java.lang.String extBinFolder;
  /** The external libraries folder. */
  private java.lang.String extLibFolder;
  /** The database URL. */
  private java.lang.String dbURL;
  /** The executor of the scheduled tasks. */
  private elv.util.server.Executor executor;
  /** The root of the application. */
  private static elv.util.Root root;
  /** The database connection. */
  private static java.sql.Connection dbConnection;
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any initialization problem.
   */
  public ServerStub() throws java.lang.Exception
  {
    java.text.DateFormat dateFormat = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.MEDIUM);
    javax.naming.Context initContext = new javax.naming.InitialContext();
    javax.naming.Context envContext  = (javax.naming.Context)initContext.lookup("java:/comp/env");
    version = (java.lang.String)envContext.lookup("application/version");
    concurrentCount = (java.lang.Integer)envContext.lookup("application/concurrent-count");
    org.apache.tomcat.dbcp.dbcp.BasicDataSource dataSource = (org.apache.tomcat.dbcp.dbcp.BasicDataSource)envContext.lookup("jdbc/db");
    dbURL = dataSource.getUrl();
//    javax.sql.DataSource dataSource = (javax.sql.DataSource)envContext.lookup("jdbc/db");
    try
    {
      workFolder = (java.lang.String)envContext.lookup("folder/work") + "/.elv/" + version;
    }
    catch (java.lang.Exception exc)
    {
      workFolder = java.lang.System.getProperty("user.home") + "/.elv/" + version;
    }
    // Create the root.
    root = new elv.util.Root(version, concurrentCount, workFolder, dbURL);
    // Create the executor.
    executor = new elv.util.server.Executor(concurrentCount);
    // Create the database connection. 
    java.lang.System.out.println("ELV: " + dateFormat.format(new java.util.Date()) + " Connecting to database");
    dbConnection = dataSource.getConnection();
    java.lang.System.out.println("ELV: " + dateFormat.format(new java.util.Date()) + " Connected to database");
  }
  
  /**
   * Method for getting the executor.
   * @return the system executor.
   */
  protected elv.util.server.Executor getExecutor()
  {
    return executor;
  }
  
  /**
   * Method for getting the root of the application.
   * @return the root object of the application.
   */
  public static elv.util.Root getRoot()
  {
    return root;
  }
  
  /**
   * Method for getting the database connection.
   * @return the SQL connection to the database.
   */
  public static java.sql.Connection getDataBaseConnection()
  {
      return dbConnection;
  }
  
  /**
   * Method for loading all descendants of the system root.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized elv.util.Root loadAllDescendants() throws java.lang.Exception
  {
    root.loadChildren();
    java.util.Vector<elv.util.User> users = root.getChildren();
    for(elv.util.User iteratorUser : users)
    {
      iteratorUser.loadChildren();
      java.util.Vector<elv.task.Container> containers = iteratorUser.getChildren();
      for(elv.task.Container iteratorContainer : containers)
      {
          iteratorContainer.loadChildren();
          java.util.Vector<elv.task.Task> tasks = iteratorContainer.getChildren();
      }
    }
    return root;
  }
  
  /**
   * Method for loading the children of parentables.
   * @param parentables the vector of parentable objects which can have children.
   * @return a vector of children vector.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized java.util.Vector<java.util.Vector> loadChildren(java.util.Vector<elv.util.Parentable> parentables) throws java.lang.Exception
  {
    java.util.Vector<java.util.Vector> vectorOfChildren =  new java.util.Vector<java.util.Vector>();
    if(parentables == null)
    {
      java.util.Vector<elv.util.Parentable> childrenOfNULL = new java.util.Vector<elv.util.Parentable>();
      childrenOfNULL.add(root);
      vectorOfChildren.add(childrenOfNULL);
      root.loadChildren();
      vectorOfChildren.add(root.getChildren());
      
    }
    else
    {
      for(elv.util.Parentable iteratorParentable : parentables)
      {
        if(iteratorParentable != null && iteratorParentable.exists())
        {
          iteratorParentable.loadChildren();
          vectorOfChildren.add(iteratorParentable.getChildren());
        }
        else
        {
          vectorOfChildren.add(null);
        }
      }
    }
    return vectorOfChildren;
  }
  
  /**
   * Method for creating the user folder structure.
   * @param user An <CODE>elv.util.User</CODE> object.
   */
  public synchronized static void create(elv.util.User user) throws java.lang.Exception
  {
    user.create();
  }
  
  /**
   * Method for renaming the user folder structure.
   * @param movableUser the movable user.
   * @param newUser the new user.
   * @throws java.lang.Exception if there is any problem with moving.
   */
  public synchronized static void move(elv.util.User movableUser, elv.util.User newUser) throws java.lang.Exception
  {
    movableUser.move(newUser);
  }
  
  /**
   * Method for deleting the user folder structure.
   * @param user An <CODE>elv.util.User</CODE> object.
   */
  public synchronized static void delete(elv.util.User user) throws java.lang.Exception
  {
    user.delete();
  }
  
  /**
   * Method for copying the an existing user's folder structure to a new user.
   * @param user The existing <CODE>elv.util.User</CODE> object.
   * @param newUser The new <CODE>elv.util.User</CODE> object.
   */
  public synchronized static void copy(elv.util.User user, elv.util.User newUser) throws java.lang.Exception
  {
    user.copy(newUser);
  }
  
  /**
   * Method for creating a container.
   * @param container An <CODE>elv.task.Container</CODE> object.
   */
  public synchronized static void create(elv.task.Container container) throws java.lang.Exception
  {
    container.create();
  }
  
  /**
   * Method for moving a container.
   * @param movableContainer the movable container.
   * @param newContainer the new container.
   * @throws java.lang.Exception if there is any problem with moving.
   */
  public synchronized static void move(elv.task.Container movableContainer, elv.task.Container newContainer) throws java.lang.Exception
  {
    movableContainer.move(newContainer);
  }
  
  /**
   * Method for deleting the container.
   * @param container an <CODE>elv.task.Container</CODE> object.
   */
  public synchronized static void delete(elv.task.Container container) throws java.lang.Exception
  {
    container.delete();
  }
  
  /**
   * Method for copying an existing container's folder structure to a new container.
   * @param container the existing <CODE>elv.task.Container</CODE> object.
   * @param newContainer the new <CODE>elv.task.Container</CODE> object.
   */
  public synchronized static void copy(elv.task.Container container, elv.task.Container newContainer) throws java.lang.Exception
  {
    container.copy(newContainer);
  }
  
  /**
   * Method for creating the task folder structure.
   * @param task An <CODE>elv.task.Task</CODE> object.
   */
  public synchronized static void create(elv.task.Task task) throws java.lang.Exception
  {
    task.create();
  }
  
  /**
   * Method for moving a task.
   * @param movableTask the movable task.
   * @param newTask the new task.
   * @throws java.lang.Exception if there is any problem with moving.
   */
  public synchronized static void move(elv.task.Task movableTask, elv.task.Task newTask) throws java.lang.Exception
  {
    movableTask.move(newTask);
  }
  
  /**
   * Method for deleting the task folder structure.
   * @param task An <CODE>elv.task.Task</CODE> object.
   */
  public synchronized static void delete(elv.task.Task task) throws java.lang.Exception
  {
    task.delete();
  }
  
  /**
   * Method for copying the an existing task's folder structure to a new task.
   * @param task The existing <CODE>elv.task.Task</CODE> object.
   * @param newTask The new <CODE>elv.task.Task</CODE> object.
   */
  public synchronized static void copy(elv.task.Task task, elv.task.Task newTask) throws java.lang.Exception
  {
    task.copy(newTask);
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
    return elv.util.Property.load(containerPath, name);
  }
  
  /**
   * Method for store properties.
   * @param file the property file.
   * @param a vector of properties.
   * @throws java.lang.Exception if there is any problem with storing.
   */
  public synchronized static void storeProperties(java.lang.String file, java.util.Vector<elv.util.Property> properties) throws java.lang.Exception
  {
    elv.util.Property.store(file, properties);
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
    return elv.util.parameters.Parameter.load(containerPath, name, parameterType);
  }
  
  /**
   * Method for store parameters.
   * @param file the parameter file.
   * @param a vector of parameters.
   * @throws java.lang.Exception if there is any problem with storing.
   */
  public synchronized static void storeParameters(java.lang.String file, java.util.Vector<? extends elv.util.parameters.Parameter> parameters) throws java.lang.Exception
  {
    elv.util.parameters.Parameter.store(file, parameters);
  }
  
  /**
   * Method for getting the executed tasks.
   * @return a stack of tasks.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized java.util.Stack<elv.task.Task> getExecutedTasks() throws java.lang.Exception
  {
    return executor.getExecutedTasks();
  }
  
  /**
   * Method for getting the execution progreeses of a task.
   * @param task the owner task.
   * @return a vector with the current executable and the stack of progresses.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized java.util.Vector getExecutionProgresses(elv.task.Task task) throws java.lang.Exception
  {
    java.util.Vector executableAndProgresses = new java.util.Vector();
    elv.task.Execution execution = executor.get(task);
    if(execution != null)
    {
      executableAndProgresses.add(execution.getExecutable());
      executableAndProgresses.add(execution.getProgresses());
    }
    else
    {
      execution = new elv.task.Execution(task);
      execution.loadProgresses();
      executableAndProgresses.add(execution.getExecutable());
      executableAndProgresses.add(execution.getProgresses());
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
    return elv.util.Error.getExecutionError(task);
  }
  
  /**
   * Method for stopping the execution of a task.
   * @param task the owner task.
   * @throws java.lang.Exception if there is any problem with stopping.
   */
  public synchronized void stop(elv.task.Task task) throws java.lang.Exception
  {
    elv.task.Execution execution = executor.get(task);
    if(execution != null)
    {
      execution.stop();
    }
  }
  
  /**
   * Method for cleaning the execution of a task.
   * @param task the owner task.
   * @throws java.lang.Exception if there is any problem with cleaning.
   */
  public synchronized static void clean(elv.task.Task task) throws java.lang.Exception
  {
    elv.task.Execution.clean(task);
  }
  
  /**
   * Method for rewinding the execution of a task.
   * @param task the owner task.
   * @throws java.lang.Exception if there is any problem with rewinding.
   */
  public synchronized static void rewind(elv.task.Task task) throws java.lang.Exception
  {
    for(int i = task.getExecutables().size() - 1; i >= 0; i--)
    {
      elv.task.executables.Executable iteratorExecutable = task.getExecutables().get(i);
      if(iteratorExecutable.isDone(task))
      {
        iteratorExecutable.setDone(task, false);
        break;
      }
    }
  }
  
  /**
   * Method for deleting a file.
   * @param file the file name.
   * @throws java.lang.Exception if there is any problem with deleting.
   */
  public synchronized static void delete(java.lang.String fileName) throws java.lang.Exception
  {
    new java.io.File(fileName).delete();
  }
  
  /**
   * Method for getting the server date and time.
   * @return the actual date and time on the server.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Date getDate() throws java.lang.Exception
  {
    return new java.util.Date();
  }
  
  /**
   * Method for getting the server environment variables.
   * @return a vector of variables.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized java.util.Vector<java.lang.String> getEnvironmentVariables() throws java.lang.Exception
  {
    java.util.Vector<java.lang.String> environmentVariables = new java.util.Vector<java.lang.String>();
    environmentVariables.add(java.lang.String.valueOf(concurrentCount));
    environmentVariables.add(workFolder);
    environmentVariables.add(dbURL);
    return environmentVariables;
  }
  
  /**
   * Method for getting the tables.
   * @return a vector of tables.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Vector<elv.util.Table> getTables() throws java.lang.Exception
  {
    return elv.util.Table.getAllTables();
  }
  
  /**
   * Method for getting the possible year intervals.
   * @return an array of year intervals.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static elv.util.parameters.YearInterval[] getPossibleYearIntervals() throws java.lang.Exception
  {
    return elv.util.Table.getPossibleYearIntervals();
  }
  
}
