package org.jdkstack.logging.mini.core.record;

import java.nio.CharBuffer;
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

  /** 日志级别. */
  private String logLevel;
  /** 日志消息. */
  private CharBuffer message = CharBuffer.allocate(2048);
  /** 事件发生时的datetime. */
  private String event;
  /** 接收事件时的datetime. */
  private String ingestion;
  /** 处理事件时的datetime. */
  private String process;
  /** . */
  private String offset;
  /** 日志类. */
  private String className;
  /** 日志异常. */
  private Throwable throwable;
  /** map. */
  private Map<String, String> map = new HashMap<>(16);
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
  public final CharBuffer getMessage() {
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
  public final void setMessage(final CharBuffer message) {
    if (message.length() == 0) {
      System.out.println(message.limit());
      System.out.println(message.position());
      System.out.println("0:" + new String(message.array()));
    }
    if (message.length() == 8192) {
      System.out.println(message.limit());
      System.out.println(message.position());
      System.out.println("8192:" + new String(message.array()));
    }
    synchronized (this) {
      this.message.clear();
      this.message.put(message);
      this.message.flip();
    }
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

  @Override
  public String getLogLevel() {
    return this.logLevel;
  }

  @Override
  public void setLogLevel(final String logLevel) {
    this.logLevel = logLevel;
  }

  @Override
  public String getEvent() {
    return this.event;
  }

  @Override
  public void setEvent(final String event) {
    this.event = event;
  }

  @Override
  public String getIngestion() {
    return this.ingestion;
  }

  @Override
  public void setIngestion(final String ingestion) {
    this.ingestion = ingestion;
  }

  @Override
  public Throwable getThrowable() {
    return this.throwable;
  }

  @Override
  public Map<String, String> getMap() {
    return this.map;
  }

  @Override
  public void setMap(final Map<String, String> map) {
    this.map = map;
  }

  @Override
  public String getProcess() {
    return this.process;
  }

  @Override
  public void setProcess(final String process) {
    this.process = process;
  }
}
