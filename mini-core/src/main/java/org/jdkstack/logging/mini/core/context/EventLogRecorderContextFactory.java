package org.jdkstack.logging.mini.core.context;

import org.jdkstack.logging.mini.api.config.HandlerConfig;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContextFactory;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.lifecycle.LifecycleState;
import org.jdkstack.logging.mini.api.monitor.Monitor;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.config.LogHandlerConfig;
import org.jdkstack.logging.mini.core.config.LogRecorderConfig;
import org.jdkstack.logging.mini.core.filter.HandlerConsumeFilter;
import org.jdkstack.logging.mini.core.filter.HandlerProduceFilter;
import org.jdkstack.logging.mini.core.formatter.LogJsonFormatter;
import org.jdkstack.logging.mini.core.formatter.LogTextFormatter;
import org.jdkstack.logging.mini.core.handler.FileHandlerV2;
import org.jdkstack.logging.mini.core.handler.MmapFileHandlerV2;
import org.jdkstack.logging.mini.core.level.Constants;
import org.jdkstack.logging.mini.core.level.LogType;
import org.jdkstack.logging.mini.core.recorder.LogRecorder;

/**
 * Factory用来扩展功能，Context用来提供业务方法。
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class EventLogRecorderContextFactory implements LogRecorderContextFactory {

  /**
   * 上下文对象，用来初始化，并提供业务方法.
   */
  private final EventLogRecorderContext context = new EventLogRecorderContext();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public EventLogRecorderContextFactory() {
    this.context.setState(LifecycleState.STARTING);
    // 默认配置。
    this.insideConfig();
    this.context.setState(LifecycleState.STARTED);
  }

  @Override
  public final Recorder getRecorder(final String name) {
    // 全限定名配置.
    Recorder recorder = this.context.getRecorder(name);
    // 默认配置.
    if (null == recorder) {
      // 创建默认配置。
      //final RecorderConfig recorderConfig = new LogRecorderConfig();
      // 覆盖名字。
      //recorderConfig.setName(name);
      // 增加新的Recorder.
      //this.addRecorder(recorderConfig);
      //this.context.addLogRecorderConfig(name, recorderConfig);
      // 取出最新添加的Recorder.
      //recorder = this.context.getRecorder(name);
      // 不需要创建，直接获取默认的recorder。
      recorder = this.context.getRecorder("default");
    }
    return recorder;
  }

  @Override
  public final void addRecorder(final RecorderConfig recorderConfig) {
    final Recorder logRecorder = new LogRecorder(this.context, recorderConfig);
    this.context.addRecorder(recorderConfig.getName(), logRecorder);
  }

  @Override
  public final void addHandler(final String key, final Handler value) {
    this.context.addHandler(key, value);
  }

  @Override
  public final void addFilter(final String key, final Filter filter) {
    this.context.addFilter(key, filter);
  }

  @Override
  public final void addFormatter(final String key, final Formatter formatter) {
    this.context.addFormatter(key, formatter);
  }

  @Override
  public final void addLogRecorderConfig(final String key, final RecorderConfig logRecorderConfig) {
    this.context.addLogRecorderConfig(key, logRecorderConfig);
  }

  @Override
  public final void addLogHandlerConfig(final String key, final HandlerConfig logHandlerConfig) {
    this.context.addLogHandlerConfig(key, logHandlerConfig);
  }

  @Override
  public final void addLevel(final String name, final int value) {
    this.context.addLevel(name, value);
  }

  @Override
  public void shutdown() {
    this.context.shutdown();
  }

  @Override
  public Monitor threadMonitor() {
    return this.context.threadMonitor();
  }

  private void insideConfig() {
    // 默认日志级别。
    this.addLevel(Constants.MIN, Constants.MIN_VALUE);
    this.addLevel(Constants.SEVERE, Constants.SEVERE_VALUE);
    this.addLevel(Constants.FATAL, Constants.FATAL_VALUE);
    this.addLevel(Constants.ERROR, Constants.ERROR_VALUE);
    this.addLevel(Constants.WARN, Constants.WARN_VALUE);
    this.addLevel(Constants.INFO, Constants.INFO_VALUE);
    this.addLevel(Constants.DEBUG, Constants.DEBUG_VALUE);
    this.addLevel(Constants.CONFIG, Constants.CONFIG_VALUE);
    this.addLevel(Constants.FINE, Constants.FINE_VALUE);
    this.addLevel(Constants.FINER, Constants.FINER_VALUE);
    this.addLevel(Constants.FINEST, Constants.FINEST_VALUE);
    this.addLevel(Constants.TRACE, Constants.TRACE_VALUE);
    this.addLevel(Constants.MAX, Constants.MAX_VALUE);
    // 默认配置。
    final RecorderConfig recorderConfig = new LogRecorderConfig();
    recorderConfig.setLogTypeName(LogType.APL.getName());
    recorderConfig.setLogTypeValue(LogType.APL.intValue());
    this.addLogRecorderConfig("default", recorderConfig);
    // 默认Recorder。
    this.addRecorder(recorderConfig);
    // 默认配置。
    final HandlerConfig handlerConfig = new LogHandlerConfig();
    handlerConfig.setPrefix("el");
    this.addLogHandlerConfig("default", handlerConfig);
    // 默认FileHandler。
    final Handler fileHandlerV2 = new FileHandlerV2(this.context, "default");
    this.addHandler("default", fileHandlerV2);
    // 默认配置。
    final HandlerConfig mmapRecorderConfig = new LogHandlerConfig();
    mmapRecorderConfig.setName("mmap");
    this.addLogHandlerConfig("mmap", mmapRecorderConfig);
    final Handler mmap = new MmapFileHandlerV2(this.context, "mmap");
    this.addHandler("mmap", mmap);
    // 默认Filter。
    final Filter handlerConsumeFilter = new HandlerConsumeFilter(this.context, "default");
    this.addFilter("handlerConsumeFilter", handlerConsumeFilter);
    // 默认Filter。
    final Filter handlerProduceFilter = new HandlerProduceFilter(this.context, "default");
    this.addFilter("handlerProduceFilter", handlerProduceFilter);
    // 默认Formatter。
    final Formatter logJsonFormatter = new LogJsonFormatter(this.context);
    this.addFormatter("logJsonFormatter", logJsonFormatter);
    final Formatter logTextFormatter = new LogTextFormatter(this.context);
    this.addFormatter("logTextFormatter", logTextFormatter);
  }
}
