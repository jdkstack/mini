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
  void produce(final String logLevel, final String datetime, final String message, final Record lr);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void consume(final Record lr);

  StringBuilder format(Record logRecord);

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
  void process(final String logLevel, final String datetime, final String message);
}
