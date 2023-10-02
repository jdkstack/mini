package org.jdkstack.logging.mini.core.config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jdkstack.logging.mini.api.config.Configuration;
import org.jdkstack.logging.mini.api.filter.Filter;
import org.jdkstack.logging.mini.api.formatter.Formatter;
import org.jdkstack.logging.mini.api.handler.Handler;
import org.jdkstack.logging.mini.api.recorder.Recorder;

public class LogRecorderConfiguration extends AbstractConfiguration implements Configuration {
  private final Lock configLock = new ReentrantLock();
  // 配置
  private final ConcurrentMap<String, Handler> handlers = new ConcurrentHashMap<>();
  // 配置
  private final ConcurrentMap<String, Filter> filters = new ConcurrentHashMap<>();
  // 配置
  private final ConcurrentMap<String, Formatter> formatters = new ConcurrentHashMap<>();
  // 配置
  private final ConcurrentMap<String, Recorder> recorders = new ConcurrentHashMap<>();
  // 配置公告属性。
  private final ConcurrentMap<String, Property> properties = new ConcurrentHashMap<>();
  // 配置环境变量。
  private final ConcurrentMap<String, Env> envs = new ConcurrentHashMap<>();
}
