/*
 * DistrictResult.java
 */

package elv.task.executables;

/**
 * Class for district results.
 * @author Qpa
 */
public class DistrictResult implements java.io.Serializable
{
  
  /**
   * Variables.
   */
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
  protected double trend = 0;
  protected java.lang.String trendSignificance = "";
  protected double trendCorrelation = 0;
  protected int trendCategory = 0;
  
  public DistrictResult(int population, int observedCases, double expectedCases, double incidence, double smr, int smrSignificance,
    int smrCategory, double probability, int probabilityCategory, double trend, java.lang.String trendSignificance, double trendCorrelation,
    int trendCategory)
  {
    this.population = population;
    this.observedCases = observedCases;
    this.expectedCases = expectedCases;
    this.incidence = incidence;
    this.smr = smr;
    this.smrSignificance = smrSignificance;
    this.smrCategory = smrCategory;
    this.probability = probability;
    this.probabilityCategory = probabilityCategory;
    this.trend = trend;
    this.trendSignificance = trendSignificance;
    this.trendCorrelation = trendCorrelation;
    this.trendCategory = trendCategory;
  }
  
}
