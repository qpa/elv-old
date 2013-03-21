package org.oki.elv.common.util;

import java.beans.PersistenceDelegate;

/**
 * Persistence.
 * @author Elv
 */
public interface Persistence {
  /**
   * Getter of the persistence delegate.
   * @return the persistence delegate of this bean.
   */
  public PersistenceDelegate getPersistenceDelegate();
}
