package org.jdkstack.logging.mini.core.context;

import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.context.LogRecorderContextFactory;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.config.LogRecorderConfig;
import org.jdkstack.logging.mini.core.filter.LogFilter;
import org.jdkstack.logging.mini.core.formatter.LogJsonFormatter;
import org.jdkstack.logging.mini.core.formatter.LogTextFormatter;
import org.jdkstack.logging.mini.core.handler.FileHandlerV2;
import org.jdkstack.logging.mini.core.level.Constants;
import org.jdkstack.logging.mini.core.recorder.LogRecorder;

public class AsyncLogRecorderContextFactory implements LogRecorderContextFactory {
  /** 上下文对象，用来初始化，并提供业务方法. */
  private final LogRecorderContext context = new AsyncLogRecorderContext();

  public AsyncLogRecorderContextFactory() {
    final LogRecorderConfig recorderConfig = new LogRecorderConfig();
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
    this.addLogRecorderConfig("default", recorderConfig);
    // 默认Filter。
    final Filter filter = new LogFilter(this.context);
    this.addFilter("logFilter", filter);
    // 默认Formatter。
    final Formatter logJsonFormatter = new LogJsonFormatter(this.context);
    this.addFormatter("logJsonFormatter", logJsonFormatter);
    final Formatter logTextFormatter = new LogTextFormatter();
    this.addFormatter("logTextFormatter", logTextFormatter);
    // 默认FileHandler。
    final Handler fileHandlerV2 = new FileHandlerV2(this.context, "default");
    this.addHandler("default", fileHandlerV2);
    // 默认Recorder。
    this.addRecorder(recorderConfig);
  }

  @Override
  public final Recorder getRecorder(final String name) {
    // 全限定名配置.
    Recorder recorder = this.context.getRecorder(name);
    // 默认配置.
    if (null == recorder) {
      final Recorder root =
          this.context.getRecorder(org.jdkstack.logging.mini.core.context.Constants.DEFAULT);
      // 创建一个新的Recorder.
      recorder = new LogRecorder(this.context, name);
      // 将ROOT的所有配置给新的Recorder.
      recorder.addHandlers(name, root.getHandler(root.getName()));
      recorder.setMinLevel(root.getMinLevel());
      recorder.setMaxLevel(root.getMaxLevel());
      // 增加新的Recorder.
      this.context.addRecorder(name, recorder);
    }
    return recorder;
  }

  @Override
  public final void addRecorder(final RecorderConfig recorderConfig) {
    // 设置日志处理器, 如果没有配置,不会继承父,直接使用ROOT.
    final String handlers = recorderConfig.getHandlers();
    final Recorder logRecorder = new LogRecorder(this.context, recorderConfig.getName());
    final String[] splits = handlers.split(",");
    for (final String split : splits) {
      final String[] split1 = split.split(":");
      if (org.jdkstack.logging.mini.core.context.Constants.N2 == split1.length) {
        final String levelName = split1[0];
        final String handlerName = split1[1];
        logRecorder.addHandlers(levelName, handlerName);
      } else {
        logRecorder.addHandlers(recorderConfig.getName(), split);
      }
    }
    // 设置日志级别.  // 如果没有配置,不会继承父,直接使用ROOT.
    final String minLevel = recorderConfig.getMinLevel();
    // 设置日志级别.  // 如果没有配置,不会继承父,直接使用ROOT.
    final String maxLevel = recorderConfig.getMaxLevel();
    logRecorder.setMinLevel(this.context.findLevel(minLevel));
    logRecorder.setMaxLevel(this.context.findLevel(maxLevel));
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
  public final void addLevel(final String name, final int value) {
    this.context.addLevel(name, value);
  }
}
