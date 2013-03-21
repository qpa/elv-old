/**
 * ProgressStack.java
 */
package org.oki.elv.common.face.step;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Stack of progresses.
 * @author Elv
 */
public class ProgressStack implements Serializable {
  /** Serial ID. */
  private static final long serialVersionUID = -8936171760726917165L;

  /** The message of the progress. */
  private String message;
  /** The stack of progresses. */
  private Stack<Progress> progresses;
  
  /** Contructor. */
  public ProgressStack() {
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
   * Getter of the progresses.
   * @return the progresses.
   */
  public final List<Progress> getProgresses() {
    return Collections.unmodifiableList(progresses);
  }
  
  /**
   * Pusher of a new progress.
   * @param progress
   */
  public void push(Progress progress) {
    progresses.push(progress);
  }
  
  /**
   * Popper of the last progress.
   * @return the last progress from the stack.
   */
  public Progress pop() {
    return progresses.pop();
  }
  
  /**
   * Setter of the value of peek progress.
   * @param value the value to set.
   */
  public void setPeekValue(int value) {
    progresses.peek().setValue(value);
  }
  
  /**
   * Getter of the aggregate progress.
   * @return a single progress resulting from the aggregation of the stacked progresses.
   */
  public Progress getAggregateProgress() {
    int value = 0;
    int max = 0;
    for(Progress iteratorProgress : progresses) {
      value += iteratorProgress.getValue() - iteratorProgress.getMinimum();
      max += iteratorProgress.getMaximum() - iteratorProgress.getMinimum();
    }
    Progress aggregateProgress = new Progress("Aggregation", 0, max);
    aggregateProgress.setValue(value);
    return aggregateProgress;
  }
}
