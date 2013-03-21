/**
 * CategoryBetweenMinMax.java
 */
package org.oki.elv.common.face.step.category;


/**
 * Category between the minimum and maximum values.
 * @author Elv
 */
public final class CategoryBetweenMinMax implements Category {
  /** Count of categories. */
  private int count = 5;
  
  /** Contructor. */
  public CategoryBetweenMinMax() {
  }

  /**
   * Getter of the count.
   * @return the count.
   */
  public final int getCount() {
    return count;
  }

  /**
   * Setter of the count.
   * @param count the count to set.
   */
  public final void setCount(int count) {
    this.count = count;
  }

  /**
   * Categorizer between minimum and maximum values, considering the <code>count</code> of the categories.
   * @param value the categorizable value.
   * @param argument the argument of categorizing. The <code>minValue</code> and the <code>maxValue</code> parameters are considered.
   * @return the category level.
   */
  @Override
  public int categorize(double value, CategorizingArgument argument) {
    if(argument == null) {
      throw new NullPointerException("Null argument!");
    }
    double interval = (argument.getMaxValue() - argument.getMinValue()) / count;
    double level = argument.getMinValue() + interval;
    int category;
    for(category = 1; category <= count; category++) {
      if(level >= value) {
        break;
      }
      else {
        level = level + interval;
      }
    }
    return category;
  }
}
