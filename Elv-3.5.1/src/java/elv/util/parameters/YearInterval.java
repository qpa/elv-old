/*
 * YearInterval.java
 */
package elv.util.parameters;

/**
 * Class for reprazenting an interval of years.
 * @author Qpa
 */
public class YearInterval extends Interval
{
  
  /**
   * Constants.
   */
  private final static java.lang.String FILE = "year-intervals.param";
  private final static java.lang.String ICON_FILE = "intervals.gif";
  public final static java.lang.String TITLE = "Interval.Years";
  public final static java.lang.String YEARS_TITLE = "Years";
  public final static java.lang.String MONTHS_TITLE = "Months";
  public final static java.lang.String DAYS_TITLE = "Days";
  public static int MINIMUM = 1986;
  public static int MAXIMUM = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR) - 1;
  
  /**
   * Variables.
   */
  private int minimum = MINIMUM;
  private int maximum = MAXIMUM;
  
  /**
   * Constructor.
   */
  public YearInterval()
  {
  }
  
  /**
   * Constructor.
   * @param from the begining of interval.
   * @param to the end of interval.
   */
  public YearInterval(int from, int to)
  {
    super(from, to);
  }
    
  /**
   * Method for setting the minimum and the maximum values.
   * @param min the new minimum value.
   * @param max the new maximum value.
   */
  public void setMinMax(int min, int max)
  {
    this.minimum = min;
    this.maximum = max;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Interval</CODE>.
   * @return the minimum value of this interval.
   */
  public int getMinimum()
  {
    return minimum;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Interval</CODE>.
   * @return the maximum value of this interval.
   */
  public int getMaximum()
  {
    return maximum;
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @param line the stored line reprezentation.
   * @throws java.lang.Exception if there is any problem with parsing.
   */
  public <P extends Parameter> P parse(java.lang.String line) throws java.lang.Exception
  {
    //Line:  from-to
    java.util.StringTokenizer sT = new java.util.StringTokenizer(line, "-");
    int from = java.lang.Integer.parseInt(sT.nextToken());
    int to = java.lang.Integer.parseInt(sT.nextToken());
    return (P)new YearInterval(from, to);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Parameter</CODE>.
   * @return a parameter.
   * @throws java.lang.Exception if there is any problem with getting.
   */
  public <P extends Parameter> P getDefault() throws java.lang.Exception
  {
    return (P)new YearInterval(minimum, maximum);
  }
  
  /**
   * Implemented method from <CODE>elv.util.parameters.Interval</CODE>.
   * @param from the begining of interval.
   * @param to the end of interval.
   */
  public <P extends Interval> P parseInit(int from, int to) throws java.lang.Exception
  {
    return (P)new YearInterval(from, to);
  }
  
  /**
   * Method for getting the all existing years.
   * @return a vector of years.
   */
  public static java.util.Vector<java.lang.Integer> getAllYears()
  {
    java.util.Vector<java.lang.Integer> years = new java.util.Vector<java.lang.Integer>();
    years.add(0);
    for(int i = MINIMUM; i <= MAXIMUM; i++)
    {
      years.add(i);
    }
    return years;
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
