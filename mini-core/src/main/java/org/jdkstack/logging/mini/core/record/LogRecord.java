package org.jdkstack.logging.mini.core.record;

import org.jdkstack.logging.mini.api.record.Record;

/**
 * 日志记录，最终被格式化成字符串.
 *
 * <p>.
 *
 * @author admin
 */
public class LogRecord implements Record {

  /** . */
  private String logLevel;
  /** . */
  private StringBuilder message;
  /** . */
  private Throwable thrown;
  /** 事件发生时间event(传入日志方法).  事件进入时间ingestion(日志方法系统时间). */
  private StringBuilder sb;
  /** . */
  private long year;
  /** . */
  private long month;
  /** . */
  private long day;
  /** . */
  private long hours;
  /** . */
  private long minute;
  /** . */
  private long second;
  /** . */
  private long mills;
  /** . */
  private String offset;
  /** . */
  private String className;
  /** . */
  private String classMethod;
  /** . */
  private int lineNumber;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public LogRecord() {
    //
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
  public final StringBuilder getMessage() {
    return this.message;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @author admin
   */
  @Override
  public final void setMessage(final StringBuilder message) {
    this.message = message;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Throwable .
   * @author admin
   */
  @Override
  public final Throwable getThrown() {
    return this.thrown;
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
    this.thrown = thrown;
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
  public final String getLevel() {
    return this.logLevel;
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
  public final void setLevel(final String logLevel) {
    this.logLevel = logLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String 日志级别名.
   * @author admin
   */
  @Override
  public final String getLevelName() {
    return this.logLevel;
  }

  @Override
  public final String getClassName() {
    return this.className;
  }

  @Override
  public final void setClassName(final String className) {
    this.className = className;
  }

  @Override
  public final String getClassMethod() {
    return this.classMethod;
  }

  @Override
  public final void setClassMethod(final String classMethod) {
    this.classMethod = classMethod;
  }

  @Override
  public final int getLineNumber() {
    return this.lineNumber;
  }

  @Override
  public final void setLineNumber(final int lineNumber) {
    this.lineNumber = lineNumber;
  }

  @Override
  public final StringBuilder getSb() {
    return this.sb;
  }

  @Override
  public final void setSb(final StringBuilder sb) {
    this.sb = sb;
  }

  @Override
  public final long getYear() {
    return this.year;
  }

  @Override
  public final void setYear(final long year) {
    this.year = year;
  }

  @Override
  public final long getMonth() {
    return this.month;
  }

  @Override
  public final void setMonth(final long month) {
    this.month = month;
  }

  @Override
  public final long getDay() {
    return this.day;
  }

  @Override
  public final void setDay(final long day) {
    this.day = day;
  }

  @Override
  public final long getHours() {
    return this.hours;
  }

  @Override
  public final void setHours(final long hours) {
    this.hours = hours;
  }

  @Override
  public final long getMinute() {
    return this.minute;
  }

  @Override
  public final void setMinute(final long minute) {
    this.minute = minute;
  }

  @Override
  public final long getSecond() {
    return this.second;
  }

  @Override
  public final void setSecond(final long second) {
    this.second = second;
  }

  @Override
  public final long getMills() {
    return this.mills;
  }

  @Override
  public final void setMills(final long mills) {
    this.mills = mills;
  }

  @Override
  public final String getOffset() {
    return this.offset;
  }

  @Override
  public final void setOffset(final String offset) {
    this.offset = offset;
  }
}
