/*
 * Aggregation.java
 */
package elv.util.parameters;

/**
 * Class for aggregation reprezentation.
 * @author Qpa
 */
public class Aggregation implements java.io.Serializable
{
  /**
   * Constants.
   */
  private final static java.lang.String[] NAMES = {"Aggregation.No", "Aggregation.Custom", "Aggregation.2000"};
  // The indices in the above array
  public final static int NO = 0;
  public final static int CUSTOM = 1;
  public final static int MM = 2;
// Future aggregations.
// "Aggregation.3000", "Aggregation.4000", "Aggregation.5000"
// Future aggregation indices.
//  public final static int MMM = 2;
//  public final static int MMMM = 3;
//  public final static int MMMMM = 4;
  
  /**
   * Variables.
   */
  private int index = -1;
  
  /**
   * Constructor.
   * @param index the index in the NAMES array.
   */
  public Aggregation(int index)
  {
    if(index < 0 || index >= NAMES.length)
    {
      this.index = 0;
    }
    this.index = index;
  }
  
  /**
   * Constructor.
   * @param name the unique name reprezentation of an aggregation.
   * @throws java.lang.Exception if there is any problem with naming.
   */
  public Aggregation(java.lang.String name) throws java.lang.Exception
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
   * Method for getting the index of the aggregation.
   * @return the index of aggregation.
   */
  protected int getIndex()
  {
    return index;
  }
  
  /**
   * Method for getting the name of the aggregation.
   * @return the name of aggregation.
   */
  public java.lang.String getName()
  {
    return NAMES[index];
  }
  
  /**
   * Method for getting the all existing aggregations.
   * @return a vector of aggregations.
   */
  public static java.util.Vector<Aggregation> getAllAggregations()
  {
    java.util.Vector<Aggregation> aggregations = new java.util.Vector<Aggregation>();
    for(int i = 0; i < NAMES.length; i++)
    {
      aggregations.add(new Aggregation(i));
    }
    return aggregations;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param aggregation An <CODE>elv.util.parameters.Aggregation</CODE> object.
   * @return true, if this aggregation equals with the given aggregation.
   */
  public boolean equals(java.lang.Object aggregation)
  {
    boolean isEqual = false;
    if(aggregation != null && aggregation instanceof Aggregation)
    {
      isEqual = (index == ((Aggregation)aggregation).index);
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this aggregation.
   */
  public java.lang.String toString()
  {
    return elv.util.Util.translate(getName());
  }
  
}
