/**
 * CategorizingArgument.java
 */
package org.oki.elv.common.face.step.category;

/**
 * Categorizing argument.
 * @author Elv
 */
public final class CategorizingArgument {
  /** Significance. */
  private int significance;
  /** Minimum value. */
  private double minValue;
  /** Maximum value. */
  private double maxValue;
  
  /**
   * Contructor.
   * @param significance
   * @param minValue
   * @param maxValue
   */
  public CategorizingArgument(int significance, double minValue, double maxValue) {
    this.significance = significance;
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  /**
   * Getter of the significance.
   * @return the significance.
   */
  public final int getSignificance() {
    return significance;
  }

  /**
   * Setter of the significance.
   * @param significance the significance to set.
   */
  public final void setSignificance(int significance) {
    this.significance = significance;
  }

  /**
   * Getter of the minValue.
   * @return the minValue.
   */
  public final double getMinValue() {
    return minValue;
  }

  /**
   * Setter of the minValue.
   * @param minValue the minValue to set.
   */
  public final void setMinValue(double minValue) {
    this.minValue = minValue;
  }

  /**
   * Getter of the maxValue.
   * @return the maxValue.
   */
  public final double getMaxValue() {
    return maxValue;
  }

  /**
   * Setter of the maxValue.
   * @param maxValue the maxValue to set.
   */
  public final void setMaxValue(double maxValue) {
    this.maxValue = maxValue;
  }
}
