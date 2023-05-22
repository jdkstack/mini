package org.jdkstack.pool;

import org.jdkstack.pool.Future;

public interface RunnableFuture<V> extends Runnable, Future<V> {
  /** Sets this Future to the result of its computation unless it has been cancelled. */
  void run();
}
