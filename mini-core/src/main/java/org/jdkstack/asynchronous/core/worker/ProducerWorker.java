package org.jdkstack.asynchronous.core.worker;

import org.jdkstack.asynchronous.api.worker.ProWorker;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.queue.Queue;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ProducerWorker implements ProWorker<Handler> {

  private Level logLevel;
  private String className;
  private String classMethod;
  private int lineNumber;
  private StringBuilder message;


  /** . */
  private final Queue<Record> queue;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param queue 放入队列中的元素.
   * @author admin
   */
  public ProducerWorker(final Queue<Record> queue) {
    this.queue = queue;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param event 放入队列中的元素.
   * @author admin
   */
  @Override
  public final void handle(final Handler event) {
  /*  // 需要放到多线程中。
    final Record lr = queue.pub();
    lr.setClassName(className);
    lr.setClassMethod(classMethod);
    lr.setLineNumber(lineNumber);
    lr.setLevel(logLevel);
    lr.setMessage(message);
    // 默认时区获取当前系统的日期.
    final long current = System.currentTimeMillis();
    lr.setSb(DateTimeEncoder.encoder(current));
    final int year = DateTimeEncoder.year(current);
    lr.setYear(year);
    final int month = DateTimeEncoder.month(current);
    lr.setMonth(month);
    final int day = DateTimeEncoder.day(current);
    lr.setDay(day);
    final int hours = DateTimeEncoder.hours(current);
    lr.setHours(hours);
    final int minute = DateTimeEncoder.minutes(current);
    lr.setMinute(minute);
    final int second = DateTimeEncoder.seconds(current);
    lr.setSecond(second);
    final int mills = DateTimeEncoder.millisecond(current);
    lr.setMills(mills);
    queue.start();*/
  }

  public void setLogLevel(final Level logLevel) {
    this.logLevel = logLevel;
  }

  public void setClassName(final String className) {
    this.className = className;
  }

  public void setClassMethod(final String classMethod) {
    this.classMethod = classMethod;
  }

  public void setLineNumber(final int lineNumber) {
    this.lineNumber = lineNumber;
  }

  public void setMessage(final StringBuilder message) {
    this.message = message;
  }
}
