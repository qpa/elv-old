/**
 * ClusterSpottingAttribute.java
 */
package org.oki.elv.common.face.step.spotting;

import java.io.Serializable;

import org.oki.elv.common.face.step.Attribute;

/**
 * Spotting attribute.
 * @author Elv
 */
public class ClusterSpottingAttribute implements Attribute, Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = 4718530551703671950L;
  
  /** File name. */
  private static final String ATTRIBUTE_FILE = "spotting.xml";

  /** The inner radius. */
  private int innerRadius;
  /** The outer radius. */
  private int outerRadius;
  /** The inner radius. */
  private int radiusScale;
  /** The inner radius. */
  private int radiusPerDiagonal;
  
  /** Contructor. */
  public ClusterSpottingAttribute() {
  }

  @Override
  public String getFileName() {
    return ATTRIBUTE_FILE;
  }

  @Override
  public void setDefaultValues() {
    innerRadius = 1000;
    outerRadius = 4000;
    radiusScale = 2000;
    radiusPerDiagonal = 1;
  }

  /**
   * Getter of the innerRadius.
   * @return the innerRadius.
   */
  public final int getInnerRadius() {
    return innerRadius;
  }

  /**
   * Setter of the innerRadius.
   * @param innerRadius the innerRadius to set.
   */
  public final void setInnerRadius(int innerRadius) {
    this.innerRadius = innerRadius;
  }

  /**
   * Getter of the outerRadius.
   * @return the outerRadius.
   */
  public final int getOuterRadius() {
    return outerRadius;
  }

  /**
   * Setter of the outerRadius.
   * @param outerRadius the outerRadius to set.
   */
  public final void setOuterRadius(int outerRadius) {
    this.outerRadius = outerRadius;
  }

  /**
   * Getter of the radiusScale.
   * @return the radiusScale.
   */
  public final int getRadiusScale() {
    return radiusScale;
  }

  /**
   * Setter of the radiusScale.
   * @param radiusScale the radiusScale to set.
   */
  public final void setRadiusScale(int radiusScale) {
    this.radiusScale = radiusScale;
  }

  /**
   * Getter of the radiusPerDiagonal.
   * @return the radiusPerDiagonal.
   */
  public final int getRadiusPerDiagonal() {
    return radiusPerDiagonal;
  }

  /**
   * Setter of the radiusPerDiagonal.
   * @param radiusPerDiagonal the radiusPerDiagonal to set.
   */
  public final void setRadiusPerDiagonal(int radiusPerDiagonal) {
    this.radiusPerDiagonal = radiusPerDiagonal;
  }
}
