package org.jdkstack.logging.mini.core.context;

import java.nio.Buffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import org.jdkstack.logging.mini.core.level.Constants;
import org.jdkstack.logging.mini.core.level.LogLevel;
import org.jdkstack.logging.mini.core.record.RecordEventFactory;
import org.jdkstack.logging.mini.core.recorder.SystemLogRecorder;
import org.jdkstack.logging.mini.core.ringbuffer.MpmcBlockingQueueV3;

public class AsyncLogRecorderContext implements LogRecorderContext {

  /** 内部使用,用来记录日志. */
  private static final SystemLogRecorder SYSTEM = SystemLogRecorder.getSystemRecorder();

  private final Configuration configuration = new LogRecorderConfiguration();

  /** 有界数组阻塞队列. */
  private final MpmcBlockingQueueV3<Record> queue =
      new MpmcBlockingQueueV3<>(
          org.jdkstack.logging.mini.core.handler.Constants.CAPACITY, new RecordEventFactory<>());

  /** . */
  private final Map<String, Recorder> recorders = new ConcurrentHashMap<>(1000);

  /** . */
  private final Map<String, Filter> filters = new ConcurrentHashMap<>(32);

  /** . */
  private final Map<String, Formatter> formatters = new ConcurrentHashMap<>(32);

  /** . */
  private final Map<String, Handler> handlers = new ConcurrentHashMap<>(32);

  /** . */
  private final Map<String, Level> levels = new ConcurrentHashMap<>(32);

  /** . */
  private final Map<String, RecorderConfig> logRecorderConfigs = new ConcurrentHashMap<>(1024);

  public AsyncLogRecorderContext() {
    //
  }

  @Override
  public final Recorder getRecorder(final String name) {
    return this.recorders.get(name);
  }

  @Override
  public final void addRecorder(final String name, final Recorder recorder) {
    this.recorders.put(name, recorder);
  }

  @Override
  public final Handler getHandler(final String name) {
    return this.handlers.get(name);
  }

  @Override
  public final void addHandler(final String key, final Handler value) {
    this.handlers.put(key, value);
  }

  @Override
  public final void addFilter(final String key, final Filter filter) {
    this.filters.put(key, filter);
  }

  @Override
  public final void addFormatter(final String key, final Formatter formatter) {
    this.formatters.put(key, formatter);
  }

  @Override
  public final void addLogRecorderConfig(final String key, final RecorderConfig logRecorderConfig) {
    this.logRecorderConfigs.put(key, logRecorderConfig);
  }

  @Override
  public final RecorderConfig getValue(final String key) {
    return this.logRecorderConfigs.get(key);
  }

  @Override
  public final void addLevel(final String name, final int value) {
    final Level level = new LogLevel(name, value);
    this.levels.put(name, level);
  }

  @Override
  public final boolean doFilter(
      final String logLevels, final Level maxLevel, final Level minLevel) {
    final Level level = this.findLevel(logLevels);
    // 只要有一个不是真,则表示日志会过滤掉.
    final boolean b2 = level.intValue() <= maxLevel.intValue();
    final boolean b1 = level.intValue() >= minLevel.intValue();
    return b2 && b1;
  }

  @Override
  public final Level findLevel(final String name) {
    // 找到注册的日志级别。
    Level level = this.levels.get(name);
    if (null == level) {
      // 创建一个临时的日志级别。
      level = new LogLevel(name, Constants.LEVEL_VALUE);
      this.levels.putIfAbsent(name, level);
    }
    return level;
  }

  @Override
  public final Buffer formatter(final String formatterName, final Record logRecord) {
    final Formatter formatter = this.formatters.get(formatterName);
    return formatter.format(logRecord);
  }

  @Override
  public final boolean filter(final String filterName, final Record logRecord) {
    final Filter filter = this.filters.get(filterName);
    return filter.doFilter(logRecord);
  }

  @Override
  public final void consume() {
    // 消费业务.
    try {
      // 1.预消费(从循环队列head取一个元素对象).
      final Record logRecord = this.queue.head();
      // className,从配置中获取到对应Recorder的RecorderConfig.
      // 从配置中获取所有的数据，包括handlers.
      // 应该用全局的配置AbstractConfiguration，可以获取所有的handler,filter,formatter
      RecorderConfig value = this.getValue(logRecord.getClassName());
      if (value == null) {
        value = this.getValue("default");
      }
      final Handler handler = this.getHandler(value.getName());
      // 2.消费数据(从元素对象的每一个字段中获取数据).
      handler.consume(logRecord);
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
          final Throwable thrown) {
    // 生产业务.
    try {
      // 1.预生产(从循环队列tail取一个元素对象).
      final Record lr = this.queue.tail();
      // className,从配置中获取到对应Recorder的RecorderConfig.
      // 从配置中获取所有的数据，包括handlers.
      RecorderConfig value = this.getValue(className);
      if (value == null) {
        value = this.getValue("default");
      }
      final Handler handler = this.getHandler(value.getName());
      // 2.生产数据(为元素对象的每一个字段填充数据).
      handler.produce(
          logLevel, dateTime, message, className, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
          arg9, null, lr);
    } catch (final Exception e) {
      SYSTEM.log("SYSTEM", e.getMessage());
    } finally {
      // 3.生产数据完成的标记(数据可以从循环队列head消费).
      this.queue.start();
    }
  }
}
