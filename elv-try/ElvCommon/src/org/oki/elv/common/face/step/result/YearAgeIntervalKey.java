/**
 * YearAgeIntervalKey.java
 */
package org.oki.elv.common.face.step.result;

import org.oki.elv.common.face.step.Year;
import org.oki.elv.common.io.Files;
import org.oki.elv.common.parameter.Interval;

/**
 * Key object: age-interval in a year.
 * @author Elv
 */
public class YearAgeIntervalKey {
  /** The year. */
  private Year year;
  /** The age interval. */
  private Interval ageInterval;
  
  /** Contructor. */
  public YearAgeIntervalKey() {
  }

  /**
   * Getter of the year.
   * @return the year.
   */
  public final Year getYear() {
    return year;
  }

  /**
   * Setter of the year.
   * @param year the year to set.
   */
  public final void setYear(Year year) {
    this.year = year;
  }

  /**
   * Getter of the ageInterval.
   * @return the ageInterval.
   */
  public final Interval getAgeInterval() {
    return ageInterval;
  }

  /**
   * Setter of the ageInterval.
   * @param ageInterval the ageInterval to set.
   */
  public final void setAgeInterval(Interval ageInterval) {
    this.ageInterval = ageInterval;
  }
  
  @Override
  public String toString() {
    return year + Files.CSV_SEP + ageInterval;
  }
}
