package org.jdkstack.logging.mini.core.level;

/**
 * 日志级别.
 *
 * <p>Another description after blank line.
 * <pre>
 *   事件日志Event logs包括：application logs（diagnostic logs ，audit logs）。
 * </pre>
 *
 * @author admin
 */
public enum LogType {

  EL("event logs", -2), APL("application logs", -1), DL("diagnostic logs", 0), AL("audit logs", 1);
  /**
   * 日志名.
   */
  private final String name;
  /**
   * 日志级别.
   */
  private final int value;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name  日志级别的名称.
   * @param value 日志级别的值.
   * @author admin
   */
  LogType(final String name, final int value) {
    this.name = name;
    this.value = value;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  public String getName() {
    return this.name;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int .
   * @author admin
   */
  public int intValue() {
    return this.value;
  }
}
