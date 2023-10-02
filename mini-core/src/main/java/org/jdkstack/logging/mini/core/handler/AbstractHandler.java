package org.jdkstack.logging.mini.core.handler;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.resource.CoFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;
import org.jdkstack.logging.mini.core.factory.LogFactory;
import org.jdkstack.logging.mini.core.formatter.LogFormatterV2;
import org.jdkstack.logging.mini.core.record.RecordEventFactory;
import org.jdkstack.logging.mini.core.recorder.SystemLogRecorder;
import org.jdkstack.logging.mini.core.resource.FilterFactory;
import org.jdkstack.logging.mini.core.resource.FormatterFactory;
import org.jdkstack.ringbuffer.core.mpmc.version3.MpmcBlockingQueueV3;

/**
 * This is a method description.
 *
 * <p>.
 *
 * @author admin
 */
public abstract class AbstractHandler implements Handler {
  /** 内部使用,用来记录日志. */
  private static final SystemLogRecorder SYSTEM = LogFactory.getSystemLog();

  /** 批量flush. */
  protected final AtomicLong atomicLong = new AtomicLong(0L);

  /** 批量. */
  protected final int batchSize;

  /** 有界数组阻塞队列. */
  protected final MpmcBlockingQueueV3<Record> queue;

  /** . */
  protected final String key;

  /** 阻塞队列名称. */
  protected final String target;
  
  /** 日志级别格式化 . */
  private final Map<String, String> formatters = new HashMap<>(16);

  /** 日志级别过滤器 . */
  private final Map<String, String> filters = new HashMap<>(16);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @author admin
   */
  protected AbstractHandler(final String key) {
    this.key = key;
    final CoFactory info = StartApplication.getBean("configFactory", CoFactory.class);
    this.batchSize = Integer.parseInt(info.getValue(key, "batchSize"));
    this.target = info.getValue(key, "prefix");
    this.queue = new MpmcBlockingQueueV3<>(Constants.CAPACITY, new RecordEventFactory<>());
    this.formatters.put(info.getValue(key, "name"), info.getValue(key, "formatter"));
    this.filters.put(info.getValue(key, "name"), info.getValue(key, "filter"));
  }

  @Override
  public final Buffer format(final Record logRecord) {
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    final String formatterName = this.formatters.get(info1.getValue(key, "name"));
    final FormatterFactory info =
        StartApplication.getBean("formatterFactory", FormatterFactory.class);
    return info.formatter(formatterName, logRecord);
  }

  public boolean filter(final Record logRecord) {
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    final String filterName = this.filters.get(info1.getValue(key, "name"));
    final FilterFactory info = StartApplication.getBean("filterFactory", FilterFactory.class);
    return info.filter(filterName, logRecord);
  }

  @Override
  public final void process(
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
      final Throwable thrown) {
    // 单线程。
    this.singleThread(
        logLevel, className, dateTime, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
        arg9, thrown);
  }

  public void singleThread(
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
      final Throwable thrown) {
    // 生产业务.
    try {
      // 1.预生产(从循环队列tail取一个元素对象).
      final Record lr = this.queue.tail();
      // 2.生产数据(为元素对象的每一个字段填充数据).
      this.produce(
          logLevel, dateTime, message, className, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
          arg9, thrown, lr);
    } catch (final Exception e) {
      SYSTEM.log("SYSTEM", e.getMessage());
    } finally {
      // 3.生产数据完成的标记(数据可以从循环队列head消费).
      this.queue.start();
    }
    // 消费业务.
    try {
      // 1.预消费(从循环队列head取一个元素对象).
      final Record logRecord = this.queue.head();
      // 2.消费数据(从元素对象的每一个字段中获取数据).
      this.consume(logRecord);
      // 3.清空数据(为元素对象的每一个字段重新填充空数据).
      logRecord.clear();
    } catch (final Exception e) {
      SYSTEM.log("SYSTEM", e.getMessage());
    } finally {
      // 4.消费数据完成的标记(数据可以从循环队列tail生产).
      this.queue.end();
    }
  }

  @Override
  public void produce(
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
      DateTimeEncoder.encoder(event, current, 8 * 3600);
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
  abstract void rules(final Record lr, final int length) throws Exception;
}
