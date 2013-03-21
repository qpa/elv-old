/**
 * SmrValue.java
 */
package org.oki.elv.common.face.step.result;

import org.oki.elv.common.io.Files;

/**
 * Value object: SMR .
 * @author Elv
 */
public class SmrValue {
  /** The observed cases. */
  private int observedCases = 0;
  /** The expected cases. */
  private double expectedCases = 0;
  /** The incidence. */
  private double incidence = 0;
  /** The SMR. */
  private double smr = 0;

  /** Constructor. */
  public SmrValue() {
  }
  
  /**
   * Getter of the observedCases.
   * @return the observedCases.
   */
  public final int getObservedCases() {
    return observedCases;
  }

  /**
   * Setter of the observedCases.
   * @param observedCases the observedCases to set.
   */
  public final void setObservedCases(int observedCases) {
    this.observedCases = observedCases;
  }

  /**
   * Getter of the expectedCases.
   * @return the expectedCases.
   */
  public final double getExpectedCases() {
    return expectedCases;
  }

  /**
   * Setter of the expectedCases.
   * @param expectedCases the expectedCases to set.
   */
  public final void setExpectedCases(double expectedCases) {
    this.expectedCases = expectedCases;
  }

  /**
   * Getter of the incidence.
   * @return the incidence.
   */
  public final double getIncidence() {
    return incidence;
  }

  /**
   * Setter of the incidence.
   * @param incidence the incidence to set.
   */
  public final void setIncidence(double incidence) {
    this.incidence = incidence;
  }

  /**
   * Getter of the smr.
   * @return the smr.
   */
  public final double getSmr() {
    return smr;
  }

  /**
   * Setter of the smr.
   * @param smr the smr to set.
   */
  public final void setSmr(double smr) {
    this.smr = smr;
  }

  @Override
  public String toString() {
    return observedCases + Files.CSV_SEP + expectedCases + Files.CSV_SEP + incidence + Files.CSV_SEP + smr; 
  }
}
