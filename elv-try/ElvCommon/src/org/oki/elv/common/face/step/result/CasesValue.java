/**
 * CasesValue.java
 */

package org.oki.elv.common.face.step.result;

import org.oki.elv.common.io.Files;

/**
 * Value object: cases.
 * @author Elv
 */
public final class CasesValue extends PopulationValue{
  /** The total cases. */
  private int totalCases;
  /** The analysed cases. */
  private int analysedCases;

  /** Constructor. */
  public CasesValue() {
  }

  /**
   * Getter of the totalCases.
   * @return the totalCases.
   */
  public final int getTotalCases() {
    return totalCases;
  }

  /**
   * Setter of the totalCases.
   * @param totalCases the totalCases to set.
   */
  public final void setTotalCases(int totalCases) {
    this.totalCases = totalCases;
  }

  /**
   * Getter of the analysedCases.
   * @return the analysedCases.
   */
  public final int getAnalysedCases() {
    return analysedCases;
  }

  /**
   * Setter of the analysedCases.
   * @param analysedCases the analysedCases to set.
   */
  public final void setAnalysedCases(int analysedCases) {
    this.analysedCases = analysedCases;
  }

  @Override
  public String toString() {
    return super.toString() + Files.CSV_SEP + totalCases + Files.CSV_SEP + analysedCases;
  }
}
    