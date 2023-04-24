package org.jdkstack.logging.mini.api.handler;

import org.jdkstack.logging.mini.api.record.Record;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Handler {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord logRecord.
   * @author admin
   */
  void process(final Record logRecord);

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
  void execute(
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
  void execute(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message);
}
