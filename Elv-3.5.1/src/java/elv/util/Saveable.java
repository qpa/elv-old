/*
 * Saveable.java
 */
package elv.util;

/**
 * Interface for save methods.
 * @author Qpa
 */
public interface Saveable
{
  
  /**
   * Method for getting the owner object.
   * @return the changeable owner object.
   */
  public elv.util.Changeable getOwner();
  
  /**
   * Method for getting the type object.
   * @return the type object.
   */
  public java.lang.Object getType();
  
  /**
   * Method for saving.
   */
  public void save();
  
  /**
   * Method for getting the changed attribute.
   * @return true if there were changes.
   */
  public boolean isChanged();
  
  /**
   * Method for setting the changed attribute.
   * @param isChanged boolean to set the change state.
   */
  public void setChanged(boolean isChanged);

}
