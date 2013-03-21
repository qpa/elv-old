package org.oki.elv.common.parameter;

/**
 * Interval of integers.
 * @author Elv
 */
public class Interval {
  /** Dash. */
  public static final String DASH = "-"; 
  
  /** The "from" value. */
  private int from ;
  /** The "to" value. */
  private int to;
  
  /**
   * Constructor.
   * @param from the begining of the interval.
   * @param to the end of the interval.
   */
  public Interval(int from, int to) {
    if(from > to) {
      throw new IllegalArgumentException("FROM value bigger than TO value: " + from + " >" + to);
    }
    this.from = from;
    this.to = to;
  }
    
  /**
   * Getter of the "from" value.
   * @return the "from" value.
   */
  public int getFromValue() {
    return from;
  }
  
  /**
   * Setter of the "from" value.
   * @param from the "from" value to set.
   */
  public void setFromValue(int from) {
    this.from = from;
  }
  
  /**
   * Getter of the "to" value.
   * @return the "to" value.
   */
  public int getToValue() {
    return to;
  }
  
  /**
   * Getter of the "to" value.
   * @param to the "to" value set.
   */
  public void setToValue(int to) {
    this.to = to;
  }
  
  /**
   * Tester of the inclusion.
   * @param value the test value.
   * @return true, if this interval includes the given value.
   */
  public boolean includes(int value) {
    return (from <= value && value <= to);
  }
  
  @Override
  public String toString() {
    return from + DASH + to;
  }

  /**
   * Creator of an <code>Interval</code> from a string.
   * @param string the string representation of an interval.
   * @return the constructed interval.
   * @see toString
   */
  public static Interval fromString(String string) {
    String[] fields = string.split(DASH);
    return new Interval(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]));
  }
  
  /**
   * Morbidity year-interval.
   * @author elv
   */
  public static class Morbidity extends Interval {

    /**
     * Contructor.
     * @param from
     * @param to
     */
    public Morbidity(int from, int to) {
      super(from, to);
    }
  }
  
  /**
   * Mortality year-interval.
   * @author elv
   */
  public static class Mortality extends Interval {

    /**
     * Contructor.
     * @param from
     * @param to
     */
    public Mortality(int from, int to) {
      super(from, to);
    }
  }
  
  /**
   * Population year-interval.
   * @author elv
   */
  public static class Population extends Interval {

    /**
     * Contructor.
     * @param from
     * @param to
     */
    public Population(int from, int to) {
      super(from, to);
    }
  }
}
