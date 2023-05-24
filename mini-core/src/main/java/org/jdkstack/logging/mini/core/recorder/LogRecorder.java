package org.jdkstack.logging.mini.core.recorder;

import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.api.resource.HaFactory;
import org.jdkstack.logging.mini.api.resource.LeFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.formatter.LogFormatter;

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
  /** Recorder类别 . */
  private final String type;
  /** 日志级别处理器 . */
  private final Map<String, String> handlers = new HashMap<>(16);
  /** Recorder可以处理最小的日志级别. */
  private Level minLevel;
  /** Recorder可以处理最大的日志级别. */
  private Level maxLevel;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @param type type.
   * @author admin
   */
  public LogRecorder(final String name, final String type) {
    this.name = name;
    this.type = type;
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
    final LeFactory info = StartApplication.getBean("levelFactory", LeFactory.class);
    return info.doFilter(logLevels, this.maxLevel, this.minLevel);
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
   * @return String .
   * @author admin
   */
  @Override
  public final String getType() {
    return this.type;
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
  public final void core(final String logLevel, final CharBuffer message) {
    this.core(logLevel, null, message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message .
   * @author admin
   */
  public final void core(final String logLevel, final String datetime, final CharBuffer message) {
    // 日志级别是否匹配.
    if (this.doFilter(logLevel)) {
      final HaFactory info = StartApplication.getBean("handlerFactory", HaFactory.class);
      // 日志级别.
      String handler = this.handlers.get(logLevel);
      if (null == handler) {
        // 自己.
        handler = this.handlers.get(this.name);
      }
      Handler handler1 = info.getHandler(handler);
      handler1.process(logLevel, this.name, datetime, message, null);
    }
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
  public final void core(final String logLevel, final CharBuffer message, final Throwable thrown) {
    this.core(logLevel, null, message, thrown);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param datetime .
   * @param message .
   * @author admin
   */
  public final void core(
      final String logLevel,
      final String datetime,
      final CharBuffer message,
      final Throwable thrown) {
    // 日志级别是否匹配.
    if (this.doFilter(logLevel)) {
      final HaFactory info = StartApplication.getBean("handlerFactory", HaFactory.class);
      // 日志级别.
      String handler = this.handlers.get(logLevel);
      if (null == handler) {
        // 自己.
        handler = this.handlers.get(this.name);
      }
      Handler handler1 = info.getHandler(handler);
      handler1.process(logLevel, this.name, datetime, message, thrown);
    }
  }

  @Override
  public final void log(
      final String logLevel, final String datetime, final String message, final Throwable thrown) {
    this.core(logLevel, datetime, LogFormatter.format(message), thrown);
  }

  @Override
  public final void log(final String logLevel, final String message, final Throwable thrown) {
    this.core(logLevel, LogFormatter.format(message), thrown);
  }

  @Override
  public final void log(final String logLevel, final String datetime, final String message) {
    this.core(logLevel, datetime, LogFormatter.format(message));
  }

  @Override
  public final void log(final String logLevel, final String message) {
    this.core(logLevel, LogFormatter.format(message));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final CharBuffer message,
      final Throwable thrown) {
    this.core(logLevel, datetime, message);
  }

  @Override
  public final void log(final String logLevel, final CharBuffer message, final Throwable thrown) {
    this.core(logLevel, message);
  }

  @Override
  public final void log(final String logLevel, final String datetime, final CharBuffer message) {
    this.core(logLevel, datetime, message);
  }

  @Override
  public final void log(final String logLevel, final CharBuffer message) {
    this.core(logLevel, message);
  }
}
