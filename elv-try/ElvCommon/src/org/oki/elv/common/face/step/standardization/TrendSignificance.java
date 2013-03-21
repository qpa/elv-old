/**
 * 
 */
package org.oki.elv.common.face.step.standardization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.oki.elv.common.io.Files;

/**
 * Trend significance.
 * @author Elv
 */
public class TrendSignificance implements Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = 3169398311098765530L;
  
  /** Equal. */
  public static final String EQUAL = "=";
  /** Comma. */
  public static final String COMMA = ",";
  
  /** The year count. */
  private int yearCount;
  /** The first associated value. */
  private double trend0_1;
  /** The second associated value. */
  private double trend0_05;
  /** The third associated value. */
  private double trend0_01;
  /** The forth associated value. */
  private double trend0_001;
  
  /**
   * Constructor.
   * @param line the parsable line.
   */
  public TrendSignificance(String line) {
    String[] splittedLine = line.split(Pattern.quote(EQUAL));
    yearCount = Integer.parseInt(splittedLine[0]);
    String[] trends = splittedLine[1].split(Pattern.quote(COMMA));
    trend0_1 = Double.parseDouble(trends[0]);
    trend0_05 = Double.parseDouble(trends[1]);
    trend0_01 = Double.parseDouble(trends[2]);
    trend0_001 = Double.parseDouble(trends[3]);
  }
  
  /**
   * Getter of all trend significances.
   * @return a list of trend significances.
   * @throws IOException 
   * @throws UnsupportedEncodingException 
   */
  public static List<TrendSignificance> getAllTrendSignificances() throws UnsupportedEncodingException, IOException {
    List<TrendSignificance> trendSignificances = new ArrayList<TrendSignificance>();
    String line;
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(TrendSignificance.class.getResource("resources/trend-significance.properties").openStream(), Files.FILE_ENCODING));
    while((line = fileReader.readLine()) != null) {
      trendSignificances.add(new TrendSignificance(line));
    }
    fileReader.close();
    return trendSignificances;
  }

  /**
   * Getter of the year count.
   * @return the year count.
   */
  public final int getYearCount() {
    return yearCount;
  }

  /**
   * Getter of the trend0_1.
   * @return the trend0_1.
   */
  public final double getTrend0_1() {
    return trend0_1;
  }

  /**
   * Getter of the trend0_05.
   * @return the trend0_05.
   */
  public final double getTrend0_05() {
    return trend0_05;
  }

  /**
   * Getter of the trend0_01.
   * @return the trend0_01.
   */
  public final double getTrend0_01() {
    return trend0_01;
  }

  /**
   * Getter of the trend0_001.
   * @return the trend0_001.
   */
  public final double getTrend0_001() {
    return trend0_001;
  }
}
