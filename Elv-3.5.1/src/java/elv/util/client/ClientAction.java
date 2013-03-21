/*
 * ClientAction.java
 */
package elv.util.client;

/**
 * Class for defining client actions.
 * @author Qpa
 */
public class ClientAction implements java.io.Serializable
{
  // Constants.
  /** Action flag. */
  public final static int LOAD_ALL_DESCENDANTS = 0;
  /** Action flag. */
  public final static int LOAD_CHILDREN = 1;
  
  /** Action flag. */
  public final static int CREATE_USER = 2;
  /** Action flag. */
  public final static int MOVE_USER = 3;
  /** Action flag. */
  public final static int DELETE_USER = 4;
  /** Action flag. */
  public final static int COPY_USER = 5;
  
  /** Action flag. */
  public final static int CREATE_CONTAINER = 6;
  /** Action flag. */
  public final static int MOVE_CONTAINER = 7;
  /** Action flag. */
  public final static int DELETE_CONTAINER = 8;
  /** Action flag. */
  public final static int COPY_CONTAINER = 9;
  
  /** Action flag. */
  public final static int CREATE_TASK = 11;
  /** Action flag. */
  public final static int MOVE_TASK = 12;
  /** Action flag. */
  public final static int DELETE_TASK = 13;
  /** Action flag. */
  public final static int COPY_TASK = 14;
  
  /** Action flag. */
  public final static int LOAD_PROPERTIES = 20;
  /** Action flag. */
  public final static int STORE_PROPERTIES = 21;
  /** Action flag. */
  public final static int LOAD_PARAMETERS = 22;
  /** Action flag. */
  public final static int STORE_PARAMETERS = 23;
  
   /** Action flag. */
  public final static int GET_EXECUTED_TASKS = 30;
 /** Action flag. */
  public final static int GET_EXECUTION_PROGRESSES = 31;
  /** Action flag. */
  public final static int GET_EXECUTION_ERROR = 32;
  /** Action flag. */
  public final static int STOP_EXECUTION = 33;
  /** Action flag. */
  public final static int CLEAN_EXECUTION = 34;
  /** Action flag. */
  public final static int REWIND_EXECUTION = 35;
  
  /** Action flag. */
  public final static int GET_DATE = 40;
  /** Action flag. */
  public final static int GET_ENVIRONMENT = 41;
  /** Action flag. */
  public final static int GET_ENVIRONMENT_VARIABLES = 42;
  /** Action flag. */
  public final static int DELETE_FILE = 43;
  
  /** Action flag. */
  public final static int GET_TABLES = 50;
  /** Action flag. */
  public final static int GET_POSSIBLE_YEAR_INTERVALS = 51;
  
  // Variables.
  /** The action type (one of the flags). */
  private int actionType;
  /** The transported object (the parameter of the action).*/
  private java.lang.Object transport;
  
  /**
   * Constructor.
   * @param actionType the type of action (one of the flags).
   * @param transport the transported object of action.
   */
  public ClientAction(int actionType, java.lang.Object transport)
  {
    this.actionType = actionType;
    this.transport = transport;
  }
  
  /**
   * Method for getting the type of the action.
   * @return the type of this action.
   */
  public int getActionType()
  {
    return actionType;
  }
  
  /**
   * Method for getting the transport.
   * @return the transport of this action.
   */
  public java.lang.Object getTransport()
  {
    return transport;
  }
  
}
