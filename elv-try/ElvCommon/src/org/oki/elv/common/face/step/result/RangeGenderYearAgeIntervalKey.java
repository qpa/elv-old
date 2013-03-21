/**
 * RangeGenderYearAgeIntervalKey.java
 */
package org.oki.elv.common.face.step.result;

import org.oki.elv.common.io.Files;
import org.oki.elv.common.parameter.Range;

/**
 * Key object: range, gender and age-interval in a year.
 * @author Elv
 */
public class RangeGenderYearAgeIntervalKey extends GenderYearAgeIntervalKey {
  /** The header. */
  public static final String HEADER = "Year" + Files.CSV_SEP + 
    "Gender" + Files.CSV_SEP + "Age interval" + Files.CSV_SEP + "Range code" + Files.CSV_SEP + "Range name";
  
  /** The range. */
  private Range range;

  /** Contructor. */
  public RangeGenderYearAgeIntervalKey() {
  }

  /**
   * Getter of the range.
   * @return the range.
   */
  public final Range getRange() {
    return range;
  }

  /**
   * Setter of the range.
   * @param range the range to set.
   */
  public final void setRange(Range range) {
    this.range = range;
  }

  @Override
  public String toString() {
    return super.toString() + Files.CSV_SEP + range.getCode() + Files.CSV_SEP + range.getName();
  }
}
