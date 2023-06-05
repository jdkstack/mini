package org.jdkstack.pool.core.policy;

import org.jdkstack.pool.core.RejectedExecutionHandler;
import org.jdkstack.pool.core.ThreadPoolExecutor;


public class DiscardOldestPolicy implements RejectedExecutionHandler {

  public DiscardOldestPolicy() {}


  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
  //
  }
}
