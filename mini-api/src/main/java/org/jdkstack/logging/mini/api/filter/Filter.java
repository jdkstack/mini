package org.jdkstack.logging.mini.api.filter;

import org.jdkstack.logging.mini.api.record.Record;

/**
 * 对日志记录进行拦截.
 *
 * <p>按照逻辑判断日志是否应该丢弃.
 *
 * @author admin
 */
public interface Filter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 日志对象.
   * @return b.
   * @author admin
   */
  boolean doFilter(Record logRecord);
}
