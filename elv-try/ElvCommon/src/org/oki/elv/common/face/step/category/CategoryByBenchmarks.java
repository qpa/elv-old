/**
 * CategoryByBenchmarks.java
 */
package org.oki.elv.common.face.step.category;

import java.util.List;

/**
 * Category by benchmarks.
 * @author Elv
 */
public class CategoryByBenchmarks implements Category {
  /** The benchmarks. */
  protected List<Double> benchmarks;
  
  /** Contructor. */
  public CategoryByBenchmarks() {
  }
  
  /**
   * Getter of the benchmarks.
   * @return the benchmarks.
   */
  public List<Double> getBenchmarks() {
    return benchmarks;
  }

  /**
   * Setter of the benchmarks.
   * @param benchmarks the benchmarks to set.
   */
  public void setBenchmarks(List<Double> benchmarks) {
    this.benchmarks = benchmarks;
  }

  /**
   * Categorizer by benchmarks, considering the list of <code>benchmarks</code>.
   * @param value the categorizable value.
   * @param argument not considered, can be <code>null</code>.
   * @return the category level.
   */
  @Override
  public int categorize(double value, CategorizingArgument argument) {
    int category;
    for(category = 0; category < benchmarks.size(); category++) {
      if(benchmarks.get(category) > value) {
        break;
      }
    }
    return category + 1;
  }
}
