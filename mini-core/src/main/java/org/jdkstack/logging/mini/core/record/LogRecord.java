package org.jdkstack.logging.mini.core.record;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.logging.mini.api.record.Record;
import org.jdkstack.logging.mini.api.recorder.Recorder;

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
  private final StringBuilder event = new StringBuilder(100);

  /** 接收事件时的datetime. */
  private String ingestion;

  /** 处理事件时的datetime. */
  private String process;

  /** . */
  private String offset;

  /** 日志类. */
  private String name;

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

  private final Object[] params = new Object[9];

  private final int[] paths = new int[9];
  private int argsCount;
  private int usedCount;
  private int placeholderCount;

  private Recorder recorder;

  private final StringBuilder messageText = new StringBuilder(1024);

  /** . */
  private final StringBuilder pid = new StringBuilder(16);

  /** . */
  private final String appName = System.getProperty("appName", "app");

  /** . */
  private final String hostName = System.getProperty("hostName", "localhost");

  /** . */
  private final String ip = System.getProperty("ip", "127.0.0.1");

  /** . */
  private final String port = System.getProperty("port", "0000");

  /** . */
  private final String timeZone = System.getProperty("timeZone", "Z");

  public LogRecord() {
    this.pid.append(ProcessHandle.current().pid());
  }

  @Override
  public final Recorder getRecorder() {
    return this.recorder;
  }

  @Override
  public final void setRecorder(final Recorder recorder) {
    this.recorder = recorder;
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
  public final int getArgsCount() {
    return this.argsCount;
  }

  @Override
  public final void setArgsCount(final int argsCount) {
    this.argsCount = argsCount;
  }

  @Override
  public final int getUsedCount() {
    return this.usedCount;
  }

  @Override
  public final void setUsedCount(final int usedCount) {
    this.usedCount = usedCount;
  }

  @Override
  public final int getPlaceholderCount() {
    return this.placeholderCount;
  }

  @Override
  public final void setPlaceholderCount(final int placeholderCount) {
    this.placeholderCount = placeholderCount;
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
    this.message = message;
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
  public final String getLogLevel() {
    return this.logLevel;
  }

  @Override
  public final void setLogLevel(final String logLevel) {
    this.logLevel = logLevel;
  }

  @Override
  public final StringBuilder getEvent() {
    return this.event;
  }

  @Override
  public final void setEvent(final String event) {
    this.event.append(event);
  }

  @Override
  public final String getIngestion() {
    return this.ingestion;
  }

  @Override
  public final void setIngestion(final String ingestion) {
    this.ingestion = ingestion;
  }

  @Override
  public final Throwable getThrowable() {
    return this.throwable;
  }

  @Override
  public final Map<String, String> getMap() {
    return this.map;
  }

  @Override
  public final void setMap(final Map<String, String> map) {
    this.map = map;
  }

  @Override
  public final String getProcess() {
    return this.process;
  }

  @Override
  public final void setProcess(final String process) {
    this.process = process;
  }

  @Override
  public final Object getArgs1() {
    return this.args1;
  }

  @Override
  public final void setArgs1(final Object args1) {
    this.args1 = args1;
  }

  @Override
  public final Object getArgs2() {
    return this.args2;
  }

  @Override
  public final void setArgs2(final Object args2) {
    this.args2 = args2;
  }

  @Override
  public final Object getArgs3() {
    return this.args3;
  }

  @Override
  public final void setArgs3(final Object args3) {
    this.args3 = args3;
  }

  @Override
  public final Object getArgs4() {
    return this.args4;
  }

  @Override
  public final void setArgs4(final Object args4) {
    this.args4 = args4;
  }

  @Override
  public final Object getArgs5() {
    return this.args5;
  }

  @Override
  public final void setArgs5(final Object args5) {
    this.args5 = args5;
  }

  @Override
  public final Object getArgs6() {
    return this.args6;
  }

  @Override
  public final void setArgs6(final Object args6) {
    this.args6 = args6;
  }

  @Override
  public final Object getArgs7() {
    return this.args7;
  }

  @Override
  public final void setArgs7(final Object args7) {
    this.args7 = args7;
  }

  @Override
  public final Object getArgs8() {
    return this.args8;
  }

  @Override
  public final void setArgs8(final Object args8) {
    this.args8 = args8;
  }

  @Override
  public final Object getArgs9() {
    return this.args9;
  }

  @Override
  public final void setArgs9(final Object args9) {
    this.args9 = args9;
  }

  @Override
  public final StringBuilder getMessageText() {
    return this.messageText;
  }

  @Override
  public final void clear() {
    this.messageText.setLength(0);
    this.event.setLength(0);
    this.map.clear();
  }

  @Override
  public final StringBuilder getPid() {
    return this.pid;
  }

  @Override
  public final String getAppName() {
    return this.appName;
  }

  @Override
  public final String getHostName() {
    return this.hostName;
  }

  @Override
  public final String getIp() {
    return this.ip;
  }

  @Override
  public final String getPort() {
    return this.port;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(final String name) {
    this.name = name;
  }
}
