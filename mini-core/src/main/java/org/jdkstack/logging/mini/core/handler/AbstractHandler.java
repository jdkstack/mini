package org.jdkstack.logging.mini.core.handler;

import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.resource.CoFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.buffer.Internal;
import org.jdkstack.logging.mini.core.datetime.DateTimeEncoder;
import org.jdkstack.logging.mini.core.resource.FilterFactory;
import org.jdkstack.logging.mini.core.resource.FormatterFactory;
import org.jdkstack.pool.core.ThreadPoolExecutor;
import org.jdkstack.ringbuffer.core.EventFactory;
import org.jdkstack.ringbuffer.core.mpmc.version3.MpmcBlockingQueueV3;

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
  /** 有界数组阻塞队列. */
  protected final MpmcBlockingQueueV3<Record> queue;
  /** . */
  protected final String key;
  /** 阻塞队列名称. */
  protected final String target;

  protected final ThreadPoolExecutor threadPoolExecutor =
      new ThreadPoolExecutor(
          4, 4, 0, TimeUnit.SECONDS, new MpmcBlockingQueueV3<>(1024, new TaskEventFactory<>()));
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
      final String datetime,
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
    singleThread(
        logLevel, className, datetime, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
        arg9, thrown);
  }

  public void singleThread(
      final String logLevel,
      final String className,
      final String datetime,
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
    try {
      // 预生产(从循环队列tail取一个元素对象地址).
      final Record lr = queue.tail();
      // 为元素对象生产数据.
      produce(
          logLevel, datetime, message, className, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
          arg9, thrown, lr);
    } catch (Exception e) {
      Internal.log(e);
    } finally {
      // 生产数据完成的标记(数据可以从循环队列head消费).
      queue.start();
    }

    try {
      // 预消费(从循环队列head取一个元素对象).
      final Record logRecord = queue.head();
      // 从元素对象消费数据.
      consume(logRecord);
    } catch (Exception e) {
      Internal.log(e);
    } finally {
      // 消费数据完成的标记(数据可以从循环队列tail生产).
      queue.end();
    }
  }

  public void multiThread(
      final String logLevel,
      final String className,
      final String datetime,
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
    Task user = (Task) threadPoolExecutor.getTaskWorker();
    user.setLogLevel(logLevel);
    user.setDatetime(datetime);
    user.setClassName(className);
    user.setMessage(message);
    user.setThrown(thrown);
    user.setArg1(arg1);
    user.setArg2(arg2);
    user.setArg3(arg3);
    user.setArg4(arg4);
    user.setArg5(arg5);
    user.setArg6(arg6);
    user.setArg7(arg7);
    user.setArg8(arg8);
    user.setArg9(arg9);
    threadPoolExecutor.start();
  }

  @Override
  public void produce(
      final String logLevel,
      final String datetime,
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
    lr.setClassName(className);
    lr.setThrown(thrown);
    lr.setLevel(logLevel);
    lr.setMessage(message);
    lr.setArgs1(arg1);
    lr.setArgs2(arg2);
    lr.setArgs3(arg3);
    lr.setArgs4(arg4);
    lr.setArgs5(arg5);
    lr.setArgs6(arg6);
    lr.setArgs7(arg7);
    lr.setArgs8(arg8);
    lr.setArgs9(arg9);
    lr.setEvent(datetime);
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
  }

  abstract void rules(final Record lr, final int length) throws Exception;

  public class TaskEventFactory<E> implements EventFactory<E> {

    @Override
    public E newInstance() {
      return (E) new Task();
    }
  }

  public class Task implements Runnable {
    private String logLevel;
    private String className;
    private String datetime;
    private String message;
    private Object arg1;
    private Object arg2;
    private Object arg3;
    private Object arg4;
    private Object arg5;
    private Object arg6;
    private Object arg7;
    private Object arg8;
    private Object arg9;
    private Throwable thrown;

    public String getLogLevel() {
      return this.logLevel;
    }

    public void setLogLevel(final String logLevel) {
      this.logLevel = logLevel;
    }

    public String getClassName() {
      return this.className;
    }

    public void setClassName(final String className) {
      this.className = className;
    }

    public String getDatetime() {
      return this.datetime;
    }

    public void setDatetime(final String datetime) {
      this.datetime = datetime;
    }

    public String getMessage() {
      return this.message;
    }

    public void setMessage(final String message) {
      this.message = message;
    }

    public Object getArg1() {
      return this.arg1;
    }

    public void setArg1(final Object arg1) {
      this.arg1 = arg1;
    }

    public Object getArg2() {
      return this.arg2;
    }

    public void setArg2(final Object arg2) {
      this.arg2 = arg2;
    }

    public Object getArg3() {
      return this.arg3;
    }

    public void setArg3(final Object arg3) {
      this.arg3 = arg3;
    }

    public Object getArg4() {
      return this.arg4;
    }

    public void setArg4(final Object arg4) {
      this.arg4 = arg4;
    }

    public Object getArg5() {
      return this.arg5;
    }

    public void setArg5(final Object arg5) {
      this.arg5 = arg5;
    }

    public Object getArg6() {
      return this.arg6;
    }

    public void setArg6(final Object arg6) {
      this.arg6 = arg6;
    }

    public Object getArg7() {
      return this.arg7;
    }

    public void setArg7(final Object arg7) {
      this.arg7 = arg7;
    }

    public Object getArg8() {
      return this.arg8;
    }

    public void setArg8(final Object arg8) {
      this.arg8 = arg8;
    }

    public Object getArg9() {
      return this.arg9;
    }

    public void setArg9(final Object arg9) {
      this.arg9 = arg9;
    }

    public Throwable getThrown() {
      return this.thrown;
    }

    public void setThrown(final Throwable thrown) {
      this.thrown = thrown;
    }

    @Override
    public void run() {
      try {
        // 预生产(从循环队列tail取一个元素对象地址).
        final Record lr = queue.tail();
        // 为元素对象生产数据.
        produce(
            logLevel, datetime, message, className, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
            arg9, thrown, lr);
      } catch (Exception e) {
        Internal.log(e);
      } finally {
        // 生产数据完成的标记(数据可以从循环队列head消费).
        queue.start();
      }

      try {
        // 预消费(从循环队列head取一个元素对象).
        final Record logRecord = queue.head();
        // 从元素对象消费数据.
        consume(logRecord);
      } catch (Exception e) {
        Internal.log(e);
      } finally {
        // 消费数据完成的标记(数据可以从循环队列tail生产).
        queue.end();
      }
    }
  }
}
