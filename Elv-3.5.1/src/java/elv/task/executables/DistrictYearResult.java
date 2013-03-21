/*
 * DistrictYearResult.java
 */

package elv.task.executables;

/**
 * Class for district results per year.
 * @author Qpa
 */
public class DistrictYearResult implements java.io.Serializable
{

  /**
   * Variables.
   */
  protected int year;

  protected int population;
  protected int observedCases;
  protected double expectedCases;
  protected double incidence;
  protected double smr;
  protected int smrSignificance;
  protected int smrCategory;
  protected double probability;
  protected int probabilityCategory;

  public DistrictYearResult(int year, int population, int observedCases, double expectedCases, double incidence, double smr, int smrSignificance, int smrCategory, double probability, int probabilityCategory)
  {
    this.year = year;
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

  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param districtYearResult an <CODE>elv.task.executables.DistrictYearResult</CODE> object.
   */
  public boolean equals(java.lang.Object districtYearResult)
  {
    boolean isEqual = false;
    if(districtYearResult != null && districtYearResult instanceof DistrictYearResult)
    {
      isEqual = (year == ((DistrictYearResult) districtYearResult).year);
    }
    return isEqual;
  }

}
