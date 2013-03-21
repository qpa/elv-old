/**
 * StandardizationAttribute.java
 */
package org.oki.elv.common.face.step.standardization;

import org.oki.elv.common.face.step.Attribute;

/**
 * Standardization attribute.
 * @author Elv
 */
public class StandardizationAttribute implements Attribute {
  /** File name. */
  private static final String ATTRIBUTE_FILE = "standardization.xml";
  
  /** Mode. */
  private MODE mode;
  /** Year-windos. */
  private int yearWindows;
  
  /** Contructor. */
  public StandardizationAttribute() {
    setDefaultValues();
  }

  @Override
  public String getFileName() {
    return ATTRIBUTE_FILE;
  }

  @Override
  public void setDefaultValues() {
    mode = MODE.INDIRECT;
  }

  /**
   * Getter of the mode.
   * @return the mode.
   */
  public final MODE getMode() {
    return mode;
  }

  /**
   * Setter of the mode.
   * @param mode the mode to set.
   */
  public final void setMode(MODE mode) {
    this.mode = mode;
  }

  /**
   * Getter of the yearWindows.
   * @return the yearWindows.
   */
  public final int getYearWindows() {
    return yearWindows;
  }

  /**
   * Setter of the yearWindows.
   * @param yearWindows the yearWindows to set.
   */
  public final void setYearWindows(int yearWindows) {
    this.yearWindows = yearWindows;
  }
}
