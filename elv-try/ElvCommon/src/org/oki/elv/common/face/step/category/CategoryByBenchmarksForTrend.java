/**
 * CategoryByBenchmarks.java
 */
package org.oki.elv.common.face.step.category;

import java.util.ArrayList;
import java.util.List;

/**
 * Category by fixed benchmarks for trends.
 * @author Elv
 */
public final class CategoryByBenchmarksForTrend extends CategoryByBenchmarks {
  /** Fixed benchmarks. */
  private static List<Double> BENCHMARKS = new ArrayList<Double>();
  
  /** Initializator. */
  static {
    BENCHMARKS.add(0.25);
    BENCHMARKS.add(0.06);
    BENCHMARKS.add(-0.06);
    BENCHMARKS.add(-0.25);
  }
  
  /** Contructor. */
  public CategoryByBenchmarksForTrend() {
    benchmarks = BENCHMARKS;
  }
  
  /**
   * Overridden to do nothing.
   */
  public final void setBenchmarks(List<Double> benchmarks) {
    throw new UnsupportedOperationException();
  }
}
