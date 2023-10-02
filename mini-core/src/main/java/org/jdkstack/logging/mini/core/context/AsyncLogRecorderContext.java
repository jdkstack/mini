package org.jdkstack.logging.mini.core.context;

import java.nio.Buffer;
import org.jdkstack.logging.mini.api.config.Configuration;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.config.LogRecorderConfiguration;
import org.jdkstack.logging.mini.core.record.RecordEventFactory;
import org.jdkstack.logging.mini.core.recorder.SystemLogRecorder;
import org.jdkstack.logging.mini.core.ringbuffer.MpmcBlockingQueueV3;

public class AsyncLogRecorderContext implements LogRecorderContext {

  /** 内部使用,用来记录日志. */
  private static final SystemLogRecorder SYSTEM = SystemLogRecorder.getSystemRecorder();

  private final Configuration configuration = new LogRecorderConfiguration();

  /** 有界数组阻塞队列. */
  private final MpmcBlockingQueueV3<Record> queue =
      new MpmcBlockingQueueV3<>(Constants.CAPACITY, new RecordEventFactory<>());

  public AsyncLogRecorderContext() {
    //
  }

  @Override
  public final Recorder getRecorder(final String name) {
    return this.configuration.getRecorder(name);
  }

  @Override
  public final void addRecorder(final String name, final Recorder recorder) {
    this.configuration.addRecorder(name, recorder);
  }

  @Override
  public final Handler getHandler(final String name) {
    return this.configuration.getHandler(name);
  }

  @Override
  public final void addHandler(final String key, final Handler value) {
    this.configuration.addHandler(key, value);
  }

  @Override
  public final void addFilter(final String key, final Filter filter) {
    this.configuration.addFilter(key, filter);
  }

  @Override
  public final void addFormatter(final String key, final Formatter formatter) {
    this.configuration.addFormatter(key, formatter);
  }

  @Override
  public final void addLogRecorderConfig(final String key, final RecorderConfig logRecorderConfig) {
    this.configuration.addLogRecorderConfig(key, logRecorderConfig);
  }

  @Override
  public final RecorderConfig getRecorderConfig(final String key) {
    return this.configuration.getRecorderConfig(key);
  }

  @Override
  public final void addLevel(final String name, final int value) {
    this.configuration.addLevel(name, value);
  }

  @Override
  public final boolean doFilter(
      final String logLevelName, final String maxLevelName, final String minLevelName) {
    final Level logLevel = this.findLevel(logLevelName);
    final Level maxLevel = this.findLevel(maxLevelName);
    final Level minLevel = this.findLevel(minLevelName);
    // 只要有一个不是真,则表示日志会过滤掉.
    final boolean b2 = logLevel.intValue() <= maxLevel.intValue();
    final boolean b1 = logLevel.intValue() >= minLevel.intValue();
    return b2 && b1;
  }

  @Override
  public final Level findLevel(final String name) {
    // 找到注册的日志级别。
    return this.configuration.findLevel(name);
  }

  @Override
  public final Buffer formatter(final String formatterName, final Record logRecord) {
    return this.configuration.formatter(formatterName, logRecord);
  }

  @Override
  public final boolean filter(final String filterName, final Record logRecord) {
    return this.configuration.filter(filterName, logRecord);
  }

  @Override
  public final void consume() {
    // 消费业务.
    try {
      // 1.预消费(从循环队列head取一个元素对象).
      final Record lr = this.queue.head();
      // 用Recorder的name获取到Recorder自己的配置。
      final RecorderConfig value = this.getRecorderConfig(lr.getName());
      // 使用消费Filter，检查当前的日志消息是否符合条件，符合条件才写入文件，不符合条件直接丢弃。
      if (this.filter(value.getHandlerConsumeFilter(), lr)) {
        // className,从配置中获取到对应Recorder的RecorderConfig.
        // 从配置中获取所有的数据，包括handlers.
        // 应该用全局的配置AbstractConfiguration，可以获取所有的handler,filter,formatter
        final Handler handler = this.getHandler(value.getName());
        // 2.消费数据(从元素对象的每一个字段中获取数据).
        handler.consume(lr);
      }
      // 3.清空数据(为元素对象的每一个字段重新填充空数据).
      lr.clear();
    } catch (final Exception e) {
      SYSTEM.log("SYSTEM", e.getMessage());
    } finally {
      // 4.消费数据完成的标记(数据可以从循环队列tail生产).
      this.queue.end();
    }
  }

  @Override
  public final void produce(
      final String logLevel,
      final String dateTime,
      final String message,
      final String name,
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
      // 用Recorder的name获取到Recorder自己的配置。
      final RecorderConfig value = this.getRecorderConfig(name);
      // 使用生产Filter，检查当前的日志消息是否符合条件，符合条件才写入RingBuffer，不符合条件直接丢弃。
      if (this.filter(value.getHandlerProduceFilter(), lr)) {
        // className,从配置中获取到对应Recorder的RecorderConfig.
        // 从配置中获取所有的数据，包括handlers.
        final Handler handler = this.getHandler(value.getName());
        // 2.生产数据(为元素对象的每一个字段填充数据).
        handler.produce(
            logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9,
            thrown, lr);
      }
    } catch (final Exception e) {
      SYSTEM.log("SYSTEM", e.getMessage());
    } finally {
      // 3.生产数据完成的标记(数据可以从循环队列head消费).
      this.queue.start();
    }
  }
}
