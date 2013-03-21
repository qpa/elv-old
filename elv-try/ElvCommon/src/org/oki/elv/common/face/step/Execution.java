package org.oki.elv.common.face.step;

import java.util.List;

import org.oki.elv.common.io.ReaderWriter;

/**
 * Execution. The count of file names and accessers must be the same.
 * @author Elv
 * @param <A> the type of attribute.
 * @param <R> the type of result.
 */
public interface Execution<A extends Attribute, R> {
  /**
   * Getter of the file names.
   * @return the file names.
   */
  public List<String> getFileNames();

  /**
   * Executor.
   * @param accessers the accessers of execution (readers and writers).
   * @return the result of execution.
   */
  public R execute(ReaderWriter<R>... accessers);
}
