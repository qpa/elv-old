/**
 * PopulationPreparationResult.java
 */
package org.oki.elv.common.face.step.result;

import org.oki.elv.common.face.step.GENDER;
import org.oki.elv.common.face.step.Year;
import org.oki.elv.common.io.Files;
import org.oki.elv.common.parameter.Interval;
import org.oki.elv.common.parameter.Range;

/**
 * Preparation result.
 * @author Elv
 */
public class PopulationPreparationResult {
  /**
   * Creator of a result from the given string.
   * @param string 
   * @return the created result.
   */
  public static PopulationPreparationResult valueOf(String string) {
    String[] fields = string.split(Files.CSV_SEP);
    Year year = Year.fromString(fields[0]);
    GENDER gender = GENDER.valueOf(fields[1]);
    Interval ageInterval = Interval.fromString(fields[2]);
    Range range = Range.fromLine(fields[3]);
    int population = Integer.parseInt(fields[4]);
    PopulationPreparationResult result = new PopulationPreparationResult(year, gender, ageInterval, range);
    result.population = population;
    return result;
  }
  
  /** The year. */
  private Year year;
  /** The gender. */
  private GENDER gender;
  /** The age interval. */
  private Interval ageInterval;
  /** The range. */
  private Range range;
  /** The population count (value). */
  private int population;

  
  /**
   * Contructor.
   * @param year
   * @param gender
   * @param ageInterval
   * @param range
   */
  public PopulationPreparationResult(Year year, GENDER gender, Interval ageInterval, Range range) {
    this.year = year;
    this.gender = gender;
    this.ageInterval = ageInterval;
    this.range = range;
  }

  /**
   * Getter of the year.
   * @return the year.
   */
  public final Year getYear() {
    return year;
  }

  /**
   * Getter of the gender.
   * @return the gender.
   */
  public final GENDER getGender() {
    return gender;
  }

  /**
   * Getter of the ageInterval.
   * @return the ageInterval.
   */
  public final Interval getAgeInterval() {
    return ageInterval;
  }

  /**
   * Getter of the range.
   * @return the range.
   */
  public final Range getRange() {
    return range;
  }

  /**
   * Getter of the population.
   * @return the population.
   */
  public final int getPopulation() {
    return population;
  }
  
  @Override
  public String toString() {
    return year + Files.CSV_SEP + gender + Files.CSV_SEP + ageInterval + Files.CSV_SEP;
//      iteratorDistrict.getCode() + Files.CSV_SEP + iteratorDistrict.getName() + Files.CSV_SEP + population;
  }
}
