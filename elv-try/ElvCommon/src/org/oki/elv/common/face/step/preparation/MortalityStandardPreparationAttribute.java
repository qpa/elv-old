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
public class MortalityStandardPreparationAttribute implements Attribute {
  /** File name. */
  private static final String ATTRIBUTE_FILE = "preparation.xml";
  
  /** Gender. */
  private GENDER gender;
  /** Benchmark year. */
  private Year benchmarkYear;
  
  /** Contructor. */
  public MortalityStandardPreparationAttribute() {
    setDefaultValues();
  }

  @Override
  public String getFileName() {
    return ATTRIBUTE_FILE;
  }

  @Override
  public void setDefaultValues() {
    gender = GENDER.ALL;
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
