package org.jdkstack.logging.mini.api.context;

import org.jdkstack.logging.mini.api.config.ContextConfiguration;
import org.jdkstack.logging.mini.api.config.HandlerConfig;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.monitor.Monitor;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.recorder.Recorder;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface LogRecorderContext {

  Recorder getRecorder(String name);

  void addRecorder(String name, Recorder recorder);

  Handler getHandler(String name);

  void addHandler(String key, Handler value);

  void addFilter(String key, Filter filter);

  void addFormatter(String key, Formatter formatter);

  void addLogRecorderConfig(String key, RecorderConfig logRecorderConfig);

  RecorderConfig getRecorderConfig(String key);

  void addLogHandlerConfig(String key, HandlerConfig logHandlerConfig);

  HandlerConfig getHandlerConfig(String key);

  void addLevel(String name, int value);

  boolean doFilter(String logLevelName, String maxLevelName, String minLevelName);

  Level findLevel(String name);

  StringBuilder formatter(String formatterName, Record logRecord);

  boolean filter(String filterName, Record logRecord);

  void consume(Record event);

  void produce(StackTraceElement stackTraceElement, String logLevel, String dateTime, String message, String name, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown, Record lr);

  void process(String logLevel, String dateTime, String message, String name, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown);

  void produce(String logLevel, String dateTime, String message, String name, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown, Record lr);

  void process(int index, String logLevel, String dateTime, String message, String name, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8, Object arg9, Throwable thrown);

  void start();

  void shutdown();

  Monitor threadMonitor();

  ContextConfiguration getContextConfiguration();
}
