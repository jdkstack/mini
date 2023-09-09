package org.jdkstack.logging.mini.api.monitor;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Monitor {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param context 上下文.
   * @author admin
   */
  // void monitor(WorkerContext context);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param thread 线程.
   * @author admin
   */
  default void registerThread(final Thread thread) {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void close();
}
