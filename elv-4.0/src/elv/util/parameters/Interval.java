/*
 * Interval.java
 */
package elv.util.parameters;

/**
 * Class for reprazenting a generic interval parameter.
 * @author Elv
 */
public abstract class Interval extends Parameter
{
 
  /**
   * Constant.
   */
  public final static java.lang.String TITLE = "Intervals";
  
  /**
   * Variables.
   */
  private int from ;
  private int to;
  
  /**
   * Constructor.
   */
  public Interval()
  {
  }
  
  /**
   * Constructor.
   * @param from the begining of interval.
   * @param to the end of interval.
   */
  public Interval(int from, int to)
  {
    this.from = from;
    this.to = to;
  }
    
  /**
   * Method for getting the "from" value of this interval.
   * @return the "from" value of this interval.
   */
  public int getFromValue()
  {
    return from;
  }
  
  /**
   * Method for setting the "from" value of this interval.
   * @param from the new "from" value.
   */
  public void setFromValue(int from)
  {
    this.from = from;
  }
  
  /**
   * Method for getting the "to" value of this interval.
   * @return the "to" value of this interval.
   */
  public int getToValue()
  {
    return to;
  }
  
  /**
   * Method for setting the "to" value of this interval.
   * @param to the new "to" value.
   */
  public void setToValue(int to)
  {
    this.to = to;
  }
  
  /**
   * Method for testing the inclusion.
   * @return true, if this interval includes the given value.
   */
  public boolean includes(int value)
  {
    return (from <= value && value <= to);
  }
  
  /**
   * Method for getting the minimum value of an interval.
   * @return the minimum value of this interval.
   */
  public abstract int getMinimum();
  
  /**
   * Method for getting the maximum value of an interval.
   * @return the maximum value of this interval.
   */
  public abstract int getMaximum();
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param interval an <CODE>elv.util.proprties.Interval</CODE> object.
   * @return true if the given interval equals with this interval.
   */
  public boolean equals(java.lang.Object interval)
  {
    boolean isEqual = false;
    if(interval != null && interval instanceof Interval)
    {
      isEqual = (from == ((Interval)interval).from && to == ((Interval)interval).to);
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this interval.
   */
  public java.lang.String toString()
  {
    return toLine();
  }
  
  /**
   * Method for initial parsing of interval.
   * @param from the begining of interval.
   * @param to the end of interval.
   */
  public abstract <P extends Interval> P parseInit(int from, int to) throws java.lang.Exception;
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the line reprezentation of this interval.
   */
  public java.lang.String toLine()
  {
    return from + "-" + to;
  }
  
}
