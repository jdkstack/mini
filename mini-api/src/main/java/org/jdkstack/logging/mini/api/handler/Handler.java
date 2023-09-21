package org.jdkstack.logging.mini.api.handler;

import java.nio.Buffer;
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
   * @author admin
   */
  void produce(
      final String logLevel,
      final String dateTime,
      final String message,
      final String className,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Object arg9,
      final Throwable thrown,
      final Record lr);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void consume(final Record lr) throws Exception;

  Buffer format(Record logRecord);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param dateTime .
   * @param logLevel .
   * @param message .
   * @author admin
   */
  void process(
      final String logLevel,
      final String className,
      final String dateTime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Object arg9,
      final Throwable thrown);
}
