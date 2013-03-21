package org.oki.elv.common.face.step;

import java.util.List;

import org.oki.elv.common.util.Name;

/**
 * Step.
 * @param <A> the type of attribute.
 * @param <E> the type of execution.
 * @author Elv
 */
public interface Step<A extends Attribute, E extends Execution> extends Name{
  /**
   * Getter of the attribute.
   * @return the attribute.
   */
  public A getAttribute();
  
  /**
   * Getter of the execution.
   * @return the execution.
   */
  public E getExecution();
  
  /**
   * Getter of the parent.
   * @param <PA> the type of parent's attribute.
   * @param <PE> the type of parent's execution.
   * @return the parent.
   */
  public <PA extends Attribute, PE extends Execution> Step<PA, PE> getParent();

  /**
   * Setter of the parent.
   * @param <PA> the type of parent's attribute.
   * @param <PE> the type of parent's execution.
   * @param parent the parent to set.
   */
  public <PA extends Attribute, PE extends Execution> void  setParent(Step<PA, PE> parent);

  /**
   * Getter of the children.
   * @param <CA> the type of childrens' attribute.
   * @param <CE> the type of childrens' execution.
   * @return the children.
   */
  public <CA extends Attribute, CE extends Execution> List<Step<CA, CE>> getChildren();

  /**
   * Setter of the children.
   * @param <CA> the type of childrens' attribute.
   * @param <CE> the type of childrens' execution.
   * @param children the children to set.
   */
  public <CA extends Attribute, CE extends Execution> void setChildren(List<Step<CA, CE>> children);

  /**
   * Tester of parent.
   * @return true, if this paragraph has no parent.
   */
  public boolean isRoot();
  
  /**
   * Tester of children.
   * @return true, if this paragraph has no children.
   */
  public boolean isLeaf();
}
