/**
 * Category.java
 */
package org.oki.elv.common.face.step.category;

/**
 * Category.
 * @author Elv
 */
public interface Category {
  /**
   * Categorizer. 
   * @param value the categorizable value.
   * @param argument the argument of categorizing.
   * @return the category level.
   */
  public int categorize(double value, CategorizingArgument argument);
}
