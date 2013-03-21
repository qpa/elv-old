/**
 * SmrWithProbabilityValue.java
 */

package org.oki.elv.common.face.step.result;

import org.oki.elv.common.io.Files;

/**
 * Value object: SMR with probability.
 * @author Elv
 */
public class SmrWithProbabilityValue extends SmrValue {
  /** The population. */
  private int population;
  /** The significance of SMR. */
  private int smrSignificance;
  /** The category of SMR. */
  private int smrCategory;
  /** The probability. */
  private double probability;
  /** The category of probability. */
  private int probabilityCategory;

  /** Contructor. */
  public SmrWithProbabilityValue() {
  }
  
  /**
   * Getter of the population.
   * @return the population.
   */
  public final int getPopulation() {
    return population;
  }

  /**
   * Setter of the population.
   * @param population the population to set.
   */
  public final void setPopulation(int population) {
    this.population = population;
  }

  /**
   * Getter of the smrSignificance.
   * @return the smrSignificance.
   */
  public final int getSmrSignificance() {
    return smrSignificance;
  }

  /**
   * Setter of the smrSignificance.
   * @param smrSignificance the smrSignificance to set.
   */
  public final void setSmrSignificance(int smrSignificance) {
    this.smrSignificance = smrSignificance;
  }

  /**
   * Getter of the smrCategory.
   * @return the smrCategory.
   */
  public final int getSmrCategory() {
    return smrCategory;
  }

  /**
   * Setter of the smrCategory.
   * @param smrCategory the smrCategory to set.
   */
  public final void setSmrCategory(int smrCategory) {
    this.smrCategory = smrCategory;
  }

  /**
   * Getter of the probability.
   * @return the probability.
   */
  public final double getProbability() {
    return probability;
  }

  /**
   * Setter of the probability.
   * @param probability the probability to set.
   */
  public final void setProbability(double probability) {
    this.probability = probability;
  }

  /**
   * Getter of the probabilityCategory.
   * @return the probabilityCategory.
   */
  public final int getProbabilityCategory() {
    return probabilityCategory;
  }

  /**
   * Setter of the probabilityCategory.
   * @param probabilityCategory the probabilityCategory to set.
   */
  public final void setProbabilityCategory(int probabilityCategory) {
    this.probabilityCategory = probabilityCategory;
  }

  @Override
  public String toString() {
    return population + Files.CSV_SEP + super.toString() + Files.CSV_SEP +
      smrSignificance + Files.CSV_SEP + smrCategory + Files.CSV_SEP +
      probability + Files.CSV_SEP + probabilityCategory;
  }
}
