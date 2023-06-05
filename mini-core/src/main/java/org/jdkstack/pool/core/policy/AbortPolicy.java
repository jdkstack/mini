package org.jdkstack.pool.core.policy;

import java.util.concurrent.RejectedExecutionException;
import org.jdkstack.pool.core.RejectedExecutionHandler;
import org.jdkstack.pool.core.ThreadPoolExecutor;

public class AbortPolicy implements RejectedExecutionHandler {

  public AbortPolicy() {}

  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    throw new RejectedExecutionException("Task " + r.toString() + " rejected from " + e.toString());
  }
}
