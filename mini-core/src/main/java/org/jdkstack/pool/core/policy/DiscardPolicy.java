package org.jdkstack.pool.core.policy;

import org.jdkstack.pool.core.RejectedExecutionHandler;
import org.jdkstack.pool.core.ThreadPoolExecutor;

/** A handler for rejected tasks that silently discards the rejected task. */
public class DiscardPolicy implements RejectedExecutionHandler {
  /** Creates a {@code DiscardPolicy}. */
  public DiscardPolicy() {}

  /**
   * Does nothing, which has the effect of discarding task r.
   *
   * @param r the runnable task requested to be executed
   * @param e the executor attempting to execute this task
   */
  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {}
}
