/*
 * Changeable.java
 */
package elv.util;

/**
 * Interface for change support.
 * @author Elv
 */
public abstract class Changeable implements java.io.Serializable
{
  
  // Variable.
  /** The change support variable. */
  protected javax.swing.event.SwingPropertyChangeSupport changeSupport = new javax.swing.event.SwingPropertyChangeSupport(this);
  
  /**
   * Method for getting the property change support.
   * @return the change support object.
   */
  public javax.swing.event.SwingPropertyChangeSupport getChangeSupport()
  {
    return changeSupport;
  }
  
}
