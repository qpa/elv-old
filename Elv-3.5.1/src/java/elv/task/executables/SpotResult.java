/*
 * SpotResult.java
 */

package elv.task.executables;

/**
 * Class for spot result.
 * @author Qpa
 */
public class SpotResult implements java.io.Serializable
{

  /**
   * Constant.
   */
  public final static int RANK_COUNT = 4;
  
  /**
   * Variables.
   */
  protected boolean isSelected;
  protected boolean isFavorable;
  protected boolean isSuper = false;
  protected java.util.Vector<elv.util.parameters.District> districts;
  protected double meanSMR = 0;
  protected int population = 0;
  protected int observedCases = 0;
  protected double expectedCases = 0;
  protected double incidence = 0;
  protected double smr = 0;
  protected double smrSignificance = 0;
  protected java.lang.String smrProbability = "";
  protected double trend = 0;
  protected double trendConstant = 0;
  protected java.lang.String trendSignificance = "";
  protected double trendCorrelation = 0;
  protected int trendCategory = 0;

  /**
   * Constructor.
   * @param isSelected true, if this spot is not irrelevant.
   * @param isFavorable true, if this spot is favorable .
   * @param districts the districts in this spot.
   */
  public SpotResult(boolean isSelected, boolean isFavorable, java.util.Vector<elv.util.parameters.District> districts)
  {
    this.isSelected = isSelected;
    this.isFavorable = isFavorable;
    this.districts = districts;
  }
  
}
