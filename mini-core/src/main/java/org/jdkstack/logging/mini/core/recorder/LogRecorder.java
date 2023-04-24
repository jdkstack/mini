package org.jdkstack.logging.mini.core.recorder;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.bean.api.bean.Bean;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.api.resource.HaFactory;
import org.jdkstack.logging.mini.api.resource.LeFactory;
import org.jdkstack.logging.mini.core.StartApplication;
import org.jdkstack.logging.mini.core.formatter.LogFormatter;
import org.jdkstack.logging.mini.core.pool.StringBuilderPool;

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
  /** Recorder可以处理最小的日志级别. */
  private Level minLevel;
  /** Recorder可以处理最大的日志级别. */
  private Level maxLevel;
  /** 日志级别处理器 . */
  private final Map<String, String> handlers = new HashMap<>(16);

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
    final Bean logInfos = StartApplication.context().getBean("levelFactory");
    final Object obj3 = logInfos.getObj();
    final LeFactory info = (LeFactory) obj3;
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
   * @param className .
   * @param classMethod .
   * @param lineNumber .
   * @param message .
   * @author admin
   */
  public final void core(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    // 如果日志级别匹配.
    if (this.doFilter(logLevel)) {
      final Bean logInfos = StartApplication.context().getBean("handlerFactory");
      final Object obj3 = logInfos.getObj();
      final HaFactory info = (HaFactory) obj3;
      // 日志级别.
      String handler = this.handlers.get(logLevel);
      if (null == handler) {
        // 自己.
        handler = this.handlers.get(this.name);
      }
      info.execute(handler, logLevel, className, classMethod, lineNumber, message);
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param className .
   * @param datetime .
   * @param classMethod .
   * @param lineNumber .
   * @param message .
   * @author admin
   */
  public final void core(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final StringBuilder message) {
    // 如果日志级别匹配.
    if (this.doFilter(logLevel)) {
      final Bean logInfos = StartApplication.context().getBean("handlerFactory");
      final Object obj3 = logInfos.getObj();
      final HaFactory info = (HaFactory) obj3;
      // 日志级别.
      String handler = this.handlers.get(logLevel);
      if (null == handler) {
        // 自己.
        handler = this.handlers.get(this.name);
      }
      info.execute(handler, logLevel, datetime, className, classMethod, lineNumber, message);
    }
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message) {
    final StringBuilder sb = StringBuilderPool.poll();
    this.core(logLevel, datetime, className, classMethod, lineNumber, sb.append(message));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4, args5));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6, args7));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6, args7, args8));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8,
      final StringBuilder args9) {
    this.core(
        logLevel,
        datetime,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(
            message, args1, args2, args3, args4, args5, args6, args7, args8, args9));
  }

  @Override
  public final void log(final String logLevel, final String datetime, final String message) {
    final StringBuilder sb = StringBuilderPool.poll();
    this.core(logLevel, datetime, "", "", 0, sb.append(message));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1) {
    this.core(logLevel, datetime, "", "", 0, LogFormatter.format(message, args1));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2) {
    this.core(logLevel, datetime, "", "", 0, LogFormatter.format(message, args1, args2));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3) {
    this.core(logLevel, datetime, "", "", 0, LogFormatter.format(message, args1, args2, args3));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4) {
    this.core(
        logLevel, datetime, "", "", 0, LogFormatter.format(message, args1, args2, args3, args4));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5) {
    this.core(
        logLevel,
        datetime,
        "",
        "",
        0,
        LogFormatter.format(message, args1, args2, args3, args4, args5));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6) {
    this.core(
        logLevel,
        datetime,
        "",
        "",
        0,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7) {
    this.core(
        logLevel,
        datetime,
        "",
        "",
        0,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6, args7));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8) {
    this.core(
        logLevel,
        datetime,
        "",
        "",
        0,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6, args7, args8));
  }

  @Override
  public final void log(
      final String logLevel,
      final String datetime,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8,
      final StringBuilder args9) {
    this.core(
        logLevel,
        datetime,
        "",
        "",
        0,
        LogFormatter.format(
            message, args1, args2, args3, args4, args5, args6, args7, args8, args9));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message) {
    final StringBuilder sb = StringBuilderPool.poll();
    this.core(logLevel, className, classMethod, lineNumber, sb.append(message));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1) {
    this.core(logLevel, className, classMethod, lineNumber, LogFormatter.format(message, args1));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2) {
    this.core(
        logLevel, className, classMethod, lineNumber, LogFormatter.format(message, args1, args2));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3) {
    this.core(
        logLevel,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4) {
    this.core(
        logLevel,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5) {
    this.core(
        logLevel,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4, args5));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6) {
    this.core(
        logLevel,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7) {
    this.core(
        logLevel,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6, args7));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8) {
    this.core(
        logLevel,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6, args7, args8));
  }

  @Override
  public final void log(
      final String logLevel,
      final String className,
      final String classMethod,
      final int lineNumber,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8,
      final StringBuilder args9) {
    this.core(
        logLevel,
        className,
        classMethod,
        lineNumber,
        LogFormatter.format(
            message, args1, args2, args3, args4, args5, args6, args7, args8, args9));
  }

  @Override
  public final void log(final String logLevel, final String message) {
    final StringBuilder sb = StringBuilderPool.poll();
    this.core(logLevel, "", "", 0, sb.append(message));
  }

  @Override
  public final void log(final String logLevel, final String message, final StringBuilder args1) {
    this.core(logLevel, "", "", 0, LogFormatter.format(message, args1));
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2) {
    this.core(logLevel, "", "", 0, LogFormatter.format(message, args1, args2));
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3) {
    this.core(logLevel, "", "", 0, LogFormatter.format(message, args1, args2, args3));
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4) {
    this.core(logLevel, "", "", 0, LogFormatter.format(message, args1, args2, args3, args4));
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5) {
    this.core(logLevel, "", "", 0, LogFormatter.format(message, args1, args2, args3, args4, args5));
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6) {
    this.core(
        logLevel,
        "",
        "",
        0,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6));
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7) {
    this.core(
        logLevel,
        "",
        "",
        0,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6, args7));
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8) {
    this.core(
        logLevel,
        "",
        "",
        0,
        LogFormatter.format(message, args1, args2, args3, args4, args5, args6, args7, args8));
  }

  @Override
  public final void log(
      final String logLevel,
      final String message,
      final StringBuilder args1,
      final StringBuilder args2,
      final StringBuilder args3,
      final StringBuilder args4,
      final StringBuilder args5,
      final StringBuilder args6,
      final StringBuilder args7,
      final StringBuilder args8,
      final StringBuilder args9) {
    this.core(
        logLevel,
        "",
        "",
        0,
        LogFormatter.format(
            message, args1, args2, args3, args4, args5, args6, args7, args8, args9));
  }
}
