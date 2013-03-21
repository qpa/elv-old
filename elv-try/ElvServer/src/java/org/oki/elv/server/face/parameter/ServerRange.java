/**
 * ServerRange.java
 */
package org.oki.elv.server.face.parameter;

import java.util.ArrayList;
import java.util.List;

import org.oki.elv.common.face.step.result.RangeResult;
import org.oki.elv.common.face.step.result.RangeWindowIntervalResult;
import org.oki.elv.common.face.step.result.RangeYearResult;
import org.oki.elv.common.parameter.Range;
import org.oki.elv.common.parameter.Territory;

/**
 * Server-side range.
 * @author Elv
 */
public final class ServerRange extends Range {
  /** The result. */
  private RangeResult result;
  /** The results per year. */
  private List<RangeYearResult> yearResults = new ArrayList<RangeYearResult>();
  /** The results per year-window and year-interval. */
  private List<RangeWindowIntervalResult> windowIntervalResults = new ArrayList<RangeWindowIntervalResult>();

  /**
   * Constructor.
   * @param code the numeric code of range.
   * @param name the name of range.
   * @param settlementCount the count of settlements of range.
   * @param settlements the settlements of range.
   * @param area the area of range.
   * @param xCoordinate the X-coordinate of range.
   * @param yCoordinate the Y-coordinate of range.
   * @param type the type of range.
   */
  public ServerRange(TYPE type, String code, String name, int settlementCount, List<Territory> settlements, double area, double xCoordinate, double yCoordinate) {
    super(type, code, name, settlementCount, settlements, area, xCoordinate, yCoordinate);
  }

  /**
   * Getter of the result.
   * @return the result.
   */
  public RangeResult getResult() {
    return result;
  }

  /**
   * Setter of the result.
   * @param result the result to set.
   */
  public void setResult(RangeResult result) {
    this.result = result;
  }

  /**
   * Getter of the yearResults.
   * @return the yearResults.
   */
  public List<RangeYearResult> getYearResults() {
    return yearResults;
  }

  /**
   * Setter of the yearResults.
   * @param yearResults the yearResults to set.
   */
  public void setYearResults(List<RangeYearResult> yearResults) {
    this.yearResults = yearResults;
  }

  /**
   * Getter of the windowIntervalResults.
   * @return the windowIntervalResults.
   */
  public List<RangeWindowIntervalResult> getWindowIntervalResults() {
    return windowIntervalResults;
  }

  /**
   * Setter of the windowIntervalResults.
   * @param windowIntervalResults the windowIntervalResults to set.
   */
  public void setWindowIntervalResults(List<RangeWindowIntervalResult> windowIntervalResults) {
    this.windowIntervalResults = windowIntervalResults;
  }
  
  /**
   * Creator of a <code>Range</code> from a string.
   * @param line the line representation of a range.
   * @return the constructed range.
   */
  public static ServerRange fromLine(String line) {
    //Line: type|code|name|settlementCount|area|xCoordinate|yCoordinate
    String[] splits = line.split(BAR);
    TYPE type = TYPE.fromString(splits[0]);
    String code = splits[1];
    String name = splits[2];
    int settlementCount = Integer.parseInt(splits[3]);
    double area = Double.parseDouble(splits[4]);
    double xCoordinate = Double.parseDouble(splits[5]);
    double yCoordinate = Double.parseDouble(splits[6]);
    // The settlements are not saved.
    List<Territory> settlements = new ArrayList<Territory>();
    return new ServerRange(type, code, name, settlementCount, settlements, area, xCoordinate, yCoordinate);
  }
}
