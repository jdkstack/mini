package org.jdkstack.logging.mini.core.config;

import java.nio.Buffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jdkstack.logging.mini.api.config.Configuration;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.level.Constants;
import org.jdkstack.logging.mini.core.level.LogLevel;

public class LogRecorderConfiguration extends AbstractConfiguration implements Configuration {

  private final Lock configLock = new ReentrantLock();

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

  // 配置公告属性。
  private final ConcurrentMap<String, Property> properties = new ConcurrentHashMap<>();
  // 配置环境变量。
  private final ConcurrentMap<String, Env> envs = new ConcurrentHashMap<>();

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
    Handler handler = this.handlers.get(name);
    if (handler == null) {
      handler = this.handlers.get("default");
    }
    return handler;
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
  public final RecorderConfig getRecorderConfig(final String key) {
    // 获取
    RecorderConfig value = this.logRecorderConfigs.get(key);
    if (value == null) {
      value = this.logRecorderConfigs.get("default");
    }
    return value;
  }

  @Override
  public final void addLevel(final String name, final int value) {
    final Level level = new LogLevel(name, value);
    this.levels.put(name, level);
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
    Formatter formatter = this.formatters.get(formatterName);
    if (formatter == null) {
      formatter = this.formatters.get("default");
    }
    return formatter.format(logRecord);
  }

  @Override
  public final boolean filter(final String filterName, final Record logRecord) {
    Filter filter = this.filters.get(filterName);
    if (filter == null) {
      filter = this.filters.get("default");
    }
    return filter.doFilter(logRecord);
  }
}
