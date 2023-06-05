package org.jdkstack.pool.core.policy;

import org.jdkstack.pool.core.RejectedExecutionHandler;
import org.jdkstack.pool.core.ThreadPoolExecutor;


public class CallerRunsPolicy implements RejectedExecutionHandler {

  public CallerRunsPolicy() {}

  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
    //
  }
}
