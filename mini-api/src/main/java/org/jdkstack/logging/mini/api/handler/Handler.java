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
   * @author admin
   */
  void consume(final Record lr) throws Exception;

  void produce(String logLevel, String dateTime, String message, String name, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown, Record lr);

  void produce(StackTraceElement stackTraceElement, String logLevel, String dateTime, String message, String name, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown, Record lr);
}
