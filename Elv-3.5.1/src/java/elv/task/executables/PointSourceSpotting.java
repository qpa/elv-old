/*
 * PointSourceSpotting.java
 */
package elv.task.executables;

/**
 * Class for implementing the point-source spotting.
 * @author Qpa
 */
public class PointSourceSpotting extends Spotting
{
  
  /**
   * Constants.
   */
  public static final double BENCHMARK_RADIUS = 40000;
  
  public final static java.lang.String EXAMINATION_NAME = NAME + ".Examination";
  public final static java.lang.String ENVIRONMENT_NAME = NAME + ".Environment";
  
  // Property values.
  public final static java.lang.String[] EXAMINATIONS = {"Examination.QQ", "Examination.Qualitative", "Examination.Quantitative", "Examination.LowPrevalence"};
    // The indices in the above array
    public final static int QQ = 0;
    public final static int QUALITATIV = 1;
    public final static int QUANTITATIV = 2;
    public final static int LOW_PREVALENCE = 3;
  public final static java.lang.String[] ENVIRONMENTS = {"Environment.Neighbourhood", "Environment.RingSector", "Environment.Custom"};
    // The indices in the above array.
    public final static int NEIGHBOURHOOD = 0;
    public final static int RING_SECTOR = 1;
    public final static int CUSTOM = 2;
    
  // Sub-property names.
  public final static java.lang.String NEIGHBOURS_NAME = ENVIRONMENTS[NEIGHBOURHOOD] + ".Neighbours";
  public final static java.lang.String INNER_RADIUS_NAME = ENVIRONMENTS[RING_SECTOR] + ".Radius.In";
  public final static java.lang.String OUTER_RADIUS_NAME = ENVIRONMENTS[RING_SECTOR] + ".Radius.Out";
  public final static java.lang.String RADIUS_SCALE_NAME = ENVIRONMENTS[RING_SECTOR] + ".Radius.Scale";
  public final static java.lang.String DIRECTION_NAME = ENVIRONMENTS[RING_SECTOR] + ".Direction";
  
  // Sub-property values.
  public final static int DEFAULT_NEIGHBOURS = 5;
  public final static int DEFAULT_INNER_RADIUS = 1000;
  public final static int DEFAULT_OUTER_RADIUS = 4000;
  public final static int DEFAULT_RADIUS_SCALE = 2000;
  public final static java.lang.String[] DIRECTIONS = {"Direction.North", "Direction.NorthEast", "Direction.East",
    "Direction.SouthEast", "Direction.South", "Direction.SouthWest", "Direction.West", "Direction.NorthWest"};
    // The indices in the above array.
    public final static int NORTH = 0;
    public final static int NORTH_EAST = 1;
    public final static int EAST = 2;
    public final static int SOUTH_EAST = 3;
    public final static int SOUTH = 4;
    public final static int SOUTH_WEST = 5;
    public final static int WEST = 6;
    public final static int NORTH_WEST = 7;
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public PointSourceSpotting() throws java.lang.Exception
  {
    super();
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, EXAMINATION_NAME, EXAMINATIONS[QQ]));
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.COMBO_BOX, true, ENVIRONMENT_NAME, ENVIRONMENTS[NEIGHBOURHOOD]));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, NEIGHBOURS_NAME, DEFAULT_NEIGHBOURS));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, INNER_RADIUS_NAME, DEFAULT_INNER_RADIUS));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, OUTER_RADIUS_NAME, DEFAULT_OUTER_RADIUS));
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.SPINNER, false, RADIUS_SCALE_NAME, DEFAULT_RADIUS_SCALE));
    java.util.Vector<java.lang.String> directions = new java.util.Vector<java.lang.String>();
    directions.add(DIRECTIONS[NORTH]);
    properties.add(new elv.util.Property(elv.util.Property.STRING_ARRAY, elv.util.Property.LIST_BUTTON, true, DIRECTION_NAME, directions));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(EXAMINATION_NAME, properties).setDefaultValues(elv.util.Util.vectorize(EXAMINATIONS));
    elv.util.Property.get(ENVIRONMENT_NAME, properties).setDefaultValues(elv.util.Util.vectorize(ENVIRONMENTS));
    elv.util.Property.get(DIRECTION_NAME, properties).setDefaultValues(elv.util.Util.vectorize(DIRECTIONS));
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
//    java.lang.Runtime.getRuntime().exec("arc_point_source " + task.getExecutionFolder());
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
