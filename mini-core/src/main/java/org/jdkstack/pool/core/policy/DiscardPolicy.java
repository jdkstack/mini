package org.jdkstack.pool.core.policy;

import org.jdkstack.pool.core.RejectedExecutionHandler;
import org.jdkstack.pool.core.ThreadPoolExecutor;

public class DiscardPolicy implements RejectedExecutionHandler {

  public DiscardPolicy() {}

  public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {}
}
