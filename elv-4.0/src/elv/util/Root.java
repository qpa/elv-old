/*
 * Root.java
 */

package elv.util;

/**
 * Class for root reprezentation.
 * @author Elv
 */
public class Root<P> extends elv.util.Propertable implements elv.util.Visualizable, elv.util.Parentable
{
  
  // Constants.
  // Property names.
  /** The "root" property name.*/
  public final static java.lang.String NAME = "Elv";
  /** The "concurrent count" property name.*/
  public final static java.lang.String CONCURRENT_COUNT_NAME = NAME + ".Count.Concurrent";
  /** The "base URL" property name.*/
  public final static java.lang.String BASE_URL_NAME = NAME + ".URL.Base";
  /** The "servlet URL" property name.*/
  public final static java.lang.String SERVLET_URL_NAME = NAME + ".URL.Servlet";
  /** The "work folder" property name.*/
  public final static java.lang.String WORK_FOLDER_NAME = NAME + ".Folder.Work";
  /** The "database URL" property name.*/
  public final static java.lang.String DATABASE_URL_NAME = NAME + ".URL.DB";
  
  // Variables.
  /** The version of the application. */
  private java.lang.String version;
  /** The count of concurrently executed tasks. */
  private int concurrentCount;
  /** The work folder. */
  private java.lang.String workFolder;
  /** The database URL. */
  private java.lang.String dbURL;
  /** The vector of children. */
  private java.util.Vector<P> children = null;
  
  /**
   * Constructor.
   * @throws java.lang.Exception if there is any problem with the setting.
   */
  public Root(java.lang.String version, int concurrentCount, java.lang.String workFolder, java.lang.String dbURL)
  {
    this.version = version;
    this.concurrentCount = concurrentCount;
    this.workFolder = workFolder;
    this.dbURL = dbURL;
    try
    {
      properties = new java.util.Vector<elv.util.Property>();
      setDefaultProperties();
    }
    catch(java.lang.Exception exc)
    {
    }
  }
  
  /**
   * Method for getting the folder path of the application.
   * @return the folder path of this application.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public java.lang.String getFolderPath() throws java.lang.Exception
  {
    return workFolder;
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
    return null;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any problem with the setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.INTEGER, elv.util.Property.LABEL_FIELD, false, CONCURRENT_COUNT_NAME, concurrentCount));
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.LABEL_FIELD, false, WORK_FOLDER_NAME, workFolder));
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.LABEL_FIELD, false, DATABASE_URL_NAME, dbURL));
  }
  
  /**
   * Method for setting client side properties.
   * @throws java.lang.Exception if there is any problem with the setting.
   */
  public void setClientProperties() throws java.lang.Exception
  {
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.LABEL_FIELD, false, BASE_URL_NAME, elv.util.client.ClientStub.getBaseURL()));
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.LABEL_FIELD, false, SERVLET_URL_NAME, elv.util.client.ClientStub.getServletURL()));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any problem with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
  }
  
  /**
   * Method for getting the icon of the root.
   * @return the icon of root.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/elv.gif"));
  }
  
  /**
   * Overriddem method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this class.
   */
  public java.lang.String toString()
  {
    return NAME;
  }

  /**
   * Method for testing the existence of a parentable.
   * @return true, if the parantable exists.
   * @throws java.lang.Exception if there is any problem with testing.
   */
  public boolean exists() throws java.lang.Exception
  {
    return new java.io.File(workFolder).exists();
  }
  
  /**
   * Method for getting the children of a parentable.
   * @return a vector of children.
   */
  public java.util.Vector<P> getChildren()
  {
    return children;
  }
  
  /**
   * Method for setting the children of a parentable.
   * @parem children the vector of children.
   */
  public void setChildren(java.util.Vector children)
  {
    this.children = children;
  }
  
  /**
   * Method for loading the children of a parentable.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public synchronized void loadChildren() throws java.lang.Exception
  {
    children = new java.util.Vector<P>();
    java.lang.String[] files = new java.io.File(workFolder).list();
    for(int i = 0; i < files.length; i++)
    {
      User user = User.parse(this, files[i]);
      children.add((P)user);
    }
  }
  
}
