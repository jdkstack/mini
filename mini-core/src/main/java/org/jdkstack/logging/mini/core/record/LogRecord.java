package org.jdkstack.logging.mini.core.record;

import java.util.Arrays;
import java.util.LinkedHashMap;
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
  private Map<String, Object> map = new LinkedHashMap<>(64);
  /**
   * 日志级别名.
   */
  private String levelName;
  /**
   * 日志级别值.
   */
  private int levelValue;

  /**
   * 名.
   */
  private String producerThreadName;
  /**
   * 值.
   */
  private int producerThreadValue;

  /**
   * 名.
   */
  private String consumerThreadName;
  /**
   * 值.
   */
  private int consumerThreadValue;
  /**
   * 日志异常.
   */
  private Throwable throwable;

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
  public final StringBuilder getMessageText() {
    return this.messageText;
  }

  @Override
  public final void setMessageText(String message) {
    this.messageText.append(message);
  }

  @Override
  public final void clear() {
    this.levelName = null;
    this.levelValue = 0;
    this.name = null;
    this.messageText.setLength(0);
    this.datetime.setLength(0);
    this.throwable = null;
    Arrays.fill(this.params, null);
    Arrays.fill(this.paths, 0);
    this.map.clear();
    this.producerThreadName = null;
    this.producerThreadValue = 0;
    this.consumerThreadName = null;
    this.consumerThreadValue = 0;
  }

  @Override
  public void setMap(String key, Object value) {
    this.map.put(key, value);
  }

  @Override
  public Map<String, Object> getMap() {
    return this.map;
  }

  @Override
  public String getProducerThreadName() {
    return this.producerThreadName;
  }

  @Override
  public void setProducerThreadName(final String producerThreadName) {
    this.producerThreadName = producerThreadName;
  }

  @Override
  public int getProducerThreadValue() {
    return this.producerThreadValue;
  }

  @Override
  public void setProducerThreadValue(final int producerThreadValue) {
    this.producerThreadValue = producerThreadValue;
  }

  @Override
  public String getConsumerThreadName() {
    return this.consumerThreadName;
  }

  @Override
  public void setConsumerThreadName(final String consumerThreadName) {
    this.consumerThreadName = consumerThreadName;
  }

  @Override
  public int getConsumerThreadValue() {
    return this.consumerThreadValue;
  }

  @Override
  public void setConsumerThreadValue(final int consumerThreadValue) {
    this.consumerThreadValue = consumerThreadValue;
  }
}
