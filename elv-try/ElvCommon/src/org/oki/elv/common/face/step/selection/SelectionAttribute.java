/**
 * SelectionAttribute.java
 */
package org.oki.elv.common.face.step.selection;

import org.oki.elv.common.face.step.Attribute;
import org.oki.elv.common.face.step.GENDER;

/**
 * Selection attribute.
 * @author Elv
 */
public class SelectionAttribute implements Attribute {
  /** File name. */
  private static final String ATTRIBUTE_FILE = "selection.xml";
  
  /** Gender. */
  private GENDER gender;
  
  /** Contructor. */
  public SelectionAttribute() {
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
