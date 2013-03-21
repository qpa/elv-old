/*
 * Action.java
 */
package elv.util;

/**
 * Class for reprezenting an action.
 * @author Elv
 */
public class Action implements java.io.Serializable
{
  
  /**
   * Constants.
   */
  private final static java.lang.String[] NAMES =
  {
    "Action.File",
      "Action.File.New", "Action.File.Save", "Action.File.SaveAll", "Action.File.SaveDocuments", "Action.File.Import", "Action.File.Export", "Action.File.Exit",
    "Action.Edit",
      "Action.Edit.Cut", "Action.Edit.Copy", "Action.Edit.Paste", "Action.Edit.Rename", "Action.Edit.Delete",
    "Action.Tools",
      "Action.Tools.Schedule", "Action.Tools.Stop", "Action.Tools.Clean", "Action.Tools.Rewind", "Action.Tools.Executions", "Action.Tools.Tables", elv.util.State.NAMES[elv.util.State.ERROR], "Action.Tools.Refresh",
    "Action.Window",
      "Action.Window.Navigator", "Action.Window.Attributes", "Action.Window.Intervals", "Action.Window.Settlements", "Action.Window.Districts", "Action.Window.Diagnosises", "Action.Window.Progresses", "Action.Window.Documents", "Action.Window.Properties",
    "Action.Help",
      "Action.Help.Contents", "Action.Help.About",
    "Action.Generic",
      "Action.Generic.Add", "Action.Generic.Remove", "Action.Generic.Modify", "Action.Generic.Close", "Action.Generic.Ok", "Action.Generic.Cancel", "Action.Generic.Yes", "Action.Generic.No", "Action.Generic.Aggregate", "Action.Generic.Minimize",
    "Action.Wizard",
      "Action.Wizard.Previous", "Action.Wizard.Next", "Action.Wizard.Finish", "Clone",
    "Action.Navigation",
      "Action.Navigation.First", "Action.Navigation.Previous", "Action.Navigation.Next", "Action.Navigation.Last", "Action.Navigation.Bookmarks", "Action.Navigation.Thumbnails", "Action.Navigation.Plain", "Action.Navigation.Html"
  };
  private final static java.lang.String[] ICONS =
  {
    null,
      "/resources/images/new.gif", "/resources/images/save.gif", "/resources/images/save_all.gif", "/resources/images/empty.gif", "/resources/images/empty.gif", "/resources/images/empty.gif", "/resources/images/empty.gif",
    null,
      "/resources/images/cut.gif", "/resources/images/copy.gif", "/resources/images/paste.gif", "/resources/images/empty.gif", "/resources/images/delete.gif",
    null,
      "/resources/images/schedule.gif", "/resources/images/stop.gif", "/resources/images/clean.gif", "/resources/images/rewind.gif", "/resources/images/executions.gif", "/resources/images/tables.gif", "/resources/images/empty.gif", "/resources/images/refresh.gif",
    null,
      "/resources/images/empty.gif", "/resources/images/attributes.gif", "/resources/images/intervals.gif", "/resources/images/settlements.gif", "/resources/images/districts.gif", "/resources/images/diagnosises.gif", "/resources/images/progresses.gif", "/resources/images/plain.gif", "/resources/images/empty.gif",
    null,
      "/resources/images/empty.gif", "/resources/images/empty.gif",
    null,
      "/resources/images/add.gif", "/resources/images/remove.gif", null, "/resources/images/close.gif", null, null, null, null, "/resources/images/aggregate.gif", "/resources/images/minimize.gif",
    null,
      null, null, null, null,
    "/resources/images/navigation.gif",
      "/resources/images/first.gif", "/resources/images/previous.gif", "/resources/images/next.gif", "/resources/images/last.gif", null, null, "/resources/images/plain.gif", "/resources/images/html.gif"
  };
  
  private final static java.lang.String[] SUFFIXES =
  {
    "",
      "...", "", "", "", "...", "...", "",
    "",
      "", "", "", "", "...",
    "",
      "...", "...", "...", "...", "...", "...", "...", "",
    "",
      "", "", "", "", "", "", "", "","",
    "",
      "", "...",
    "",
      "", "", "", "", "", "", "", "", "", "",
    "",
      " <", " >", "", "",
    "",
      "", "", "", "", "", "", "", ""
  };
  // The indices in the above arrays.
  public final static int FILE = 0;
  public final static int NEW = 1;
  public final static int SAVE = 2;
  public final static int SAVE_ALL = 3;
  public final static int SAVE_DOCUMENTS = 4;
  public final static int IMPORT = 5;
  public final static int EXPORT = 6;
  public final static int EXIT = 7;
  
  public final static int EDIT = 8;
  public final static int CUT = 9;
  public final static int COPY = 10;
  public final static int PASTE = 11;
  public final static int RENAME = 12;
  public final static int DELETE = 13;
  
  public final static int TOOLS = 14;
  public final static int SCHEDULE = 15;
  public final static int STOP = 16;
  public final static int CLEAN = 17;
  public final static int REWIND = 18;
  public final static int EXECUTIONS = 19;
  public final static int TABLES = 20;
  public final static int ERROR = 21;
  public final static int REFRESH = 22;
  
  public final static int WINDOW = 23;
  public final static int NAVIGATOR = 24;
  public final static int ATTRIBUTES = 25;
  public final static int INTERVALS = 26;
  public final static int SETTLEMENTS = 27;
  public final static int DISTRICTS = 28;
  public final static int DIAGNOSISES = 29;
  public final static int PROGRESSES = 30;
  public final static int DOCUMENTS = 31;
  public final static int PROPERTIES = 32;
  
  public final static int HELP = 33;
  public final static int CONTENTS = 34;
  public final static int ABOUT = 35;

  public final static int GENERIC = 36;
  public final static int ADD = 37;
  public final static int REMOVE = 38;
  public final static int MODIFY = 39;
  public final static int CLOSE = 40;
  public final static int OK = 41;
  public final static int CANCEL = 42;
  public final static int YES = 43;
  public final static int NO = 44;
  public final static int AGGREGATE = 45;
  public final static int MINIMIZE = 46;
  
  public final static int WIZARD = 47;
  public final static int PREVIOUS = 48;
  public final static int NEXT = 49;
  public final static int FINISH = 50;
  public final static int CLONE = 51;
  
  public final static int NAVIGATION = 52;
  public final static int FIRST_PAGE = 53;
  public final static int PREVIOUS_PAGE = 54;
  public final static int NEXT_PAGE = 55;
  public final static int LAST_PAGE = 56;
  public final static int BOOKMARKS = 57;
  public final static int THUMBNAILS = 58;
  public final static int PLAIN = 59;
  public final static int HTML = 60;
  
  private final static java.lang.String QUESTION_SUFFIX = ".Question";
  private final static java.lang.String STATEMENT_SUFFIX = ".Statement";
  
  /**
   * Variable.
   */
  private int index = -1;
   
  /**
   * Constructor.
   * @param index the index in the NAMES array.
   */
  public Action(int index)
  {
    if(index < 0 || index >= NAMES.length)
    {
      this.index = 0;
    }
    this.index = index;
  }
  
  /**
   * Method for getting the name of the action.
   * @return the name of this action.
   */
  public java.lang.String getName()
  {
    return NAMES[index];
  }
  
  /**
   * Method for getting the icon of the action.
   * @return the icon of this action.
   */
  public javax.swing.ImageIcon getIcon()
  {
    return new javax.swing.ImageIcon(getClass().getResource(ICONS[index]));
  }
  
  /**
   * Method for getting the all existing actions.
   */
  public static java.util.Vector<Action> getAllActions()
  {
    java.util.Vector<Action> actions = new java.util.Vector<Action>();
    for(int i = 0; i < NAMES.length; i++)
    {
      actions.add(new Action(i));
    }
    return actions;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param action An <CODE>elv.util.Action</CODE> object.
   * @return true, if this action equals with the given action.
   */
  public boolean equals(java.lang.Object action)
  {
    boolean isEqual = false;
    if(action != null && action instanceof Action)
    {
      isEqual = getName().equals(((Action)action).getName());
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this action.
   */
  public java.lang.String toString()
  {
    return elv.util.Util.translate(getName()) + SUFFIXES[index];
  }
  
  /**
   * Method for getting the question string.
   * @return the question of this action.
   */
  public java.lang.String getQuestion()
  {
    return elv.util.Util.translate(getName() + QUESTION_SUFFIX);
  }
  
  /**
   * Method for getting the statement string.
   * @return the statement of this action.
   */
  public java.lang.String getStatement()
  {
    return elv.util.Util.translate(getName() + STATEMENT_SUFFIX);
  }
  
}
