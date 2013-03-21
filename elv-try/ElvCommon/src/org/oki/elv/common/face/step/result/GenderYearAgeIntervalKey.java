/**
 * GenderYearAgeIntervalKey.java
 */
package org.oki.elv.common.face.step.result;

import org.oki.elv.common.face.step.GENDER;
import org.oki.elv.common.io.Files;

/**
 * Key object: gender and age-interval in a year.
 * @author Elv
 */
public class GenderYearAgeIntervalKey extends YearAgeIntervalKey {
  /** The gender. */
  private GENDER gender;

  /** Contructor. */
  public GenderYearAgeIntervalKey() {
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

  @Override
  public String toString() {
    return super.toString() + Files.CSV_SEP + gender;
  }
}
