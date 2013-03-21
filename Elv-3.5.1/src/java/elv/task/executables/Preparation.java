/*
 * Preparation.java
 */
package elv.task.executables;

/**
 * Abstract class for implementing the different preparation methods.
 * @author Qpa
 */
public abstract class Preparation extends Executable
{
  
  /**
   * Constants.
   */
  protected final static java.lang.String PROPERTY_FILE = "preparation.prop";
  protected final static java.lang.String[] EXECUTION_FILES = {"preparation.csv"};
  protected final static java.lang.String[] EXECUTION_FILE_TITLES = {"Preparation"};
  // Indices in the above arrays.
  public final int PREPARATION = 0;
  
  // Property names.
  public final static java.lang.String NAME = "Preparation";
  public final static java.lang.String GENDERS_NAME = NAME + ".Genders";
  public final static java.lang.String RESOLUTION_NAME = NAME + ".Resolution";
  public final static java.lang.String SELECTION_NAME = NAME + ".Selection";
  public final static java.lang.String BENCHMARK_YEAR_NAME = NAME + ".Benchmark.Year";
  
  // Property values.
  public final static java.lang.String[] RESOLUTIONS = {"Resolution.YearIntervaly", "Resolution.Yearly", "Resolution.Monthly", "Resolution.Daily"};
    // The indices in the above array.
    public final static int YEAR_INTERVALY = 0;
    public final static int YEARLY = 1;
    public final static int MONTHLY = 2;
    public final static int DAILY = 3;
  public final static java.lang.String[] SELECTIONS = {"Selection.NotDistinct", "Selection.Distinct"};
    // The indices in the above array.
    public final static int NOT_DISTINCT = 0;
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
   * @return a vector with the execution file names.
   * @throws java.lang.Exception if there is any error with getting.
   */
  public java.util.Vector<java.lang.String> getExecutionFiles() throws java.lang.Exception
  {
    java.util.Vector<java.lang.String> executionFiles = new java.util.Vector<java.lang.String>();
    for(java.lang.String iteratorFileName : EXECUTION_FILES)
    {
      executionFiles.add(iteratorFileName);
    }
    return executionFiles;
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return a vector with the execution file titless.
   * @throws java.lang.Exception if there is any error with getting.
   */
  public java.util.Vector<java.lang.String> getExecutionFileTitles() throws java.lang.Exception
  {
    java.util.Vector<java.lang.String> executionFileTitles = new java.util.Vector<java.lang.String>();
    for(java.lang.String iteratorFileTitle : EXECUTION_FILE_TITLES)
    {
      executionFileTitles.add(elv.util.Util.translate(iteratorFileTitle));
    }
    return executionFileTitles;
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
