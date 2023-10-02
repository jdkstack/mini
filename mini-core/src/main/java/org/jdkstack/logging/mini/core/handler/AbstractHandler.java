package org.jdkstack.logging.mini.core.handler;

import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.record.Record;

/**
 * This is a method description.
 *
 * <p>.
 *
 * @author admin
 */
public abstract class AbstractHandler implements Handler {

  /** . */
  protected final String key;

  protected final LogRecorderContext context;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  protected AbstractHandler(final LogRecorderContext context, final String key) {
    this.context = context;
    this.key = key;
  }

  @Override
  public abstract void consume(final Record lr) throws Exception;

  @Override
  public abstract void produce(
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
   * 切换文件的条件.
   *
   * <p>文件行数,文件大小,日期时间.
   *
   * @param lr lr.
   * @param length length.
   * @author admin
   */
  public abstract void rules(final Record lr, final int length) throws Exception;
}
