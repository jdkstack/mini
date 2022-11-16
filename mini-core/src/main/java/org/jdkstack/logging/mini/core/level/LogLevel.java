package org.jdkstack.logging.mini.core.level;

import org.jdkstack.logging.mini.api.level.Level;

/**
 * 日志级别.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class LogLevel implements Level {

  /** 日志名. */
  private final String name;
  /** 日志级别. */
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
  public LogLevel(final String name, final int value) {
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
  @Override
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
  @Override
  public int intValue() {
    return this.value;
  }
}
