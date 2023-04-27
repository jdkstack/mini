package org.jdkstack.logging.mini.core.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.queue.Queue;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.resource.CoFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.queue.FileQueue;
import org.jdkstack.logging.mini.core.resource.FilterFactory;
import org.jdkstack.logging.mini.core.resource.FormatterFactory;

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
  /** . */
  protected final Queue<Record> queue;
  /** 锁. */
  protected final Lock lock = new ReentrantLock();
  /** 日志级别格式化 . */
  private final Map<String, String> formatters = new HashMap<>(16);
  /** 日志级别过滤器 . */
  private final Map<String, String> filters = new HashMap<>(16);
  /** . */
  private final String key;

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
    this.queue = new FileQueue(Constants.CAPACITY, info.getValue(key, "prefix"));
    this.formatters.put(info.getValue(key, "name"), info.getValue(key, "formatter"));
    this.filters.put(info.getValue(key, "name"), info.getValue(key, "filter"));
  }

  protected final StringBuilder format(final Record logRecord) {
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    final String formatterName = this.formatters.get(info1.getValue(key, "name"));
    final FormatterFactory info =
        StartApplication.getBean("formatterFactory", FormatterFactory.class);
    return info.formatter(formatterName, logRecord);
  }

  @Override
  public final void execute(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    // 预发布.
    this.queue.pub(logLevel, datetime, className, classMethod, lineNumber, message);
    // 发布完成.
    this.queue.start();
    // 预消费.
    final Record logRecord = this.queue.take();
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    final String filterName = this.filters.get(info1.getValue(key, "name"));
    final FilterFactory info = StartApplication.getBean("filterFactory", FilterFactory.class);
    final boolean filter = info.filter(filterName, logRecord);
    if (filter) {
      // 执行业务.
      this.process(logRecord);
    }
    // 消费完成.
    this.queue.end();
  }

  @Override
  public final void execute(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    // 预发布.
    this.queue.pub(logLevel, className, classMethod, lineNumber, message);
    // 发布完成.
    this.queue.start();
    // 预消费.
    final Record logRecord = this.queue.take();
    // 使用过滤器,过滤掉不符合条件的日志记录.
    final CoFactory info1 = StartApplication.getBean("configFactory", CoFactory.class);
    final String filterName = this.filters.get(info1.getValue(key, "name"));
    final FilterFactory info = StartApplication.getBean("filterFactory", FilterFactory.class);
    final boolean filter = info.filter(filterName, logRecord);
    if (filter) {
      // 执行业务.
      this.process(logRecord);
    }
    // 消费完成.
    this.queue.end();
  }
}
