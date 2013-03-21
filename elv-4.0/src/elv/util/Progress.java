/*
 * Progress.java
 */

package elv.util;

/**
 * Class for a progress reprezentation.
 * @author Elv
 */
public class Progress implements java.io.Serializable
{
  
  /**
   * Constants.
   */
  private final static java.lang.String NAME = "Progress";
  protected final static java.lang.String PROGRESSES_NAME = "Progresses";
  public final static java.lang.String INITIALIZE = "Progress.Initialize";
  public final static java.lang.String PARSE = "Progress.Parse";
  
  /**
   * Variables.
   */
  private java.lang.String name;
  private int minimum = 0;
  private int value = 0;
  private int maximum = 0;
 
  /**
   * Constructor.
   * @param name the name of the progress.
   * @param minimum the minimum value of the progress.
   * @param value the current value of the progress.
   * @param maximum the maximum value of the progress.
   */
  public Progress(java.lang.String name, int minimum, int maximum)
  {
    this.name = name;
    this.minimum = minimum;
    this.value = minimum;
    this.maximum = maximum;
  }
  
  /**
   * Method for getting the name.
   * @return the name of parsing.
   */
  public java.lang.String getName()
  {
    return name;
  }
  
  /**
   * Method for getting the minimum value.
   * @return the minimum value of parsing.
   */
  public int getMinimum()
  {
    return minimum;
  }
  
  /**
   * Method for getting the current value.
   * @return the actual value of parsing.
   */
  public int getValue()
  {
    return value;
  }
  
  /**
   * Method for setting the current value.
   * @param the new value of parsing.
   */
  public void setValue(int value)
  {
    this.value = value;
  }
  
  /**
   * Method for getting the maximum value.
   * @return the maximum value of parsing.
   */
  public int getMaximum()
  {
    return maximum;
  }
  
  /**
   * Method for getting the visibility.
   * @return true, if there are more than 1 parsed objects.
   */
  public boolean isVisible()
  {
    return (maximum > 1);
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param progress an <CODE>elv.util.Progress</CODE> object.
   */
  public boolean equals(java.lang.Object progress)
  {
    boolean isEqual = false;
    if(progress != null && progress instanceof elv.util.Progress)
    {
      isEqual = (name.equals(((elv.util.Progress)progress).getName()) && maximum == ((elv.util.Progress)progress).getMaximum());
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this progress.
   */
  public java.lang.String toString()
  {
    return elv.util.Util.translate(name);
  }
  
  /**
   * Method for getting the icon.
   * @return the icon of progresses.
   */
  public static javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/progresses.gif"));
  }
  
  /**
   * Method for getting the title.
   * @return the title of progresses.
   */
  public static java.lang.String getTitle()
  {
    return elv.util.Util.translate(NAME);
  }
  
}
