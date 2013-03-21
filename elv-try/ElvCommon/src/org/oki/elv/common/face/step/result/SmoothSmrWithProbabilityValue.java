/**
 * SmoothSmrWithProbabilityValue.java
 */

package org.oki.elv.common.face.step.result;

import org.oki.elv.common.io.Files;

/**
 * Value object: smooth SMR with probability.
 * @author Elv
 */
public class SmoothSmrWithProbabilityValue extends SmrWithProbabilityValue{
  /** The smooth SMR. */
  private double smoothSmr = 0;
  /** The category of smooth SMR. */
  private int smoothSmrCategory = 0;
  
  /** Contructor. */
  public SmoothSmrWithProbabilityValue() {
  }

  /**
   * Getter of the smoothSmr.
   * @return the smoothSmr.
   */
  public final double getSmoothSmr() {
    return smoothSmr;
  }

  /**
   * Setter of the smoothSmr.
   * @param smoothSmr the smoothSmr to set.
   */
  public final void setSmoothSmr(double smoothSmr) {
    this.smoothSmr = smoothSmr;
  }

  /**
   * Getter of the smoothSmrCategory.
   * @return the smoothSmrCategory.
   */
  public final int getSmoothSmrCategory() {
    return smoothSmrCategory;
  }

  /**
   * Setter of the smoothSmrCategory.
   * @param smoothSmrCategory the smoothSmrCategory to set.
   */
  public final void setSmoothSmrCategory(int smoothSmrCategory) {
    this.smoothSmrCategory = smoothSmrCategory;
  }

  @Override
  public String toString() {
    return super.toString() + Files.CSV_SEP + smoothSmr + Files.CSV_SEP + smoothSmrCategory;
  }
}
