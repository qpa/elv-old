/*
 * Documenting.java
 */
package elv.task.executables;

/**
 * Class for implementing the different documenting methods.
 * @author Qpa
 */
public abstract class Documenting extends Executable
{
  
  /**
   * Constants.
   */
  private final static java.lang.String PROPERTY_FILE = "documenting.prop";
  private final static java.lang.String[] EXECUTION_FILES = {"documenting.pdf"};
  // Indices in the above array.
  public final int DEFAULT = 0;
  
  // Property names.
  public final static java.lang.String NAME = "Documenting";
  public final static java.lang.String LOCALES_NAME = NAME + ".Locales";
  public final static java.lang.String CODES_NAME = NAME + ".Codes";
   
  // Property values.
  public final static java.lang.String[] CODES = {"Code.Postal", "Code.Statistical", "Code.Place", "Code.District"};
  
  protected final static int SMALL_BIG_LEVEL = 10000;
  protected final static int DISTRIBUTIONS_LENGTH = 10;
  protected final static java.lang.String INTEGER_PATTERN = "{0,number,#}";
  protected final static java.lang.String DOUBLE_PATTERN = "{0,number,#.###}";
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any error with setting.
   */
  public Documenting() throws java.lang.Exception
  {
    properties = new java.util.Vector<elv.util.Property>();
    setDefaultProperties();
  }
  
  /**
   * Implemented method from <CODE>elv.task.execution.Executable</CODE>.
   * @return the name of this step.
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
   * @throws java.lang.Exception if there is any error with setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.LOCALE_ARRAY, elv.util.Property.LIST_BUTTON, false, LOCALES_NAME, elv.util.Util.getAvailableDictionaries()));
    properties.add(new elv.util.Property(elv.util.Property.STRING_ARRAY, elv.util.Property.LIST_BUTTON, true, CODES_NAME, elv.util.Util.vectorize(CODES)));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    elv.util.Property.get(LOCALES_NAME, properties).setDefaultValues(elv.util.Util.getAvailableDictionaries());
    elv.util.Property.get(CODES_NAME, properties).setDefaultValues(elv.util.Util.vectorize(CODES));
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param documenting an <CODE>elv.task.execution.Preparation</CODE> object.
   */
  public boolean equals(java.lang.Object documenting)
  {
    boolean isEqual = false;
    if(documenting != null && documenting instanceof Documenting)
    {
      isEqual = getPropertyFile().equals(((Documenting)documenting).getPropertyFile());
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
    java.util.Vector<java.util.Locale> locales = (java.util.Vector<java.util.Locale>)elv.util.Property.get(LOCALES_NAME, properties).getValue();
    for(java.util.Locale iteratorLocale : locales)
    {
      java.lang.String fileExtension = elv.util.Util.getFileExtension(EXECUTION_FILES[DEFAULT]);
      java.lang.String fileName = EXECUTION_FILES[DEFAULT].split(java.util.regex.Pattern.quote("." + fileExtension))[0] + "_" + iteratorLocale + "." + fileExtension;
      executionFiles.add(fileName);
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
    java.util.Vector<java.util.Locale> locales = (java.util.Vector<java.util.Locale>)elv.util.Property.get(LOCALES_NAME, properties).getValue();
    for(java.util.Locale iteratorLocale : locales)
    {
      executionFileTitles.add(iteratorLocale.getDisplayName(iteratorLocale));
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
