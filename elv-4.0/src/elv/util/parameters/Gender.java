/*
 * Gender.java
 */
package elv.util.parameters;

/**
 * Class for reprezenting a gender.
 * @author Elv
 */
public class Gender implements java.io.Serializable
{
  
  // Constants.
  /** The name. */
  public final static java.lang.String NAME = "Gender";
  /** Array of posible genders. */
  private final static java.lang.String[] NAMES = {"All", "Gender.Males", "Gender.Females"};
  /** Array of posible gender values. */
  private final static java.lang.String[] VALUES = {"0", "1", "2"};
  // The indices in the above arrays.
  /** Index in the <code>NAMES</code> array. */
  private final static int ALL = 0;
  /** Index in the <code>NAMES</code> array. */
  private final static int MALES = 1;
  /** Index in the <code>NAMES</code> array. */
  private final static int FEMALES = 2;
  
  /**
   * Variables.
   */
  protected int index = -1;
  
  /**
   * Constructor.
   * @param index the index in the NAMES array.
   */
  public Gender(int index)
  {
    if(index < 0 || index >= NAMES.length)
    {
      this.index = 0;
    }
    this.index = index;
  }
  
  /**
   * Constructor.
   * @param name the unique name reprezentation of a gender.
   * @throws java.lang.Exception if there is any error with the parsing.
   */
  public Gender(java.lang.String name) throws java.lang.Exception
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
   * Method for getting the name of the gender.
   * @return the name of this gender.
   */
  public java.lang.String getName()
  {
    return NAMES[index];
  }
  
  /**
   * Method for getting the value of the gender.
   * @return the value of this gender.
   */
  public java.lang.String getValue()
  {
    return VALUES[index];
  }
  
  /**
   * Method for getting the all existing genders.
   * @return a vector of genders.
   */
  public static java.util.Vector<Gender> getAllGenders()
  {
    java.util.Vector<Gender> genders = new java.util.Vector<Gender>();
    // ALL is not included in the gender array.
    for(int i = 1; i < NAMES.length; i++)
    {
      genders.add(new Gender(i));
    }
    return genders;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return a parameter.
   */
  public static Gender getDefault()
  {
    return new Gender(ALL);
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param gender an <CODE>elv.util.parameters.Gender</CODE> object.
   * @return true, if this gender equals with the given gender.
   */
  public boolean equals(java.lang.Object gender)
  {
    boolean isEqual = false;
    if(gender != null && gender instanceof Gender)
    {
      isEqual = getName().equals(((Gender)gender).getName());
    }
    return isEqual;
  }
  
  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @return the string reprezentation of this gender.
   */
  public java.lang.String toString()
  {
    java.lang.String string = null;
    if(index == ALL)
    {
      string = getName();
    }
    else
    {
      string = elv.util.Util.translate(getName());
    }
    return string;
  }
  
}
