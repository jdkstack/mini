package org.jdkstack.logging.mini.api.formatter;

import org.jdkstack.logging.mini.api.record.Record;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Formatter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 日志对象.
   * @return s.
   * @author admin
   */
  StringBuilder format(Record logRecord);
}
