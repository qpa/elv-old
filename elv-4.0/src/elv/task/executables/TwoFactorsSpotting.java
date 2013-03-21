/*
 * TwoFactorsSpotting.java
 */
package elv.task.executables;

/**
 * Class for implementing the two factors spotting.
 * @author Elv
 */
public class TwoFactorsSpotting extends Spotting
{
  
  /**
   * Constants.
   */
  public final static java.lang.String FIRST_FACTOR = NAME + ".Factor.First";
  public final static java.lang.String SECOND_FACTOR = NAME + ".Factor.Second";
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public TwoFactorsSpotting() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.TASK, elv.util.Property.TREE_BUTTON, false, FIRST_FACTOR, null));
    properties.add(new elv.util.Property(elv.util.Property.TASK, elv.util.Property.TREE_BUTTON, false, SECOND_FACTOR, null));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @param execution an execution object.
   * @return true, if the execution was finished correctly.
   * @throws java.lang.Exception if there is any problem with execution.
   */
  public boolean execute(elv.task.Execution execution) throws java.lang.Exception
  {
    execution.setExecutable(this);
    // Parse.
//    java.lang.Runtime.getRuntime().exec("arc_two_factors " + task.getExecutionFolder());
    // Wait for the external execution.
    while(!isDone(execution.getTask()))
    {
      java.lang.Thread.yield();
      java.lang.Thread.sleep(5000);
      // Return, if execution was stopped.
      if(!execution.isExecuted())
      {
        return false;
      }
    }
   return true;
  }
  
}
