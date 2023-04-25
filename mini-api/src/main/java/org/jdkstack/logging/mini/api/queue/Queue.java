package org.jdkstack.logging.mini.api.queue;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @param <T> 泛型对象.
 * @author admin
 */
public interface Queue<T> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return T .
   * @author admin
   */
  T take();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void end();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String String.
   * @author admin
   */
  String getTarget();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param className .
   * @param classMethod .
   * @param lineNumber .
   * @param message .
   * @author admin
   */
  void pub(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param datetime .
   * @param logLevel .
   * @param className .
   * @param classMethod .
   * @param lineNumber .
   * @param message .
   * @author admin
   */
  void pub(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void start();
}
