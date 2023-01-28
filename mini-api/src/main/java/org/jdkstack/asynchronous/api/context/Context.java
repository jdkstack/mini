package org.jdkstack.asynchronous.api.context;

import org.jdkstack.asynchronous.api.worker.Worker;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Context {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler 处理器程序.
   * @param event   处理器事件.
   * @param <T>     处理器元素.
   * @author admin
   */
  <T> void dispatch(T event, Worker<T> handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler 处理业务的Runnable.
   * @author admin
   */
  void dispatch(Runnable handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void beginDispatch();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void endDispatch();
}
