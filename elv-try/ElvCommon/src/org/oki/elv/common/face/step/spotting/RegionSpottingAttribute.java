/**
 * RegionSpottingAttribute.java
 */
package org.oki.elv.common.face.step.spotting;

import java.io.Serializable;

import org.oki.elv.common.face.step.Attribute;

/**
 * Spotting attribute.
 * @author Elv
 */
public class RegionSpottingAttribute implements Attribute, Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = -4604796375978352540L;
  
  /** File name. */
  private static final String ATTRIBUTE_FILE = "spotting.xml";
  
  /** The size. */
  private double size;
  /** The lower threshold. */
  private double lowerThreshold;
  /** The upper threshold. */
  private double upperThreshold;
  /** The percentage.*/
  private double percentage;
  
  /** Contructor. */
  public RegionSpottingAttribute() {
  }

  @Override
  public String getFileName() {
    return ATTRIBUTE_FILE;
  }
  
  @Override
  public void setDefaultValues() {
    size = 2.5;
    lowerThreshold = 2.5;
    upperThreshold = 5.5;
    percentage = 2.5;
  }

  /**
   * Getter of the size.
   * @return the size.
   */
  public final double getSize() {
    return size;
  }

  /**
   * Setter of the size.
   * @param size the size to set.
   */
  public final void setSize(double size) {
    this.size = size;
  }

  /**
   * Getter of the lowerThreshold.
   * @return the lowerThreshold.
   */
  public final double getLowerThreshold() {
    return lowerThreshold;
  }

  /**
   * Setter of the lowerThreshold.
   * @param lowerThreshold the lowerThreshold to set.
   */
  public final void setLowerThreshold(double lowerThreshold) {
    this.lowerThreshold = lowerThreshold;
  }

  /**
   * Getter of the upperThreshold.
   * @return the upperThreshold.
   */
  public final double getUpperThreshold() {
    return upperThreshold;
  }

  /**
   * Setter of the upperThreshold.
   * @param upperThreshold the upperThreshold to set.
   */
  public final void setUpperThreshold(double upperThreshold) {
    this.upperThreshold = upperThreshold;
  }

  /**
   * Getter of the percentage.
   * @return the percentage.
   */
  public final double getPercentage() {
    return percentage;
  }

  /**
   * Setter of the percentage.
   * @param percentage the percentage to set.
   */
  public final void setPercentage(double percentage) {
    this.percentage = percentage;
  }
}
