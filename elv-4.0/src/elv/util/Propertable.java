/*
 * Propertable.java
 */
package elv.util;

/**
 * Class for properties.
 * @author Elv
 */
public abstract class Propertable extends elv.util.Changeable
{
  
  //Variable.
  /** The vector of properties. */
  protected java.util.Vector<elv.util.Property> properties = new java.util.Vector<elv.util.Property>();
  
  /**
   * Method for getting the root of properties.
   * @return the root name of properties.
   */
  public abstract java.lang.String getRootName();
  
  /**
   * Method for getting the properties.
   * @return the properties.
   */
  public java.util.Vector<elv.util.Property> getProperties()
  {
    return properties;
  }
  
  /**
   * Method for setting the properties.
   * @param properties the new properties.
   */
  public void setProperties(java.util.Vector<elv.util.Property> properties)
  {
    this.properties = properties;
  }
  
  /**
   * Method for getting the property file.
   * @return the propety file name.
   */
  public abstract java.lang.String getPropertyFile();
  
  /**
   * Method for setting the default properties.
   * @param properties a vector of properties.
   * @throws java.lang.Exception if there is any error with setting.
   */
  protected abstract void setDefaultProperties() throws java.lang.Exception;
  
  /**
   * Method for setting the default values for the properties.
   * @throws java.lang.Exception if there is any error with setting.
   */
  public abstract void setPropertiesDefaultValues() throws java.lang.Exception;
  
}
