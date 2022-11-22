package org.jdkstack.asynchronous.api.context;

import org.jdkstack.asynchronous.api.worker.Worker;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface WorkerContext {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param event   处理对象.
   * @param handler 处理器.
   * @param <T>     传入handler的元素.
   * @author admin
   */
  <T> void executeInExecutorService(T event, Worker<T> handler);
}
