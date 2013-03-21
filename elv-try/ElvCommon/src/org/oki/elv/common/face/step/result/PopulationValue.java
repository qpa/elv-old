/**
 * PopulationValue.java
 */
package org.oki.elv.common.face.step.result;

/**
 * Value object: population.
 * @author Elv
 */
public class PopulationValue {
  /** The population. */
  private int population;

  /** Contructor. */
  public PopulationValue() {
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
  
  @Override
  public String toString() {
    return "" + population;
  }
}
