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
 * Standardization probability.
 * @author Elv
 */
public class StandardizationProbability implements Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = -7606311920707027725L;
  
  /** Equal. */
  public static final String EQUAL = "=";

  /** The probability. */
  private double probability;
  /** The associated value. */
  private double hi2;
  
  /**
   * Constructor.
   * @param line the parsable line.
   */
  private StandardizationProbability(String line) {
    String[] splitedLine = line.split(Pattern.quote(EQUAL));
    probability = Double.parseDouble(splitedLine[0]);
    hi2 = Double.parseDouble(splitedLine[1]);
  }
  
  /**
   * Getter of all trend significances.
   * @return a list of standardization probabilities.
   * @throws IOException 
   * @throws UnsupportedEncodingException 
   */
  public static List<StandardizationProbability> getAllStandardizationProbabilityies() throws UnsupportedEncodingException, IOException {
    List<StandardizationProbability> standardizationProbabilities = new ArrayList<StandardizationProbability>();
    String line;
    BufferedReader fileReader = new BufferedReader(new InputStreamReader(StandardizationProbability.class.getResource("resources/probability.properties").openStream(), Files.FILE_ENCODING));
    while((line = fileReader.readLine()) != null) {
      standardizationProbabilities.add(new StandardizationProbability(line));
    }
    fileReader.close();
    return standardizationProbabilities;
  }

  /**
   * Getter of the probability.
   * @return the probability.
   */
  public final double getProbability() {
    return probability;
  }

  /**
   * Getter of the hi2.
   * @return the hi2.
   */
  public final double getHi2() {
    return hi2;
  }
}
