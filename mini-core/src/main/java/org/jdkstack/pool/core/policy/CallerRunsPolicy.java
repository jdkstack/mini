package org.jdkstack.pool.core.policy;

import org.jdkstack.pool.core.RejectedExecutionHandler;
import org.jdkstack.pool.core.ThreadPoolExecutor;

/**
 * A handler for rejected tasks that runs the rejected task directly in the calling thread of the
 * {@code execute} method, unless the executor has been shut down, in which case the task is
 * discarded.
 */
public class CallerRunsPolicy implements RejectedExecutionHandler {
  /** Creates a {@code CallerRunsPolicy}. */
  public CallerRunsPolicy() {}

  /**
   * Executes task r in the caller's thread, unless the executor has been shut down, in which case
   * the task is discarded.
   *
   * @param r the runnable task requested to be executed
   * @param e the executor attempting to execute this task
   */
  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    if (!e.isShutdown()) {
      r.run();
    }
  }
}
