package org.jdkstack.logging.mini.api.context;

import java.nio.Buffer;
import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.recorder.Recorder;

public interface LogRecorderContext {
  Recorder getRecorder(String name);

  void addRecorder(String name, Recorder recorder);

  Handler getHandler(String name);

  void addHandler(String key, Handler value);

  void addFilter(String key, Filter filter);

  void addFormatter(String key, Formatter formatter);

  void addLogRecorderConfig(String key, RecorderConfig logRecorderConfig);

  RecorderConfig getValue(String key);

  void addLevel(String name, int value);

  boolean doFilter(
          String logLevels, Level maxLevel, Level minLevel);

  Level findLevel(String name);

  Buffer formatter(String formatterName, Record logRecord);

  boolean filter(String filterName, Record logRecord);

  void consume();

  void produce(
      String logLevel,
      String dateTime,
      String message,
      String className,
      Object arg1,
      Object arg2,
      Object arg3,
      Object arg4,
      Object arg5,
      Object arg6,
      Object arg7,
      Object arg8,
      Object arg9,
      Throwable thrown);
  //
}
