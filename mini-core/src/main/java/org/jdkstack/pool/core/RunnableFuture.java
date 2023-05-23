package org.jdkstack.pool.core;

public interface RunnableFuture<V> extends Runnable, Future<V> {
  /** Sets this Future to the result of its computation unless it has been cancelled. */
  void run();
}
