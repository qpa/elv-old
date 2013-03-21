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
public class MorbidityPreparationAttribute implements Attribute {
  /** File name. */
  private static final String ATTRIBUTE_FILE = "preparation.xml";
  
  /** Gender. */
  private GENDER gender;
  /** Resolution. */
  private RESOLUTION resolution;
  /** Selection. */
  private SELECTION selection;
  
  /** Contructor. */
  public MorbidityPreparationAttribute() {
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
    selection = SELECTION.NOT_DISTINCT;
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
}
