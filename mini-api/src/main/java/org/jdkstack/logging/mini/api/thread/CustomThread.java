package org.jdkstack.logging.mini.api.thread;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface CustomThread {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long startTime();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void beginEmission();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void endEmission();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return StackTraceElement[] StackTraceElement[].
   * @author admin
   */
  StackTraceElement[] getStackTrace();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param on on.
   * @author admin
   */
  void setDaemon(boolean on);
}
