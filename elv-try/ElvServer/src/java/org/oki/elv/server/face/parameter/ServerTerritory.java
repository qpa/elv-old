/**
 * ServerTerritory.java
 */
package org.oki.elv.server.face.parameter;

import java.util.ArrayList;
import java.util.List;

import org.oki.elv.common.face.step.result.TerritoryYearResult;
import org.oki.elv.common.parameter.Territory;

/**
 * Server-side Territory.
 * @author Elv
 */
public final class ServerTerritory extends Territory {
  /** The year results. */
  private List<TerritoryYearResult> yearResults = new ArrayList<TerritoryYearResult>();

  /**
   * Constructor.
   * @param type the type of territory.
   * @param label the paragraph label.
   * @param text the paragraph text.
   * @param isDefault if true, the default text is provided.
   */
  private ServerTerritory(TYPE type, String label, String text, boolean isDefault) {
    super(type, label, text, isDefault);
  }

  /**
   * Getter of the yearResults.
   * @return the yearResults.
   */
  public List<TerritoryYearResult> getYearResults() {
    return yearResults;
  }

  /**
   * Setter of the yearResults.
   * @param yearResults the yearResults to set.
   */
  public void setYearResults(List<TerritoryYearResult> yearResults) {
    this.yearResults = yearResults;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  protected ServerTerritory fromLine(String line, boolean isDefault) {
    String[] parts = line.split(SEPARATOR);
    return new ServerTerritory(type, parts[0], parts[1], isDefault);
  }
}
