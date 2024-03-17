package org.jdkstack.logging.mini.core.level;

/**
 * 日志级别.
 *
 * <p>An event log is a chronologically ordered list of the recorded events.
 * <p>In computer systems, an event log captures information about both hardware and software events.
 * <p>These event logs can be part of the operating system or specific to an application.
 * <p>Event logging provides system administrators with information useful for diagnostics and auditing.
 * <pre>
 *   事件日志Event logs包括：application logs，system logs。
 *   应用日志application logs包括：diagnostic logs ，audit logs。
 *   系统日志system logs包括：diagnostic logs ，audit logs。
 * </pre>
 *
 * @author admin
 */
public enum EventType {

  SE("System Software Events", 0), APE("Application Software Events", 1);
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
  EventType(final String name, final int value) {
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
