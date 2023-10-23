package org.jdkstack.logging.mini.core.context;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
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
import org.jdkstack.logging.mini.core.ringbuffer.RingBufferLogEventHandler;
import org.jdkstack.logging.mini.core.ringbuffer.RingBufferLogEventTranslator;
import org.jdkstack.logging.mini.core.thread.LogThreadFactory;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class AsyncLogRecorderContext implements LogRecorderContext {

  private final Configuration configuration = new LogRecorderConfiguration();

  private final ThreadLocal<RingBufferLogEventTranslator> tlt = new ThreadLocal<>();

  private final Disruptor<Record> disruptor;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public AsyncLogRecorderContext() {
    // 对象工厂。
    final EventFactory<Record> eventFactory = new RecordEventFactory();
    // 生产者使用多线程模式。
    final ProducerType producerType = ProducerType.MULTI;
    // 等待策略。
    final WaitStrategy waitStrategy = new BusySpinWaitStrategy();
    // 创建disruptor。
    disruptor =
        new Disruptor<>(
            eventFactory,
            4096,
            new LogThreadFactory("log-consume", null),
            producerType,
            waitStrategy);
    // 添加异常处理。
    final ExceptionHandler<Record> errorHandler =
        new ExceptionHandler<Record>() {
          @Override
          public void handleEventException(Throwable ex, long sequence, Record event) {
            ex.printStackTrace();
          }

          @Override
          public void handleOnStartException(Throwable ex) {
            ex.printStackTrace();
          }

          @Override
          public void handleOnShutdownException(Throwable ex) {
            ex.printStackTrace();
          }
        };
    disruptor.setDefaultExceptionHandler(errorHandler);
    // 添加业务处理（单个线程）。
    final RingBufferLogEventHandler[] handlers = {new RingBufferLogEventHandler(this)};
    disruptor.handleEventsWith(handlers);
    // 启动disruptor。
    disruptor.start();
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
  public final void consume(Record lr) {
    // 消费业务.
    try {
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
    } catch (final Exception ignore) {
      // ignore.
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
      final Throwable thrown,
      final Record lr) {
    // 消费业务.
    try {
      // 用Recorder的name获取到Recorder自己的配置。
      final RecorderConfig value = this.getRecorderConfig(name);
      final Handler handler = this.getHandler(value.getName());
      handler.produce(
          logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9,
          thrown, lr);
      // 使用消费Filter，检查当前的日志消息是否符合条件，符合条件才写入文件，不符合条件直接丢弃。
      if (this.filter(value.getHandlerProduceFilter(), lr)) {
        //
      }
    } catch (final Exception ignore) {
      // ignore.
    }
  }

  @Override
  public final void process(
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
    // 获取当前线程绑定的对象。
    final RingBufferLogEventTranslator translator = getCachedTranslator();
    // 将参数传递到对象中。
    translator.process(
            logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9,
            thrown);
    // 从循环数组中取出一个对象，并向对象中插入数据。
    disruptor.publishEvent(translator);
  }

  private RingBufferLogEventTranslator getCachedTranslator() {
    RingBufferLogEventTranslator result = tlt.get();
    if (result == null) {
      result = new RingBufferLogEventTranslator(this);
      tlt.set(result);
    }
    return result;
  }
}
