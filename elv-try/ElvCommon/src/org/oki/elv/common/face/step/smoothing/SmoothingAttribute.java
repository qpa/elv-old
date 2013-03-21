/**
 * SmoothingAttribute.java
 */
package org.oki.elv.common.face.step.smoothing;

import java.io.Serializable;

import org.oki.elv.common.face.step.Attribute;
import org.oki.elv.common.face.step.category.Category;
import org.oki.elv.common.face.step.category.CategoryBySignificance;

/**
 * Smoothing attribute.
 * @author Elv
 */
public class SmoothingAttribute implements Attribute, Serializable {
  /** Serial ID.*/
  private static final long serialVersionUID = -7587901017898321910L;
  
  /** File name. */
  private static final String ATTRIBUTE_FILE = "smoothing.xml";
  
  /** Mode. */
  private MODE mode;
  /** Category. */
  private Category category;
  /** Iteration count. */
  private int iterationCount;
  /** Partition count. */
  private int partitionCount;
  /** SMR is weighed state. */
  private boolean isSMRWeighed;
  /** Distance weighing value. */
  private double distanceWeighingValue;
  
  @Override
  public String getFileName() {
    return ATTRIBUTE_FILE;
  }
  
  @Override
  public void setDefaultValues() {
    mode = MODE.IRP;
    category = new CategoryBySignificance();
    iterationCount = 50;
    partitionCount = 200;
    isSMRWeighed = true;
    distanceWeighingValue = 2.0;
  }
  
  /**
   * Getter of the mode.
   * @return the mode.
   */
  public final MODE getMode() {
    return mode;
  }
  /**
   * Setter of the mode.
   * @param mode the mode to set.
   */
  public final void setMode(MODE mode) {
    this.mode = mode;
  }
  /**
   * Getter of the category.
   * @return the category.
   */
  public final Category getCategory() {
    return category;
  }
  /**
   * Setter of the category.
   * @param category the category to set.
   */
  public final void setCategory(Category category) {
    this.category = category;
  }
  /**
   * Getter of the iterationCount.
   * @return the iterationCount.
   */
  public final int getIterationCount() {
    return iterationCount;
  }
  /**
   * Setter of the iterationCount.
   * @param iterationCount the iterationCount to set.
   */
  public final void setIterationCount(int iterationCount) {
    this.iterationCount = iterationCount;
  }
  /**
   * Getter of the partitionCount.
   * @return the partitionCount.
   */
  public final int getPartitionCount() {
    return partitionCount;
  }
  /**
   * Setter of the partitionCount.
   * @param partitionCount the partitionCount to set.
   */
  public final void setPartitionCount(int partitionCount) {
    this.partitionCount = partitionCount;
  }
  /**
   * Getter of the isSMRWeighed.
   * @return the isSMRWeighed.
   */
  public final boolean isSMRWeighed() {
    return isSMRWeighed;
  }
  /**
   * Setter of the isSMRWeighed.
   * @param isSMRWeighed the isSMRWeighed to set.
   */
  public final void setSMRWeighed(boolean isSMRWeighed) {
    this.isSMRWeighed = isSMRWeighed;
  }
  /**
   * Getter of the distanceWeighingValue.
   * @return the distanceWeighingValue.
   */
  public final double getDistanceWeighingValue() {
    return distanceWeighingValue;
  }
  /**
   * Setter of the distanceWeighingValue.
   * @param distanceWeighingValue the distanceWeighingValue to set.
   */
  public final void setDistanceWeighingValue(double distanceWeighingValue) {
    this.distanceWeighingValue = distanceWeighingValue;
  }
}
