/*
 * AgeInterval.java
 */
package elv.util.parameters;

/**
 * Class for reprazenting an interval of ages.
 * @author Elv
 */
public class AgeInterval extends Interval
{
  
  /**
   * Constants.
   */
  private final static java.lang.String FILE = "age-intervals.param";
  private final static java.lang.String ICON_FILE = "intervals.gif";
  public final static java.lang.String TITLE = "Interval.Ages";
  public final static java.lang.String I_INTERVAL = "Interval.Ages.I";
  public final static java.lang.String XX_INTERVALS = "Interval.Ages.XX";
  public final static java.lang.String C_INTERVALS = "Interval.Ages.C";
  private final static int MINIMUM = 0;
  private final static int MAXIMUM = 100;
  
  /**
   * Constructor.
   */
  public AgeInterval()
  {
  }
  
  /**
   * Constructor.
   * @param from the begining of interval.
   * @param to the end of interval.
   */
  public AgeInterval(int from, int to)
  {
    super(from, to);
  }
    
  /**
   * Implemented method from <CODE>elv.util.parameters.Interval</CODE>.
   * @return the minimum value of this interval.
   */
  public int getMinimum()
  {
    return MINIMUM;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Interval</CODE>.
   * @return the maximum value of this interval.
   */
  public int getMaximum()
  {
    return MAXIMUM;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public <P extends Parameter> P parse(java.lang.String line) throws java.lang.Exception
  {
    //Line:  from-to
    java.lang.String[] splits = line.split(java.util.regex.Pattern.quote("-"));
    return (P)new AgeInterval(java.lang.Integer.parseInt(splits[0]), java.lang.Integer.parseInt(splits[1]));
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return a parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public <P extends Parameter> P getDefault() throws java.lang.Exception
  {
    return (P)new AgeInterval(MINIMUM, MAXIMUM);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Interval</CODE>.
   * @param from the begining of interval.
   * @param to the end of interval.
   */
  public <P extends Interval> P parseInit(int from, int to) throws java.lang.Exception
  {
    return (P)new AgeInterval(from, to);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the file name of parameter.
   */
  public java.lang.String getFile()
  {
    return FILE;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the icon of parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public  javax.swing.ImageIcon getIcon() throws java.lang.Exception
  {
    return new javax.swing.ImageIcon(new elv.util.Util().getClass().getResource("/resources/images/" + ICON_FILE));
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return the title of parameter.
   */
  public java.lang.String getTitle()
  {
    return elv.util.Util.translate(TITLE);
  }
  
}
