package org.oki.elv.common.face.step;

/**
 * Gender.
 * @author Elv
 */
public enum GENDER {
  /** All genders. */
  ALL("0", "All"),
  /** Males. */
  MALES("1", "Males"),
  /** Females. */
  FEMALES("2", "Females");
  
  /** Code. */
  public final String code;
  /** Name. */
  public final String name;
  
  /**
   * Contructor.
   * @param code gender code.
   */
  private GENDER(String code, String name) {
    this.code = code;
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
