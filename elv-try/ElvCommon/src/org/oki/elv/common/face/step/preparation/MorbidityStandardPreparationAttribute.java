/**
 * MorbidityStandardPreparationAttribute.java
 */
package org.oki.elv.common.face.step.preparation;

import org.oki.elv.common.face.step.Attribute;
import org.oki.elv.common.face.step.GENDER;
import org.oki.elv.common.face.step.Year;

/**
 * Preparation attribute. 
 * @author Elv
 */
public class MorbidityStandardPreparationAttribute implements Attribute {
  /** File name. */
  private static final String ATTRIBUTE_FILE = "preparation.xml";
  
  /** Gender. */
  private GENDER gender;
  /** Selection. */
  private SELECTION selection;
  /** Benchmark year. */
  private Year benchmarkYear;
  
  /** Contructor. */
  public MorbidityStandardPreparationAttribute() {
    setDefaultValues();
  }

  @Override
  public String getFileName() {
    return ATTRIBUTE_FILE;
  }

  @Override
  public void setDefaultValues() {
    gender = GENDER.ALL;
    selection = SELECTION.NOT_DISTINCT;
    benchmarkYear = null;
  }

  /**
   * Getter of the gender.
   * @return the gender.
   */
  public final GENDER getGender() {
    return gender;
  }

  /**
   * Setter of the gender.
   * @param gender the gender to set.
   */
  public final void setGender(GENDER gender) {
    this.gender = gender;
  }

  /**
   * Getter of the selection.
   * @return the selection.
   */
  public final SELECTION getSelection() {
    return selection;
  }

  /**
   * Setter of the selection.
   * @param selection the selection to set.
   */
  public final void setSelection(SELECTION selection) {
    this.selection = selection;
  }

  /**
   * Getter of the benchmarkYear.
   * @return the benchmarkYear.
   */
  public final Year getBenchmarkYear() {
    return benchmarkYear;
  }

  /**
   * Setter of the benchmarkYear.
   * @param benchmarkYear the benchmarkYear to set.
   */
  public final void setBenchmarkYear(Year benchmarkYear) {
    this.benchmarkYear = benchmarkYear;
  }
}
