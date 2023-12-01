package org.jdkstack.logging.mini.core.record;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.jdkstack.logging.mini.api.record.Record;

/**
 * 日志记录，最终被格式化成字符串.
 *
 * <p>.
 *
 * @author admin
 */
public class LogRecord implements Record {

  /**
   * 格式化后日志消息.
   */
  private final StringBuilder messageText = new StringBuilder(1024);
  /**
   * 事件发生时的datetime.
   */
  private final StringBuilder datetime = new StringBuilder(100);
  /**
   * 参数列表.
   */
  private final Object[] params = new Object[9];
  /**
   * 参数的位置.
   */
  private final int[] paths = new int[9];
  /**
   * 日志记录器名.
   */
  private String name;
  /**
   * map.
   */
  private Map<String, String> map = new HashMap<>(64);
  /**
   * map.
   */
  private Map<String, String> context = new HashMap<>(64);
  /**
   * 日志级别名.
   */
  private String levelName;
  /**
   * 日志级别值.
   */
  private int levelValue;
  /**
   * 日志异常.
   */
  private Throwable throwable;

  public LogRecord() {
    this.map.put("pid", ProcessHandle.current().pid() + "");
    this.map.put("appName", System.getProperty("appName", ""));
    this.map.put("hostName", System.getProperty("hostName", ""));
    this.map.put("ip", System.getProperty("ip", ""));
    this.map.put("port", System.getProperty("port", ""));
    this.map.put("timeZone", System.getProperty("timeZone", "Z"));
  }

  @Override
  public int getLevelValue() {
    return this.levelValue;
  }

  @Override
  public void setLevelValue(final int levelValue) {
    this.levelValue = levelValue;
  }

  @Override
  public final Object[] getParams() {
    return this.params;
  }

  @Override
  public final void setParams(final Object params, final int index) {
    this.params[index] = params;
  }

  @Override
  public final int[] getPaths() {
    return this.paths;
  }

  @Override
  public final void setPaths(final int path, final int index) {
    this.paths[index] = path;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param thrown .
   * @author admin
   */
  @Override
  public final void setThrown(final Throwable thrown) {
    this.throwable = thrown;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Level .
   * @author admin
   */
  @Override
  public final String getLevelName() {
    return this.levelName;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @author admin
   */
  @Override
  public final void setLevelName(final String logLevel) {
    this.levelName = logLevel;
  }

  @Override
  public final StringBuilder getEvent() {
    return this.datetime;
  }

  @Override
  public final Throwable getThrowable() {
    return this.throwable;
  }

  @Override
  public final Map<String, String> getContext() {
    return this.context;
  }

  @Override
  public final void setContext(String key, String value) {
    this.context.put(key, value);
  }

  @Override
  public final StringBuilder getMessageText() {
    return this.messageText;
  }

  @Override
  public final void setMessageText(String message) {
    this.messageText.append(message);
  }

  @Override
  public final void clear() {
    this.setLevelName(null);
    this.setLevelValue(0);
    this.setName(null);
    this.messageText.setLength(0);
    this.datetime.setLength(0);
    this.context.clear();
    this.setThrown(null);
    Arrays.fill(this.params, null);
    Arrays.fill(this.paths, 0);
  }

  @Override
  public Map<String, String> getMap() {
    return this.map;
  }
}
