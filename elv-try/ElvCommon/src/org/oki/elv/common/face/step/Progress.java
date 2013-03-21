/**
 * Progress.java
 */
package org.oki.elv.common.face.step;

import java.io.Serializable;

/**
 * Execution progress.
 * @author Elv
 */
public class Progress implements Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = 206476560007466148L;
  
  /** The message of the progress. */
  private String message;
  /** The minimum value of the progress. */
  private int minimum = 0;
  /** The current value of the progress. */
  private int value = 0;
  /** The maximum value of the progress. */
  private int maximum = 0;
 
  /**
   * Constructor.
   * @param message the message of the progress.
   * @param minimum the minimum value of the progress.
   * @param maximum the maximum value of the progress.
   */
  public Progress(String message, int minimum, int maximum) {
    this.message = message;
    this.minimum = minimum;
    this.value = minimum;
    this.maximum = maximum;
  }
  
  /**
   * Getter of the message.
   * @return the message.
   */
  public final String getMessage() {
    return message;
  }

  /**
   * Setter of the message.
   * @param message the message to set.
   */
  public final void setMessage(String message) {
    this.message = message;
  }

  /**
   * Getter of the minimum.
   * @return the minimum.
   */
  public final int getMinimum() {
    return minimum;
  }

  /**
   * Setter of the minimum.
   * @param minimum the minimum to set.
   */
  public final void setMinimum(int minimum) {
    this.minimum = minimum;
  }

  /**
   * Getter of the value.
   * @return the value.
   */
  public final int getValue() {
    return value;
  }

  /**
   * Setter of the value.
   * @param value the value to set.
   */
  public final void setValue(int value) {
    this.value = value;
  }

  /**
   * Getter of the maximum.
   * @return the maximum.
   */
  public final int getMaximum() {
    return maximum;
  }

  /**
   * Setter of the maximum.
   * @param maximum the maximum to set.
   */
  public final void setMaximum(int maximum) {
    this.maximum = maximum;
  }

  /**
   * Getter of the visibility.
   * @return true, if there are more than 1 objects to progress.
   */
  public boolean isVisible() {
    return (maximum > 1);
  }
}
