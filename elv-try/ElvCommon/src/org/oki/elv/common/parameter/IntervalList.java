package org.oki.elv.common.parameter;

import java.util.ArrayList;

/**
 * A list of intervals. 
 * @author Elv
 */
public final class IntervalList extends ArrayList<Interval> {
  /** Interval type. */
  public static enum TYPE {
    /** Interval type. */
    YEAR("year.par", 0, 100),
    /** Interval type. */
    AGE("age.par", 1986, 2010);
    
    /** File name. */
    public final String fileName;
    /** Type min value. */
    public final int min;
    /** Type max value. */
    public final int max;
    
    /**
     * Contructor.
     * @param fileName type name.
     */
    private TYPE(String fileName, int min, int max) {
      this.fileName = fileName;
      this.min = min;
      this.max = max;
    }
    
    @Override
    public String toString() {
      return fileName;
    }

    /**
     * Creates a <code>TYPE</code> from the given string.
     * @param fileName the file name of a type.
     * @return the converted type.
     * @see toString
     */
    public static TYPE fromString(String fileName) {
      for(TYPE iteratorT : TYPE.values()) {
        if(iteratorT.fileName.equals(fileName)) {
          return iteratorT;
        }
      }
      throw new IllegalArgumentException("Not a valid type file name: \"" + fileName + "\"");
    }
  }
 
  /** Singleton istance of the default year-interval list. */
  public static final IntervalList DEFAULT_YEAR_INTERVAL_LIST = new IntervalList(TYPE.YEAR);
  
  /** Singleton istance of the default age-interval list. */
  public static final IntervalList DEFAULT_AGE_INTERVAL_LIST = new IntervalList(TYPE.AGE);
  
  /** Singleton istance of the five-yearly age-interval list. */
  public static final IntervalList FIVE_YEARLY_AGE_INTERVAL_LIST = new IntervalList(TYPE.AGE);
  
  /** Singleton istance of the yearly age-interval list. */
  public static final IntervalList YEARLY_AGE_INTERVAL_LIST = new IntervalList(TYPE.AGE);
  
  /**
   * Getter of a year-interval list.
   * @return an empty year-interval list.
   */
  public static final IntervalList getYearIntervalList() {
    return new IntervalList(TYPE.YEAR);
  }
  
  /**
   * Getter of an age-interval list.
   * @return an empty age-interval list.
   */
  public static final IntervalList getAgeIntervalList() {
    return new IntervalList(TYPE.AGE);
  }
  
  /** The type of the interval. */
  private TYPE type;
  
  /**
   * Contructor.
   * @param type
   */
  private IntervalList(TYPE type) {
    super();
    this.type = type;
  }
  
  /**
   * Getter of the type.
   * @return the type.
   */
  public final TYPE getType() {
    return type;
  }

  /**
   * Setter of the type.
   * @param type the type to set.
   */
  public final void setType(TYPE type) {
    this.type = type;
  }
}
