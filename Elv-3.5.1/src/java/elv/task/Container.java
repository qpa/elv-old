/*
 * Container.java
 */
package elv.task;

/**
 * Class for reprezenting a container of tasks.
 * @author Qpa
 */
public class Container<P> extends elv.util.Propertable implements java.io.Serializable, elv.util.Visualizable, elv.util.Parentable
{
  
  //Constant.
  /** The title of the container. */
  public final static java.lang.String TITLE = "Container";
  // Property names.
  /** The "root" property name.*/
  public final static java.lang.String ROOT_NAME = "Container";
  /** The "type" property name.*/
  public final static java.lang.String TYPE_NAME = ROOT_NAME + ".Type";
  /** The "modified" property name.*/
  public final static java.lang.String MODIFIED_NAME = ROOT_NAME + ".Modified";
  
  //Variables.
  /** The user of the container. */
  private elv.util.User user;
  /** The name of the container. */
  private java.lang.String name;
  /** The vector of children. */
  protected java.util.Vector<P> children = null;
  
  /**
   * Constructor.
   * @param user the user of the container.
   * @param name the name of the container.
   */
  public Container(elv.util.User user, java.lang.String name)
  {
    this.user = user;
    this.name = name;
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
   * Method for getting the user of this container.
   * @return the user of this container.
   */
  public elv.util.User getUser()
  {
    return user;
  }
  
  /**
   * Method for getting the name of this container.
   * @return the name of this container.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method to setting the name of this container.
   * @param name the new name.
   */
  public void setName(java.lang.String name)
  {
    this.name = name;
  }
  
  /**
   * Method for getting the folder path of the container.
   * @return the folder path of this container.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public java.lang.String getFolderPath() throws java.lang.Exception
  {
    return user.getFolderPath() + elv.util.Util.getFS() + name;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @return the root name of properties.
   */
  public java.lang.String getRootName()
  {
    return ROOT_NAME;
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
    properties.add(new elv.util.Property(elv.util.Property.STRING, elv.util.Property.LABEL_FIELD, true, TYPE_NAME, TITLE));
    properties.add(new elv.util.Property(elv.util.Property.DATE, elv.util.Property.LABEL_FIELD, false, MODIFIED_NAME, new java.util.Date(new java.io.File(getFolderPath()).lastModified())));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any problem with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
  }
  
  /**
   * Method for getting the icon of the container.
   * @return the icon of container.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/container.gif"));
  }
  
  /**
   * Method for getting the title.
   * @return the title of this container.
   */
  public java.lang.String getTitle()
  {
    return user + elv.util.Util.TITLE_SEPARATOR + name;
  }
  
  /**
   * Method for creating the container.
   * @throws java.lang.Exception if there is any creation error.
   */
  public synchronized void create() throws java.lang.Exception
  {
    boolean done = new java.io.File(getFolderPath()).mkdirs();
    if(!done)
    {
      throw new java.io.InvalidObjectException(getFolderPath());
    }
  }
  
  /**
   * Method for moving the container.
   * @param newContainer the new container.
   * @throws java.lang.Exception if there is any moving error.
   */
  public synchronized void move(Container newContainer) throws java.lang.Exception
  {
    java.lang.String newName = elv.util.Util.getNumbered(newContainer.getName(), elv.util.Util.vectorize(new java.io.File(newContainer.getUser().getFolderPath()).list()), null);
    java.lang.String newFolderPath = newContainer.getUser().getFolderPath() + elv.util.Util.getFS() + newName;
    boolean done = new java.io.File(getFolderPath()).renameTo(new java.io.File(newFolderPath));
    if(!done)
    {
      throw new java.io.InvalidObjectException(newFolderPath);
    }
    name = newContainer.getName();
  }
  
  /**
   * Method for deleting the container.
   * @throws java.lang.Exception if there is any deleting error.
   */
  public synchronized void delete() throws java.lang.Exception
  {
    elv.util.Util.delete(getFolderPath());
  }
  
  /**
   * Method for copying the container folder structure to a new container.
   * @param newContainer the new container.
   * @throws java.lang.Exception if there is any copying error.
   */
  public synchronized void copy(Container newContainer) throws java.lang.Exception
  {
    java.lang.String newName = elv.util.Util.getNumbered(newContainer.getName(), elv.util.Util.vectorize(new java.io.File(newContainer.getUser().getFolderPath()).list()), null);
    java.lang.String newFolderPath = newContainer.getUser().getFolderPath() + elv.util.Util.getFS() + newName;
    elv.util.Util.copy(getFolderPath(), newFolderPath);
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param container an <CODE>elv.task.Container</CODE> object.
   * @return true, if this container equals with the given container.
   */
  public boolean equals(java.lang.Object container)
  {
    boolean isEqual = false;
    if(container != null && container instanceof Container)
    {
      Container c = (Container)container;
      isEqual = (user.equals(c.user) && name.equals(c.name));
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this container.
   */
  public java.lang.String toString()
  {
    return name;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return a clone of this container.
   */
  public java.lang.Object clone()
  {
    return new Container(user, name);
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
  public void loadChildren() throws java.lang.Exception
  {
    children = new java.util.Vector<P>();
    java.lang.String[] files = new java.io.File(getFolderPath()).list();
    for(int i = 0; i < files.length; i++)
    {
      Task child = Task.parse(this, files[i]);
      children.add((P)child);
    }
  }
  
}
