package org.jdkstack.logging.mini.core.handler;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.bean.api.bean.Bean;
import org.jdkstack.logging.mini.api.option.HandlerOption;
import org.jdkstack.logging.mini.api.queue.Queue;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.exception.LogRuntimeException;
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
public abstract class AbstractFileHandler extends AbstractHandler {

  /** 日志文件切换开关. */
  protected final AtomicLong policy = new AtomicLong(-1L);
  /** 批量flush. */
  protected final AtomicLong atomicLong = new AtomicLong(0L);
  /** 配置文件. */
  private final HandlerOption handlerOption;
  /** 日志文件. */
  protected Path path;
  /** 日志目录. */
  private File dir;
  /** 日志文件名. */
  private String logFileName;
  /** 日志文件名中的时间. */
  protected String format1 = "log";
  /** 批量. */
  protected final int batchSize;
  /** . */
  protected final Queue<Record> queue;
  /** 日志级别格式化 . */
  private final Map<String, String> formatters = new HashMap<>(16);
  /** 日志级别过滤器 . */
  private final Map<String, String> filters = new HashMap<>(16);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handlerOption handlerOption.
   * @author admin
   */
  protected AbstractFileHandler(
      final HandlerOption handlerOption) {
    this.handlerOption = handlerOption;
    this.batchSize = Integer.parseInt(this.handlerOption.getBatchSize());
    this.queue = new FileQueue(Constants.CAPACITY, handlerOption.getPrefix());
    this.formatters.put(handlerOption.getName(), handlerOption.getFormatter());
    this.filters.put(handlerOption.getName(), handlerOption.getFilter());
  }

  protected final StringBuilder format(final Record logRecord) {
    final String formatterName = this.formatters.get(this.handlerOption.getName());
    final Bean logInfos = StartApplication.context().getBean("formatterFactory");
    final Object obj3 = logInfos.getObj();
    final FormatterFactory info = (FormatterFactory) obj3;
    return info.formatter(formatterName, logRecord);
  }

  /**
   * 配置.
   *
   * @author admin
   */
  public abstract void doConfig();

  /**
   * .
   *
   * <p>.
   *
   * @author admin
   */
  protected final void fileName() {
    // 拼接文件名.
    final long l = this.policy.get();
    if (Constants.SIZE > l) {
      this.logFileName = this.format1 + "-0" + l + ".log";
    } else {
      this.logFileName = this.format1 + '-' + l + ".log";
    }
  }

  protected final void file() {
    // 得到日志的完整路径部分+日志文件名完整部分.
    this.path = new File(this.dir, this.logFileName).toPath();
  }

  /**
   * .
   *
   * <p>.
   *
   * @author admin
   */
  protected final void directory() {
    if (null == this.dir) {
      // 日志完整目录部分(日志子目录).
      this.dir = new File(this.handlerOption.getDirectory() + File.separator + this.handlerOption.getPrefix());
      // 不存在,创建目录和子目录.
      if (!this.dir.exists()) {
        this.dir.mkdirs();
      }
    }
  }

  /**
   * 切换日志文件规则.
   *
   * <p>切换日志文件规则.
   *
   * @param logRecord logRecord
   * @return int .
   * @author admin
   */
  protected final long getMerge(final Record logRecord) {
    final String type = this.handlerOption.getType();
    final long interval;
    switch (type) {
      case Constants.SECOND:
        interval = logRecord.getSecond();
        break;
      case Constants.MINUTE:
        interval = logRecord.getMinute();
        break;
      case Constants.HOUR:
        interval = logRecord.getHours();
        break;
      case Constants.DAY:
        interval = logRecord.getDay() - 1;
        break;
      case Constants.MONTH:
        interval = logRecord.getMonth() - 1;
        break;
      default:
        throw new LogRuntimeException("不支持.");
    }
    final long remainder = interval % Long.parseLong(this.handlerOption.getInterval());
    return interval - remainder;
  }

  protected final void check(final Record logRecord) {
    final String type = this.handlerOption.getType();
    final long year = logRecord.getYear();
    final long month = logRecord.getMonth();
    final long day = logRecord.getDay();
    final long hours = logRecord.getHours();
    final long minute = logRecord.getMinute();
    switch (type) {
      case Constants.SECOND:
        this.format1 = year + Constants.SEPARATOR + month + Constants.SEPARATOR + day
            + Constants.SEPARATOR + hours + Constants.SEPARATOR + minute;
        break;
      case Constants.MINUTE:
        this.format1 = year + Constants.SEPARATOR + month + Constants.SEPARATOR + day + Constants.SEPARATOR + hours;
        break;
      case Constants.HOUR:
        this.format1 = year + Constants.SEPARATOR + month + Constants.SEPARATOR + day;
        break;
      case Constants.DAY:
        this.format1 = year + Constants.SEPARATOR + month;
        break;
      case Constants.MONTH:
        this.format1 = year + Constants.SEPARATOR;
        break;
      default:
        throw new LogRuntimeException("不支持.");
    }
  }

  @Override
  public final void execute(final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    // 预发布.
    this.queue.pub(logLevel, datetime, className, classMethod, lineNumber, message);
    //发布完成.
    this.queue.start();
    // 预消费.
    final Record logRecord = this.queue.take();
    final String filterName = this.filters.get(this.handlerOption.getName());
    final Bean logInfos = StartApplication.context().getBean("filterFactory");
    final Object obj3 = logInfos.getObj();
    final FilterFactory info = (FilterFactory) obj3;
    final boolean filter = info.filter(filterName, logRecord);
    if (filter) {
      // 执行业务.
      this.process(logRecord);
    }
    //消费完成.
    this.queue.end();
  }

  @Override
  public final void execute(final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    // 预发布.
    this.queue.pub(logLevel, className, classMethod, lineNumber, message);
    //发布完成.
    this.queue.start();
    // 预消费.
    final Record logRecord = this.queue.take();
    // 使用过滤器,过滤掉不符合条件的日志记录.
    final String filterName = this.filters.get(this.handlerOption.getName());
    final Bean logInfos = StartApplication.context().getBean("filterFactory");
    final Object obj3 = logInfos.getObj();
    final FilterFactory info = (FilterFactory) obj3;
    final boolean filter = info.filter(filterName, logRecord);
    if (filter) {
      // 执行业务.
      this.process(logRecord);
    }
    //消费完成.
    this.queue.end();
  }
}
