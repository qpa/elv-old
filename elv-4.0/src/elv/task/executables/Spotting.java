/*
 * Spotting.java
 */
package elv.task.executables;

/**
 * Abstract class for implementing the spotting methods.
 * @author Elv
 */
public abstract class Spotting extends Executable
{
  
  // Constants.
  /** The property file name. */
  private final static java.lang.String PROPERTY_FILE = "spotting.prop";
  /** The execution files. */
  private final static ExecutionFile[] EXECUTION_FILES = {new ExecutionFile("spotting.jpg", "Spotting.Spots"), new ExecutionFile("spotting_districts.csv", "Spotting.Districts")};
  // Indices in the above array.
  /** Index in the <code>EXECUTION_FILES</code> array. */
  public final int SPOTTING = 0;
  /** Index in the <code>EXECUTION_FILES</code> array. */
  public final int SPOTTING_DISTRICTS = 1;
  
  // Property names.
  /** The "name" property name.*/
  public final static java.lang.String NAME = "Spotting";
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public Spotting() throws java.lang.Exception
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
   * @param spotting an <CODE>elv.task.executables.Spotting</CODE> object.
   */
  public boolean equals(java.lang.Object spotting)
  {
    boolean isEqual = false;
    if(spotting != null && spotting instanceof Spotting)
    {
      isEqual = getPropertyFile().equals(((Spotting)spotting).getPropertyFile());
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
