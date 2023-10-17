package org.jdkstack.logging.mini.api.context;

import org.jdkstack.logging.mini.api.config.RecorderConfig;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.recorder.Recorder;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface LogRecorderContextFactory {
  Recorder getRecorder(String name);

  void addRecorder(RecorderConfig recorderConfig);

  void addHandler(String key, Handler value);

  void addFilter(String key, Filter filter);

  void addFormatter(String key, Formatter formatter);

  void addLogRecorderConfig(String key, RecorderConfig logRecorderConfig);

  void addLevel(String name, int value);
  //
}
