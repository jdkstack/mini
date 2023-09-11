package org.jdkstack.logging.mini.core.record;

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
  private String message;
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

  private Object args1;
  private Object args2;
  private Object args3;
  private Object args4;
  private Object args5;
  private Object args6;
  private Object args7;
  private Object args8;
  private Object args9;

  private Object[] params = new Object[9];
  private int[] paths = new int[9];
  private int argsCount;
  private int usedCount;
  private int placeholderCount;

  @Override
  public Object[] getParams() {
    return this.params;
  }
  @Override
  public void setParams(final Object params, int index) {
    this.params[index] = params;
  }
  @Override
  public int[] getPaths() {
    return this.paths;
  }
  @Override
  public void setPaths(final int path, int index) {
    this.paths[index] = path;
  }
  @Override
  public int getArgsCount() {
    return this.argsCount;
  }
  @Override
  public void setArgsCount(final int argsCount) {
    this.argsCount = argsCount;
  }
  @Override
  public int getUsedCount() {
    return this.usedCount;
  }
  @Override
  public void setUsedCount(final int usedCount) {
    this.usedCount = usedCount;
  }
  @Override
  public int getPlaceholderCount() {
    return this.placeholderCount;
  }
  @Override
  public void setPlaceholderCount(final int placeholderCount) {
    this.placeholderCount = placeholderCount;
  }

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
  public final String getMessage() {
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
  public final void setMessage(final String message) {
    this.message=message;
    //.clear();
   // this.message.put(message);
   // this.message.flip();
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
  @Override
  public Object getArgs1() {
    return this.args1;
  }
  @Override
  public void setArgs1(final Object args1) {
    this.args1 = args1;
  }
  @Override
  public Object getArgs2() {
    return this.args2;
  }
  @Override
  public void setArgs2(final Object args2) {
    this.args2 = args2;
  }
  @Override
  public Object getArgs3() {
    return this.args3;
  }
  @Override
  public void setArgs3(final Object args3) {
    this.args3 = args3;
  }
  @Override
  public Object getArgs4() {
    return this.args4;
  }
  @Override
  public void setArgs4(final Object args4) {
    this.args4 = args4;
  }
  @Override
  public Object getArgs5() {
    return this.args5;
  }
  @Override
  public void setArgs5(final Object args5) {
    this.args5 = args5;
  }
  @Override
  public Object getArgs6() {
    return this.args6;
  }
  @Override
  public void setArgs6(final Object args6) {
    this.args6 = args6;
  }
  @Override
  public Object getArgs7() {
    return this.args7;
  }
  @Override
  public void setArgs7(final Object args7) {
    this.args7 = args7;
  }
  @Override
  public Object getArgs8() {
    return this.args8;
  }
  @Override
  public void setArgs8(final Object args8) {
    this.args8 = args8;
  }
  @Override
  public Object getArgs9() {
    return this.args9;
  }
  @Override
  public void setArgs9(final Object args9) {
    this.args9 = args9;
  }
}
