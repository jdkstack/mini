package org.jdkstack.logging.mini.core.filter;

import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.record.Record;

/**
 * 日志记录过滤器.
 *
 * <p>拦截每一条日志记录.
 *
 * @author admin
 */
public class LogFilter implements Filter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogFilter(final LogRecorderContext context) {
    //
  }

  /**
   * 日志拦截器.
   *
   * <p>日志拦截器.
   *
   * @param logRecord 日志记录对象.
   * @return boolean 返回false,代表当前日志记录丢弃.
   * @author admin
   */
  @Override
  public final boolean doFilter(final Record logRecord) {
    return null != logRecord.getLevel();
  }
}
