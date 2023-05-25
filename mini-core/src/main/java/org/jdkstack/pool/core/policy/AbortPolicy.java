package org.jdkstack.pool.core.policy;

import java.util.concurrent.RejectedExecutionException;
import org.jdkstack.pool.core.RejectedExecutionHandler;
import org.jdkstack.pool.core.ThreadPoolExecutor;

/**
 * A handler for rejected tasks that throws a {@link RejectedExecutionException}.
 *
 * <p>This is the default handler for {@link java.util.concurrent.ThreadPoolExecutor} and {@link
 * ScheduledThreadPoolExecutor}.
 */
public class AbortPolicy implements RejectedExecutionHandler {
  /** Creates an {@code AbortPolicy}. */
  public AbortPolicy() {}

  /**
   * Always throws RejectedExecutionException.
   *
   * @param r the runnable task requested to be executed
   * @param e the executor attempting to execute this task
   * @throws RejectedExecutionException always
   */
  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    throw new RejectedExecutionException("Task " + r.toString() + " rejected from " + e.toString());
  }
}
