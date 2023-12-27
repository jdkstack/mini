package org.jdkstack.logging.mini.extension.context;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.jdkstack.logging.mini.api.config.Configuration;
import org.jdkstack.logging.mini.api.config.ContextConfiguration;
import org.jdkstack.logging.mini.api.config.HandlerConfig;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.lifecycle.LifecycleState;
import org.jdkstack.logging.mini.api.monitor.Monitor;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.config.LogRecorderConfiguration;
import org.jdkstack.logging.mini.core.config.LogRecorderContextConfiguration;
import org.jdkstack.logging.mini.core.lifecycle.LifecycleBase;
import org.jdkstack.logging.mini.core.monitor.ThreadMonitor;
import org.jdkstack.logging.mini.core.record.LogRecord;
import org.jdkstack.logging.mini.core.record.RecordEventFactory;
import org.jdkstack.logging.mini.core.ringbuffer.RingBufferLogWorkHandler;
import org.jdkstack.logging.mini.core.thread.LogConsumeThread;
import org.jdkstack.logging.mini.core.thread.LogConsumeThreadFactory;
import org.jdkstack.logging.mini.core.thread.LogProduceThread;
import org.jdkstack.logging.mini.core.tool.ThreadLocalTool;

/**
 * .
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class AuditLogRecorderContext extends LifecycleBase implements LogRecorderContext {

  private final Configuration configuration = new LogRecorderConfiguration();
  private final ContextConfiguration contextConfiguration = new LogRecorderContextConfiguration();
  private final ThreadLocal<Record> rtl = new ThreadLocal<>();
  private Disruptor<Record> disruptor = null;
  private final ThreadMonitor threadMonitor = new ThreadMonitor();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public AuditLogRecorderContext() {
    this.setState(LifecycleState.INITIALIZING);
    String state = contextConfiguration.getState();
    switch (state) {
      case "synchronous":
        //
        break;
      case "asynchronous":
        getRecordDisruptor();
        break;
      default:
        throw new RuntimeException("不支持。");
    }
    this.setState(LifecycleState.INITIALIZED);
  }

  private void getRecordDisruptor() {
    // 对象工厂。
    final EventFactory<Record> eventFactory = new RecordEventFactory();
    // 生产者使用多线程模式。
    final ProducerType producerType = ProducerType.MULTI;
    // 等待策略。
    final WaitStrategy waitStrategy = new BusySpinWaitStrategy();
    // 创建disruptor。
    this.disruptor = new Disruptor<>(eventFactory, contextConfiguration.getRingBufferSize(), new LogConsumeThreadFactory("apl-log-consume", null), producerType, waitStrategy);
    // 添加异常处理。
    final ExceptionHandler<Record> errorHandler = new ExceptionHandler<>() {
      @Override
      public void handleEventException(Throwable ignore, long sequence, Record event) {
        //
      }

      @Override
      public void handleOnStartException(Throwable ignore) {
        //
      }

      @Override
      public void handleOnShutdownException(Throwable ignore) {
        //
      }
    };
    this.disruptor.setDefaultExceptionHandler(errorHandler);
    // 使用多消费者。
    RingBufferLogWorkHandler[] workHandlers = new RingBufferLogWorkHandler[contextConfiguration.getConsumers()];
    for (int i = 0; i < workHandlers.length; i++) {
      workHandlers[i] = new RingBufferLogWorkHandler(this);
    }
    // 使用多消费者。
    this.disruptor.handleEventsWithWorkerPool(workHandlers);
    // 启动disruptor。
    this.disruptor.start();
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
  public final void addLogHandlerConfig(final String key, final HandlerConfig logHandlerConfig) {
    this.configuration.addLogHandlerConfig(key, logHandlerConfig);
  }

  @Override
  public final HandlerConfig getHandlerConfig(final String key) {
    return this.configuration.getLogHandlerConfig(key);
  }

  @Override
  public final void addLevel(final String name, final int value) {
    this.configuration.addLevel(name, value);
  }

  @Override
  public final boolean doFilter(final String logLevelName, final String maxLevelName, final String minLevelName) {
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
  public final StringBuilder formatter(final String formatterName, final Record logRecord) {
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
      // 用Recorder name找到RecorderConfig配置信息。
      final RecorderConfig recorderConfig = this.getRecorderConfig(lr.getName());
      // 从配置中读取Recorder引用的Handler name。
      final String handlers = recorderConfig.getHandlers();
      // 使用Handler name找到HandlerConfig配置信息。
      final HandlerConfig handlerConfig = this.getHandlerConfig(handlers);
      // 使用消费Filter，检查当前的日志消息是否符合条件，符合条件才写入文件，不符合条件直接丢弃。
      if (this.filter(handlerConfig.getHandlerConsumeFilter(), lr)) {
        // 使用Handler name找到Handler，并执行Handler消费方法。
        final Handler handler = this.getHandler(handlerConfig.getName());
        // 消费数据(从元素对象的每一个字段中获取数据).
        handler.consume(lr);
      }
    } catch (final Exception ignore) {
      // ignore.
      ignore.printStackTrace();
    } finally {
      // 当前线程是日志库提供的吗?
      LogConsumeThread logConsumeThread = ThreadLocalTool.getLogConsumeThread();
      if (threadMonitor.isNull(logConsumeThread.getName())) {
        threadMonitor.registerThread(logConsumeThread);
      }
    }
  }

  @Override
  public final void produce(final String logLevel, final String dateTime, final String message, final String name, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown, final Record lr) {
    // 消费业务.
    try {
      // 用Recorder name找到RecorderConfig配置信息。
      final RecorderConfig recorderConfig = this.getRecorderConfig(name);
      // 从配置中读取Recorder引用的Handler name。
      final String handlers = recorderConfig.getHandlers();
      // 使用Handler name找到HandlerConfig配置信息。
      final HandlerConfig handlerConfig = this.getHandlerConfig(handlers);
      // 使用Handler name找到Handler，并执行Handler生产方法。
      final Handler handler = this.getHandler(handlerConfig.getName());
      // 生产数据(向元素对象的每一个字段中设置数据).
      handler.produce(logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown, lr);
      // 使用生产Filter，检查当前的日志消息是否符合条件，符合条件才写入文件，不符合条件直接丢弃。
      if (this.filter(recorderConfig.getHandlerProduceFilter(), lr)) {
        //
      }
    } catch (final Exception ignore) {
      // ignore.
      ignore.printStackTrace();
    } finally {
      // 当前线程是日志库提供的吗?
      LogProduceThread logProduceThread = ThreadLocalTool.getLogProduceThread();
      if (threadMonitor.isNull(logProduceThread.getName())) {
        threadMonitor.registerThread(logProduceThread);
      }
    }
  }

  @Override
  public final void process(final String logLevel, final String dateTime, final String message, final String name, final Object arg1, final Object arg2, final Object arg3, final Object arg4, final Object arg5, final Object arg6, final Object arg7, final Object arg8, final Object arg9, final Throwable thrown) {
    // 获取当前线程绑定的对象。
    //final RingBufferLogEventTranslator translator = getCachedTranslator();
    // 将参数传递到对象中。
    //translator.process(logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
    // 从循环数组中取出一个对象，并向对象中插入数据。
    //disruptor.publishEvent(translator);
    String state = contextConfiguration.getState();
    switch (state) {
      case "synchronous":
        synchronous(logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
        break;
      case "asynchronous":
        if (LifecycleState.STARTED == getState()) {
          asynchronous(logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
        } else {
          synchronous(logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
        }
        break;
      default:
        throw new RuntimeException("不支持。");
    }
  }

  private void asynchronous(String logLevel, String dateTime, String message, String name, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown) {
    RingBuffer<Record> ringBuffer = disruptor.getRingBuffer();
    long sequence = ringBuffer.next();
    try {
      Record record = ringBuffer.get(sequence);
      this.produce(logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown, record);
    } finally {
      ringBuffer.publish(sequence);
    }
  }

  private void synchronous(String logLevel, String dateTime, String message, String name, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown) {
    Record record = getLogRecord();
    try {
      this.produce(logLevel, dateTime, message, name, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown, record);
      this.consume(record);
    } finally {
      record.clear();
    }
  }

  private Record getLogRecord() {
    Record result = rtl.get();
    if (result == null) {
      result = new LogRecord();
      rtl.set(result);
    }
    return result;
  }

  @Override
  public void shutdown() {
    this.setState(LifecycleState.STOPPING);
    this.disruptor.shutdown();
    this.setState(LifecycleState.STOPPED);
  }

  @Override
  public Monitor threadMonitor() {
    return this.threadMonitor;
  }

  @Override
  public ContextConfiguration getContextConfiguration() {
    return this.contextConfiguration;
  }
}
