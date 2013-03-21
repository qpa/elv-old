/*
 * DistrictWindowIntervalResult.java
 */

package elv.task.executables;

/**
 * Class for district results.
 * @author Elv
 */
public class DistrictWindowIntervalResult implements java.io.Serializable
{
  
  /**
   * Variables.
   */
  protected int yearWindow;
  protected elv.util.parameters.Interval yearInterval;
  protected int population;
  protected int observedCases;
  protected double expectedCases;
  protected double incidence;
  protected double smr;
  protected int smrSignificance;
  protected int smrCategory;
  protected double probability;
  protected int probabilityCategory;
  protected double smoothSmr = 0;
  protected int smoothSmrCategory = 0;
  
  public DistrictWindowIntervalResult(int yearWindow, elv.util.parameters.Interval yearInterval, int population, int observedCases, double expectedCases, double incidence, double smr, int smrSignificance, int smrCategory, double probability, int probabilityCategory)
  {
    this.yearWindow = yearWindow;
    this.yearInterval = yearInterval;
    this.population = population;
    this.observedCases = observedCases;
    this.expectedCases = expectedCases;
    this.incidence = incidence;
    this.smr = smr;
    this.smrSignificance = smrSignificance;
    this.smrCategory = smrCategory;
    this.probability = probability;
    this.probabilityCategory = probabilityCategory;
  }
  
}
