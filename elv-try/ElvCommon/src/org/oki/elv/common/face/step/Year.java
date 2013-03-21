package org.oki.elv.common.face.step;

/**
 * Year.
 * @author Elv
 */
public class Year {
  /** The begining of era. */
  public static final int BEGIN = 1900;
  /** The end of era. */
  public static final int END = 2999;
  
  /** The value of year. */
  private int value;

  /**
   * Contructor.
   * @param value
   */
  public Year(int value) {
    if(BEGIN > value || END < value) {
      throw new IllegalArgumentException("Year :" + value);
    }
    this.value = value;
  }

  /**
   * Getter of the value.
   * @return the value.
   */
  public final int getValue() {
    return value;
  }

  /**
   * Setter of the value.
   * @param value the value to set.
   */
  public final void setValue(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return Integer.toString(value);
  }

  /**
   * Creator of a year from the given string.
   * @param string
   * @return the created year.
   * @see toString().
   */
  public static Year fromString(String string) {
    return new Year(Integer.parseInt(string));
  }
}
