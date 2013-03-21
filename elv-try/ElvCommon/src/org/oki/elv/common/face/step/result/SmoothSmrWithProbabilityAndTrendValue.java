/**
 * SmoothSmrWithProbabilityAndTrendValue.java
 */
package org.oki.elv.common.face.step.result;

import org.oki.elv.common.io.Files;

/**
 * Value object: smooth SMR with probability and trend.
 * @author Elv
 */
public class SmoothSmrWithProbabilityAndTrendValue extends SmoothSmrWithProbabilityValue {
  /** The trend. */
  private double trend = 0;
  /** The significance of trend. */
  private String trendSignificance = "";
  /** The correlation of trend. */
  private double trendCorrelation = 0;
  /** The category of trend. */
  private int trendCategory = 0;
  
  /** Costructor. */
  public SmoothSmrWithProbabilityAndTrendValue() {
  }

  /**
   * Getter of the trend.
   * @return the trend.
   */
  public final double getTrend() {
    return trend;
  }

  /**
   * Setter of the trend.
   * @param trend the trend to set.
   */
  public final void setTrend(double trend) {
    this.trend = trend;
  }

  /**
   * Getter of the trendSignificance.
   * @return the trendSignificance.
   */
  public final String getTrendSignificance() {
    return trendSignificance;
  }

  /**
   * Setter of the trendSignificance.
   * @param trendSignificance the trendSignificance to set.
   */
  public final void setTrendSignificance(String trendSignificance) {
    this.trendSignificance = trendSignificance;
  }

  /**
   * Getter of the trendCorrelation.
   * @return the trendCorrelation.
   */
  public final double getTrendCorrelation() {
    return trendCorrelation;
  }

  /**
   * Setter of the trendCorrelation.
   * @param trendCorrelation the trendCorrelation to set.
   */
  public final void setTrendCorrelation(double trendCorrelation) {
    this.trendCorrelation = trendCorrelation;
  }

  /**
   * Getter of the trendCategory.
   * @return the trendCategory.
   */
  public final int getTrendCategory() {
    return trendCategory;
  }

  /**
   * Setter of the trendCategory.
   * @param trendCategory the trendCategory to set.
   */
  public final void setTrendCategory(int trendCategory) {
    this.trendCategory = trendCategory;
  }

  @Override
  public String toString() {
    return super.toString() + Files.CSV_SEP + trend + Files.CSV_SEP +
      trendSignificance + Files.CSV_SEP + trendCorrelation + Files.CSV_SEP + trendCategory;
  }
}
