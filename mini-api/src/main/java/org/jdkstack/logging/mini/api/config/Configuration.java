package org.jdkstack.logging.mini.api.config;

import java.nio.Buffer;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.level.Level;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.recorder.Recorder;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Configuration {
  Recorder getRecorder(String name);

  void addRecorder(String name, Recorder recorder);

  Handler getHandler(String name);

  void addHandler(String key, Handler value);

  void addFilter(String key, Filter filter);

  void addFormatter(String key, Formatter formatter);

  void addLogRecorderConfig(String key, RecorderConfig logRecorderConfig);

  RecorderConfig getRecorderConfig(String key);

  void addLogHandlerConfig(String key, HandlerConfig logHandlerConfig);

  HandlerConfig getLogHandlerConfig(String key);

  void addLevel(String name, int value);

  Level findLevel(String name);

  Buffer formatter(String formatterName, Record logRecord);

  boolean filter(String filterName, Record logRecord);
}
