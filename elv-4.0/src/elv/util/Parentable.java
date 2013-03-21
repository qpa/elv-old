/*
 * Parentable.java
 */

package elv.util;

/**
 * Interface for defining the parentable methods.
 * @author Elv
 */
public interface Parentable<P>
{
  
  /**
   * Method for testing the existence of a parentable.
   * @return true, if the parantable exists.
   * @throws java.lang.Exception if there is any problem with testing.
   */
  public boolean exists() throws java.lang.Exception;
  
  /**
   * Method for getting the children of a parentable.
   * @return a vector of children.
   */
  public java.util.Vector<P> getChildren();
  
  /**
   * Method for setting the children of a parentable.
   * @parem children the vector of children.
   */
  public void setChildren(java.util.Vector<P> children);
  
  /**
   * Method for loading the children of a parentable.
   * @throws java.lang.Exception if there is any problem with loading.
   */
  public void loadChildren() throws java.lang.Exception;
  
}

