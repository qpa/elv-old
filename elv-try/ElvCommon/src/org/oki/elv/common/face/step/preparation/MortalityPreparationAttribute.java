/**
 * MorbidityPreparationAttribute.java
 */
package org.oki.elv.common.face.step.preparation;

import org.oki.elv.common.face.step.Attribute;
import org.oki.elv.common.face.step.GENDER;

/**
 * Preparation attribute. 
 * @author Elv
 */
public class MortalityPreparationAttribute implements Attribute {
  /** File name. */
  private static final String ATTRIBUTE_FILE = "preparation.xml";
  
  /** Gender. */
  private GENDER gender;
  /** Resolution. */
  private RESOLUTION resolution;
  
  /** Contructor. */
  public MortalityPreparationAttribute() {
    setDefaultValues();
  }

  @Override
  public String getFileName() {
    return ATTRIBUTE_FILE;
  }

  @Override
  public void setDefaultValues() {
    gender = GENDER.ALL;
    resolution = RESOLUTION.YEAR_INTERVALY;
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
   * Getter of the resolution.
   * @return the resolution.
   */
  public final RESOLUTION getResolution() {
    return resolution;
  }

  /**
   * Setter of the resolution.
   * @param resolution the resolution to set.
   */
  public final void setResolution(RESOLUTION resolution) {
    this.resolution = resolution;
  }
}
