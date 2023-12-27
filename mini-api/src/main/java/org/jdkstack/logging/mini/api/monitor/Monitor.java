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
   * @param thread 线程.
   * @author admin
   */
  default void registerThread(final Thread thread) {
    //
  }

  boolean isNull(String name);
}
