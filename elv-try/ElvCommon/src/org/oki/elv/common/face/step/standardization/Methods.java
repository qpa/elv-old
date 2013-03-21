/**
 * Methods.java
 */
package org.oki.elv.common.face.step.standardization;

import java.util.List;

import org.oki.elv.common.face.step.Year;

/**
 * Standardization methods.
 * @author Elv
 */
public final class Methods {
  /**
   * Calculator of Poisson SMR significance.
   * @param observedCases the analysed cases.
   * @param expectedCases the expected cases.
   * @return the significance of standardization.
   */
  public static double signifyPoisson(int observedCases, double expectedCases) {
    return (observedCases == 0 || expectedCases == 0 ? 0 : ((double)observedCases - expectedCases)*((double)observedCases - expectedCases) / expectedCases);
  }
  
  /**
   * Calculator of Pearson SMR significance.
   * @param observedCases the analysed cases.
   * @param totalCases the total cases.
   * @param population the analysed population.
   * @param totalPopulation the total population.
   * @return the significance of standardization.
   */
  protected static double signifyPearson(int observedCases, int totalCases, int population, int totalPopulation) {
    double e1 = ((double)observedCases + population) * totalCases / (totalCases + totalPopulation);
    double e2 = ((double)observedCases + population) * totalPopulation / (totalCases + totalPopulation);
    double e3 = ((double)totalCases - observedCases + totalPopulation - population) *totalCases / (totalCases + totalPopulation);
    double e4 = ((double)totalCases - observedCases + totalPopulation - population) *totalPopulation / (totalCases + totalPopulation);
    
    return ((double)observedCases - e1) * ((double)observedCases - e1) / e1 +
      ((double)population - e2) * ((double)population - e2) / e2 +
      ((double)totalCases - observedCases - e3) * ((double)totalCases - observedCases - e3) / e3 +
      ((double)totalPopulation - population - e4) * ((double)totalPopulation - population - e4) / e4;
   }
  
  /**
   * Calculator of Likelyhood SMR significance.
   * @param observedCases the analysed cases.
   * @param totalCases the total cases.
   * @param population the analysed population.
   * @param totalPopulation the total population.
   * @return the significance of standardization.
   */
  protected static double signifyLikelyhood(int observedCases, int totalCases, int population, int totalPopulation) {
    double e1 = ((double)observedCases + population) * totalCases / (totalCases + totalPopulation);
    double e2 = ((double)observedCases + population) * totalPopulation / (totalCases + totalPopulation);
    double e3 = ((double)totalCases - observedCases + totalPopulation - population) *totalCases / (totalCases + totalPopulation);
    double e4 = ((double)totalCases - observedCases + totalPopulation - population) *totalPopulation / (totalCases + totalPopulation);
    
    return 2 * (observedCases * java.lang.Math.log(observedCases / e1) +
      population * java.lang.Math.log(population / e2) +
      (totalCases - observedCases) * java.lang.Math.log((totalCases - observedCases) / e3) +
      (totalPopulation - population) * java.lang.Math.log((totalPopulation - population) / e4));
    }
  
  /**
   * Calculator of SMR significance.
   * @param observedCases the analysed cases.
   * @param expectedCases the expected cases.
   * @return the significance of standardization.
   */
  protected static int signify(int observedCases, double expectedCases)
  {
    double hi2 = signifyPoisson(observedCases, expectedCases);
    return (hi2 > 3.84 ? 1 : -1);
  }
  
  /**
   * Calculator of probability.
   * @param hi2 the significance of standardization.
   * @param sP a list of standardization probabilities.
   * @return the probability of standardization.
   */
  protected static double computeProbability(double hi2, List<StandardizationProbability> sP) {
    double probability = 0;
    if(hi2 < sP.get(0).getHi2()) { // The firt element is the minimum in hi2s.
      probability = 1;
    }
    else if(hi2 > sP.get(sP.size() - 1).getHi2()) { // The last element is the maximum in hi2s.
      probability = 0;
    }
    else {
      int i = -1;
      for(StandardizationProbability iteratorSP : sP) {
        i++;
        if(hi2 < iteratorSP.getHi2()) {
          break;
        }
      }
      if(i == sP.size() - 1 && hi2 == sP.get(i).getHi2()) {
        probability = 1 - sP.get(i).getProbability();
      }
      else if(hi2 == sP.get(i - 1).getHi2()) {
        probability = 1 - sP.get(i - 1).getProbability();
      }
      else
      {
        probability = 1 - (sP.get(i - 1).getProbability() + (sP.get(i).getProbability() - sP.get(i - 1).getProbability()) /
          (sP.get(i).getHi2() - sP.get(i - 1).getHi2()) * (hi2 - sP.get(i - 1).getHi2()));
      }
    }
    return probability;
  }
  
  /**
   * Calculator of probability.
   * @param hi2 the significance of standardization.
   * @param sP a list of standardization probabilities.
   * @return the probability of standardization.
   */
  protected static java.lang.String computeProbabilityAsString(double hi2,  List<StandardizationProbability> sP) {
    double probability = computeProbability(hi2, sP);
    java.lang.String probabilityString = "= 0";
    if(probability == 1) {
      probabilityString = "= 1";
    }
    else if(probability == 0) {
      probabilityString = "= 0";
    }
    if(probability <= 0.01) {
      probabilityString = "<= 0.01";
    }
    else if(probability <= 0.05) {
      probabilityString = "<= 0.05";
    }
    else if(probability <= 0.1) {
      probabilityString = "> 0.05 <= 0.1";
    }
    else {
      probabilityString = "> 0.1";
    }
    return probabilityString;
  }
  
  /**
   * Calculator of trend significance.
   * @param sQx the year correlation.
   * @param sQy the incidence correlation.
   * @param sP the mixed correlation.
   * @param years a list of years.
   * @param trendSignificances a list of trend significances.
   * @return the significance of standardization.
   */
  protected static String signify(double sQx, double sQy, double sP, List<Year> years, List<TrendSignificance> trendSignificances) {
    java.lang.String significance = "= 0";
    if(years.size() > 2) {  // Otherwise trend significance has no meaning.
      int index = -1;
      for(TrendSignificance tS : trendSignificances) {
        index++;
        if(years.size() - 2 >= tS.getYearCount()) {
          break;
        }
      }
      double a = sQy - sP * sP / sQx;
      double b = a / (years.size() - 2);
      double trend = sP / sQx;
      double T = java.lang.Math.abs(trend / java.lang.Math.sqrt(java.lang.Math.abs(b / sQx)));

      if(trend == 0) {
        significance = "= 0";
      }
      else if(T < trendSignificances.get(index).getTrend0_1()) {
        significance = "> 0.1";
      }
      else if(T >= trendSignificances.get(index).getTrend0_1() && T < trendSignificances.get(index).getTrend0_05()) {
        significance = "> 0.05 <= 0.1";
      }
      else if(T >= trendSignificances.get(index).getTrend0_05() && T < trendSignificances.get(index).getTrend0_01()) {
        significance = "<= 0.05";
      }
      else if(T >= trendSignificances.get(index).getTrend0_01() && T < trendSignificances.get(index).getTrend0_001()) {
        significance = "<= 0.01";
      }
      else {
        significance = "<= 0.001";
      }
    }
    return significance;
  }
}
