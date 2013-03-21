/**
 * PopulationPreparationAttribute.java
 */
package org.oki.elv.common.face.step.preparation;

import org.oki.elv.common.face.step.Attribute;
import org.oki.elv.common.face.step.GENDER;

/**
 * Preparation attribute.
 * @author Elv
 */
public class PopulationPreparationAttribute implements Attribute {
  /** File name. */
  private static final String ATTRIBUTE_FILE = "preparation.xml";
  
  /** Gender. */
  private GENDER gender;
  
  /** Contructor. */
  public PopulationPreparationAttribute() {
    setDefaultValues();
  }

  @Override
  public String getFileName() {
    return ATTRIBUTE_FILE;
  }

  @Override
  public void setDefaultValues() {
    gender = GENDER.ALL;
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
}
