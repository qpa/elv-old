/*
 * Spotting.java
 */
package elv.task.executables;

/**
 * Abstract class for implementing the spotting methods.
 * @author Qpa
 */
public abstract class Spotting extends Executable
{
  
  /**
   * Constants.
   */
  private final static java.lang.String PROPERTY_FILE = "spotting.prop";
  private final static java.lang.String[] EXECUTION_FILES = {"spotting.jpg", "spotting_districts.csv"};
  private final static java.lang.String[] EXECUTION_FILE_TITLES = {"Spotting.Spots", "Spotting.Districts"};
  // Indices in the above arrays.
  public final int SPOTTING = 0;
  public final int SPOTTING_DISTRICTS = 1;
  
  // Property names.
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
