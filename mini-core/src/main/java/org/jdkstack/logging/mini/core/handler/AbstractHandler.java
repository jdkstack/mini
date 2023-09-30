package org.jdkstack.logging.mini.core.handler;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;
import org.jdkstack.logging.mini.core.datetime.TimeZone;
import org.jdkstack.logging.mini.core.formatter.LogFormatterV2;

/**
 * This is a method description.
 *
 * <p>.
 *
 * @author admin
 */
public abstract class AbstractHandler implements Handler {

  /** 批量flush. */
  protected final AtomicLong atomicLong = new AtomicLong(0L);

  /** 批量. */
  protected final int batchSize;

  /** . */
  protected final String key;

  /** 阻塞队列名称. */
  protected final String target;
  
  /** 日志级别格式化 . */
  private final Map<String, String> formatters = new HashMap<>(16);

  /** 日志级别过滤器 . */
  private final Map<String, String> filters = new HashMap<>(16);

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
    this.batchSize = 1024;
    this.target = this.context.getValue(key).getPrefix();
    this.formatters.put(
        this.context.getValue(key).getName(), this.context.getValue(key).getFormatter());
    this.filters.put(this.context.getValue(key).getName(), this.context.getValue(key).getFilter());
  }

  @Override
  public final Buffer format(final Record logRecord) {
    final String formatterName = this.formatters.get(this.context.getValue(this.key).getName());
    return this.context.formatter(formatterName, logRecord);
  }

  public final boolean filter(final Record logRecord) {
    final String filterName = this.filters.get(this.context.getValue(this.key).getName());
    return this.context.filter(filterName, logRecord);
  }

  @Override
  public void consume(final Record lr) throws Exception {
    //
  }

  @Override
  public final void produce(
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
      final Record lr) {
    // 设置日志级别.
    lr.setLevel(logLevel);
    // 设置日志日期时间.
    final StringBuilder event = lr.getEvent();
    // 如果参数为空,使用系统当前的时间戳,the current time(UTC 8) in milliseconds.
    if (null == dateTime) {
      // 系统当前的时间戳.
      final long current = System.currentTimeMillis();
      // 使用固定时区+8:00(8小时x3600秒).
      DateTimeEncoder.encoder(event, current, TimeZone.EAST8);
    } else {
      // 不处理参数传递过来的日期时间.
      event.append(dateTime);
    }
    // 设置9个参数.
    lr.setParams(arg1, 0);
    lr.setParams(arg2, 1);
    lr.setParams(arg3, 2);
    lr.setParams(arg4, 3);
    lr.setParams(arg5, 4);
    lr.setParams(arg6, 5);
    lr.setParams(arg7, 6);
    lr.setParams(arg8, 7);
    lr.setParams(arg9, 8);
    // 用9个参数替换掉message中的占位符{}.
    LogFormatterV2.format(lr, message);
    // location中的className(暂时不处理method和lineNumber).
    lr.setClassName(className);
    // 异常信息.
    lr.setThrown(thrown);
  }

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
