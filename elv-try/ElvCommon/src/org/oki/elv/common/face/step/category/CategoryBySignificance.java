/**
 * CategoryBySignificance.java
 */
package org.oki.elv.common.face.step.category;

/**
 * Category by significance.
 * @author Elv
 */
public class CategoryBySignificance implements Category {
  /** Contructor. */
  public CategoryBySignificance() {
  }

  /**
   * Categorizer by significance.
   * @param value the categorizable value.
   * @param argument the argument of categorizing. The significance parameter is considered.
   * @return the category level.
   */
  @Override
  public int categorize(double value, CategorizingArgument argument) {
    if(argument == null) {
      throw new NullPointerException("Null argument!");
    }
    int category = 0;
    if(argument.getSignificance() == 1) { // Significant.
      category = (value > 1 ? 1 : 5); // High or low.
    }
    else { // Non significant.
      category = (value > 1.1 ? 2 : (value < 0.9 ? 4 : 3)); // High, low or medium.
    }
    return category;
  }
}
