/**
 * YearWindowIntervalKey.java
 */
package org.oki.elv.common.face.step.result;

import org.oki.elv.common.io.Files;
import org.oki.elv.common.parameter.Interval;

/**
 * Key object: year-interval in a year-window.
 * @author Elv
 */
public class YearWindowIntervalKey {
  /** The year window. */
  private int yearWindow;
  /** The year interval. */
  private Interval yearInterval;
  
  /** Contructor. */
  public YearWindowIntervalKey() {
  }

  /**
   * Getter of the yearWindow.
   * @return the yearWindow.
   */
  public final int getYearWindow() {
    return yearWindow;
  }

  /**
   * Setter of the yearWindow.
   * @param yearWindow the yearWindow to set.
   */
  public final void setYearWindow(int yearWindow) {
    this.yearWindow = yearWindow;
  }

  /**
   * Getter of the yearInterval.
   * @return the yearInterval.
   */
  public final Interval getYearInterval() {
    return yearInterval;
  }

  /**
   * Setter of the yearInterval.
   * @param yearInterval the yearInterval to set.
   */
  public final void setYearInterval(Interval yearInterval) {
    this.yearInterval = yearInterval;
  }

  @Override
  public String toString() {
    return yearWindow + Files.CSV_SEP + yearInterval;
  }
}
