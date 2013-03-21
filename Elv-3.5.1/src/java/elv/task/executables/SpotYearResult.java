/*
 * SpotYearResult.java
 */

package elv.task.executables;

/**
 * Class for spot results per year.
 * @author Qpa
 */
public class SpotYearResult implements java.io.Serializable
{

  /**
   * Variables.
   */
  protected int year;
  protected int observedCases = 0;
  protected double expectedCases = 0;
  protected double incidence = 0;
  protected double smr = 0;

  /**
   * Constructor.
   * @param year the year of this spot.
   * @param observedCases the observed cases (mortality/morbidity) of this spot.
   * @param expectedCases the expected cases (mortality/morbidity) of this spot.
   * @param incidence the incidence of this spot.
   * @param smr the SMR of this spot.
   */
  public SpotYearResult(int year, int observedCases, double expectedCases, double incidence, double smr)
  {
    this.year = year;
    this.observedCases = observedCases;
    this.expectedCases = expectedCases;
    this.incidence = incidence;
    this.smr = smr;
  }

  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param spotYearResult an <CODE>elv.task.executables.SpotYearResult</CODE> object.
   */
  public boolean equals(java.lang.Object spotYearResult)
  {
    boolean isEqual = false;
    if(spotYearResult != null && spotYearResult instanceof SpotYearResult)
    {
      SpotYearResult sYR = (SpotYearResult)spotYearResult;
      isEqual = (year == sYR.year);
    }
    return isEqual;
  }

}
