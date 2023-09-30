package org.jdkstack.logging.mini.core.recorder;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContext;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.recorder.Recorder;

/**
 * 提供所有日志的方法.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class LogRecorder implements Recorder {
  
  /** Recorder名字. */
  private final String name;

  /** 日志级别处理器 . */
  private final Map<String, String> handlers = new HashMap<>(16);

  /** Recorder可以处理最小的日志级别. */
  private Level minLevel;

  /** Recorder可以处理最大的日志级别. */
  private Level maxLevel;

  /** 一个LogRecorder有一个LogRecorderConfig配置. 有界数组阻塞队列. */
  private RecorderConfig recorderConfig;

  private final LogRecorderContext context;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @author admin
   */
  public LogRecorder(final LogRecorderContext context, final String name) {
    this.context = context;
    this.name = name;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevels .
   * @return boolean .
   * @author admin
   */
  @Override
  public final boolean doFilter(final String logLevels) {
    return this.context.doFilter(logLevels, this.maxLevel, this.minLevel);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getName() {
    return this.name;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param handler .
   * @author admin
   */
  @Override
  public final void removeHandler(final String handler) {
    this.handlers.remove(handler);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param handler .
   * @author admin
   */
  @Override
  public final void addHandlers(final String key, final String handler) {
    this.handlers.put(key, handler);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param key .
   * @author admin
   */
  @Override
  public final String getHandler(final String key) {
    return this.handlers.get(key);
  }

  @Override
  public final Level getMinLevel() {
    return this.minLevel;
  }

  @Override
  public final void setMinLevel(final Level minLevel) {
    this.minLevel = minLevel;
  }

  @Override
  public final Level getMaxLevel() {
    return this.maxLevel;
  }

  @Override
  public final void setMaxLevel(final Level maxLevel) {
    this.maxLevel = maxLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message .
   * @author admin
   */
  public final void core(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Object arg9) {
    this.core(logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param dateTime .
   * @param message .
   * @author admin
   */
  public final void core(
      final String logLevel,
      final String dateTime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Object arg9) {
    this.process(
        logLevel, this.name, dateTime, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
        arg9, null);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message .
   * @author admin
   */
  public final void core(
      final String logLevel,
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
    this.core(
        logLevel, null, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, thrown);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param message .
   * @author admin
   */
  public final void core(
      final String logLevel,
      final String dateTime,
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
    this.process(
        logLevel, this.name, dateTime, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
        arg9, thrown);
  }

  @Override
  public void log(
      final String logLevel, final String datetime, final String message, final Throwable thrown) {
    //
  }

  @Override
  public final void log(final String logLevel, final String message) {
    this.core(logLevel, message, null, null, null, null, null, null, null, null, null);
  }

  @Override
  public final void log(final String logLevel, final String message, final Object arg1) {
    this.core(logLevel, message, arg1, null, null, null, null, null, null, null, null);
  }

  @Override
  public final void log(
      final String logLevel, final String message, final Object arg1, final Object arg2) {
    this.core(logLevel, message, arg1, arg2, null, null, null, null, null, null, null);
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3) {
    this.core(logLevel, message, arg1, arg2, arg3, null, null, null, null, null, null);
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, null, null, null, null, null);
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, null, null, null, null);
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, arg6, null, null, null);
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, null, null);
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, null);
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Object arg9) {
    this.core(logLevel, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }

  @Override
  public final void log(final String logLevel, final String message, final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel, final String message, final Object arg1, final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
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
    //
  }

  @Override
  public final void log(final String logLevel, final String datetime, final String message) {
    //
  }

  @Override
  public final void log(
      final String logLevel, final String datetime, final String message, final Object arg1) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Object arg8) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
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
      final Object arg9) {
    this.core(logLevel, datetime, message, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Throwable thrown) {
    //
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final Object arg1,
      final Object arg2,
      final Object arg3,
      final Object arg4,
      final Object arg5,
      final Object arg6,
      final Object arg7,
      final Throwable thrown) {
    //
  }

  @Override
  public final void process(
      final String logLevel,
      final String className,
      final String dateTime,
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
    // 日志级别是否匹配.
    if (this.doFilter(logLevel)) {
      // 生产.
      this.context.produce(
          logLevel, dateTime, message, className, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8,
          arg9, null);
      // 消费.
      this.context.consume();
    }
  }
}
