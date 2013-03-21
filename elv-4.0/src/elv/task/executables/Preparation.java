/*
 * Preparation.java
 */
package elv.task.executables;

/**
 * Abstract class for implementing the different preparation methods.
 * @author Elv
 */
public abstract class Preparation extends Executable
{
  
  // Constants.
  /** The property file name. */
  protected final static java.lang.String PROPERTY_FILE = "preparation.prop";
  /** The execution files. */
  private final static ExecutionFile[] EXECUTION_FILES = {new ExecutionFile("preparation.csv", "Preparation")};
    // Indices in the above array.
    /** Index in the <code>EXECUTION_FILES</code> array. */
    public final int PREPARATION = 0;
  
  // Property names.
  /** The "name" property name.*/
  public final static java.lang.String NAME = "Preparation";
  /** The "genders" property name.*/
  public final static java.lang.String GENDERS_NAME = NAME + ".Genders";
  /** The "resolution" property name.*/
  public final static java.lang.String RESOLUTION_NAME = NAME + ".Resolution";
  /** The "selection" property name.*/
  public final static java.lang.String SELECTION_NAME = NAME + ".Selection";
  /** The "benchmark year" property name.*/
  public final static java.lang.String BENCHMARK_YEAR_NAME = NAME + ".Benchmark.Year";
  
  // Property values.
  /** The array of possible resolution types. */
  public final static java.lang.String[] RESOLUTIONS = {"Resolution.YearIntervaly", "Resolution.Yearly", "Resolution.Monthly", "Resolution.Daily"};
    // The indices in the above array.
    /** Index in the <code>RESOLUTIONS</code> array. */
    public final static int YEAR_INTERVALY = 0;
    /** Index in the <code>RESOLUTIONS</code> array. */
    public final static int YEARLY = 1;
    /** Index in the <code>RESOLUTIONS</code> array. */
    public final static int MONTHLY = 2;
    /** Index in the <code>RESOLUTIONS</code> array. */
    public final static int DAILY = 3;
  /** The array of possible selection types. */
  public final static java.lang.String[] SELECTIONS = {"Selection.NotDistinct", "Selection.Distinct"};
    // The indices in the above array.
    /** Index in the <code>SELECTIONS</code> array. */
    public final static int NOT_DISTINCT = 0;
    /** Index in the <code>SELECTIONS</code> array. */
    public final static int DISTINCT = 1;
    
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public Preparation() throws java.lang.Exception
  {
    properties = new java.util.Vector<elv.util.Property>();
    setDefaultProperties();
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return the name of this executable.
   */
  public java.lang.String getName()
  {
    return NAME;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @return the root name of properties.
   */
  public java.lang.String getRootName()
  {
    return NAME;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @return the propety file name.
   */
  public java.lang.String getPropertyFile()
  {
    return PROPERTY_FILE;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param preparation an <CODE>elv.task.executables.Preparation</CODE> object.
   */
  public boolean equals(java.lang.Object preparation)
  {
    boolean isEqual = false;
    if(preparation != null && preparation instanceof Preparation)
    {
      isEqual = getPropertyFile().equals(((Preparation)preparation).getPropertyFile());
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this executable.
   */
  public java.lang.String toString()
  {
    return elv.util.Util.translate(getName());
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return an array with the execution files.
   */
  public ExecutionFile[] getExecutionFiles()
  {
    return EXECUTION_FILES;
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return the icon of executable.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/executable.gif"));
  }
  
}
