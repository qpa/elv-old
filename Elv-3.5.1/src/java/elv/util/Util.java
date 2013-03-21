/*
 * Util.java
 */
package elv.util;

/**
 * Class for utility methods.
 * @author Qpa
 */
public class Util
{
  
  // Constants.
  public final static java.lang.String TITLE = "Title";
  public final static java.lang.String NAME = "Name";
  public final static java.lang.String NAME_STATEMENT = "Name.Statement";
  public final static java.lang.String VALUE = "Value";
  public final static java.lang.String OLD_VALUE = "Value.Old";
  public final static java.lang.String OLD_STATEMENT = "Value.Old.Statement";
  public final static java.lang.String NEW_VALUE = "Value.New";
  public final static java.lang.String CONFIRMED_VALUE = "Value.Confirmed";
  public final static java.lang.String CONFIRMED_STATEMENT = "Value.Confirmed.Statement";
  public final static java.lang.String NO_VALUE = "Value.No";
  public final static java.lang.String CLONE = "Clone";
  public final static java.lang.String OPTIONS = "Options";
  public final static java.lang.String STAGES = "Stages";
  public final static java.lang.String CHOOSE = "Choose";
  public final static java.lang.String REQUEST = "Request";
  public final static java.lang.String LOGIN = "Login";
  public final static java.lang.String LOGIN_STATEMENT = "Login.Statement";
  public final static java.lang.String EXECUTIONS = "Executions";
  public final static char PASSWORD_ECHO_CHAR = '\u2022';
  public final static java.lang.String CHANGED_MARK = "*";
  public final static int ROW_HEIGHT = 20;
  
  public final static java.lang.String TITLE_SEPARATOR = ".";
  public final static java.lang.String ZIP_SEPARATOR = "/";
  public final static int BUFFER_SIZE = 2048;
  /** The character set for text file reading and writing. */
  public final static java.lang.String FILE_ENCODING = "UTF-8";

  private final static int MAXIMUM_FRACTION_DIGITS = 3;
  
  // Variables.
  /** The actual user (client side). */
  private static User user;
  /** The main frame (client side). */
  private static elv.gui.Manager mainFrame = null;
  /** The actual locale (client side). */
  private static java.util.Locale locale = new java.util.Locale("en");
  /** The actual collator (client side). */
  private static java.text.Collator collator = java.text.Collator.getInstance(locale);
  
  /** The count of concurrently executed tasks (server side). */
  private static int concurrentCount = 0;
  /** The file separator (server side). */
  private static java.lang.String fileSeparator = null;
  /** The work folder (server side). */
  private static java.lang.String workFolder = null;
  /** The log folder (server side). */
  private static java.lang.String logFolder = null;
  /** The database URL (server side). */
  private static java.lang.String dbURL = null;
  
  /**
   * Class for the default tree cell rendering.
   */
  public static class DefaultTreeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer
  {
    
    /**
     * Overridden method from <CODE>javax.swing.tree.DefaultTreeCellRenderer</CODE>.
     */
    public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree, java.lang.Object treeNode, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
    {
      super.getTreeCellRendererComponent(tree, treeNode, sel, expanded, leaf, row, hasFocus);
      try
      {
        if(leaf)
        {
          setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/leaf.gif")));
        }
        else if(expanded)
        {
          setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/expanded.gif")));
        }
        else
        {
          setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/collapsed.gif")));
        }
        setToolTipText(treeNode.toString());
      }
      catch(java.lang.Exception exc)
      {
        elv.util.Error.showErrorMessage(this, exc);
      }
      return this;
    }
    
  }
  
  /** 
   * Method for initializing static the variables.
   * @param aUser a user object.
   * @param aMainFrame a main frame object.
   * @throws java.lang.Exception if there is any problem with initializing.
   */
  public static void init(User aUser, elv.gui.Manager aMainFrame) throws java.lang.Exception
  {
    user = aUser;
    mainFrame = aMainFrame;
    locale = (java.util.Locale)elv.util.Property.get(elv.util.User.LOCALE_NAME, aUser.getProperties()).getValue();
    collator = java.text.Collator.getInstance(locale);    
//    java.util.Hashtable environment  = elv.util.client.ClientStub.getEnvironment();
//    fileSeparator = (java.lang.String)environment.get("file.separator");
//    workFolder = (java.lang.String)environment.get("folder/work");
//    logFolder = (java.lang.String)environment.get("folder/log");
    
    java.util.Vector<java.lang.String> environmentVariables  = elv.util.client.ClientStub.getEnvironmentVariables();
    concurrentCount = java.lang.Integer.parseInt(environmentVariables.get(0));
    fileSeparator = environmentVariables.get(1);
    workFolder = environmentVariables.get(2);
    logFolder = environmentVariables.get(3);
    dbURL = environmentVariables.get(4);
  }
  
  /**
   * Method for getting the main frame.
   * @return the main frame object.
   */
  public static elv.gui.Manager getMainFrame()
  {
    return mainFrame;
  }
  
  /**
   * Method for getting the actual user.
   * @return the actual user object.
   */
  public static User getActualUser()
  {
    return user;
  }
  
  /**
   * Method for getting the actual locale.
   * @return the actual locale object.
   */
  public static java.util.Locale getActualLocale()
  {
    return locale;
  }
  
  /**
   * Method for setting the actual locale.
   * @param a new locale object.
   */
  public static void setActualLocale(java.util.Locale aLocale)
  {
    locale = aLocale;
  }
  
  /**
   * Method for getting the actual collator.
   * @return the collator locale object.
   */
  public static java.text.Collator getActualCollator()
  {
    return collator;
  }
  
  /**
   * Method for translating a string to the actual language.
   * @param entry the translatable string.
   * @return the translated string.
   */
  public static java.lang.String translate(java.lang.String entry)
  {
    java.lang.String transletedEntry = null;
    try
    {
      transletedEntry = java.util.ResourceBundle.getBundle("resources.dictionary", getActualLocale()).getString(entry);
    }
    catch(java.lang.Exception exc)
    {
      transletedEntry = "Missing entry: <" + entry + ">" + "in <" + getActualLocale().getDisplayName(getActualLocale()) + "> dictionary!";
    }
    return transletedEntry;
  }
  
  /**
   * Method for translating a string to the given language.
   * @param entry the translatable string.
   * @param locale the locale of language.
   * @return the translated string.
   */
  public static java.lang.String translate(java.lang.String entry, java.util.Locale locale)
  {
    java.lang.String transletedEntry = null;
    try
    {
      transletedEntry = java.util.ResourceBundle.getBundle("resources.dictionary", locale).getString(entry);
    }
    catch(java.lang.Exception exc)
    {
      transletedEntry = "Missing entry: <" + entry + ">" + "in <" + locale.getDisplayName(locale) + "> dictionary!";
    }
    return transletedEntry;
  }
  
  /**
   * Method for getting the base part of a file name.
   * @param fileName the file name.
   * @return the base name.
   */
  public static java.lang.String getFileBase(java.lang.String fileName)
  {
    java.lang.String baseName = null;
    if(fileName != null)
    {
      // Get the last path component.
      java.util.StringTokenizer fileTokenizer = new java.util.StringTokenizer(fileName);
      while(fileTokenizer.hasMoreTokens())
      {
        fileName = fileTokenizer.nextToken();
      }
      // Get base name.
      int i = fileName.lastIndexOf('.');
      if(i > 0 && i < fileName.length() - 1)
      {
        baseName = fileName.substring(0, i);
      }
      else
      {
        baseName = fileName;
      }
    }
    return baseName;
  }
  
  /**
   * Method for getting the extension part of a file name.
   * @param fileName the file name.
   * @return the extension.
   */
  public static java.lang.String getFileExtension(java.lang.String fileName)
  {
    java.lang.String extension = "";
    if(fileName != null)
    {
      int i = fileName.lastIndexOf('.');
      if(i > 0 && i < fileName.length() - 1)
      {
        extension = fileName.substring(i + 1).toLowerCase();
      }
    }
    return extension;
  }
  
  /**
   * Method for parsing the string reprezentation of a point.
   * @param string the string reprezentation of a point.
   * @return the created point object.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public static java.awt.Point parsePoint(java.lang.String string) throws java.lang.Exception
  {
    int x = 0;
    int y = 0;
    java.util.StringTokenizer sT = new java.util.StringTokenizer(string, ",");
    x = java.lang.Integer.parseInt(sT.nextToken().trim());
    y = java.lang.Integer.parseInt(sT.nextToken().trim());
    return new java.awt.Point(x, y);
  }
  
  /**
   * Method for parsing the string reprezentation of a dimension.
   * @param string the string reprezentation of a dimension.
   * @return the created dimension object.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public static java.awt.Dimension parseDimension(java.lang.String string) throws java.lang.Exception
  {
    int x = 0;
    int y = 0;
    java.util.StringTokenizer sT = new java.util.StringTokenizer(string, ",");
    x = java.lang.Integer.parseInt(sT.nextToken().trim());
    y = java.lang.Integer.parseInt(sT.nextToken().trim());
    return new java.awt.Dimension(x, y);
  }
  
  /**
   * Method for parsing the string reprezentation of a date.
   * @param string the string reprezentation of a date.
   * @return the created date object.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public static java.util.Date parseDate(java.lang.String string) throws java.lang.Exception
  {
    java.text.DateFormat dateFormat = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.SHORT, new java.util.Locale("en"));
    return dateFormat.parse(string);
  }
  
  /**
   * Method for parsing the string reprezentation of a locale.
   * @param string the string reprezentation of a locale.
   * @return the created locale object.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public static java.util.Locale parseLocale(java.lang.String string) throws java.lang.Exception
  {
    java.util.Locale locale = null;
    java.lang.String[] localeParameters = string.split(java.util.regex.Pattern.quote("_"));
    if(localeParameters.length == 1)
    {
      locale = new java.util.Locale(localeParameters[0]);
    }
    else if(localeParameters.length == 2)
    {
      locale = new java.util.Locale(localeParameters[0], localeParameters[1]);
    }
    else if(localeParameters.length == 3)
    {
      locale = new java.util.Locale(localeParameters[0], localeParameters[1], localeParameters[2]);
    }
    return locale;
  }
  
  /**
   * Method for string reprezentation of double.
   * @param aDouble a double value.
   * @return the string reprezentation of the double.
   */
  public static java.lang.String toString(double aDouble)
  {
    java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(new java.util.Locale("en"));
    numberFormat.setGroupingUsed(false);
    numberFormat.setMaximumFractionDigits(MAXIMUM_FRACTION_DIGITS);
    return numberFormat.format(aDouble);
  }
  
  /**
   * Method for string reprezentation of a point.
   * @param point a point object.
   * @return the string reprezentation of the point.
   */
  public static java.lang.String toString(java.awt.Point point)
  {
    return java.lang.String.valueOf(point.x) + "," + java.lang.String.valueOf(point.y);
  }
  
  /**
   * Method for string reprezentation of a dimension.
   * @param dimension a dimension object.
   * @return the string reprezentation of the dimension.
   */
  public static java.lang.String toString(java.awt.Dimension dimension)
  {
    return java.lang.String.valueOf(dimension.width) + "," + java.lang.String.valueOf(dimension.height);
  }
  
  /**
   * Method for string reprezentation of a date.
   * @param date a date object.
   * @return the string reprezentation of the date.
   */
  public static java.lang.String toString(java.util.Date date)
  {
    java.text.DateFormat dateFormat = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.SHORT, new java.util.Locale("en"));
    return dateFormat.format(date);
  }
  
  /**
   * Method for formatting int, by the actual locale.
   * @param anInt an int value.
   * @return the formatted string reprezentation of the int.
   */
  public static java.lang.String format(int anInt)
  {
    java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(getActualLocale());
    numberFormat.setGroupingUsed(false);
    return numberFormat.format(anInt);
  }
  
  /**
   * Method for formatting int, by the given locale.
   * @param anInt an int value.
   * @param locale the locale of language.
   * @return the formatted string reprezentation of the int.
   */
  public static java.lang.String format(int anInt, java.util.Locale locale)
  {
    java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(locale);
    numberFormat.setGroupingUsed(false);
    return numberFormat.format(anInt);
  }
  
  /**
   * Method for formatting double, by the actual locale.
   * @param aDouble a double value.
   * @return the formatted string reprezentation of the double.
   */
  public static java.lang.String format(double aDouble)
  {
    java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(getActualLocale());
    numberFormat.setGroupingUsed(false);
    numberFormat.setMaximumFractionDigits(MAXIMUM_FRACTION_DIGITS);
    return numberFormat.format(aDouble);
  }
  
  /**
   * Method for formatting double, by the given locale.
   * @param aDouble a double value.
   * @param locale the locale of language.
   * @return the formatted string reprezentation of the double.
   */
  public static java.lang.String format(double aDouble, java.util.Locale locale)
  {
    java.text.NumberFormat numberFormat = java.text.NumberFormat.getInstance(locale);
    numberFormat.setGroupingUsed(false);
    numberFormat.setMaximumFractionDigits(MAXIMUM_FRACTION_DIGITS);
    return numberFormat.format(aDouble);
  }
  
  /**
   * Method for formatting date, by the actual locale.
   * @param date a date value.
   * @return the formatted string reprezentation of the date.
   */
  public static java.lang.String format(java.util.Date date)
  {
    java.text.DateFormat dateFormat = java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.SHORT, getActualLocale());
    return dateFormat.format(date);
  }
  
  /**
   * Method for getting the installed dictonary locales.
   * @return a vector of locales.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public static java.util.Vector<java.util.Locale> getAvailableDictionaries() throws java.lang.Exception
  {
    java.util.Vector<java.util.Locale> dictionaryLocales = new java.util.Vector<java.util.Locale>();
    java.util.Locale defaultLocale = java.util.ResourceBundle.getBundle("resources.dictionary").getLocale();
    java.util.Locale[] locales = java.util.Locale.getAvailableLocales();
    dictionaryLocales.add(defaultLocale);
    for(int i = 1; i < locales.length; i++)
    {
      java.util.ResourceBundle resourceBundle = null;
      try
      {
        resourceBundle = java.util.ResourceBundle.getBundle("resources.dictionary", locales[i]);
      }
      catch(java.lang.Exception exc)
      {
      }
      if(resourceBundle != null && !resourceBundle.getLocale().equals(defaultLocale) && resourceBundle.getLocale().equals(locales[i]))
      {
        dictionaryLocales.add(locales[i]);
      }
    }
    return dictionaryLocales;
  }
  
  /**
   * Method for getting the count of concurrently executed tasks.
   * @return the count of concurrent tasks.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public static int getConcurrentCount() throws java.lang.Exception
  {
    if(concurrentCount != 0)
    {
      return concurrentCount;
    }
    javax.naming.Context initContext = new javax.naming.InitialContext();
    javax.naming.Context envContext  = (javax.naming.Context)initContext.lookup("java:/comp/env");
    return (java.lang.Integer)envContext.lookup("count/concurrent");
  }
  
  /**
   * Method for getting the file separator property.
   * @return the file separator on the system.
   */
  public static java.lang.String getFS()
  {
    if(fileSeparator != null)
    {
      return fileSeparator;
    }
    return java.lang.System.getProperty("file.separator");
  }
  
  /**
   * Method for getting the URL string of the database.
   * @return an URL.
   * @throws java.lang.Exception if there is any problem with URL parsing.
   */
  public static java.lang.String getDatabaseURL() throws java.lang.Exception
  {
    if(dbURL != null)
    {
      return dbURL;
    }
    javax.naming.Context initContext = new javax.naming.InitialContext();
    javax.naming.Context envContext  = (javax.naming.Context)initContext.lookup("java:/comp/env");
    org.apache.tomcat.dbcp.dbcp.BasicDataSource dataSource = (org.apache.tomcat.dbcp.dbcp.BasicDataSource)envContext.lookup("jdbc/db");
    return dataSource.getUrl();
  }
  
  /**
   * Method for getting the working folder.
   * @return the name of the working folder.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public static java.lang.String getWorkFolder() throws java.lang.Exception
  {
    if(workFolder != null)
    {
      return workFolder;
    }
    javax.naming.Context initContext = new javax.naming.InitialContext();
    javax.naming.Context envContext  = (javax.naming.Context)initContext.lookup("java:/comp/env");
    return (java.lang.String)envContext.lookup("folder/work");
  }
  
  /**
   * Method for getting the logging folder.
   * @return the name of the logging folder.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public static java.lang.String getLogFolder() throws java.lang.Exception
  {
    if(logFolder != null)
    {
      return logFolder;
    }
    javax.naming.Context initContext = new javax.naming.InitialContext();
    javax.naming.Context envContext  = (javax.naming.Context)initContext.lookup("java:/comp/env");
    return (java.lang.String)envContext.lookup("folder/log");
  }
  
  /**
   * Method for getting the URL string of the application.
   * @return an URL.
   * @throws java.lang.Exception if there is any problem with URL parsing.
   */
  public static java.lang.String getBaseURL() throws javax.jnlp.UnavailableServiceException
  {
//    javax.jnlp.BasicService basicService = (javax.jnlp.BasicService)javax.jnlp.ServiceManager.lookup("javax.jnlp.BasicService"); 
//    return basicService.getCodeBase().toString();
    return "http://localhost:8084/elv/";
  }
  
  /**
   * Method for getting the URL string of the servlet.
   * @return an URL.
   * @throws java.lang.Exception if there is any problem with URL getting.
   */
  public static java.lang.String getServletURL() throws javax.jnlp.UnavailableServiceException
  {
    java.lang.String servletMapping = "servlet"; // The url-pattern mapped to elv.util.server.Servlet.
    return getBaseURL() + servletMapping;
  }
  
  /**
   * Method for recursive copying of files and folders.
   * @throws java.lang.Exception if there is any error with the copy.
   */
  public static synchronized void copy(java.lang.String fromPath, java.lang.String toPath) throws java.lang.Exception
  {
    java.io.File fromFile = new java.io.File(fromPath);
    java.io.File toFile = new java.io.File(toPath);
    if(fromFile.isDirectory())
    {
      toFile.mkdirs();
      java.lang.String[] paths = fromFile.list();
      for(int i = 0; i < paths.length; i++)
      {
        copy(fromPath + getFS() + paths[i], toPath + getFS() + paths[i]);
      }
    }
    else
    {
      java.io.InputStream inputStream = new java.io.BufferedInputStream(new java.io.FileInputStream(fromPath));
      java.io.OutputStream outputStream = new java.io.BufferedOutputStream(new java.io.FileOutputStream(toPath));
      int count;
      byte[] buffer = new byte[elv.util.Util.BUFFER_SIZE];
      while((count = inputStream.read(buffer, 0,  elv.util.Util.BUFFER_SIZE)) != -1)
      {
        outputStream.write(buffer, 0,  count);
      }
      inputStream.close();
      outputStream.close();
    }
  }
  
  /**
   * Method for recursive deleting of files and folders.
   * @throws java.lang.Exception if there is any error with the deleting.
   */
  public static synchronized void delete(java.lang.String path) throws java.lang.Exception
  {
    boolean done = true;
    java.io.File file = new java.io.File(path);
    java.lang.String[] paths;
    if(file.isDirectory())
    {
      paths = file.list();
      for(int i = 0; i < paths.length; i++)
      {
        delete(path + getFS() + paths[i]);
      }
      done = file.delete();
      if(!done)
      {
        throw new java.io.InvalidObjectException(path);
      }
    }
    else
    {
      done = file.delete();
      if(!done)
      {
        throw new java.io.InvalidObjectException(path);
      }
    }
  }
  
  /**
   * Method for getting a numbered entry name for the given entry between the array of entries.
   * @param entry the entry name.
   * @param entries the already existing entries.
   * @param postfix the postfix string of the entry, the numbering has to be done before the postfix.
   * @return the numbered (if needed) entry name.
   */
  public synchronized static java.lang.String getNumbered(java.lang.String entry, java.util.Vector<java.lang.String> entries, java.lang.String postfix)
  {
    if(entries == null)
    {
      return entry;
    }
    java.lang.String newEntry = entry;
    java.lang.String pureEntry = entry;
    if(postfix == null || postfix.equals(""))
    {
      postfix = "";
    }
    else
    {
      pureEntry = entry.substring(0, entry.lastIndexOf(postfix));
    }
    for(int i = 0; i < entries.size(); i++)
    {
      boolean found = false;
      for(java.lang.String iteratorEntry : entries)
      {
        if(newEntry.equals(iteratorEntry))
        {
          found = true;
          break;
        }
      }
      if(!found)
      {
        break;
      }
      newEntry = pureEntry + "_" + java.lang.Integer.toString(i + 1) + postfix;
    }
    return newEntry;
  }
  
  /**
   * Method for converting the given array into a vector.
   * @param objectArray the array of objects.
   * @return a vector of objects.
   */
  public static <O> java.util.Vector<O> vectorize(O[] objectArray)
  {
    if(objectArray == null)
    {
      return null;
    }
    java.util.Vector<O> objects = new java.util.Vector<O>();
    for(O iteratorObject : objectArray)
    {
      objects.add(iteratorObject);
    }
    return objects;
  }
  
}
