/*
 * SettlementYearResult.java
 */

package elv.task.executables;

/**
 * Class for settlement year result.
 * @author Qpa
 */
public class SettlementYearResult implements java.io.Serializable
{

  /**
   * Variables.
   */
  protected int year;
  protected elv.util.parameters.Interval ageInterval;
  protected int population;
  protected int totalCases;
  protected int analyzedCases;

  /**
   * Constructor.
   * @param year the year of this preparation.
   * @param population the population of this preparation.
   * @param totalCases the total cases (mortality/morbidity) of this preparation.
   * @param analyzedCases the analysed cases (mortality/morbidity) of this preparation.
   */
  public SettlementYearResult(int year, elv.util.parameters.Interval ageInterval, int population, int totalCases, int analyzedCases)
  {
    this.year = year;
    this.ageInterval = ageInterval;
    this.population = population;
    this.totalCases = totalCases;
    this.analyzedCases = analyzedCases;
  }

  /**
   * Overridden method from <CODE>java.lang.Object</CODE>.
   * @param settlementYearResult an <CODE>elv.task.executables.SettlementYearResult</CODE> object.
   */
  public boolean equals(java.lang.Object settlementYearResult)
  {
    boolean isEqual = false;
    if(settlementYearResult != null && settlementYearResult instanceof SettlementYearResult)
    {
      SettlementYearResult sYR = (SettlementYearResult)settlementYearResult;
      isEqual = (year == sYR.year && ageInterval.equals(sYR.ageInterval));
    }
    return isEqual;
  }

}
    