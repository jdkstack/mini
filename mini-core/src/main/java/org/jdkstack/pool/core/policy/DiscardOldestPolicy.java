package org.jdkstack.pool.core.policy;

import org.jdkstack.pool.core.RejectedExecutionHandler;
import org.jdkstack.pool.core.ThreadPoolExecutor;

/**
 * A handler for rejected tasks that discards the oldest unhandled request and then retries {@code
 * execute}, unless the executor is shut down, in which case the task is discarded.
 */
public class DiscardOldestPolicy implements RejectedExecutionHandler {
  /** Creates a {@code DiscardOldestPolicy} for the given executor. */
  public DiscardOldestPolicy() {}

  /**
   * Obtains and ignores the next task that the executor would otherwise execute, if one is
   * immediately available, and then retries execution of task r, unless the executor is shut down,
   * in which case task r is instead discarded.
   *
   * @param r the runnable task requested to be executed
   * @param e the executor attempting to execute this task
   */
  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
  //
  }
}
