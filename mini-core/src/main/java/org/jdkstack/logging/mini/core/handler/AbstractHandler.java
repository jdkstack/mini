package org.jdkstack.logging.mini.core.handler;

import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.resource.CoFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.buffer.Internal;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;
import org.jdkstack.logging.mini.core.resource.FilterFactory;
import org.jdkstack.logging.mini.core.resource.FormatterFactory;
import org.jdkstack.ringbuffer.core.mpmc.version3.MpmcBlockingQueueV3;

/**
 * This is a method description.
 *
 * <p>PrintWriter.
 *
 * @author admin
 */
public abstract class AbstractHandler implements Handler {

  /** 批量flush. */
  protected final AtomicLong atomicLong = new AtomicLong(0L);
  /** 批量. */
  protected final int batchSize;
  /** 有界数组阻塞队列. */
  protected final MpmcBlockingQueueV3<Record> queue;
  /** 日志级别格式化 . */
  private final Map<String, String> formatters = new HashMap<>(16);
  /** 日志级别过滤器 . */
  private final Map<String, String> filters = new HashMap<>(16);
  /** . */
  protected final String key;
  /** 阻塞队列名称. */
  protected final String target;

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
      final String datetime,
      final CharBuffer message,
      final Throwable thrown) {
    try {
      // 预生产(从循环队列tail取一个元素对象地址).
      final Record lr = queue.tail();
      // 为元素对象生产数据.
      produce(logLevel, datetime, message, className, thrown, lr);
    } finally {
      // 生产数据完成的标记(数据可以从循环队列head消费).
      queue.start();
    }

    try {
      // 预消费(从循环队列head取一个元素对象).
      final Record logRecord = queue.head();
      // 从元素对象消费数据.
      consume(logRecord);
    } finally {
      // 消费数据完成的标记(数据可以从循环队列tail生产).
      queue.end();
    }
  }

  @Override
  public void produce(
      final String logLevel,
      final String datetime,
      final CharBuffer message,
      final String className,
      final Throwable thrown,
      final Record lr) {
    try {
      lr.setEvent(datetime);
      lr.setClassName(className);
      lr.setThrown(thrown);
      lr.setLevel(logLevel);
      lr.setMessage(message);
      // 记录接收事件时的日期时间.
      final long current = System.currentTimeMillis();
      final long year = DateTimeEncoder.year(current);
      lr.setYear(year);
      final long month = DateTimeEncoder.month(current);
      lr.setMonth(month);
      final long day = DateTimeEncoder.day(current);
      lr.setDay(day);
      final long hours = DateTimeEncoder.hours(current);
      lr.setHours(hours);
      final long minute = DateTimeEncoder.minutes(current);
      lr.setMinute(minute);
      final long second = DateTimeEncoder.seconds(current);
      lr.setSecond(second);
      final long mills = DateTimeEncoder.millisecond(current);
      lr.setMills(mills);
    } catch (final Exception e) {
      Internal.log(e);
    }
  }

  abstract void rules(final Record lr, final int length) throws Exception;
}
