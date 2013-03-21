/*
 * ClusterSpotting.java
 */
package elv.task.executables;

/**
 * Class for implementing the cluster spotting.
 * @author Elv
 */
public class ClusterSpotting extends Spotting
{
  
  /**
   * Constants.
   */
  public final static java.lang.String INNER_RADIUS_NAME = NAME + ".Radius.In";
  public final static java.lang.String OUTER_RADIUS_NAME = NAME + ".Radius.Out";
  public final static java.lang.String RADIUS_SCALE_NAME = NAME + ".Radius.Scale";
  public final static java.lang.String RADIUS_PER_DIAGONAL_NAME = NAME + ".Radius.PerDiagonal";
  
  // Property values.
  public final static int DEFAULT_INNER_RADIUS = 1000;
  public final static int DEFAULT_OUTER_RADIUS = 4000;
  public final static int DEFAULT_RADIUS_SCALE = 2000;
  public final static double DEFAULT_RADIUS_PER_DIAGONAL = 1;
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public ClusterSpotting() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, INNER_RADIUS_NAME, DEFAULT_INNER_RADIUS));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, OUTER_RADIUS_NAME, DEFAULT_OUTER_RADIUS));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, RADIUS_SCALE_NAME, DEFAULT_RADIUS_SCALE));
    properties.add(new elv.util.Property(elv.util.Property.DOUBLE, elv.util.Property.SPINNER, false, RADIUS_PER_DIAGONAL_NAME, DEFAULT_RADIUS_PER_DIAGONAL));
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
//    java.lang.Runtime.getRuntime().exec("arc_clusters " + task.getExecutionFolder());
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
