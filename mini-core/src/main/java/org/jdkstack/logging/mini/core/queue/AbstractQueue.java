package org.jdkstack.logging.mini.core.queue;

import org.jdkstack.jdkringbuffer.core.mpmc.version3.MpmcBlockingQueueV3;
import org.jdkstack.logging.mini.api.queue.Queue;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.datetime.DateTimeDecoder;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;
import org.jdkstack.logging.mini.core.pool.StringBuilderPool;

/**
 * 日志消息阻塞队列,主要用于消息的短暂缓冲.
 *
 * <p>Another description after blank line.
 *
 * @param <T> 泛型对象.
 * @author admin
 */
public abstract class AbstractQueue<T> implements Queue<T> {

  /** 有界数组阻塞队列. */
  private final MpmcBlockingQueueV3<T> queue;

  /**
   * 建一个容量capacity的队列,默认容量2048.
   *
   * <p>容量需要设置成2的幂次方,比如1024,2048,没有特殊需要1024,2048即可.
   *
   * @param capacity 队列容量.
   * @author admin
   */
  protected AbstractQueue(final int capacity) {
    this.queue = new MpmcBlockingQueueV3<>(capacity, new RecordEventFactory());
  }

  /**
   * 出队时用阻塞方法take,队列空时阻塞.
   *
   * <p>Another description after blank line.
   *
   * @return T .
   * @author admin
   */
  @Override
  public final T take() {
    try {
      // 使用阻塞方法从队列获取元素. 天然的背压方式,当队列空时阻塞.
      return this.queue.take();
    } catch (final Exception e) {
      Thread.currentThread().interrupt();
      throw new LogRuntimeException(e);
    }
  }

  @Override
  public final void end() {
    this.queue.end();
  }

  /**
   * 入队时使用阻塞方法put,队列满时阻塞.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void pub(final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    // 使用阻塞方法将元素插入队列. 天然的背压方式,当队列满后阻塞.
    // 需要放到多线程中。
    final Record lr = (Record) this.queue.pub();
    lr.setClassName(className);
    lr.setClassMethod(classMethod);
    lr.setLineNumber(lineNumber);
    lr.setLevel(logLevel);
    lr.setMessage(message);
    // 默认时区获取当前系统的日期.
    final long current = System.currentTimeMillis();
    lr.setSb(DateTimeEncoder.encoder(current));
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
  }

  @Override
  public final void pub(final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    // 使用阻塞方法将元素插入队列. 天然的背压方式,当队列满后阻塞.
    // 需要放到多线程中。
    final Record lr = (Record) this.queue.pub();
    lr.setClassName(className);
    lr.setClassMethod(classMethod);
    lr.setLineNumber(lineNumber);
    lr.setLevel(logLevel);
    lr.setMessage(message);
    // 默认时区获取当前系统的日期.
    final StringBuilder current = StringBuilderPool.poll();
    current.append(datetime);
    lr.setSb(current);
    final long year = DateTimeDecoder.year(current);
    lr.setYear(year);
    final long month = DateTimeDecoder.month(current);
    lr.setMonth(month);
    final long day = DateTimeDecoder.day(current);
    lr.setDay(day);
    final long hours = DateTimeDecoder.hours(current);
    lr.setHours(hours);
    final long minute = DateTimeDecoder.minutes(current);
    lr.setMinute(minute);
    final long second = DateTimeDecoder.seconds(current);
    lr.setSecond(second);
    final long mills = DateTimeDecoder.millisecond(current);
    lr.setMills(mills);
  }

  @Override
  public final void start() {
    this.queue.start();
  }
}
