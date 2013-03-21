/*
 * User.java
 */
package elv.util;

/**
 * Class for user reprezentation.
 * @author Qpa
 */
public class User<P> extends elv.util.Propertable implements java.io.Serializable, elv.util.Visualizable, elv.util.Parentable
{
  
  //Constants.
  /** The property file name. */
  private final static java.lang.String PROPERTY_FILE_NAME = "user.prop";
  
  // Property names.
  /** The "root" property name.*/
  public final static java.lang.String ROOT_NAME = "User";
  /** The "group" property name.*/
  public final static java.lang.String TYPE_NAME = ROOT_NAME + ".Type";
  /** The "description" property name.*/
  public final static java.lang.String DESCRIPTION_NAME = ROOT_NAME + ".Description";
  /** The "password" property name.*/
  public final static java.lang.String PASSWORD_NAME = ROOT_NAME + ".Password";
  /** The "locale" property name.*/
  public final static java.lang.String LOCALE_NAME = ROOT_NAME + ".Locale";
  /** The "look and feel class" property name.*/
  public final static java.lang.String LOOK_AND_FEEL_CLASS_NAME = ROOT_NAME + ".LookAndFeelClass";
  /** The "refresh delay" property name.*/
  public final static java.lang.String REFRESH_DELAY_NAME = ROOT_NAME + ".RefreshDelay";
  /** The "divider location" property name.*/
  public final static java.lang.String DIVIDER_LOCATION_NAME = "Frame.DividerLocation";
  /** The "locations" property name.*/
  public final static java.lang.String LOCATIONS_NAME = "Frame.Locations";
  /** The "sizes" property name.*/
  public final static java.lang.String SIZES_NAME = "Frame.Sizes";
  
  // Property values.
  /** The array of posible user types. */
  public final static java.lang.String[] USER_TYPES = {"User.Type.Administrator", "User.Type.Single", "User.Type.Community"};
    // The indices in the above array.
    /** Index in the <code>USER_TYPES</code> array. */
    public final static int ADMINISTRATOR = 0;
    /** Index in the <code>USER_TYPES</code> array. */
    public final static int SINGLE = 1;
    /** Index in the <code>USER_TYPES</code> array. */
    public final static int COMMUNITY = 2;
    
  // The indices in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>).
  /** Index in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>). */
  public final static int WINDOW = 0;
  /** Index in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>). */
  public final static int PROPERTIES = 1;
  /** Index in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>). */
  public final static int INTERVALS = 2;
  /** Index in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>). */
  public final static int DIAGNOSISES = 3;
  /** Index in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>). */
  public final static int SETTLEMENTS = 4;
  /** Index in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>). */
  public final static int DOCUMENTS = 5;
  /** Index in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>). */
  public final static int PROGRESSES = 6;
  /** Index in the frame arrays (<code>LOCATIONS</code> and <code>SIZES</code>). */
  public final static int TABLES = 7;
  
  //Variables.
  /** The name of the user. */
  private java.lang.String name;
  /** The type of the user. */
  private int type = -1;
  /** The vector of children. */
  private java.util.Vector<P> children = null;
  
  
  /**
   * Constructor for a new user.
   * @param name the name of a user.
   * @throws java.lang.Exception if there is any creation error.
   */
  public User(java.lang.String name, int type) throws java.lang.Exception
  {
    if(name == null)
    {
      throw new java.lang.NullPointerException();
    }
    else if(name.equals(""))
    {
      throw new java.lang.IllegalArgumentException(name);
    }
    
    this.name = name;
    this.type = type;
    properties = new java.util.Vector<elv.util.Property>();
    setDefaultProperties();
  }
  
  /**
   * Parser for an existing user.
   * @param name the name of a user.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public static User parse(java.lang.String name) throws java.lang.Exception
  {
    User user = new User(name, 0);
    user.properties = Property.load(user.getFolderPath(), PROPERTY_FILE_NAME);
    // Set the proper user type.
    java.lang.String typeName = (java.lang.String)elv.util.Property.get(TYPE_NAME, user.properties).getValue();
    for(int i = 0; i < USER_TYPES.length; i++)
    {
      if(USER_TYPES[i].equals(typeName))
      {
        user.type = i;
        break;
      }
    }
    return user;
  }
  
  /**
   * Method for getting the <CODE>name</CODE> variable.
   * @return the name if this user.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method to setting the name variable.
   * @param name the new name.
   */
  public void setName(java.lang.String name)
  {
    this.name = name;
  }
  
  /**
   * Method for getting the type of the user.
   * @retun the type of this user.
   */
  public int getType()
  {
    return type;
  }
  
  /**
   * Method for setting the type of the user.
   * @param type the new type of this user.
   * @throws java.lang.Exception if there is any problem with setting.
   */
  public void setType(int type) throws java.lang.Exception
  {
    elv.util.Property.get(TYPE_NAME, properties).setValue(USER_TYPES[type]);
    this.type = type;
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
    return PROPERTY_FILE_NAME;
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with setting.
   */
  protected void setDefaultProperties() throws java.lang.Exception
  {
    properties.add(new Property(Property.STRING, Property.COMBO_BOX, true, TYPE_NAME, USER_TYPES[type]));
    properties.add(new Property(Property.STRING, Property.TEXT_FIELD, false, DESCRIPTION_NAME, ""));
    properties.add(new Property(Property.STRING, Property.PASSWORD_FIELD, false, PASSWORD_NAME, name));
    properties.add(new Property(Property.LOCALE, Property.COMBO_BOX, false, LOCALE_NAME, Util.getAvailableDictionaries().get(0)));
    properties.add(new Property(Property.STRING, Property.TEXT_FIELD, false, LOOK_AND_FEEL_CLASS_NAME, ""));
    properties.add(new Property(Property.INTEGER, Property.SPINNER, false, REFRESH_DELAY_NAME, 5000));
    properties.add(new Property(Property.INTEGER, Property.NO_FIELD, false, DIVIDER_LOCATION_NAME, 250));
    java.util.Vector<java.awt.Point> points = new java.util.Vector<java.awt.Point>();
    points.add(new java.awt.Point(0,0));
    points.add(new java.awt.Point(0,0));
    points.add(new java.awt.Point(0,300));
    points.add(new java.awt.Point(200,0));
    points.add(new java.awt.Point(200,300));
    points.add(new java.awt.Point(50,100));
    points.add(new java.awt.Point(100,100));
    points.add(new java.awt.Point(100,0));
    properties.add(new Property(Property.POINT_ARRAY, Property.NO_FIELD, false, LOCATIONS_NAME, points));
    java.util.Vector<java.awt.Dimension> dimensions = new java.util.Vector<java.awt.Dimension>();
    dimensions.add(new java.awt.Dimension(800,600));
    dimensions.add(new java.awt.Dimension(200,250));
    dimensions.add(new java.awt.Dimension(200,230));
    dimensions.add(new java.awt.Dimension(310,350));
    dimensions.add(new java.awt.Dimension(330,300));
    dimensions.add(new java.awt.Dimension(300,400));
    dimensions.add(new java.awt.Dimension(300,300));
    dimensions.add(new java.awt.Dimension(200,200));
    properties.add(new Property(Property.DIMENSION_ARRAY, Property.NO_FIELD, false, SIZES_NAME, dimensions));
  }
  
  /**
   * Implemented method from <CODE>elv.util.Propertable</CODE>.
   * @throws java.lang.Exception if there is any error with the setting.
   */
  public void setPropertiesDefaultValues() throws java.lang.Exception
  {
    java.util.Vector<java.lang.String> types = new java.util.Vector<java.lang.String>();
    if(elv.util.Util.getActualUser().getType() == elv.util.User.ADMINISTRATOR)
    {
      types = elv.util.Util.vectorize(elv.util.User.USER_TYPES);
    }
    else if(elv.util.Util.getActualUser().getType() == elv.util.User.SINGLE)
    {
      types.add(elv.util.User.USER_TYPES[elv.util.User.SINGLE]);
    }
    else
    {
      types.add(elv.util.User.USER_TYPES[elv.util.User.COMMUNITY]);
      types.add(elv.util.User.USER_TYPES[elv.util.User.SINGLE]);
    }
    Property.get(TYPE_NAME, properties).setDefaultValues(types);
    Property.get(LOCALE_NAME, properties).setDefaultValues(Util.getAvailableDictionaries());
  }
  
  /**
   * Method for getting the folder variable.
   * @return the folder of this user.
   * @throws java.lang.Exception if there is any error with getting.
   */
  public java.lang.String getFolderPath() throws java.lang.Exception
  {
    return Util.getWorkFolder() + Util.getFS() + name;
  }
  
  /**
   * Method for creating the user folder.
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
   * Method for moving the user and its folder structure.
   * @param newUser the new user.
   * @throws java.lang.Exception if there is any renaming error.
   */
  public synchronized void move(User newUser) throws java.lang.Exception
  {
    boolean done = new java.io.File(getFolderPath()).renameTo(new java.io.File(newUser.getFolderPath()));
    if(!done)
    {
      throw new java.io.InvalidObjectException(newUser.getFolderPath());
    }
    name = newUser.getName();
  }
  
  /**
   * Method for deleting the user folder structure.
   * @throws java.lang.Exception if there is any deleting error.
   */
  public synchronized void delete() throws java.lang.Exception
  {
    Util.delete(getFolderPath());
  }
  
  /**
   * Method for copying the user folder structure to a new user.
   * @param newUser the new user.
   * @throws java.lang.Exception if there is any copying error.
   */
  public synchronized void copy(User newUser) throws java.lang.Exception
  {
    java.lang.String newName = Util.getNumbered(newUser.getName(), Util.vectorize(new java.io.File(Util.getWorkFolder()).list()), null);
    java.lang.String newFolderPath = Util.getWorkFolder() + Util.getFS() + newName;
    Util.copy(getFolderPath(), newFolderPath);
  }
  
  /**
   * Method for getting a list of related users.
   * @param users the vector of users.
   * @return the related users.
   * @throws java.lang.Exception if there is any problem with the relationship.
   */
  public <U extends Parentable> java.util.Vector<U> getRelatives(java.util.Vector<U> users) throws java.lang.Exception
  {
    java.util.Vector<U> relatives = new java.util.Vector<U>();
    if(type == ADMINISTRATOR)
    {
      relatives = users;
    }
    else if(type == SINGLE)
    {
      relatives.add((U)this);
    }
    else
    {
      for(Parentable iteratorParentable : users)
      {
        User iteratorUser = (User)iteratorParentable;
        if(iteratorUser.getType() == type)
        {
          relatives.add((U)iteratorParentable);
        }
      }
    }
    return relatives;
  }
  
  /**
   * Method for getting the icon of the user.
   * @return the icon of user.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/user.gif"));
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param user an <CODE>elv.util.User</CODE> object.
   * @return true if the given user equals with this user.
   */
  public boolean equals(java.lang.Object user)
  {
    boolean isEqual = false;
    if(user != null && user instanceof User)
    {
      isEqual = name.equals(((User)user).name);
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   */
  public java.lang.String toString()
  {
    return name;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return a clone of this archive.
   */
  public java.lang.Object clone()
  {
    User cloneUser = null;
    try
    {
      cloneUser = new User(name, type);
      cloneUser.setProperties(elv.util.Property.clone(properties));
    }
    catch (java.lang.Exception exc)
    {
      exc.printStackTrace();
    }
    return cloneUser;
  }
  
  /**
   * Method for getting the named user.
   * @param name the name of user.
   * @param users the list of users.
   * @return the named user object.
   * @throws java.lang.Exception if there is any error with getting.
   */
  public static User get(java.lang.String name, java.util.Vector<User> users) throws java.lang.Exception
  {
    User user = null;
    for(User iteratorUser : users)
    {
      if(iteratorUser.getName().equals(name))
      {
        user = iteratorUser;
        break;
      }
    }
    return user;
  }
  
  /**
   * Method for getting the users.
   * @return a vector of users.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public synchronized static java.util.Vector<User> getAllUsers() throws java.lang.Exception
  {
    java.util.Vector<User> users = new java.util.Vector<User>();
    java.lang.String folder = elv.util.Util.getWorkFolder();
    java.lang.String[] files = new java.io.File(folder).list();
    for(int i = 0; i < files.length; i++)
    {
      users.add(parse(files[i]));
    }
    return users;
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
      elv.task.Container child = null;
      java.io.File file = new java.io.File(getFolderPath() + Util.getFS() + files[i]);
      java.lang.String base = Util.getFileBase(file.getName());
      java.lang.String extension = Util.getFileExtension(file.getName());
      if(file.isDirectory()) // Container.
      {
        child = new elv.task.Container(this, files[i]);
        children.add((P)child);
      }
      else if(file.isFile() && extension.equals(elv.task.Archive.EXTENSION)) // Archive.
      {
        child = new elv.task.Archive(this, base);
        children.add((P)child);
      }
    }
  }
  
}