/*
 * State.java
 */
package elv.util;

/**
 * Class for reprezenting a state.
 * @author Qpa
 */
public class State implements java.io.Serializable
{
  
  // Constants.
  /** The title of the class. */
  public final static java.lang.String TITLE = "State";
  /** The array of posible state names. */
  protected final static java.lang.String[] NAMES =
    {"State.Undefined", "State.Defined", "State.Scheduled",  "State.Executed", "State.Stopped", "State.Done", "State.Error"};
  /** The  array of posible state icons. */
  private final static java.lang.String[] ICONS =
    {"/resources/images/state_undefined.gif", "/resources/images/state_defined.gif", "/resources/images/state_scheduled.gif",
     "/resources/images/state_executed.gif", "/resources/images/state_stopped.gif", "/resources/images/state_done.gif",
     "/resources/images/state_error.gif"};
    // The indices in the above arrays.
    /** Index in the <code>NAMES</code> and <code>ICONS</code> arrays. */
    public final static int UNDEFINED = 0;
    /** Index in the <code>NAMES</code> and <code>ICONS</code> arrays. */
    public final static int DEFINED = 1;
    /** Index in the <code>NAMES</code> and <code>ICONS</code> arrays. */
    public final static int SCHEDULED = 2;
    /** Index in the <code>NAMES</code> and <code>ICONS</code> arrays. */
    public final static int EXECUTED = 3;
    /** Index in the <code>NAMES</code> and <code>ICONS</code> arrays. */
    public final static int STOPPED = 4;
    /** Index in the <code>NAMES</code> and <code>ICONS</code> arrays. */
    public final static int DONE = 5;
    /** Index in the <code>NAMES</code> and <code>ICONS</code> arrays. */
    public final static int ERROR = 6;
  
  // Variable.
  /** The index in the <code>NAMES</code> and <code>ICONS</code> arrays. */
  private int index = -1;
  
  /**
   * Constructor.
   * @param index the index in the NAMES array.
   */
  public State(int index)
  {
    if(index < 0 || index >= NAMES.length)
    {
      this.index = 0;
    }
    this.index = index;
  }
  
  /**
   * Constructor.
   * @param name the unique name reprezentation of a state.
   * @throws java.lang.Exception if there is any problem with the parsing.
   */
  public State(java.lang.String name) throws java.lang.Exception
  {
    for(int i = 0; i < NAMES.length; i++)
    {
      if(NAMES[i].equals(name))
      {
        this.index = i;
      }
    }
    if(index < 0)
    {
      throw new java.lang.IllegalArgumentException(name);
    }
  }
  
  /**
   * Method for getting the name of the state.
   * @return the name of this state.
   */
  public java.lang.String getName()
  {
    return NAMES[index];
  }
  
  /**
   * Method for getting the icon of the state.
   * @return the icon of this state.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(getClass().getResource(ICONS[index]));
  }
  
  /**
   * Method for getting the all existing states.
   * @return a vector of states.
   */
  public static java.util.Vector<State> getAllStates()
  {
    java.util.Vector<State> states = new java.util.Vector<State>();
    for(int i = 0; i < NAMES.length; i++)
    {
      states.add(new State(i));
    }
    return states;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param state an <CODE>elv.util.State</CODE> object.
   * @return true, if this state equals with the given state.
   */
  public boolean equals(java.lang.Object state)
  {
    boolean isEqual = false;
    if(state != null && state instanceof State)
    {
      isEqual = (index == ((State)state).index);
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this state.
   */
  public java.lang.String toString()
  {
    return Util.translate(getName());
  }
  
}
