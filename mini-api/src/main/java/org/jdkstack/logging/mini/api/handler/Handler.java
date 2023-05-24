package org.jdkstack.logging.mini.api.handler;

import java.nio.Buffer;
import java.nio.CharBuffer;
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
      final String datetime,
      final CharBuffer message,
      final String className,
      final Throwable thrown,
      final Record lr);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void consume(final Record lr);

  Buffer format(Record logRecord);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param datetime .
   * @param logLevel .
   * @param message .
   * @author admin
   */
  void process(
      final String logLevel,
      final String className,
      final String datetime,
      final CharBuffer message,
      final Throwable thrown);
}
