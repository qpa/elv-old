/**
 * CategoryByBenchmarks.java
 */
package org.oki.elv.common.face.step.category;

import java.util.ArrayList;
import java.util.List;

/**
 * Category by fixed benchmarks for probabilities.
 * @author Elv
 */
public final class CategoryByBenchmarksForProbability extends CategoryByBenchmarks {
  /** Fixed benchmarks. */
  private static List<Double> BENCHMARKS = new ArrayList<Double>();
  
  /** Initializator. */
  static {
    BENCHMARKS.add(0.01);
    BENCHMARKS.add(0.05);
    BENCHMARKS.add(0.1);
    BENCHMARKS.add(0.2);
  }
  
  /** Contructor. */
  public CategoryByBenchmarksForProbability() {
    benchmarks = BENCHMARKS;
  }

  /**
   * Overridden to do nothing.
   */
  public final void setBenchmarks(List<Double> benchmarks) {
    throw new UnsupportedOperationException();
  }
}
