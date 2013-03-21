/**
 * SpotResult.java
 */
package org.oki.elv.common.face.step.result;

import java.util.List;

import org.oki.elv.common.parameter.Range;

/**
 * Spot result.
 * @author Elv
 */
public class SpotResult {

  /** The count of ranks. */
  public static final int RANK_COUNT = 4;
  
  /** Relevant flag.*/
  protected boolean isSelected;
  /** Favorable flag. */
  protected boolean isFavorable;
  /** Super flag (spot of spots). */
  protected boolean isSuper = false;
  /** The ranges of spot. */
  protected List<Range> ranges;
  /** The mean SMR. */
  protected double meanSMR = 0;
  /** The population. */
  protected int population = 0;
  /** The observed cases. */
  protected int observedCases = 0;
  /** The expected cases. */
  protected double expectedCases = 0;
  /** The incidence. */
  protected double incidence = 0;
  /** The SMR. */
  protected double smr = 0;
  /** The significance of SMR. */
  protected double smrSignificance = 0;
  /** The probability of SMR. */
  protected String smrProbability = "";
  /** The trend. */
  protected double trend = 0;
  /** The constant of trend. */
  protected double trendConstant = 0;
  /** The significance of trend. */
  protected java.lang.String trendSignificance = "";
  /** The correlation of trend. */
  protected double trendCorrelation = 0;
  /** The category of trend. */
  protected int trendCategory = 0;

  /**
   * Constructor.
   * @param isSelected true, if this spot is relevant.
   * @param isFavorable true, if this spot is favorable .
   * @param ranges the ranges in this spot.
   */
  public SpotResult(boolean isSelected, boolean isFavorable, List<Range> ranges) {
    this.isSelected = isSelected;
    this.isFavorable = isFavorable;
    this.ranges = ranges;
  }

  /**
   * Getter of the isSelected.
   * @return the isSelected.
   */
  public boolean isSelected() {
    return isSelected;
  }

  /**
   * Setter of the isSelected.
   * @param isSelected the isSelected to set.
   */
  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }

  /**
   * Getter of the isFavorable.
   * @return the isFavorable.
   */
  public boolean isFavorable() {
    return isFavorable;
  }

  /**
   * Setter of the isFavorable.
   * @param isFavorable the isFavorable to set.
   */
  public void setFavorable(boolean isFavorable) {
    this.isFavorable = isFavorable;
  }

  /**
   * Getter of the isSuper.
   * @return the isSuper.
   */
  public boolean isSuper() {
    return isSuper;
  }

  /**
   * Setter of the isSuper.
   * @param isSuper the isSuper to set.
   */
  public void setSuper(boolean isSuper) {
    this.isSuper = isSuper;
  }

  /**
   * Getter of the ranges.
   * @return the ranges.
   */
  public List<Range> getRanges() {
    return ranges;
  }

  /**
   * Setter of the ranges.
   * @param ranges the ranges to set.
   */
  public void setRanges(List<Range> ranges) {
    this.ranges = ranges;
  }

  /**
   * Getter of the meanSMR.
   * @return the meanSMR.
   */
  public double getMeanSMR() {
    return meanSMR;
  }

  /**
   * Setter of the meanSMR.
   * @param meanSMR the meanSMR to set.
   */
  public void setMeanSMR(double meanSMR) {
    this.meanSMR = meanSMR;
  }

  /**
   * Getter of the population.
   * @return the population.
   */
  public int getPopulation() {
    return population;
  }

  /**
   * Setter of the population.
   * @param population the population to set.
   */
  public void setPopulation(int population) {
    this.population = population;
  }

  /**
   * Getter of the observedCases.
   * @return the observedCases.
   */
  public int getObservedCases() {
    return observedCases;
  }

  /**
   * Setter of the observedCases.
   * @param observedCases the observedCases to set.
   */
  public void setObservedCases(int observedCases) {
    this.observedCases = observedCases;
  }

  /**
   * Getter of the expectedCases.
   * @return the expectedCases.
   */
  public double getExpectedCases() {
    return expectedCases;
  }

  /**
   * Setter of the expectedCases.
   * @param expectedCases the expectedCases to set.
   */
  public void setExpectedCases(double expectedCases) {
    this.expectedCases = expectedCases;
  }

  /**
   * Getter of the incidence.
   * @return the incidence.
   */
  public double getIncidence() {
    return incidence;
  }

  /**
   * Setter of the incidence.
   * @param incidence the incidence to set.
   */
  public void setIncidence(double incidence) {
    this.incidence = incidence;
  }

  /**
   * Getter of the smr.
   * @return the smr.
   */
  public double getSmr() {
    return smr;
  }

  /**
   * Setter of the smr.
   * @param smr the smr to set.
   */
  public void setSmr(double smr) {
    this.smr = smr;
  }

  /**
   * Getter of the smrSignificance.
   * @return the smrSignificance.
   */
  public double getSmrSignificance() {
    return smrSignificance;
  }

  /**
   * Setter of the smrSignificance.
   * @param smrSignificance the smrSignificance to set.
   */
  public void setSmrSignificance(double smrSignificance) {
    this.smrSignificance = smrSignificance;
  }

  /**
   * Getter of the smrProbability.
   * @return the smrProbability.
   */
  public String getSmrProbability() {
    return smrProbability;
  }

  /**
   * Setter of the smrProbability.
   * @param smrProbability the smrProbability to set.
   */
  public void setSmrProbability(String smrProbability) {
    this.smrProbability = smrProbability;
  }

  /**
   * Getter of the trend.
   * @return the trend.
   */
  public double getTrend() {
    return trend;
  }

  /**
   * Setter of the trend.
   * @param trend the trend to set.
   */
  public void setTrend(double trend) {
    this.trend = trend;
  }

  /**
   * Getter of the trendConstant.
   * @return the trendConstant.
   */
  public double getTrendConstant() {
    return trendConstant;
  }

  /**
   * Setter of the trendConstant.
   * @param trendConstant the trendConstant to set.
   */
  public void setTrendConstant(double trendConstant) {
    this.trendConstant = trendConstant;
  }

  /**
   * Getter of the trendSignificance.
   * @return the trendSignificance.
   */
  public java.lang.String getTrendSignificance() {
    return trendSignificance;
  }

  /**
   * Setter of the trendSignificance.
   * @param trendSignificance the trendSignificance to set.
   */
  public void setTrendSignificance(java.lang.String trendSignificance) {
    this.trendSignificance = trendSignificance;
  }

  /**
   * Getter of the trendCorrelation.
   * @return the trendCorrelation.
   */
  public double getTrendCorrelation() {
    return trendCorrelation;
  }

  /**
   * Setter of the trendCorrelation.
   * @param trendCorrelation the trendCorrelation to set.
   */
  public void setTrendCorrelation(double trendCorrelation) {
    this.trendCorrelation = trendCorrelation;
  }

  /**
   * Getter of the trendCategory.
   * @return the trendCategory.
   */
  public int getTrendCategory() {
    return trendCategory;
  }

  /**
   * Setter of the trendCategory.
   * @param trendCategory the trendCategory to set.
   */
  public void setTrendCategory(int trendCategory) {
    this.trendCategory = trendCategory;
  }
}
