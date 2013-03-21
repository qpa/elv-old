/*
 * Selection.java
 */
package elv.task.executables;

/**
 * Class for implementing the selection of rough data.
 * @author Elv
 */
public abstract class Selection extends Executable
{
  
  // Constants.
  /** The property file name. */
  protected final static java.lang.String PROPERTY_FILE = "selection.prop";
  /** The execution files. */
  private final static ExecutionFile[] EXECUTION_FILES = {new ExecutionFile("selection.csv", "Selection")};
  // Indices in the above array.
  /** Index in the <code>EXECUTION_FILES</code> array. */
  public final int SELECTION = 0;
  
  // Property names.
  /** The "name" property name.*/
  public final static java.lang.String NAME = "Selection";
  /** The "genders" property name.*/
  public final static java.lang.String GENDERS_NAME = NAME + ".Genders";
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public Selection() throws java.lang.Exception
  {
    properties = new java.util.Vector<elv.util.Property>();
    setDefaultProperties();
  }
  
  /**
   * Implemented method from <CODE>elv.task.executables.Executable</CODE>.
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
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.GENDER_ARRAY, elv.util.Property.LIST_BUTTON, true, GENDERS_NAME, elv.util.parameters.Gender.getAllGenders()));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(GENDERS_NAME, properties).setDefaultValues(elv.util.parameters.Gender.getAllGenders());
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param selection an <CODE>elv.task.executables.Selection</CODE> object.
   */
  public boolean equals(java.lang.Object selection)
  {
    boolean isEqual = false;
    if(selection != null && selection instanceof Selection)
    {
      isEqual = getPropertyFile().equals(((Selection)selection).getPropertyFile());
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
