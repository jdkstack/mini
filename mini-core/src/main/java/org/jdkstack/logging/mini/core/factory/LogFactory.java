package org.jdkstack.logging.mini.core.factory;

import org.jdkstack.logging.mini.api.config.HandlerConfig;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.context.LogRecorderContextFactory;
import org.jdkstack.logging.mini.api.factory.Factory;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.recorder.Recorder;
import org.jdkstack.logging.mini.core.context.DefaultLogRecorderContextFactory;

/**
 * LogFactory核心类.
 *
 * <p>.
 *
 * @author admin
 */
public final class LogFactory implements Factory {

  /**
   * 静态的上下文对象工厂，创建一个上下文对象.
   */
  private static final LogRecorderContextFactory FACTORY = new DefaultLogRecorderContextFactory();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private LogFactory() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name 日志的标志.
   * @return 返回一个Log对象.
   * @author admin
   */
  public static Recorder getRecorder(final String name) {
    return FACTORY.getRecorder(name);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param clazz 使用类的简单名称作为日志的标志.
   * @return 返回一个Log对象.
   * @author admin
   */
  public static Recorder getRecorder(final Class<?> clazz) {
    return FACTORY.getRecorder(clazz.getName());
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param recorderConfig 增加一个recorder.
   * @author admin
   */
  public static void addRecorder(final RecorderConfig recorderConfig) {
    FACTORY.addRecorder(recorderConfig);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key   增加一个handler.
   * @param value 增加一个handler.
   * @author admin
   */
  public static void addHandler(final String key, final Handler value) {
    FACTORY.addHandler(key, value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key    增加一个filter.
   * @param filter 增加一个filter.
   * @author admin
   */
  public static void addFilter(final String key, final Filter filter) {
    FACTORY.addFilter(key, filter);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key       增加一个formatter.
   * @param formatter 增加一个formatter.
   * @author admin
   */
  public static void addFormatter(final String key, final Formatter formatter) {
    FACTORY.addFormatter(key, formatter);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key               增加一个logRecorderConfig.
   * @param logRecorderConfig 增加一个logRecorderConfig.
   * @author admin
   */
  public static void addLogRecorderConfig(final String key, final RecorderConfig logRecorderConfig) {
    FACTORY.addLogRecorderConfig(key, logRecorderConfig);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key              增加一个logHandlerConfig.
   * @param logHandlerConfig 增加一个logHandlerConfig.
   * @author admin
   */
  public static void addLogHandlerConfig(final String key, final HandlerConfig logHandlerConfig) {
    FACTORY.addLogHandlerConfig(key, logHandlerConfig);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name  增加一个Level.
   * @param value 增加一个Level.
   * @author admin
   */
  public static void addLevel(final String name, final int value) {
    FACTORY.addLevel(name, value);
  }

  public static void shutdown() {
    FACTORY.shutdown();
  }
}
