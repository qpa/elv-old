/*
 * Executable.java
 */
package elv.task.executables;

/**
 * Interface for an executable step of a task.
 * @author Elv
 */
public abstract class Executable extends elv.util.Propertable implements elv.util.Visualizable
{
  
  // Constants.
  /** The separator character string. */
  public final static java.lang.String VS = "" + elv.util.server.Servlet.SEPARATOR_CHAR;
  /** The title of executables. */
  public final static java.lang.String EXECUTABLES_TITLE = "Steps";
  
  /**
   * Method for getting the name of the step.
   * @return the name of this step.
   */
  public abstract java.lang.String getName();
  
  /**
   * Method for getting the execution files.
   * @return an array with the execution files.
   */
  public abstract ExecutionFile[] getExecutionFiles();
  
  /**
   * Method for getting the done state.
   * This method searches for the name of this executable in the <CODE>elv.task.Execution.FILE_NAME</CODE>.
   * @param task the owner task of this executable.
   * @return true, if this executable was executed.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public boolean isDone(elv.task.Task task) throws java.lang.Exception
  {
    boolean isDone = false;
    java.lang.String line;
    java.lang.String pathName = task.getExecutionFolderPath() + "/" + elv.task.Execution.FILE_NAME;
    java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(pathName), elv.util.Util.FILE_ENCODING));
    while((line = fileReader.readLine()) != null)
    {
      if(line.equals(getName()))
      {
        isDone = true;
        break;
      }
    }
    fileReader.close();
    return isDone;
  }
  
  /**
   * Method for setting the done state.
   * This methos writes the name of this exeutable in the <CODE>elv.task.Execution.FILE_NAME</CODE>.
   * @param task the owner task of this executable.
   * @param isDone true, if this executable was executed.
   * @throws java.lang.Exception if there is any problem with setting.
   */
  public void setDone(elv.task.Task task, boolean isDone) throws java.lang.Exception
  {
    java.lang.String line;
    java.util.Vector<java.lang.String> lines = new java.util.Vector<java.lang.String>();
    java.lang.String pathName = task.getExecutionFolderPath() + "/" + elv.task.Execution.FILE_NAME;
    java.io.BufferedReader fileReader = new java.io.BufferedReader(new java.io.InputStreamReader(new java.io.FileInputStream(pathName), elv.util.Util.FILE_ENCODING));
    while((line = fileReader.readLine()) != null)
    {
      if(!line.equals(getName()))
      {
        lines.add(line);
      }
    }
    fileReader.close();
    if(isDone)
    {
      lines.add(getName());
    }
    else
    {
      // Delete the execution files.
      for(java.lang.String iteratorFile : getExecutionFiles())
      {
        java.lang.String executionPathName = task.getExecutionFolderPath() + "/" + iteratorFile;
        new java.io.File(executionPathName).delete();
      }
      // Set and store task properties.
      if(lines.size() > 0)
      {
        elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).setValue(new elv.util.State(elv.util.State.STOPPED));
      }
      else
      {
        elv.util.Property.get(elv.task.Task.STATE_NAME, task.getProperties()).setValue(new elv.util.State(elv.util.State.DEFINED));
      }
      elv.util.Property.get(elv.task.Task.MODIFIED_NAME, task.getProperties()).setValue(new java.util.Date());
      task.storeProperties();
    }
    java.io.PrintWriter fileWriter = new java.io.PrintWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(pathName), elv.util.Util.FILE_ENCODING));
    for(java.lang.String iteratorLines : lines)
    {
      fileWriter.println(iteratorLines);
    }
    fileWriter.close();
  }
  
  /**
   * Method for executing the executable.
   * @param execution the owner execution.
   * @return true, if the execution was finished correctly.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public abstract boolean execute(elv.task.Execution execution) throws java.lang.Exception;
  
}
